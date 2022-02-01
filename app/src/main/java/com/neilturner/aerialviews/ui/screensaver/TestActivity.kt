package com.neilturner.aerialviews.ui.screensaver

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import com.neilturner.aerialviews.R
import com.neilturner.aerialviews.models.prefs.GeneralPrefs
import com.neilturner.aerialviews.utils.WindowHelper

class TestActivity : Activity() {
    private var videoController: VideoController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.app_name)
        videoController = VideoController(this)
        setContentView(videoController!!.view)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_UP) {
            // Log.i(TAG, "${event.keyCode}")

            if (!GeneralPrefs.enableSkipVideos)
                finish()

            when (event.keyCode) {
                // Capture all d-pad presses for future use
                KeyEvent.KEYCODE_DPAD_CENTER,
                KeyEvent.KEYCODE_DPAD_DOWN_LEFT,
                KeyEvent.KEYCODE_DPAD_UP_LEFT,
                KeyEvent.KEYCODE_DPAD_DOWN_RIGHT,
                KeyEvent.KEYCODE_DPAD_UP_RIGHT,
                KeyEvent.KEYCODE_DPAD_UP,
                KeyEvent.KEYCODE_DPAD_DOWN -> return true

                KeyEvent.KEYCODE_DPAD_LEFT -> {
                    videoController!!.skipVideo(true)
                    return true
                }

                KeyEvent.KEYCODE_DPAD_RIGHT -> {
                    videoController!!.skipVideo()
                    return true
                }

                // Any other button press will close the screensaver
                else -> finish()
            }
        }

        return super.dispatchKeyEvent(event)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && videoController?.view != null) {
            WindowHelper.hideSystemUI(window, videoController!!.view)
        }
    }

    override fun onStop() {
        videoController!!.stop()
        super.onStop()
    }

    companion object {
        private const val TAG = "TestActivity"
    }
}
