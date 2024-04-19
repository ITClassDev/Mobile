package ru.slavapmk.shtp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.io.dto.achievements.Achievement
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AchievementsAdapter(private val myDataset: List<Achievement>) :
    RecyclerView.Adapter<AchievementsAdapter.AchievementViewHolder>() {

    class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.view_achievement, parent, false)
        return AchievementViewHolder(itemView)
    }

    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val achievement = myDataset[position]
        holder.itemView.findViewById<TextView?>(R.id.title).text = achievement.title
        holder.itemView.findViewById<TextView?>(R.id.text).text = achievement.description
        holder.itemView.findViewById<TextView?>(R.id.achievement_date).text =
            LocalDateTime.parse(achievement.accepted_at).format(formatter)
        holder.itemView.findViewById<TextView?>(R.id.achievement_score).text =
            achievement.points.toString()
        holder.itemView.findViewById<TextView?>(R.id.achievement_type).text =
            achievement.eventType
//            when (achievement.eventType) {
//                0 -> "Олимпиады / Конкурсы"
//                1 -> "Мероприятия"
//                2 -> "Системное"
//                else -> ""
//            }
    }

    override fun getItemCount() = myDataset.size
}