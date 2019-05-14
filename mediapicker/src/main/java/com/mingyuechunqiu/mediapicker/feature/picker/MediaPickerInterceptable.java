package com.mingyuechunqiu.mediapicker.feature.picker;

import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.framework.ImageEngine;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/23
 *     desc   : 多媒体选择器拦截器接口
 *     version: 1.0
 * </pre>
 */
public interface MediaPickerInterceptable {

    void beforeSetMediaPickerConfig(MediaPickerControlable control, MediaPickerConfig config);

    void afterSetMediaPickerConfig(MediaPickerControlable control, MediaPickerConfig config);

    void beforeGetImageEngine(MediaPickerControlable control);

    void afterGetImageEngine(MediaPickerControlable control, ImageEngine engine);

    void getMediaPickerStore(MediaPickerControlable control, MediaPickerStoreable store);

    void pick(MediaPickerControlable control);

    void release(MediaPickerControlable control);
}
