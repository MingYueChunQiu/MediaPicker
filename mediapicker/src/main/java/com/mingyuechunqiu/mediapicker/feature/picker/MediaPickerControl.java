package com.mingyuechunqiu.mediapicker.feature.picker;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.data.config.MediaPickerThemeConfig;
import com.mingyuechunqiu.mediapicker.data.constants.MediaPickerType;
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
    public MediaPickerControlable setMediaPickerType(MediaPickerType mediaPickerType) {
        checkOrCreateMediaPickerStore(mContext);
        if (mIntercept != null) {
            mIntercept.beforeSetMediaPickerType(this, mediaPickerType);
        }
        mStore.setMediaPickerType(mediaPickerType);
        if (mIntercept != null) {
            mIntercept.afterSetMediaPickerType(this, mediaPickerType);
        }
        return this;
    }

    @Override
    public MediaPickerControlable setMaxSelectMediaCount(int maxSelectMediaCount) {
        checkOrCreateMediaPickerStore(mContext);
        if (mIntercept != null) {
            mIntercept.beforeSetMaxSelectMediaCount(this, maxSelectMediaCount);
        }
        mStore.setMaxSelectMediaCount(maxSelectMediaCount);
        if (mIntercept != null) {
            mIntercept.afterSetMaxSelectMediaCount(this, maxSelectMediaCount);
        }
        return this;
    }

    @Override
    public MediaPickerControlable setLimitSize(long limitSize) {
        checkOrCreateMediaPickerStore(mContext);
        if (mIntercept != null) {
            mIntercept.beforeSetLimitSize(this, limitSize);
        }
        mStore.setLimitSize(limitSize);
        if (mIntercept != null) {
            mIntercept.afterSetLimitSize(this, limitSize);
        }
        return this;
    }

    @Override
    public MediaPickerControlable setLimitDuration(long limitDuration) {
        checkOrCreateMediaPickerStore(mContext);
        if (mIntercept != null) {
            mIntercept.beforeSetLimitDuration(this, limitDuration);
        }
        mStore.setLimitDuration(limitDuration);
        if (mIntercept != null) {
            mIntercept.afterSetLimitDuration(this, limitDuration);
        }
        return this;
    }

    @Override
    public MediaPickerControlable setFilterLimitMedia(boolean filterLimitMedia) {
        checkOrCreateMediaPickerStore(mContext);
        if (mIntercept != null) {
            mIntercept.beforeSetFilterLimitMedia(this, filterLimitMedia);
        }
        mStore.setFilterLimitMedia(filterLimitMedia);
        if (mIntercept != null) {
            mIntercept.afterSetFilterLimitMedia(this, filterLimitMedia);
        }
        return this;
    }

    @Override
    public MediaPickerControlable setColumnCount(int columnCount) {
        checkOrCreateMediaPickerStore(mContext);
        if (mIntercept != null) {
            mIntercept.beforeSetColumnCount(this, columnCount);
        }
        mStore.setColumnCount(columnCount);
        if (mIntercept != null) {
            mIntercept.afterSetColumnCount(this, columnCount);
        }
        return this;
    }

    @Override
    public MediaPickerControlable setStartPreviewByThird(boolean startPreviewByThird) {
        checkOrCreateMediaPickerStore(mContext);
        if (mIntercept != null) {
            mIntercept.beforeSetStartPreviewByThird(startPreviewByThird);
        }
        mStore.setStartPreviewByThird(startPreviewByThird);
        if (mIntercept != null) {
            mIntercept.afterSetStartPreviewByThird(startPreviewByThird);
        }
        return this;
    }

    @Override
    public MediaPickerControlable setThemeConfig(MediaPickerThemeConfig config) {
        checkOrCreateMediaPickerStore(mContext);
        if (mIntercept != null) {
            mIntercept.beforeSetThemeConfig(config);
        }
        mStore.setThemeConfig(config);
        if (mIntercept != null) {
            mIntercept.afterSetThemeConfig(config);
        }
        return this;
    }

    @Override
    public MediaPickerControlable setImageEngine(ImageEngine engine) {
        checkOrCreateMediaPickerStore(mContext);
        if (mIntercept != null) {
            mIntercept.beforeSetImageEngine(this, engine);
        }
        mStore.setImageEngine(engine);
        if (mIntercept != null) {
            mIntercept.afterSetImageEngine(this, engine);
        }
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
