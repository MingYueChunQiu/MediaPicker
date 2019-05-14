package com.mingyuechunqiu.mediapicker.feature.picker;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.framework.ImageEngine;

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

    private Context mContext;
    private MediaPickerStoreable mStore;
    private MediaPickerInterceptable mIntercept;

    MediaPickerControl(@NonNull Context context, MediaPickerStoreable store,
                       MediaPickerInterceptable intercept) {
        mContext = context;
        mStore = store;
        mIntercept = intercept;
    }

    @Override
    public MediaPickerControlable setMediaPickerConfig(MediaPickerConfig config) {
        checkOrCreateMediaPickerStore(mContext);
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
        checkOrCreateMediaPickerStore(mContext);
        mIntercept = intercept;
        return this;
    }

    @Override
    public ImageEngine getImageEngine() {
        checkOrCreateMediaPickerStore(mContext);
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
    public MediaPickerStoreable getMediaPickerStore() {
        checkOrCreateMediaPickerStore(mContext);
        if (mIntercept != null) {
            mIntercept.getMediaPickerStore(this, mStore);
        }
        return mStore;
    }

    @Override
    public void pick() {
        checkOrCreateMediaPickerStore(mContext);
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
        mContext = null;
    }

    private void checkOrCreateMediaPickerStore(@NonNull Context context) {
        if (mStore == null) {
            mStore = new MediaPickerStore(context);
        }
    }
}
