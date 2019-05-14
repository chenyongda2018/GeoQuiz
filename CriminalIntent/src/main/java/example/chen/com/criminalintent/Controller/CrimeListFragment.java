package example.chen.com.criminalintent.Controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.List;

import example.chen.com.criminalintent.Model.Crime;
import example.chen.com.criminalintent.Model.CrimeLab;
import example.chen.com.criminalintent.R;

public class CrimeListFragment extends Fragment {
    private static final String TAG = "CrimeListFragment";
    private RecyclerView mCrimeRecyclerView;
    private CrimeListAdapter mCrimeListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = v.findViewById(R.id.crime_list_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        Log.d(TAG, crimes.size() + " ");
        mCrimeListAdapter = new CrimeListAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mCrimeListAdapter);

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


    }

    private class CrimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView crimeTitle;
        private TextView crimeDate;
        private ImageView mSolvedImageView;

        CrimeViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_crime_item, parent, false));
            itemView.setOnClickListener(this);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
            crimeTitle = (TextView) itemView.findViewById(R.id.crime_title_item);
            crimeDate = (TextView) itemView.findViewById(R.id.crime_date_item);
        }

        void bind(Crime crime) {
            crimeTitle.setText(crime.getTitle());
            crimeDate.setText(DateFormat.getDateInstance().format(crime.getDate()));
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),crimeTitle.getText().toString() ,Toast.LENGTH_SHORT ).show();
        }
    }
}
