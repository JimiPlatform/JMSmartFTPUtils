package com.example.learnvideo.view.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaDataSource;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import io.reactivex.functions.Consumer;

public class AudioRecordUtils {
    private AudioRecord audioRecord;
    private int audioSource;
    private int sampleRateInHz;
    private int channelConfig;
    private int audioFormat;
    private int bufferSizeInBytes;
    private File parent;
    private AppCompatActivity context;
    private boolean isRecording;
    private int recordBufSize = 0;
    private AudioTrack audioTrack;

    private void init(){
        audioSource = MediaRecorder.AudioSource.MIC;
        sampleRateInHz = 44100;
        channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
        audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,channelConfig,audioFormat);
        audioRecord = new AudioRecord(audioSource,sampleRateInHz,channelConfig,audioFormat,bufferSizeInBytes);
        parent = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/AudiioRecord");
        if (!parent.exists()) {
            parent.mkdirs();
        }
    }

    @SuppressLint("CheckResult")
    private void startRecord() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            new RxPermissions(context)
                    .request(Manifest.permission.CAMERA)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (!aBoolean)
                                return;
                            getAudio();
                        }
                    });
        }
    }

    /**
     * 获取录取的音频,并且写入文件
     */
    private void getAudio() {
        isRecording = true;
        new Thread() {
            @Override
            public void run() {
                super.run();
                File file = new File(parent, "audio.pcm");
                if (file.exists()) {
                    file.delete();
                }
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DataOutputStream outputStream = null;
                try {
                    outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
                    byte[] buffer = new byte[recordBufSize];
                    //开始录音
                    audioRecord.startRecording();
                    int r = 0;
                    while (isRecording) {
                        int readResult = audioRecord.read(buffer, 0, recordBufSize);
                        for (int i = 0; i < readResult; i++) {
                            outputStream.write(buffer[i]);
                        }
                        r++;
                        Log.e("avPcm", "录制中....");
                    }
                    audioRecord.stop();
                    audioRecord.release();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void stopRecord() {
        isRecording = false;
    }

    private void playPcm() {
        DataInputStream dis = null;
        File file = new File(parent, "audio.pcm");
        try {
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
            int bufferSize = AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
            audioTrack = new AudioTrack(audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSize, AudioTrack.MODE_STREAM);
            byte[] datas = new byte[bufferSize];
            audioTrack.play();
            while (true) {
                int i = 0;
                try {
                    while (dis.available() > 0 && i < datas.length) {
                        datas[i] = dis.readByte();
                        i++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                audioTrack.write(datas, 0, datas.length);
                //表示读取完了
                if (i != bufferSize) {
                    audioTrack.stop();
                    audioTrack.release();
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
