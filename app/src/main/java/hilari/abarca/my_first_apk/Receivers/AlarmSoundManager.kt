package hilari.abarca.my_first_apk.Receivers

import android.media.Ringtone

object AlarmSoundManager {
    var ringtone: Ringtone? = null

    fun stopRingtone() {
        ringtone?.let {
            if (it.isPlaying) {
                it.stop()
            }
        }
    }
}
