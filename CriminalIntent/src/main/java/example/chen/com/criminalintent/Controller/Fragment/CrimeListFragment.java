package example.chen.com.criminalintent.Controller.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

import example.chen.com.criminalintent.Controller.Activity.CrimePagerActivity;
import example.chen.com.criminalintent.Model.Crime;
import example.chen.com.criminalintent.Model.CrimeLab;
import example.chen.com.criminalintent.R;

/**
 * Crime列表页面
 */
public class CrimeListFragment extends Fragment {
    private static final String TAG = "CrimeListFragment";
    private RecyclerView mCrimeRecyclerView;
    private CrimeListAdapter mCrimeListAdapter;
    private boolean mSubTitleVisible;
    public static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //让FragmentManager知道fragment需要接收选项菜单方法的回调
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = v.findViewById(R.id.crime_list_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubTitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        updateUI();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem subTitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubTitleVisible) {
            subTitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subTitleItem.setTitle(R.string.show_subtitle);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getContext()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);

                return true;
            case R.id.show_subtitle:
                mSubTitleVisible = !mSubTitleVisible;
                getActivity().invalidateOptionsMenu();//重建菜单项
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubTitleVisible);
    }

    /**
     * 更新副标题
     */
    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getContext());
        int crimeCount = crimeLab.getCrimes().size();
        String subTitle = getString(R.string.subtitle_format, crimeCount);

        if (!mSubTitleVisible) {
            subTitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subTitle);
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mCrimeListAdapter == null) {
            mCrimeListAdapter = new CrimeListAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mCrimeListAdapter);
        } else {
            mCrimeListAdapter.setCrimes(crimes);
            mCrimeListAdapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    private class CrimeListAdapter extends RecyclerView.Adapter<CrimeViewHolder> {

        private List<Crime> mCrimes;

        CrimeListAdapter(List<Crime> crimes) {
            this.mCrimes = crimes;
        }

        @NonNull
        @Override
        public CrimeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new CrimeViewHolder(inflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeViewHolder crimeViewHolder, int i) {
            Crime crime = mCrimes.get(i);
            crimeViewHolder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }
    }

    private class CrimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView crimeTitle;
        private TextView crimeDate;
        private ImageView mSolvedImageView;
        private Crime mCrime;

        CrimeViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_crime_item, parent, false));
            itemView.setOnClickListener(this);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
            crimeTitle = (TextView) itemView.findViewById(R.id.crime_title_item);
            crimeDate = (TextView) itemView.findViewById(R.id.crime_date_item);
        }

        void bind(Crime crime) {
            this.mCrime = crime;
            crimeTitle.setText(crime.getTitle());
            crimeDate.setText(DateFormat.getDateInstance().format(crime.getDate()));
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.INVISIBLE);
        }

        //ViewHolder的点击事件
        @Override
        public void onClick(View v) {
            //进入Crime明细页面
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }
}
