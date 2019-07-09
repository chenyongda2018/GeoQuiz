package example.chen.com.nerdlauncher.controller;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import example.chen.com.nerdlauncher.R;

public class NerdLauncherFragment extends Fragment {

    public static final String TAG = "NerdLauncherFragment";

    private RecyclerView mRecyclerView;

    public static NerdLauncherFragment newInstance() {

        Bundle args = new Bundle();

        NerdLauncherFragment fragment = new NerdLauncherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nerd_launcher, container, false);

        mRecyclerView = v.findViewById(R.id.app_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setUpAdapter();
        return v;
    }


    private void setUpAdapter() {
        Intent startUpIntent = new Intent(Intent.ACTION_MAIN);
        startUpIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(startUpIntent,0 );

        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo o1, ResolveInfo o2) {
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(
                        o1.loadLabel(pm).toString(),o2.loadLabel(pm).toString() );
            }
        });
        Log.d(TAG,"activities found "+activities.size() );

        mRecyclerView.setAdapter(new ActivityAdapter(activities));
    }


    private class ActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ResolveInfo mResolveInfo;
        private ImageView mAppIcon;
        private TextView mTextView;


        public ActivityHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = (TextView)itemView.findViewById(R.id.textView);
            mAppIcon = (ImageView)itemView.findViewById(R.id.imageView);
            mTextView.setOnClickListener(this);
        }

        public void bindActivity(ResolveInfo resolveInfo) {
            mResolveInfo = resolveInfo;
            PackageManager pm = getActivity().getPackageManager();
            String appName = resolveInfo.loadLabel(pm).toString();
            Drawable appIcon = resolveInfo.loadIcon(pm);
            mAppIcon.setImageDrawable(appIcon);
            mTextView.setText(appName);
        }

        @Override
        public void onClick(View v) {
            //使用显示Intent,启动对应的应用
            ActivityInfo activityInfo = mResolveInfo.activityInfo;
            Intent i = new Intent(Intent.ACTION_MAIN)
                    .setClassName(activityInfo.applicationInfo.packageName,
                            activityInfo.name)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//用新的任务栈来启动app，不加这句的话，新启动的app和本app在同一个任务栈中。
            Log.d(TAG,"包名:"+ activityInfo.applicationInfo.packageName);
            Log.d(TAG,"类名:"+ activityInfo.name);

            startActivity(i);

            BitmapFactory.Options options = new BitmapFactory.Options();
            String pathName;

        }
    }

    private class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder> {

        private List<ResolveInfo> mResolveInfos;


        public ActivityAdapter(List<ResolveInfo> resolveInfos) {
            mResolveInfos = resolveInfos;
        }

        @NonNull
        @Override
        public ActivityHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.fragment_activity_list_item, viewGroup, false);
            return  new ActivityHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ActivityHolder acitivityHolder, int i) {
            ResolveInfo resolveInfo = mResolveInfos.get(i);
            acitivityHolder.bindActivity(resolveInfo);
        }

        @Override
        public int getItemCount() {
            return mResolveInfos.size();
        }
    }


    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidht,int reqHeight) {
        //Bitmap的原大小
        final int height = options.outHeight;
        final int width = options.outWidth;
        int sampleSize = 1;

        if (height > reqHeight || width > reqHeight ) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            //设置合适的inSampleSize
            while (halfHeight / sampleSize >=reqHeight
                     && halfWidth / sampleSize >= reqWidht) {
                sampleSize *= 2;
            }
        }
        return sampleSize;
    }
}
