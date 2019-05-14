package com.mingyuechunqiu.mediapicker.feature.picker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.feature.main.container.MediaPickerActivity;

import static com.mingyuechunqiu.mediapicker.data.constants.Constants.EXTRA_MEDIA_PICKER_CONFIG;
import static com.mingyuechunqiu.mediapicker.data.constants.Constants.MP_REQUEST_START_MEDIA_PICKER;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/23
 *     desc   : 多媒体选择器信息存储类
 *              实现MediaPickerStoreable
 *     version: 1.0
 * </pre>
 */
class MediaPickerStore implements MediaPickerStoreable {

    private Context mContext;
    private MediaPickerConfig.Builder mConfigBuilder;
    private MediaPickerConfig mConfig;

    MediaPickerStore(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public MediaPickerStoreable setMediaPickerConfig(MediaPickerConfig config) {
        mConfig = config;
        return this;
    }

    @Override
    public MediaPickerConfig getMediaPickerConfig() {
        if (mConfig == null) {
            checkOrCreateMediaPickerConfig();
            mConfig = mConfigBuilder.build();
        }
        return mConfig;
    }

    @Override
    public void pick() {
        if (mContext == null) {
            return;
        }
        if (mConfigBuilder != null) {
            mConfig = mConfigBuilder.build();
        }
        if (mConfig == null) {
            mConfig = new MediaPickerConfig();
        }
        Intent intent = new Intent(mContext, MediaPickerActivity.class);
        intent.putExtra(EXTRA_MEDIA_PICKER_CONFIG, mConfig);
        if (mContext instanceof Activity) {
            ((Activity) mContext).startActivityForResult(intent, MP_REQUEST_START_MEDIA_PICKER);
            ((Activity) mContext).overridePendingTransition(R.anim.mp_slide_in_right, R.anim.mp_slide_out_left);
        }
    }

    @Override
    public void release() {
        mContext = null;
        mConfig = null;
        mConfigBuilder = null;
    }

    private void checkOrCreateMediaPickerConfig() {
        if (mConfigBuilder == null) {
            mConfigBuilder = new MediaPickerConfig.Builder();
        }
    }
}
