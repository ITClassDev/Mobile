package ru.slavapmk.shtp.ui.admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.Values
import ru.slavapmk.shtp.databinding.FragmentAdminNotificationsBinding
import ru.slavapmk.shtp.io.dto.notifications.PostNotification
import ru.slavapmk.shtp.ui.Dialog

class AdminNotificationsFragment : Fragment() {
    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAdminNotificationsBinding.inflate(inflater)
        var groups: Map<String, String> = mapOf()
        val types = mapOf(
            Pair(resources.getString(R.string.admin_panel_notifications_type_notification), 4),
            Pair(resources.getString(R.string.admin_panel_notifications_type_warning), 5)
        )

        Values.api.allUsers(Values.token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ allUsers ->
                groups = allUsers.userGroups.associate { it.name to it.uuid }

                (binding.adminPanelNotificationsGroups.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(
                    groups.keys.toTypedArray()
                )
            }, {})

        (binding.adminPanelNotificationsType.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(
            types.keys.toTypedArray()
        )

        binding.adminPanelNotificationsButtonSend.setOnClickListener {
            requireActivity().findViewById<View>(R.id.saving_progressbar).visibility = View.VISIBLE
            Values.api.sendNotification(
                Values.token,
                PostNotification(
                    groups[(binding.adminPanelNotificationsGroups.editText as? MaterialAutoCompleteTextView)?.text.toString()]!!,
                    binding.adminPanelNotificationsText.editText?.text.toString(),
                    types[(binding.adminPanelNotificationsType.editText as? MaterialAutoCompleteTextView)?.text.toString()]!!
                )
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                        View.GONE
                    Dialog(
                        resources.getString(R.string.dialog_title_notified),
                        resources.getString(R.string.dialog_description_notified),
                        resources.getString(R.string.dialog_button_notified)
                    ) {
                        findNavController().popBackStack()
                    }.show(childFragmentManager.beginTransaction(), "success_notified")
                }, {
                    throw RuntimeException(it)
                })
        }

        return binding.root
    }

}