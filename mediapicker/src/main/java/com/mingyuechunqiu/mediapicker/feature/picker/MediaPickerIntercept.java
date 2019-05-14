package com.mingyuechunqiu.mediapicker.feature.picker;

import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.framework.ImageEngine;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/23
 *     desc   : 多媒体选择器拦截器
 *              实现MediaPickerInterceptable
 *     version: 1.0
 * </pre>
 */
public class MediaPickerIntercept implements MediaPickerInterceptable {

    @Override
    public void beforeSetMediaPickerConfig(MediaPickerControlable control, MediaPickerConfig config) {

    }

    @Override
    public void afterSetMediaPickerConfig(MediaPickerControlable control, MediaPickerConfig config) {

    }

    @Override
    public void beforeGetImageEngine(MediaPickerControlable control) {

    }

    @Override
    public void afterGetImageEngine(MediaPickerControlable control, ImageEngine engine) {

    }

    @Override
    public void getMediaPickerStore(MediaPickerControlable control, MediaPickerStoreable store) {

    }

    @Override
    public void pick(MediaPickerControlable control) {

    }

    @Override
    public void release(MediaPickerControlable control) {

    }
}
