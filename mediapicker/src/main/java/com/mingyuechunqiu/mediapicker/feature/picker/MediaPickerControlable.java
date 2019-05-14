package com.mingyuechunqiu.mediapicker.feature.picker;

import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.framework.ImageEngine;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/23
 *     desc   : 多媒体选择控制器接口
 *     version: 1.0
 * </pre>
 */
public interface MediaPickerControlable {

    MediaPickerControlable setMediaPickerConfig(MediaPickerConfig config);

    MediaPickerControlable setMediaPickerIntercept(MediaPickerInterceptable intercept);

    ImageEngine getImageEngine();

    MediaPickerStoreable getMediaPickerStore();

    void pick();

    void release();
}
