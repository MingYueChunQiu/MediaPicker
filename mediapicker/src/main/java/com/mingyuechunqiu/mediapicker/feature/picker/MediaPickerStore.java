package com.mingyuechunqiu.mediapicker.feature.picker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.feature.main.container.MediaPickerActivity;

import java.lang.ref.WeakReference;

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

    private WeakReference<Activity> mActivityRef;
    private WeakReference<Fragment> mFragmentRef;
    private MediaPickerConfig.Builder mConfigBuilder;
    private MediaPickerConfig mConfig;

    MediaPickerStore(@NonNull Activity activity) {
        mActivityRef = new WeakReference<>(activity);
    }

    MediaPickerStore(@NonNull Fragment fragment) {
        mFragmentRef = new WeakReference<>(fragment);
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
        if ((mActivityRef == null || mActivityRef.get() == null) &&
                (mFragmentRef == null || mFragmentRef.get() == null || mFragmentRef.get().getContext() == null)) {
            return;
        }
        if (mConfigBuilder != null) {
            mConfig = mConfigBuilder.build();
        }
        if (mConfig == null) {
            mConfig = new MediaPickerConfig();
        }
        Context context = null;
        if (mActivityRef != null && mActivityRef.get() != null) {
            context = mActivityRef.get();
        } else if (mFragmentRef != null && mFragmentRef.get() != null) {
            context = mFragmentRef.get().getContext();
        }
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MediaPickerActivity.class);
        intent.putExtra(EXTRA_MEDIA_PICKER_CONFIG, mConfig);
        Activity activity = null;
        if (mActivityRef != null && mActivityRef.get() != null) {
            mActivityRef.get().startActivityForResult(intent, MP_REQUEST_START_MEDIA_PICKER);
            activity = mActivityRef.get();
        } else if (mFragmentRef != null && mFragmentRef.get() != null) {
            mFragmentRef.get().startActivityForResult(intent, MP_REQUEST_START_MEDIA_PICKER);
            activity = mFragmentRef.get().getActivity();
        }
        if (activity != null) {
            activity.overridePendingTransition(R.anim.mp_slide_in_right, R.anim.mp_slide_out_left);
        }
    }

    @Override
    public void release() {
        mActivityRef = null;
        mFragmentRef = null;
        mConfig = null;
        mConfigBuilder = null;
    }

    private void checkOrCreateMediaPickerConfig() {
        if (mConfigBuilder == null) {
            mConfigBuilder = new MediaPickerConfig.Builder();
        }
    }
}
