package example.chen.com.criminalintent.Controller.Activity;

import android.support.v4.app.Fragment;

import example.chen.com.criminalintent.Base.SingleFragmentActivity;
import example.chen.com.criminalintent.Controller.Fragment.CrimeListFragment;

/**
 *
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
