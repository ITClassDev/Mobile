package ru.slavapmk.shtp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.bumptech.glide.Glide
import ru.slavapmk.shtp.databinding.ActivityFullScreenImageBinding

class FullScreenImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityFullScreenImageBinding.inflate(layoutInflater)
        setContentView(inflate.root)
        val uri = intent.extras!!.getString("uri")!!
        (if (uri.endsWith(".gif")) Glide.with(this).asGif() else Glide.with(this).asBitmap())
            .load(uri).into(inflate.avatarFullscreenImage)

        inflate.fullscreenAvatarClose.setOnClickListener {
            finish()
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.navigationBarColor = 0
        window.statusBarColor = 0
    }
}