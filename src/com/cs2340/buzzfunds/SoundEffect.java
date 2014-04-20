package com.cs2340.buzzfunds;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * A simple wrapper to play a given sound effect.
 *
 * @author Sean Collins
 */
public class SoundEffect {
    public static void playSound(Context context, int id) {
        MediaPlayer player = MediaPlayer.create(context, id);
        player.start();
    }
}
