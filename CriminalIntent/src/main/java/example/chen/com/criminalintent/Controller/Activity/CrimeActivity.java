package example.chen.com.criminalintent.Controller.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

import example.chen.com.criminalintent.Base.SingleFragmentActivity;
import example.chen.com.criminalintent.Controller.Fragment.CrimeFragment;

public class CrimeActivity extends SingleFragmentActivity {
    public static final String EXTRA_CRIME_ID = "com.criminalintent.Controller.Activity.CrimeActivity.crime_id";

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext,CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeId );
        return intent;
    }
}
