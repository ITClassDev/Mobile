package ru.slavapmk.shtp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.Values
import ru.slavapmk.shtp.databinding.FragmentAchievementsBinding
import ru.slavapmk.shtp.io.dto.achievements.Achievement
import java.text.SimpleDateFormat
import java.util.Locale

class AchievementsFragment : Fragment() {
    private lateinit var binding: FragmentAchievementsBinding

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAchievementsBinding.inflate(inflater)
        val data = ArrayList<Achievement>()
        Values.achievements.system?.let {
            data.addAll(it)
        }
        Values.achievements.base?.let {
            data.addAll(it)
        }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.US)
        data.sortBy {
            dateFormat.parse(it.received_at)?.time
        }
        data.reverse()
        val dividerItemDecoration =
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.divider_20
            )!!
        )
        binding.textView42.addItemDecoration(dividerItemDecoration)
        binding.textView42.adapter = AchievementsAdapter(data)
        binding.textView42.layoutManager = LinearLayoutManager(context)
        return binding.root
    }
}