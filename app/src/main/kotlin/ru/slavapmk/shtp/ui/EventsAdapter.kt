package ru.slavapmk.shtp.ui

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.io.dto.events.mos.Event
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class EventsAdapter(private val myDataset: List<Event>) :
    RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val res: Resources = itemView.resources
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.view_event, parent, false)
        return EventsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val event = myDataset[position]
        val parse = ZonedDateTime.parse(event.startTime, DateTimeFormatter.ISO_DATE_TIME)
        val finish = ZonedDateTime.parse(event.finishedTime, DateTimeFormatter.ISO_DATE_TIME)

        holder.itemView.findViewById<TextView>(R.id.title).text = event.title
        holder.itemView.findViewById<TextView>(R.id.text).text = event.agent.name
        holder.itemView.findViewById<TextView>(R.id.a1).text =
            event.audiencesShort.joinToString(separator = "\n")
        holder.itemView.findViewById<TextView>(R.id.a2).text =
            holder.res.getString(R.string.a2, event.emptySeats, event.seats)
        val atZone = parse.toLocalDateTime().atZone(ZoneId.systemDefault())
        holder.itemView.findViewById<TextView>(R.id.a3).text = holder.res.getString(
            R.string.a3,
            atZone.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
            atZone.format(DateTimeFormatter.ofPattern("HH:mm"))
        )
        val d = Duration.between(parse, finish).toMinutes().toDouble() / 60
        if (d % 1 == 0.0)
            holder.itemView.findViewById<TextView>(R.id.a4).text = holder.res.getString(R.string.a4, d.toInt())
        else
            holder.itemView.findViewById<TextView>(R.id.a4).text = holder.res.getString(R.string.a4, d)
    }

    override fun getItemCount() = myDataset.size
}