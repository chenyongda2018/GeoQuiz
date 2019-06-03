package example.chen.com.criminalintent.Controller.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import example.chen.com.criminalintent.Base.SingleFragmentActivity;
import example.chen.com.criminalintent.Controller.Fragment.CrimeFragment;
import example.chen.com.criminalintent.Controller.Fragment.CrimeListFragment;
import example.chen.com.criminalintent.Model.Crime;
import example.chen.com.criminalintent.R;

/**
 *
 */
public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks,CrimeFragment.Callbacks{

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutReId() {
        return R.layout.activity_masterdetail;//会自动根据手机设备，平板设备显示对应的版面
    }

    /**
     * 如果设备是手机:则进入CrimePagerActivity
     * 如果设备时平板:则将CrimeFragment放入双版面的右边Fragment容器中
     *
     * 通过检查布局中是否含有 `R.id.detail_fragment_container` 这个在双版面中才含有的子布局
     * @param crime
     */
    @Override
    public void onCrimeSelected(Crime crime) {
        if(findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivity(intent);
        } else {
            Fragment detailFragment = CrimeFragment.newInstance(crime.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container,detailFragment )
                    .commit();
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment crimeListFragment = (CrimeListFragment)getSupportFragmentManager()
                                                .findFragmentById(R.id.fragment_container);
        crimeListFragment.updateUI();
    }
}
