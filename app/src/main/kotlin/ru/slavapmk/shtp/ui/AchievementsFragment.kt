package ru.slavapmk.shtp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.slavapmk.shtp.Values
import ru.slavapmk.shtp.Values.api
import ru.slavapmk.shtp.databinding.FragmentAchievementsBinding

class AchievementsFragment : Fragment() {
    private lateinit var inflate: FragmentAchievementsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inflate = FragmentAchievementsBinding.inflate(inflater)
        api.getAchievements(Values.token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                val achievementsAdapter = AchievementsAdapter(it.achievements.base)
                inflate.textView42.adapter = achievementsAdapter
                inflate.textView42.layoutManager = LinearLayoutManager(context)
            }
        return inflate.root
    }
}