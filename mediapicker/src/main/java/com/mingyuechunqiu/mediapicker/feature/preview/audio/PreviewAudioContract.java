package com.mingyuechunqiu.mediapicker.feature.preview.audio;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;

import com.mingyuechunqiu.mediapicker.base.presenter.BaseAbstractPresenter;
import com.mingyuechunqiu.mediapicker.base.view.IBaseView;

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
interface PreviewAudioContract {

    interface View<P extends Presenter> extends IBaseView<P> {
    }

    abstract class Presenter<V extends View> extends BaseAbstractPresenter<V> {

        abstract void setAudioFilePath(String filePath, AppCompatTextView tvPosition, AppCompatTextView tvDuration,
                                       AppCompatImageView ivControl, AppCompatSeekBar sbProgress);

        abstract void controlPlayAudio();

        abstract void seekAudioProgress(int progress);

        abstract void setProgressBeInTracking(boolean beInTracking);
    }
}
