package com.mingyuechunqiu.mediapicker.feature.preview.audio;

import android.media.MediaPlayer;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.TextUtils;

import com.mingyuechunqiu.mediapicker.R;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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
class PreviewAudioPresenter extends PreviewAudioContract.Presenter<PreviewAudioContract.View> {

    private WeakReference<AppCompatTextView> tvPositionRef;
    private WeakReference<AppCompatImageView> ivControlRef;
    private WeakReference<AppCompatSeekBar> sbProgressRef;

    private MediaPlayer mPlayer;
    //标记是否音频资源已经准备好/是否处于播放状态/是否需要播放
    private boolean hasAudioPrepared, beInPlaying, needPlay;
    private boolean beInTracking;//标记进度是否正在拖拽中
    private Disposable mProgressDisposable;

    @Override
    protected void onResume() {
        super.onResume();
        playAudio();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseAudio();
    }

    @Override
    public void release() {
        releaseProgressDisposable();
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
            mPlayer.release();
            mPlayer = null;
        }
        hasAudioPrepared = false;
        beInPlaying = false;
        needPlay = false;
        beInTracking = false;
        tvPositionRef = null;
        ivControlRef = null;
        sbProgressRef = null;
    }

    @Override
    void setAudioFilePath(String filePath, AppCompatTextView tvPosition,
                          AppCompatTextView tvDuration, AppCompatImageView ivControl,
                          AppCompatSeekBar sbProgress) {
        if (TextUtils.isEmpty(filePath) || tvPosition == null || tvDuration == null ||
                ivControl == null || sbProgress == null) {
            return;
        }
        tvPositionRef = new WeakReference<>(tvPosition);
        final WeakReference<AppCompatTextView> tvDurationRef = new WeakReference<>(tvDuration);
        ivControlRef = new WeakReference<>(ivControl);
        sbProgressRef = new WeakReference<>(sbProgress);
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    hasAudioPrepared = true;
                    if (tvDurationRef.get() != null) {
                        tvDurationRef.get().setText(getFormattedTime(mp.getDuration()));
                    }
                    if (sbProgressRef.get() != null) {
                        sbProgressRef.get().setMax(mp.getDuration());
                    }
                }
            });
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (ivControlRef.get() != null) {
                        ivControlRef.get().setImageResource(R.drawable.mp_audio_pause);
                    }
                }
            });
        } else {
            mPlayer.stop();
            mPlayer.reset();
        }
        try {
            mPlayer.setDataSource(filePath);
            mPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void controlPlayAudio() {
        if (mPlayer == null || !hasAudioPrepared) {
            return;
        }
        needPlay = !needPlay;
        if (needPlay) {
            playAudio();
        } else {
            pauseAudio();
        }
    }

    @Override
    void seekAudioProgress(int progress) {
        if (mPlayer == null || !hasAudioPrepared || tvPositionRef == null) {
            return;
        }
        mPlayer.seekTo(progress);
        setFormattedTime(tvPositionRef.get(), progress);
    }

    @Override
    void setProgressBeInTracking(boolean beInTracking) {
        this.beInTracking = beInTracking;
    }

    /**
     * 获取格式化的时间
     *
     * @param milliseconds 要格式化的毫秒数
     * @return 返回格式化后的字符串
     */
    private String getFormattedTime(int milliseconds) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return format.format(new Date(milliseconds));
    }

    private void setFormattedTime(AppCompatTextView textView, int milliseconds) {
        if (textView == null) {
            return;
        }
        textView.setText(getFormattedTime(milliseconds));
    }

    /**
     * 释放进度定时刷新
     */
    private void releaseProgressDisposable() {
        if (mProgressDisposable != null) {
            if (!mProgressDisposable.isDisposed()) {
                mProgressDisposable.dispose();
            }
            mProgressDisposable = null;
        }
    }

    private void playAudio() {
        if (mPlayer == null || !hasAudioPrepared || !needPlay || beInPlaying) {
            return;
        }
        mPlayer.start();
        if (ivControlRef != null && ivControlRef.get() != null) {
            ivControlRef.get().setImageResource(R.drawable.mp_audio_play);
        }
        releaseProgressDisposable();
        mProgressDisposable = Observable.interval(50, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        if (mPlayer == null || !hasAudioPrepared) {
                            return;
                        }
                        //当处于用户拖拽中时，不更新进度显示
                        if (!beInTracking && sbProgressRef != null && sbProgressRef.get() != null) {
                            sbProgressRef.get().setProgress(mPlayer.getCurrentPosition());
                        }
                        if (tvPositionRef != null) {
                            setFormattedTime(tvPositionRef.get(), mPlayer.getCurrentPosition());
                        }
                    }
                })
                .subscribe();
        beInPlaying = true;
    }

    private void pauseAudio() {
        //暂停时不需要判断是否需要播放标签，直接暂停
        if (mPlayer == null || !hasAudioPrepared || !beInPlaying) {
            return;
        }
        mPlayer.pause();
        if (ivControlRef != null && ivControlRef.get() != null) {
            ivControlRef.get().setImageResource(R.drawable.mp_audio_pause);
        }
        beInPlaying = false;
        releaseProgressDisposable();
    }
}
