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

class AdminGroupFragment : Fragment() {
    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAdminGroupBinding.inflate(inflater)

        updateUsers(binding)

        binding.addGroupButton.setOnClickListener {
            binding.addUserFrame.visibility = View.VISIBLE
        }

        binding.applyAddGroupButton.setOnClickListener {
            val name = binding.vreojfickdp.editText?.text.toString()
            Values.api.createGroup(Values.token, GroupPut(name))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    updateUsers(binding)
                    binding.addUserFrame.visibility = View.GONE
                }, {
                    Toast.makeText(requireContext(), "Internet error", Toast.LENGTH_LONG).show()
                })
        }

        return binding.root
    }

    @SuppressLint("CheckResult")
    private fun updateUsers(binding: FragmentAdminGroupBinding) {
        Values.api.allUsers(Values.token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                binding.list.adapter = GroupAdapter(it.userGroups) { deleteGroup ->
                    Values.api.deleteGroup(Values.token, deleteGroup.id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            updateUsers(binding)
                        }, {
                            Toast.makeText(requireContext(), "Internet error", Toast.LENGTH_LONG)
                                .show()
                        })
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
            }, {
                Toast.makeText(requireContext(), "Internet error", Toast.LENGTH_LONG).show()
            })
    }
}