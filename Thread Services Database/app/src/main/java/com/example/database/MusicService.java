package com.example.database;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

/* AndroidManifest.xml must be modified to add this service */
public class MusicService extends Service {

    private MediaPlayer player;

    public MusicService() {

    }
    public class MyLocalBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
    private final IBinder myBinder = new MusicService.MyLocalBinder();

    public void PlayMusic() {
        //create a media player
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.bagpipes);
            player.setLooping(true);
            player.start();
        }
    }

    public void StopMusic() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }
}
