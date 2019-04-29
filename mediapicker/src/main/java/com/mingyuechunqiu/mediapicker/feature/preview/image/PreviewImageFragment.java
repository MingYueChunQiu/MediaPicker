package com.mingyuechunqiu.mediapicker.feature.preview.image;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;
import com.mingyuechunqiu.mediapicker.ui.fragment.BasePreviewFragment;

import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/24
 *     desc   : 预览图片界面
 *              继承自Fragment
 *     version: 1.0
 * </pre>
 */
public class PreviewImageFragment extends BasePreviewFragment<PreviewImageContract.View<PreviewImageContract.Presenter>, PreviewImageContract.Presenter>
        implements PreviewImageContract.View<PreviewImageContract.Presenter> {

    @Override
    protected PreviewImageContract.Presenter initPresenter() {
        return new PreviewImagePresenter();
    }

    @Override
    protected void releaseOnDestroy() {

    }

    @Override
    public void setPresenter(@NonNull PreviewImageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public Context getCurrentContext() {
        return getContext();
    }

    @Override
    public Fragment getCurrentParentFragment() {
        return getParentFragment();
    }

    /**
     * 创建预览图片界面
     *
     * @param list  多媒体信息列表
     * @param index 预览的多媒体Item所在索引位置
     * @return 返回预览图片界面
     */
    public static PreviewImageFragment newInstance(List<MediaAdapterItem> list, int index) {
        PreviewImageFragment fragment = new PreviewImageFragment();
        fragment.mList = list;
        fragment.mIndex = index;
        return fragment;
    }
}
