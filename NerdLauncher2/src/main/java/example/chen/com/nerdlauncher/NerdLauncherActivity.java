package example.chen.com.nerdlauncher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import example.chen.com.nerdlauncher.base.SingleFragmentActivity;
import example.chen.com.nerdlauncher.controller.NerdLauncherFragment;

public class NerdLauncherActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return NerdLauncherFragment.newInstance();
    }


}
