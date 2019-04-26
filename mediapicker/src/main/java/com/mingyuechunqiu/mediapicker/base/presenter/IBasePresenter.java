package com.mingyuechunqiu.mediapicker.base.presenter;

import android.arch.lifecycle.LifecycleObserver;

import com.mingyuechunqiu.mediapicker.base.view.IBaseView;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/25
 *     desc   : 所有P层的父接口
 *              这儿是简写的MVP，更详细的信息可以看我的
 *              https://github.com/MingYueChunQiu/AgileMVPFrame.git
 *     version: 1.0
 * </pre>
 */
public interface IBasePresenter<V extends IBaseView> extends LifecycleObserver {

    void attachView(V view);

    void detachView();

    void release();
}
