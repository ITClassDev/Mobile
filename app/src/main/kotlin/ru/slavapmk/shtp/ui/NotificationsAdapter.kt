package ru.slavapmk.shtp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.io.dto.notifications.Notification

class NotificationsAdapter(
    private val myDataset: List<Notification>
) :
    RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notificationTitle: TextView = itemView.findViewById(R.id.nottitle)
        val notificationText: TextView = itemView.findViewById(R.id.nottext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_notifcation, parent, false)
        return NotificationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = myDataset[position]
        holder.notificationTitle.text = when (notification.type) {
            0 -> "Достижение одобрено"
            1 -> "Достижение отклонено"
            2 -> "Новое мероприятие"
            3 -> "Новая медаль"
            else -> "Уведомление"
        }

        holder.itemView.background = AppCompatResources.getDrawable(
            holder.itemView.context, when (notification.type) {
                0 -> R.drawable.ab_g
                1 -> R.drawable.ab_r
                2 -> R.drawable.ab_b
                3 -> R.drawable.ab_g
                4 -> R.drawable.ab_b
                5 -> R.drawable.ab_y
                else -> R.drawable.ab_r
            }
        )

        holder.notificationText.text = when (notification.type) {
            0 -> "Ваше достижение «${notification.data.name}» прошло модерацию! Начисленно «${notification.data.points}» баллов"
            1 -> "Ваше достижение «${notification.data.name}» отклонено"
            2 -> "Добавлено новое школьное мероприятие - «${notification.data.name}»"
            3 -> "Вы получили новую медаль!"
            else -> notification.data.text
        }
    }

    override fun getItemCount() = myDataset.size
}