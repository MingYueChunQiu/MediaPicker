package com.mingyuechunqiu.mediapicker.feature.preview.video;

import com.mingyuechunqiu.mediapicker.base.presenter.BasePreviewPresenter;
import com.mingyuechunqiu.mediapicker.base.view.IPreviewView;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/28
 *     desc   : 预览视频相关契约类，约定相互能实现调用的api
 *     version: 1.0
 * </pre>
 */
interface PreviewVideoContract {

    interface View<P extends Presenter> extends IPreviewView<P> {
    }

    abstract class Presenter<V extends View> extends BasePreviewPresenter<V> {
    }
}
