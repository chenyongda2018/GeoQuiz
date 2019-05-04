package example.chen.com.criminalintent;

import android.support.v4.app.Fragment;

import example.chen.com.criminalintent.Base.SingleFragmentActivity;
import example.chen.com.criminalintent.Controller.CrimeFragment;

public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
