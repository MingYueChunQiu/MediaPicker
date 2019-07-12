package com.mingyuechunqiu.mediapicker.feature.preview.video.play;

import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.feature.picker.MediaPicker;
import com.mingyuechunqiu.mediapicker.ui.fragment.BasePresenterFragment;

import java.io.File;

import static com.mingyuechunqiu.mediapicker.data.constants.Constants.PREFIX_BUNDLE;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/29
 *     desc   : 播放视频界面
 *              继承自BasePresenterFragment
 *     version: 1.0
 * </pre>
 */
public class PlayVideoFragment extends BasePresenterFragment<PlayVideoContract.View<PlayVideoContract.Presenter>, PlayVideoContract.Presenter>
        implements PlayVideoContract.View<PlayVideoContract.Presenter> {

    public static final String BUNDLE_VIDEO_FILE_PATH = PREFIX_BUNDLE + "video_file_path";

    private VideoView vvVideo;

    private String mVideoFilePath;//视频文件路径
    private boolean hasVideoPrepared;//标记视频是否已经准备好了

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mp_fragment_play_video, container, false);
        vvVideo = view.findViewById(R.id.vv_play_video);
        final AppCompatImageView ivThumbnail = view.findViewById(R.id.iv_play_video_thumbnail);

        if (!TextUtils.isEmpty(mVideoFilePath) && getContext() != null) {
            MediaPicker.getImageEngine().showImage(this, new File(mVideoFilePath),
                    R.drawable.mp_media_placeholder, R.drawable.mp_media_error, ivThumbnail);
            vvVideo.setVideoPath(mVideoFilePath);
            vvVideo.setMediaController(new MediaController(getContext()));
            vvVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    ivThumbnail.setVisibility(View.GONE);
                    vvVideo.start();
                }
            });
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hasVideoPrepared && vvVideo != null) {
            vvVideo.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (vvVideo != null && vvVideo.isPlaying()) {
            vvVideo.pause();
        }
    }

    @Override
    public void setPresenter(@NonNull PlayVideoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected PlayVideoContract.Presenter initPresenter() {
        return new PlayVideoPresenter();
    }

    @Override
    protected void releaseOnDestroyView() {
        if (vvVideo != null) {
            vvVideo.stopPlayback();
            vvVideo = null;
        }
        hasVideoPrepared = false;
    }

    @Override
    protected void releaseOnDestroy() {

    }

    public static PlayVideoFragment newInstance(String filePath) {
        PlayVideoFragment fragment = new PlayVideoFragment();
        fragment.mVideoFilePath = filePath;
        return fragment;
    }
}
