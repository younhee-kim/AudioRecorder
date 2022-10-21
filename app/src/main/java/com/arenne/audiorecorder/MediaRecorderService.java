package com.arenne.audiorecorder;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;

public class MediaRecorderService extends Service {
    private static final String TAG = "^O^.MediaRecorderService";
    // creating a variable for media-recorder object class.
    private MediaRecorder mRecorder;

    // string variable is created for storing a file name
    private static String mFileName = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        mFileName = extras.getString("FileName");
        Log.d(TAG, "(+) onStartCommand");
        startRecording();
        Log.d(TAG, "(-) onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "(+) onDestroy");
        stopRecording();
        super.onDestroy();
        Log.d(TAG, "(-) onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startRecording() {
        // we are here initializing our filename variable
        // with the path of the recorded audio file.
        Log.d(TAG, "(+) startRecording");
        Log.d(TAG, "FileName: " + mFileName);
        try {
            // below method is used to initialize
            // the media recorder class
            mRecorder = new MediaRecorder();
            Log.d(TAG, "1");
            // below method is used to set the audio
            // source which we are using a mic.
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            Log.d(TAG, "2");
            // below method is used to set
            // the output format of the audio.
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            Log.d(TAG, "3");
            // below method is used to set the
            // audio encoder for our recorded audio.
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            Log.d(TAG, "4");
            // below method is used to set the
            // output file location for our recorded audio
            mRecorder.setOutputFile(mFileName);
            Log.d(TAG, "5");
            // below method will prepare
            // our audio recorder class
            mRecorder.prepare();
            Log.d(TAG, "6");
            // start method will start
            // the audio recording.
            mRecorder.start();
            Log.d(TAG, "7");
            Toast.makeText(getApplicationContext(), "Start Record", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e(TAG, "prepare() or start() failed");
        }
        Log.d(TAG, "(-) startRecording");
    }

    public void stopRecording() {
        Log.d(TAG, "(+) stopRecording");
        // below method will stop
        // the audio recording.
        try {
            mRecorder.stop();
            // below method will release
            // the media recorder class.
            mRecorder.release();
            Toast.makeText(getApplicationContext(), "Stop Record", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException ignored) {
            Log.e(TAG, "stop() or release() failed");
        }
        mRecorder = null;
        Log.d(TAG, "(-) stopRecording");
    }
}
