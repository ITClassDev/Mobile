package ru.slavapmk.shtp.ui.admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.Values
import ru.slavapmk.shtp.databinding.FragmentAdminUsersBinding

class AdminUsersFragment : Fragment() {
    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAdminUsersBinding.inflate(inflater)

        Values.api.allUsers(Values.token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                binding.list.adapter = UsersAdapter(it.users)
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
            }, {})

        return binding.root
    }
}