package com.mingyuechunqiu.mediapicker.feature.picker;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mingyuechunqiu.mediapicker.data.config.MediaPickerFilter;
import com.mingyuechunqiu.mediapicker.framework.ImageEngine;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/23
 *     desc   : 多媒体选择器
 *     version: 1.0
 * </pre>
 */
public class MediaPicker {

    private static final MediaPicker INSTANCE;//单例
    private MediaPickerControlable mControl;

    private MediaPicker() {
    }

    static {
        INSTANCE = new MediaPicker();
    }

    public static MediaPickerControlable init(@NonNull Activity activity) {
        return init(activity, new MediaPickerStore(activity), null);
    }

    public static MediaPickerControlable init(@NonNull Activity activity, MediaPickerStoreable store,
                                              MediaPickerInterceptable intercept) {
        INSTANCE.mControl = new MediaPickerControl(activity, store, intercept);
        return INSTANCE.mControl;
    }

    public static MediaPickerControlable init(@NonNull Fragment fragment) {
        return init(fragment, new MediaPickerStore(fragment), null);
    }

    public static MediaPickerControlable init(@NonNull Fragment fragment, MediaPickerStoreable store,
                                              MediaPickerInterceptable intercept) {
        INSTANCE.mControl = new MediaPickerControl(fragment, store, intercept);
        return INSTANCE.mControl;
    }

    public static MediaPicker getInstance() {
        return INSTANCE;
    }

    public MediaPickerControlable getMediaPickerControl() {
        return INSTANCE.mControl;
    }

    public static ImageEngine getImageEngine() {
        return INSTANCE.mControl.getImageEngine();
    }

    public static MediaPickerFilter getMediaPickerFilter() {
        return INSTANCE.mControl.getMediaPickerStore().getMediaPickerConfig().getMediaPickerFilter();
    }
}
