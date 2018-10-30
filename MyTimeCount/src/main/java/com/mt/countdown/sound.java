package com.mt.countdown;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

public class sound {
    /**
     * TYPE_ALARM……
     * 各种音频资源
     */
    private Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);//通用资源
    private MediaPlayer mMediaPlayer = new MediaPlayer();//Android的音频视频支持类

    public void sound_start(Context context) {
        try {
            mMediaPlayer.setDataSource(context, alert);//通过给定的Uri来设置MediaPlayer的数据源，这里的Uri可以是网络路径或是一个ContentProvider的Uri
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);//指定流媒体类型
            mMediaPlayer.setLooping(true);//设置为单曲循环
            mMediaPlayer.prepare();//同步的方式装在流媒体文件
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sound_stop() {
        mMediaPlayer.stop();
    }
}
