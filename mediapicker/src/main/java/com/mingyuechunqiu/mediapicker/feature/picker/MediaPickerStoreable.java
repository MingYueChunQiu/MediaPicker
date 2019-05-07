package com.mingyuechunqiu.mediapicker.feature.picker;

import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.data.constants.MediaPickerType;
import com.mingyuechunqiu.mediapicker.framework.ImageEngine;

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

    MediaPickerStoreable setMediaPickerType(MediaPickerType mediaPickerType);

    MediaPickerStoreable setMaxSelectMediaCount(int maxSelectMediaCount);

    MediaPickerStoreable setLimitSize(long limitSize);

    MediaPickerStoreable setLimitDuration(long limitDuration);

    MediaPickerStoreable setFilterLimitMedia(boolean filterLimitMedia);

    MediaPickerStoreable setColumnCount(int columnCount);

    MediaPickerStoreable setStartPreviewByThird(boolean startPreviewByThird);

    MediaPickerStoreable setImageEngine(ImageEngine engine);

    MediaPickerConfig getMediaPickerConfig();

    void pick();

    void release();
}
