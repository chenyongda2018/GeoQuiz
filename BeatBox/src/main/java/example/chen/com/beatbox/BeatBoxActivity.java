package example.chen.com.beatbox;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import example.chen.com.beatbox.base.SingleFragmentActivity;

public class BeatBoxActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return BeatBoxFragment.newInstance();
    }
}
