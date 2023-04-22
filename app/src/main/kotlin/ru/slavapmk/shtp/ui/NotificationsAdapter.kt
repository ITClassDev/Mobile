package ru.slavapmk.shtp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.io.dto.notifications.Notification

class NotificationsAdapter(private val myDataset: List<Notification>) :
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
        holder.notificationTitle.text = "Получено достижение"
        holder.notificationText.text = notification.data.name
    }

    override fun getItemCount() = myDataset.size
}