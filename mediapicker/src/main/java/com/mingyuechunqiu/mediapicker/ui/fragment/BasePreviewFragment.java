package com.mingyuechunqiu.mediapicker.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.base.presenter.BasePreviewPresenter;
import com.mingyuechunqiu.mediapicker.base.view.IPreviewView;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;

import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/28
 *     desc   : 所有预览界面基类
 *              继承自BasePresenterFragment
 *     version: 1.0
 * </pre>
 */
public abstract class BasePreviewFragment<V extends IPreviewView<P>, P extends BasePreviewPresenter> extends BasePresenterFragment<V, P> {

    protected List<MediaAdapterItem> mList;
    protected int mIndex;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mp_fragment_preview_image, container, false);
        RecyclerView rvList = view.findViewById(R.id.rv_mp_preview_image_list);
        mPresenter.initPreviewImageList(rvList, mList, mIndex);
        return view;
    }

    @Override
    protected void releaseOnDestroyView() {
        mList = null;
        mIndex = 0;
    }
}
