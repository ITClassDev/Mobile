package ru.slavapmk.shtp.ui

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import ru.slavapmk.shtp.R

class Dialog(
    private val title: String,
    private val description: String,
    private val successButton: String,
    private val onSuccess: View.OnClickListener
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.Dialog)
            val inflater = requireActivity().layoutInflater
            val inflate = inflater.inflate(R.layout.dialog, null)

            inflate.findViewById<TextView>(R.id.header).text = title
            inflate.findViewById<TextView>(R.id.description_message).text = description
            inflate.findViewById<Button>(R.id.button_ok).text = successButton

            inflate.findViewById<MaterialButton>(R.id.button_cancel)
                .setOnClickListener { dialog?.cancel() }
            inflate.findViewById<MaterialButton>(R.id.button_ok)
                .setOnClickListener(onSuccess)

            builder.setView(inflate).create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}