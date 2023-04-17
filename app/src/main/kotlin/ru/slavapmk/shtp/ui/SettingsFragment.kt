package ru.slavapmk.shtp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.adapter.rxjava3.HttpException
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.Values
import ru.slavapmk.shtp.databinding.FragmentSettingsBinding
import ru.slavapmk.shtp.io.dto.user.SocialLinks
import ru.slavapmk.shtp.io.dto.user.patch.PatchUserPassword
import ru.slavapmk.shtp.io.dto.user.patch.PatchUserRequest

class SettingsFragment : Fragment() {
    private var binding: FragmentSettingsBinding? = null

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater)
        binding!!.aboutInput.setText(Values.user.userAboutText)
        binding!!.githubSocInput.setText(Values.user.userGithub)
        binding!!.telegramSocInput.setText(Values.user.userTelegram)
        binding!!.stepikSocInput.setText(Values.user.userStepik)
        binding!!.kaggleSocInput.setText(Values.user.userKaggle)
        binding!!.personalSocInput.setText(Values.user.userWebsite)
        binding!!.saveButton.setOnClickListener {
            requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                View.VISIBLE

            var aboutText: String? = null
            if (binding!!.aboutInput.text != null) aboutText =
                binding!!.aboutInput.text.toString()

            var password: PatchUserPassword? = PatchUserPassword(
                binding?.repeatPasswordInput?.editText?.editableText.toString(),
                binding?.currentPasswordInput?.editText?.editableText.toString(),
                binding?.newPasswordInput?.editText?.editableText.toString()
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
                    binding!!.githubSocInput.text.toString(),
                    binding!!.kaggleSocInput.text.toString(),
                    binding!!.stepikSocInput.text.toString(),
                    binding!!.telegramSocInput.text.toString(),
                    binding!!.personalSocInput.text.toString()
                ),
                null
            )

            Values.api.updateProfile(Values.token, patchUserRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                        View.GONE
                }, {
                    if (it is HttpException && it.code() == 400)
                        Toast.makeText(context, "Current password incorrect", Toast.LENGTH_SHORT)
                            .show()
                })
        }
        return binding!!.root
    }

    companion object {
        @JvmStatic
        fun newInstance(): Fragment {
            return SettingsFragment()
        }
    }
}