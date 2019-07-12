package com.mingyuechunqiu.mediapicker.ui.dialogfragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mingyuechunqiu.mediapicker.base.presenter.IBasePresenter;
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
public abstract class BasePresenterDialogFragment<V extends IBaseView<P>, P extends IBasePresenter> extends DialogFragment {

    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            getLifecycle().removeObserver(mPresenter);
            //不能放在onDestroyView中执行，因为像输入框失去焦点这种事件会在onDestroyView之后才被调用
            mPresenter = null;
        }
    }

    /**
     * 添加Present相关
     */
    @SuppressWarnings("unchecked")
    protected void attachPresenter() {
        ((V) this).setPresenter(initPresenter());
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
            getLifecycle().addObserver(mPresenter);
        }
    }

    protected abstract P initPresenter();

    /**
     * 释放资源（在onDestroyView时调用）
     */
    protected abstract void releaseOnDestroyView();

    /**
     * 释放资源（在onDestroy时调用）
     */
    protected abstract void releaseOnDestroy();

    /**
     * DialogFragment回调
     */
    public interface DialogFragmentCallback {

        void onCall(DialogFragment dialogFragment, Bundle bundle);
    }
}
