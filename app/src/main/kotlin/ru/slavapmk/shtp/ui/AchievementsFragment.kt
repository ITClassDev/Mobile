package ru.slavapmk.shtp.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.Values
import ru.slavapmk.shtp.databinding.FragmentAchievementsBinding
import ru.slavapmk.shtp.io.dto.achievements.Achievement
import ru.slavapmk.shtp.io.dto.achievements.AchievementPut
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class AchievementsFragment : Fragment() {
    private lateinit var binding: FragmentAchievementsBinding

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAchievementsBinding.inflate(inflater)
        val data = ArrayList<Achievement>()
        Values.achievements.system?.let {
            data.addAll(it)
        }
        Values.achievements.base?.let {
            data.addAll(it)
        }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.US)
        data.sortBy {
            dateFormat.parse(it.received_at)?.time
        }
        data.reverse()
        val dividerItemDecoration =
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.divider_20
            )!!
        )
        binding.textView42.addItemDecoration(dividerItemDecoration)
        binding.textView42.adapter = AchievementsAdapter(data)
        binding.textView42.layoutManager = LinearLayoutManager(context)

        binding.addGroupButton.setOnClickListener {
            binding.frame.visibility = View.VISIBLE
        }

        var uri: Uri? = null

        val file = File(
            requireActivity().cacheDir,
            "${System.currentTimeMillis()}.png"
        ).apply {
            createNewFile()
            deleteOnExit()
        }
        val cacheUri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().packageName + ".provider",
            file
        )
        val takePhotoRegistration =
            registerForActivityResult(ActivityResultContracts.TakePicture()) {
                uri = cacheUri
            }
        val getPhotoRegistration =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
                uri = it!!
            }
        binding.photo.setOnClickListener {
            takePhotoRegistration.launch(cacheUri)
        }
        binding.gallary.setOnClickListener {
            getPhotoRegistration.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.applyAddGroupButton.setOnClickListener {
            uri?.let {
                val mime = requireContext().contentResolver.getType(uri!!)!!
                val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mime)
                requireActivity().contentResolver.openInputStream(uri!!)!!.use {
                    Values.api.putAchievement(
                        Values.token,
                        AchievementPut(
                            binding.descriptionMe.editText?.text.toString(),
                            binding.titttle.editText?.text.toString(),
                            0
                        ),
                        MultipartBody.Part.createFormData(
                            "file", "avatar.$extension", RequestBody.create(
                                MediaType.parse(mime),
                                it.readBytes()
                            )
                        )
                    )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            binding.frame.visibility = View.GONE

                            Dialog(
                                resources.getString(R.string.dialog_title_achievement),
                                resources.getString(R.string.dialog_description_achievement),
                                resources.getString(R.string.dialog_button_achievement)
                            ) {
                            }.show(childFragmentManager.beginTransaction(), "new_version_dialog")
                        }, {
                            Toast.makeText(requireContext(), "Internet error", Toast.LENGTH_LONG)
                                .show()
                        })
                }

            }

        }

        return binding.root
    }
}