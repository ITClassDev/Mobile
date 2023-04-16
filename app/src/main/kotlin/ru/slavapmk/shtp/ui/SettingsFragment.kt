package ru.slavapmk.shtp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.Values
import ru.slavapmk.shtp.databinding.FragmentSettingsBinding
import ru.slavapmk.shtp.io.dto.user.SocialLinks
import ru.slavapmk.shtp.io.dto.user.patch.PatchUserPassword
import ru.slavapmk.shtp.io.dto.user.patch.PatchUserRequest

class SettingsFragment : Fragment() {
    private var binding: FragmentSettingsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        binding!!.aboutInput.setText(Values.user.userAboutText)
        binding!!.githubSocInput.setText(Values.user.userGithub)
        binding!!.telegramSocInput.setText(Values.user.userTelegram)
        binding!!.stepikSocInput.setText(Values.user.userStepik)
        binding!!.kaggleSocInput.setText(Values.user.userKaggle)
        binding!!.personalSocInput.setText(Values.user.userWebsite)
        binding!!.saveButton.setOnClickListener { view ->
            requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                View.VISIBLE
//            var aboutText: String? = null
//            if (binding!!.aboutInput.text != null) aboutText =
//                binding!!.aboutInput.text.toString()
//
//            val password = PatchUserPassword(
//                binding?.repeatPasswordInput?.editText?.editableText.toString(),
//                binding?.currentPasswordInput?.editText?.editableText.toString(),
//                binding?.newPasswordInput?.editText?.editableText.toString()
//            )
//
//            CoroutineScope(Dispatchers.IO).launch {
//                val user = PatchUserRequest(
//                    aboutText,
//                    null,
//                    password,
//                    SocialLinks(
//                        binding!!.githubSocInput.text.toString(),
//                        binding!!.kaggleSocInput.text.toString(),
//                        binding!!.stepikSocInput.text.toString(),
//                        binding!!.telegramSocInput.text.toString(),
//                        binding!!.personalSocInput.text.toString()
//                    ),
//                    listOf()
//                )
//                Log.d("TAAG", user.toString())
//                val updateProfile = Values.api.updateProfile(Values.token, user)
//                Log.d("TAAG", updateProfile)
//            }
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