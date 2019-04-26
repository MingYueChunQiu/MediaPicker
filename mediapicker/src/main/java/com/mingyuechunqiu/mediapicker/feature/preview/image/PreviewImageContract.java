package com.mingyuechunqiu.mediapicker.feature.preview.image;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.mingyuechunqiu.mediapicker.base.presenter.BaseAbstractPresenter;
import com.mingyuechunqiu.mediapicker.base.view.IBaseView;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;

import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface PreviewImageContract {

    interface View<P extends Presenter> extends IBaseView<P> {

        void backToParentFragment();

        Context getCurrentContext();

        Fragment getCurrentParentFragment();
    }

    abstract class Presenter<V extends View> extends BaseAbstractPresenter<V> {

        abstract void initPreviewImageList(RecyclerView rvList, List<MediaAdapterItem> list, int index);
    }
}
