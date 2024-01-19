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
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.Values
import ru.slavapmk.shtp.databinding.FragmentAdminUsersBinding
import ru.slavapmk.shtp.io.dto.user.get.User
import ru.slavapmk.shtp.io.dto.user.put.UserPut
import ru.slavapmk.shtp.ui.Dialog

class AdminUsersFragment : Fragment() {
    private val usersList = ArrayList<User>()
    private val groups: MutableMap<String, String> = mutableMapOf()
    private lateinit var binding: FragmentAdminUsersBinding

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminUsersBinding.inflate(inflater)
        val usersAdapter = UsersAdapter(usersList) { delete_user ->
            Dialog(
                resources.getString(R.string.dialog_title_user_delete),
                resources.getString(R.string.dialog_description_user_delete),
                resources.getString(R.string.dialog_button_user_delete)
            ) {
                requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                    View.VISIBLE
                binding.addUserFrame.visibility = View.GONE
                Values.api.deleteUser(Values.token, delete_user.uuid)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        val indexOf = usersList.indexOf(delete_user)
                        usersList.removeAt(indexOf)
                        binding.list.adapter?.notifyItemRemoved(indexOf)
                        requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                            View.GONE
                    }, {
                        if (it is HttpException && it.code() == 500)
                            Toast.makeText(
                                requireContext(),
                                "500 Internal Server Error",
                                Toast.LENGTH_SHORT
                            ).show()
                        else
                            Toast.makeText(
                                requireContext(),
                                "Internet Error",
                                Toast.LENGTH_SHORT
                            ).show()
                        requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                            View.GONE
                    })
            }.show(childFragmentManager.beginTransaction(), "delete_user")
        }
        binding.list.adapter = usersAdapter
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

        binding.addUserButton.setOnClickListener {
            binding.addUserFrame.visibility = View.VISIBLE
        }

        val roles = mapOf(
            Pair(resources.getString(R.string.student), 0),
            Pair(resources.getString(R.string.teacher), 1),
            Pair(resources.getString(R.string.administrator), 2)
        )

        (binding.roleSelector.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(
            roles.keys.toTypedArray()
        )


        binding.applyRegisterButton.setOnClickListener {
            requireActivity().findViewById<View>(R.id.saving_progressbar).visibility = View.VISIBLE
            val name = binding.vreojfickdp.editText?.text.toString()
            val lastname = binding.eoirk.editText?.text.toString()
            val email = binding.njikm.editText?.text.toString()
            val password = binding.mklwq.editText?.text.toString()
            val role = roles[
                (binding.roleSelector.editText as? MaterialAutoCompleteTextView)?.text.toString()
            ]!!
            val year = binding.ueirowp.editText?.text.toString().toInt()
            val group = groups[
                (binding.groupSelector.editText as? MaterialAutoCompleteTextView)?.text.toString()
            ]!!
            binding.addUserFrame.visibility = View.GONE
            Values.api.createUser(
                Values.token,
                UserPut(
                    email,
                    password,
                    name,
                    lastname,
                    if (role == 0) "student" else "teacher",
                    year,
                    group
                )
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    loadData()
                }, {
                    Toast.makeText(requireContext(), "Internet error", Toast.LENGTH_LONG).show()
                })

        }
        binding.swipe.setOnRefreshListener {
            loadData()
        }

        requireActivity().findViewById<View>(R.id.saving_progressbar).visibility = View.VISIBLE
        loadData()

        return binding.root
    }

    private fun loadData(): Disposable {
        return Values.api.allUsers(Values.token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ users ->
                groups.clear()
                groups.putAll(users.userGroups.associate { group -> group.name to group.uuid })
                (binding.groupSelector.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(
                    groups.keys.toTypedArray()
                )

                val oldSize = usersList.size
                usersList.clear()
                binding.list.adapter?.notifyItemRangeRemoved(0, oldSize)
                usersList.addAll(users.users)
                binding.list.adapter?.notifyItemRangeInserted(0, users.users.size)
                requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                    View.GONE
                binding.swipe.isRefreshing = false
            }, {
                Toast.makeText(
                    requireContext(),
                    "Internet Error",
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().findViewById<View>(R.id.saving_progressbar).visibility =
                    View.GONE
                binding.swipe.isRefreshing = false
            })
    }
}