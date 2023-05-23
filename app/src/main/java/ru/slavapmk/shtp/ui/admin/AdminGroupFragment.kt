package ru.slavapmk.shtp.ui.admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.Values
import ru.slavapmk.shtp.databinding.FragmentAdminGroupBinding
import ru.slavapmk.shtp.io.dto.groups.GroupPut
import ru.slavapmk.shtp.io.dto.user.get.UserGroup
import ru.slavapmk.shtp.ui.Dialog

class AdminGroupFragment : Fragment() {
    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAdminGroupBinding.inflate(inflater)
        val groups = ArrayList<UserGroup>()
        binding.list.adapter = GroupAdapter(groups) { deleteGroup ->
            Dialog(
                resources.getString(R.string.dialog_title_group_delete),
                resources.getString(R.string.dialog_description_group_delete),
                resources.getString(R.string.dialog_button_group_delete)
            ) {
                Values.api.deleteGroup(Values.token, deleteGroup.id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        val indexOf = groups.indexOf(deleteGroup)
                        groups.removeAt(indexOf)
                        binding.list.adapter?.notifyItemRemoved(indexOf)
                    }, {
                        Toast.makeText(requireContext(), "Internet error", Toast.LENGTH_LONG)
                            .show()
                    })
            }.show(childFragmentManager.beginTransaction(), "delete_group")
        }
        binding.list.layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration =
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.divider_20
            )!!
        )
        binding.list.addItemDecoration(dividerItemDecoration)

        Values.api.allUsers(Values.token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                groups.addAll(it.userGroups)
                binding.list.adapter?.notifyItemRangeInserted(0, groups.size)
            }, {
                Toast.makeText(requireContext(), "Internet error", Toast.LENGTH_LONG).show()
            })

        binding.applyAddGroupButton.setOnClickListener {
            val name = binding.vreojfickdp.editText?.text.toString()
            Values.api.createGroup(Values.token, GroupPut(name))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Values.api.allUsers(Values.token)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            groups.clear()
                            groups.addAll(it.userGroups)
                            binding.list.adapter?.notifyItemRangeChanged(0, groups.size)
                        }, {
                            Toast.makeText(requireContext(), "Internet error", Toast.LENGTH_LONG)
                                .show()
                        })
                    binding.addUserFrame.visibility = View.GONE
                }, {
                    Toast.makeText(requireContext(), "Internet error", Toast.LENGTH_LONG).show()
                })
        }

        binding.addGroupButton.setOnClickListener {
            binding.addUserFrame.visibility = View.VISIBLE
        }

        return binding.root
    }
}