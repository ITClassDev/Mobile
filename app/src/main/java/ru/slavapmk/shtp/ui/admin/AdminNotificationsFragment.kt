package ru.slavapmk.shtp.ui.admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.slavapmk.shtp.Values
import ru.slavapmk.shtp.databinding.FragmentAdminNotificationsBinding
import ru.slavapmk.shtp.io.dto.notifications.PostNotification

class AdminNotificationsFragment : Fragment() {
    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAdminNotificationsBinding.inflate(inflater)

        Values.api.allUsers(Values.token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ allUsers ->
                binding.groups.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    allUsers.userGroups.map { it.name }
                )
            }, {})

        binding.type.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Notification", "Warning")
        )

        binding.adminPanelNotificationsButtonSend.setOnClickListener {
            Values.api.sendNotification(
                Values.token,
                PostNotification(
                    binding.groups.selectedItemPosition,
                    binding.textInputLayout.editText?.text.toString(),
                    binding.type.selectedItemPosition
                )
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }, {})
        }

        return binding.root
    }

}