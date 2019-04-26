package com.mingyuechunqiu.mediapicker.feature.preview.image;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;
import com.mingyuechunqiu.mediapicker.ui.fragment.BasePresenterFragment;

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
public class PreviewImageFragment extends BasePresenterFragment<PreviewImageContract.View<PreviewImageContract.Presenter>, PreviewImageContract.Presenter>
        implements PreviewImageContract.View<PreviewImageContract.Presenter> {

    private List<MediaAdapterItem> mList;
    private int mIndex;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mp_fragment_preview_image, container, false);
        RecyclerView rvList = view.findViewById(R.id.rv_mp_preview_image_list);
        mPresenter.initPreviewImageList(rvList, mList, mIndex);
        return view;
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

    @Override
    protected PreviewImageContract.Presenter initPresenter() {
        return new PreviewImagePresenter();
    }

    @Override
    protected void releaseOnDestroyView() {
        mList = null;
    }

    @Override
    protected void releaseOnDestroy() {

    }

    @Override
    public void setPresenter(@NonNull PreviewImageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void backToParentFragment() {
        Fragment fragment = getParentFragment();
        if (fragment != null) {
            fragment.getChildFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.mp_scale_in_magnify, R.anim.mp_scale_out_shrink)
                    .remove(this)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public Context getCurrentContext() {
        return getContext();
    }

    @Override
    public Fragment getCurrentParentFragment() {
        return getParentFragment();
    }
}
