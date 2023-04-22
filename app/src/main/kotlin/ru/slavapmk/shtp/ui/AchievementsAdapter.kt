package ru.slavapmk.shtp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.io.dto.achievements.Achievement

class AchievementsAdapter(private val myDataset: List<Achievement>) :
    RecyclerView.Adapter<AchievementsAdapter.AchievementViewHolder>() {

    class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val achievementTitle: TextView = itemView.findViewById(R.id.title)
        val achievementText: TextView = itemView.findViewById(R.id.text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_achievement, parent, false)
        return AchievementViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val achievement = myDataset[position]
        holder.achievementTitle.text = achievement.title
        holder.achievementText.text = achievement.description
    }

    override fun getItemCount() = myDataset.size
}