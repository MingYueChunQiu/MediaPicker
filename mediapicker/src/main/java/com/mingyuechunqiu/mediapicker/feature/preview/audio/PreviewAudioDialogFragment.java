package com.mingyuechunqiu.mediapicker.feature.preview.audio;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.data.bean.MediaInfo;
import com.mingyuechunqiu.mediapicker.ui.dialogfragment.BasePresenterDialogFragment;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class PreviewAudioDialogFragment extends BasePresenterDialogFragment<PreviewAudioContract.View<PreviewAudioContract.Presenter>, PreviewAudioContract.Presenter>
        implements PreviewAudioContract.View<PreviewAudioContract.Presenter> {

    private MediaInfo mMediaInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mp_dfg_preview_audio, container, false);
        AppCompatTextView tvName = view.findViewById(R.id.tv_mp_preview_audio_name);
        final AppCompatTextView tvPosition = view.findViewById(R.id.tv_mp_preview_audio_position);
        AppCompatSeekBar sbProgress = view.findViewById(R.id.sb_mp_preview_audio_progress);
        AppCompatTextView tvDuration = view.findViewById(R.id.tv_mp_preview_audio_duration);
        AppCompatImageView ivControl = view.findViewById(R.id.iv_mp_preview_audio_control);

        if (mMediaInfo != null) {
            tvName.setText(mMediaInfo.getName());
        }
        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mPresenter.setProgressBeInTracking(true);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPresenter.setProgressBeInTracking(false);
                mPresenter.seekAudioProgress(seekBar.getProgress());
            }
        });
        ivControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter == null) {
                    return;
                }
                mPresenter.controlPlayAudio();
            }
        });
        mPresenter.setAudioFilePath(mMediaInfo == null ? null : mMediaInfo.getFilePath(),
                tvPosition, tvDuration, ivControl, sbProgress);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null && getView() != null) {
            getDialog().getWindow().setLayout(getResources().getDisplayMetrics().widthPixels * 9 / 10,
                    getResources().getDisplayMetrics().heightPixels * 3 / 10);
        }
    }

    @Override
    public void setPresenter(@NonNull PreviewAudioContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected PreviewAudioContract.Presenter initPresenter() {
        return new PreviewAudioPresenter();
    }

    @Override
    protected void releaseOnDestroyView() {
        mMediaInfo = null;
    }

    @Override
    protected void releaseOnDestroy() {

    }

    public static PreviewAudioDialogFragment newInstance(MediaInfo info) {
        PreviewAudioDialogFragment fragment = new PreviewAudioDialogFragment();
        fragment.mMediaInfo = info;
        return fragment;
    }
}
