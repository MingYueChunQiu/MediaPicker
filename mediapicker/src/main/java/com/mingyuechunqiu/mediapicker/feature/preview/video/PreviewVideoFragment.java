package com.mingyuechunqiu.mediapicker.feature.preview.video;

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
 *     time   : 2019/4/28
 *     desc   : 预览视频界面
 *              继承自BasePreviewFragment
 *     version: 1.0
 * </pre>
 */
public class PreviewVideoFragment extends BasePreviewFragment<PreviewVideoContract.View<PreviewVideoContract.Presenter>, PreviewVideoContract.Presenter>
        implements PreviewVideoContract.View<PreviewVideoContract.Presenter> {

    @Override
    public Context getCurrentContext() {
        return getContext();
    }

    @Override
    public Fragment getCurrentParentFragment() {
        return getParentFragment();
    }

    @Override
    public void setPresenter(@NonNull PreviewVideoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected PreviewVideoContract.Presenter initPresenter() {
        return new PreviewVideoPresenter();
    }

    @Override
    protected void releaseOnDestroy() {

    }

    /**
     * 创建预览图片界面
     *
     * @param list  多媒体信息列表
     * @param index 预览的多媒体Item所在索引位置
     * @return 返回预览图片界面
     */
    public static PreviewVideoFragment newInstance(List<MediaAdapterItem> list, int index) {
        PreviewVideoFragment fragment = new PreviewVideoFragment();
        fragment.mList = list;
        fragment.mIndex = index;
        return fragment;
    }
}
