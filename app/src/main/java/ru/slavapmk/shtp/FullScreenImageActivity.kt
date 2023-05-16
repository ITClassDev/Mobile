package ru.slavapmk.shtp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class FullScreenImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)
        savedInstanceState?.let {
            val uri = it.getString("uri")!!
            val isGif = it.getBoolean("as_gif", false)
            if (uri.startsWith("http://") || uri.startsWith("https://")) {
                (if (isGif) Glide.with(this).asGif() else Glide.with(this).asBitmap())
                    .load(uri).into(findViewById(R.id.avatar_fullscreen_image))
            }
        }
    }
}