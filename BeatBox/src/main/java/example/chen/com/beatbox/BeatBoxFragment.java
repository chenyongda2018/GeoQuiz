package example.chen.com.beatbox;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import example.chen.com.beatbox.model.BeatBox;
import example.chen.com.beatbox.model.Sound;
import example.chen.com.beatbox.viewmodel.SoundViewModel;
import example.chen.com.beatbox.databinding.FragmentBeatBoxBinding;
import example.chen.com.beatbox.databinding.ListItemSoundBinding;


public class BeatBoxFragment extends Fragment {

    public static BeatBoxFragment newInstance() {
        return new BeatBoxFragment();
    }

    private BeatBox mBeatBox;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * setRetainInstance"
         * false:表明fragment在设备旋转时不会被保留，会被销毁并重建
         * true:保留fragment，不会随着Activity一起被销毁。
         */
        setRetainInstance(true);
        mBeatBox = new BeatBox(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentBeatBoxBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_beat_box,
                container,
                false);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        binding.recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));

        return binding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }





    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {

        private List<Sound> mSounds;

        public SoundAdapter(List<Sound> sounds) {
            mSounds = sounds;
        }

        @NonNull
        @Override
        public SoundHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ListItemSoundBinding binding = DataBindingUtil.inflate(inflater,
                    R.layout.list_item_sound,
                    viewGroup,
                    false);
            return new SoundHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull SoundHolder soundHolder, int i) {
            Sound sound = mSounds.get(i);
            soundHolder.bind(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }

    private class SoundHolder extends RecyclerView.ViewHolder {
        private ListItemSoundBinding mBinding;


        private SoundHolder(ListItemSoundBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            mBinding.setViewModel(new SoundViewModel(mBeatBox));

        }

        public void bind(Sound sound) {
            mBinding.getViewModel().setSound(sound);
            mBinding.executePendingBindings();
        }

    }

}
