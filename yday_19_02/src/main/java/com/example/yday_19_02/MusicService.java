package com.example.yday_19_02;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;

import java.io.IOException;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener{

    private MediaPlayer mediaPlayer;
    private int currentPosition;

    public MusicService() {
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private void play(String data){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(data);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void pause(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            currentPosition=mediaPlayer.getCurrentPosition();
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String data=intent.getStringExtra("pathKey");
        String action=intent.getStringExtra("actionKey");
        switch (action){
            case "PLAY":
                play(data);
                break;
            case "STOP":
                mediaPlayer.stop();
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
        super.onDestroy();
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.seekTo(currentPosition);
        mp.start();
    }
}
