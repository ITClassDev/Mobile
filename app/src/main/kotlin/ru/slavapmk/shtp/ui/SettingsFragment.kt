package ru.slavapmk.shtp.ui

import android.os.Bundle
import android.text.InputType
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipDrawable
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.Values
import ru.slavapmk.shtp.databinding.FragmentSettingsBinding
import ru.slavapmk.shtp.io.dto.ErrorResponse
import ru.slavapmk.shtp.io.dto.user.SocialLinks
import ru.slavapmk.shtp.io.dto.user.patch.PatchUserPassword
import ru.slavapmk.shtp.io.dto.user.patch.PatchUserRequest
import java.net.ConnectException


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    private var savingUpload: Disposable? = null
    private var checkBackspace = false

    private val stack = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater)
        binding.aboutInput.setText(Values.user.userAboutText)
        binding.githubSocInput.setText(Values.user.userGithub)
        binding.telegramSocInput.setText(Values.user.userTelegram)
        binding.stepikSocInput.setText(Values.user.userStepik)
        binding.kaggleSocInput.setText(Values.user.userKaggle)
        binding.personalSocInput.setText(Values.user.userWebsite)
        binding.saveButton.setOnClickListener {
            requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                View.VISIBLE

            var aboutText: String? = null
            if (binding.aboutInput.text != null) aboutText =
                binding.aboutInput.text.toString()

            var password: PatchUserPassword? = PatchUserPassword(
                binding.repeatPasswordInput.editText?.editableText.toString(),
                binding.currentPasswordInput.editText?.editableText.toString(),
                binding.newPasswordInput.editText?.editableText.toString()
            )

            if (
                password?.confirmPassword?.isEmpty() == true ||
                password?.currentPassword?.isEmpty() == true ||
                password?.newPassword?.isEmpty() == true
            ) password = null

            val patchUserRequest = PatchUserRequest(
                aboutText,
                null,
                password,
                SocialLinks(
                    binding.githubSocInput.text.toString(),
                    binding.kaggleSocInput.text.toString(),
                    binding.stepikSocInput.text.toString(),
                    binding.telegramSocInput.text.toString(),
                    binding.personalSocInput.text.toString()
                ),
                stack
            )

            savingUpload = Values.api.updateProfile(Values.token, patchUserRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                        View.GONE
                }, {
                    if (it is HttpException && it.code() == 400)
                        Toast.makeText(
                            context,
                            Gson().fromJson(
                                it.response()?.errorBody()?.string(),
                                ErrorResponse::class.java
                            ).detail,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    else if (it is ConnectException)
                        Toast.makeText(context, "No internet", Toast.LENGTH_SHORT)
                            .show()
                    requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                        View.GONE
                })
        }

        Values.user.techStack?.split(",")?.let { stack.addAll(it) }

        binding.techStackInput.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.techStackInput.setRawInputType(InputType.TYPE_CLASS_TEXT)

        var lastCall = ""
        binding.techStackInput.doOnTextChanged { text, _, _, count ->
            if (stack.isNotEmpty() && text.toString() != lastCall && count == 0 && checkBackspace) {
                stack.removeLast()
                reloadChips(stack)
                lastCall = text.toString()
            }
        }

        binding.techStackInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val fullText = binding.techStackInput.text.toString()
                val parsedOldStack =
                    stack.joinToString(separator = "  ", prefix = " ", postfix = " ")
                if (!fullText.contains(parsedOldStack) && parsedOldStack.replace(" ", "") != "") {
                    reloadChips(stack)
                    return@setOnEditorActionListener true
                }
                val element =
                    fullText.substring(
                        if (parsedOldStack.replace(
                                " ",
                                ""
                            ) == ""
                        ) 0 else parsedOldStack.length
                    )
                if (element != "") {
                    stack.add(element)
                    reloadChips(stack)
                    return@setOnEditorActionListener true
                }
            }

            return@setOnEditorActionListener false
        }

        reloadChips(stack)

        return binding.root
    }

    private fun reloadChips(stacks: ArrayList<String>) {
        checkBackspace = false
        binding.techStackInput.text?.clear()
        for (stack in stacks)
            addChip(stack)
        checkBackspace = true
    }

    private fun addChip(text: String) {
        val chipDrawable = ChipDrawable.createFromResource(requireContext(), R.xml.empty_chip)
        chipDrawable.isCloseIconVisible = false
        chipDrawable.text = text
        chipDrawable.setBounds(
            10,
            10,
            chipDrawable.intrinsicWidth + 10,
            chipDrawable.intrinsicHeight + 10
        )
        val spannableString = SpannableString(" $text ")
        spannableString.setSpan(
            ImageSpan(chipDrawable),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.techStackInput.append(spannableString)
    }

    override fun onStop() {
        super.onStop()
        savingUpload?.dispose()
    }
}