package com.mingyuechunqiu.mediapicker.feature.picker;

import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/23
 *     desc   : 多媒体选择器信息存储接口
 *     version: 1.0
 * </pre>
 */
public interface MediaPickerStoreable {

    MediaPickerStoreable setMediaPickerConfig(MediaPickerConfig config);

    MediaPickerConfig getMediaPickerConfig();

    void pick();

    void release();
}
