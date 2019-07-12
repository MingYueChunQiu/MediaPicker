package com.mingyuechunqiu.mediapicker.feature.picker;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.data.config.MediaPickerFilter;
import com.mingyuechunqiu.mediapicker.framework.ImageEngine;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/23
 *     desc   : 多媒体选择控制器（包裹MediaPickerStoreable，可以在MediaPickerStoreable前后进行切面逻辑）
 *              实现MediaPickerControlable
 *     version: 1.0
 * </pre>
 */
class MediaPickerControl implements MediaPickerControlable {

    private WeakReference<Activity> mActivityRef;
    private WeakReference<Fragment> mFragmentRef;
    private MediaPickerStoreable mStore;
    private MediaPickerInterceptable mIntercept;

    MediaPickerControl(@NonNull Activity activity, MediaPickerStoreable store,
                       MediaPickerInterceptable intercept) {
        mActivityRef = new WeakReference<>(activity);
        mStore = store;
        mIntercept = intercept;
    }

    MediaPickerControl(@NonNull Fragment fragment, MediaPickerStoreable store,
                       MediaPickerInterceptable intercept) {
        mFragmentRef = new WeakReference<>(fragment);
        mStore = store;
        mIntercept = intercept;
    }

    @Override
    public MediaPickerControlable setMediaPickerConfig(MediaPickerConfig config) {
        checkOrCreateMediaPickerStore(mActivityRef, mFragmentRef);
        if (mIntercept != null) {
            mIntercept.beforeSetMediaPickerConfig(this, config);
        }
        mStore.setMediaPickerConfig(config);
        if (mIntercept != null) {
            mIntercept.afterSetMediaPickerConfig(this, config);
        }
        return this;
    }

    @Override
    public MediaPickerControlable setMediaPickerIntercept(MediaPickerInterceptable intercept) {
        checkOrCreateMediaPickerStore(mActivityRef, mFragmentRef);
        mIntercept = intercept;
        return this;
    }

    @Override
    public ImageEngine getImageEngine() {
        checkOrCreateMediaPickerStore(mActivityRef, mFragmentRef);
        if (mIntercept != null) {
            mIntercept.beforeGetImageEngine(this);
        }
        ImageEngine engine = mStore.getMediaPickerConfig().getImageEngine();
        if (mIntercept != null) {
            mIntercept.afterGetImageEngine(this, engine);
        }
        return engine;
    }

    @Override
    public MediaPickerFilter getMediaPickerFilter() {
        checkOrCreateMediaPickerStore(mActivityRef, mFragmentRef);
        if (mIntercept != null) {
            mIntercept.beforeGetMediaPickerFilter(this);
        }
        MediaPickerFilter filter = mStore.getMediaPickerConfig().getMediaPickerFilter();
        if (mIntercept != null) {
            mIntercept.afterGetMediaPickerFilter(this, filter);
        }
        return filter;
    }

    @Override
    public MediaPickerStoreable getMediaPickerStore() {
        checkOrCreateMediaPickerStore(mActivityRef, mFragmentRef);
        if (mIntercept != null) {
            mIntercept.getMediaPickerStore(this, mStore);
        }
        return mStore;
    }

    @Override
    public void pick() {
        checkOrCreateMediaPickerStore(mActivityRef, mFragmentRef);
        if (mIntercept != null) {
            mIntercept.pick(this);
        }
        mStore.pick();
    }

    public void release() {
        if (mIntercept != null) {
            mIntercept.release(this);
        }
        if (mStore != null) {
            mStore.release();
        }
        mIntercept = null;
        mStore = null;
        mActivityRef = null;
        mFragmentRef = null;
    }

    /**
     * 检查或者创建多媒体选择器信息存储类
     *
     * @param activityRef Activity弱引用
     * @param fragmentRef Fragment弱引用
     */
    private void checkOrCreateMediaPickerStore(WeakReference<Activity> activityRef,
                                               WeakReference<Fragment> fragmentRef) {
        if (activityRef != null && activityRef.get() != null) {
            checkOrCreateMediaPickerStore(activityRef.get());
        } else if (fragmentRef != null && fragmentRef.get() != null) {
            checkOrCreateMediaPickerStore(fragmentRef.get());
        } else {
            throw new IllegalArgumentException("not set activity or fragment");
        }
    }

    private void checkOrCreateMediaPickerStore(@NonNull Activity activity) {
        if (mStore == null) {
            mStore = new MediaPickerStore(activity);
        }
    }

    private void checkOrCreateMediaPickerStore(@NonNull Fragment fragment) {
        if (mStore == null) {
            mStore = new MediaPickerStore(fragment);
        }
    }
}
