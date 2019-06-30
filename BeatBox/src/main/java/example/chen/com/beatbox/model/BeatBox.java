package example.chen.com.beatbox.model;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源管理类
 */
public class BeatBox {

    private static final String TAG = "BeatBox";

    private static final String SOUNDS_FOLDER = "sample_sounds";

    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssetManager;

    private List<Sound> mSounds = new ArrayList<>();

    private SoundPool mSoundPool;

    public BeatBox(Context context) {
        mAssetManager = context.getAssets();
        //1.指定最多同时播放多少个音频。2，音频流类型.3,采样率转换品质
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC,0 );
        loadSounds();
    }

    private void loadSounds() {
        String[] soundsName;
        try {
            soundsName = mAssetManager.list(SOUNDS_FOLDER);//列出asset中指定文件夹下的文件名
            Log.d(TAG, soundsName.length + "--");// 22
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        for (String name : soundsName) {
            try {
                String assetPath = SOUNDS_FOLDER + "/" + name;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = mAssetManager.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);//把文件载入SoundPool待播
        sound.setSoundId(soundId);
    }

    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        mSoundPool.play(soundId, 1.0f, 1.0f,1 , 0, 1.0f);
    }

    //释放音频资源
    public void release() {
        mSoundPool.release();
    }

    public List<Sound> getSounds() {
        return mSounds;
    }
}
