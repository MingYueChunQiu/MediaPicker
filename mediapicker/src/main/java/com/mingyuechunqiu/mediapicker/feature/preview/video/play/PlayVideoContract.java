package com.mingyuechunqiu.mediapicker.feature.preview.video.play;

import com.mingyuechunqiu.mediapicker.base.presenter.BaseAbstractPresenter;
import com.mingyuechunqiu.mediapicker.base.view.IBaseView;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/29
 *     desc   : 播放视频相关契约类，约定相互能实现调用的api
 *     version: 1.0
 * </pre>
 */
interface PlayVideoContract {

    interface View<P extends Presenter> extends IBaseView<P> {
    }

    abstract class Presenter<V extends View> extends BaseAbstractPresenter<V> {
    }
}
