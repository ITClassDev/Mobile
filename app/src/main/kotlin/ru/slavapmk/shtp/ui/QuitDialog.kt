package ru.slavapmk.shtp.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.Values

class QuitDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.Dialog)
            val inflater = requireActivity().layoutInflater
            val inflate = inflater.inflate(R.layout.dialog_quit, null)

            inflate.findViewById<MaterialButton>(R.id.button_cancel).setOnClickListener {
                dialog?.cancel()
            }
            inflate.findViewById<MaterialButton>(R.id.button_quit).setOnClickListener {
                requireActivity().getSharedPreferences(Values.APP_ID, 0).edit()
                    .remove(Values.AUTH_KEY_ID).apply()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

            builder.setView(inflate).create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}