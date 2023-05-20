package ru.slavapmk.shtp.ui

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import ru.slavapmk.shtp.R

class NewVersionDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.Dialog)
            val inflater = requireActivity().layoutInflater
            val inflate = inflater.inflate(R.layout.dialog_new_version, null)

            inflate.findViewById<TextView>(R.id.description_message).text = resources.getString(
                R.string.dialog_new_version_description,
                requireArguments().getString("version_name"),
                requireArguments().getInt("version_code")
            )

            inflate.findViewById<MaterialButton>(R.id.button_skip).setOnClickListener {
                dialog?.cancel()
            }
            inflate.findViewById<MaterialButton>(R.id.button_download).setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(requireArguments().getString("version_url"))
                    )
                )
            }

            builder.setView(inflate).create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}