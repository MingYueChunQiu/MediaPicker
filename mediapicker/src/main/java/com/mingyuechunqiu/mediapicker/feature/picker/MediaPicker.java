package com.mingyuechunqiu.mediapicker.feature.picker;

import android.content.Context;
import android.support.annotation.NonNull;

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

    public static final MediaPicker INSTANCE;//单例
    private MediaPickerControlable mControl;

    private MediaPicker() {
    }

    static {
        INSTANCE = new MediaPicker();
    }

    public static MediaPickerControlable init(@NonNull Context context) {
        return init(context, new MediaPickerStore(context), null);
    }

    public static MediaPickerControlable init(@NonNull Context context, MediaPickerStoreable store, MediaPickerInterceptable intercept) {
        INSTANCE.mControl = new MediaPickerControl(context, store, intercept);
        return INSTANCE.mControl;
    }

    public MediaPickerControlable getMediaPickerControl() {
        return INSTANCE.mControl;
    }

    public ImageEngine getImageEngine() {
        return INSTANCE.mControl.getImageEngine();
    }
}
