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
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.Values
import ru.slavapmk.shtp.databinding.FragmentAdminGroupBinding
import ru.slavapmk.shtp.io.dto.groups.GroupPut
import ru.slavapmk.shtp.io.dto.user.get.UserGroup
import ru.slavapmk.shtp.ui.Dialog

class AdminGroupFragment : Fragment() {
    private val groups = ArrayList<UserGroup>()
    private lateinit var binding: FragmentAdminGroupBinding

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminGroupBinding.inflate(inflater)
        binding.list.adapter = GroupAdapter(groups) { deleteGroup ->
            Dialog(
                resources.getString(R.string.dialog_title_group_delete),
                resources.getString(R.string.dialog_description_group_delete),
                resources.getString(R.string.dialog_button_group_delete)
            ) {
                requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                    View.VISIBLE
                binding.addUserFrame.visibility = View.GONE
                Values.api.deleteGroup(Values.token, deleteGroup.uuid)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        val indexOf = groups.indexOf(deleteGroup)
                        groups.removeAt(indexOf)
                        binding.list.adapter?.notifyItemRemoved(indexOf)
                        requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                            View.GONE
                    }, {
                        Toast.makeText(requireContext(), "Internet error", Toast.LENGTH_LONG)
                            .show()
                        requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                            View.GONE
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

        binding.applyAddGroupButton.setOnClickListener {
            requireActivity().findViewById<View>(R.id.saving_progressbar).visibility = View.VISIBLE
            binding.addUserFrame.visibility = View.GONE
            val name = binding.vreojfickdp.editText?.text.toString()
            Values.api.createGroup(Values.token, GroupPut(name))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    loadData()
                }, {
                    Toast.makeText(requireContext(), "Internet error", Toast.LENGTH_LONG).show()
                })
        }

        binding.addGroupButton.setOnClickListener {
            binding.addUserFrame.visibility = View.VISIBLE
        }
        binding.swipe.setOnRefreshListener {
            loadData()
        }

        requireActivity().findViewById<View>(R.id.saving_progressbar).visibility = View.VISIBLE
        loadData()

        return binding.root
    }

    private fun loadData(): Disposable {
        return Values.api.groupList(Values.token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                val oldSize = groups.size
                groups.clear()
                binding.list.adapter?.notifyItemRangeRemoved(0, oldSize)
                groups.addAll(it)
                binding.list.adapter?.notifyItemRangeInserted(0, groups.size)
                requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                    View.GONE
                binding.swipe.isRefreshing = false
            }, {
                Toast.makeText(requireContext(), "Internet error", Toast.LENGTH_LONG).show()
                requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                    View.GONE
                binding.swipe.isRefreshing = false
            })
    }
}