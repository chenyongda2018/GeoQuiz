package example.chen.com.criminalintent.Controller;

import android.support.v4.app.Fragment;

import example.chen.com.criminalintent.Base.SingleFragmentActivity;

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
