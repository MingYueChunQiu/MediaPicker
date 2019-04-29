package com.mingyuechunqiu.mediapicker.base.view;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.mingyuechunqiu.mediapicker.base.presenter.BasePreviewPresenter;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/28
 *     desc   : 预览功能view视图接口的父接口
 *              继承自IBaseView
 *     version: 1.0
 * </pre>
 */
public interface IPreviewView<P extends BasePreviewPresenter> extends IBaseView<P> {

    Context getCurrentContext();

    Fragment getCurrentParentFragment();
}
