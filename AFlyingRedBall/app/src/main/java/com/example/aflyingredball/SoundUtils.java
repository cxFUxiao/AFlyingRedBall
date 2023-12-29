package com.example.aflyingredball;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import java.util.HashMap;

//声音helper

@SuppressLint("UseSparseArrays")
@SuppressWarnings("deprecation")
public class SoundUtils {
    //上下文
    private Context context;
    //声音池
    private SoundPool soundPool;
    //声音资源参数
    private HashMap<Integer, Integer> soundPoolMap;
     //声音音量类型为多媒体
    private int soundVolType = 3;
    //无限循环
    public static final int INFINITE_PLAY = -1;
   //单次播放
    public static final int SINGLE_PLAY = 0;
   //铃声音量
    public static final int RING_SOUND = 2;
   //媒体音量
    public static final int MEDIA_SOUND = 3;


    public SoundUtils(Context context, int soundVolType) {
        this.context = context;
        this.soundVolType = soundVolType;
        // 初始化声音池和声音参数map
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundPoolMap = new HashMap<Integer, Integer>();
    }


    public void putSound(int order, int soundRes) {
        // 上下文，声音资源id，优先级
        soundPoolMap.put(order, soundPool.load(context, soundRes, 1));
    }

    /**
     *
     * TODO 播放声音
     *
     * @author Leonardo
     * @date 2015-8-20 下午3:52:44
     * @param order
     *            所添加声音的编号
     * @param times
     *            循环次数，0无不循环，-1无永远循环
     * @see
     */
    @SuppressWarnings("static-access")
    public void playSound(final int order, final int times) {
        // 实例化AudioManager对象
        AudioManager am = (AudioManager) context
                .getSystemService(context.AUDIO_SERVICE);
        // 返回当前AudioManager对象播放所选声音的类型的最大音量值
        float maxVolumn = am.getStreamMaxVolume(soundVolType);
        // 返回当前AudioManager对象的音量值
        float currentVolumn = am.getStreamVolume(soundVolType);
        // 比值
        final float volumnRatio = currentVolumn / maxVolumn;
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(soundPoolMap.get(order), volumnRatio, volumnRatio, 1,
                        times, 1);
            }
        });
    }

    //设置VolType的值
    public void setSoundVolType(int soundVolType) {
        this.soundVolType = soundVolType;
    }

    public  void stop(){
        soundPool.release();
    }
}
