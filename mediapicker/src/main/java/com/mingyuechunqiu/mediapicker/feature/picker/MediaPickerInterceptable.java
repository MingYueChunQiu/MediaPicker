package com.mingyuechunqiu.mediapicker.feature.picker;

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
 *     desc   : 多媒体选择器拦截器接口
 *     version: 1.0
 * </pre>
 */
public interface MediaPickerInterceptable {

    void beforeSetMediaPickerConfig(MediaPickerControlable control, MediaPickerConfig config);

    void afterSetMediaPickerConfig(MediaPickerControlable control, MediaPickerConfig config);

    void beforeSetMediaPickerType(MediaPickerControlable control, MediaPickerType mediaPickerType);

    void afterSetMediaPickerType(MediaPickerControlable control, MediaPickerType mediaPickerType);

    void beforeSetMaxSelectMediaCount(MediaPickerControlable control, int maxSelectMediaCount);

    void afterSetMaxSelectMediaCount(MediaPickerControlable control, int maxSelectMediaCount);

    void beforeSetLimitSize(MediaPickerControlable control, long limitSize);

    void afterSetLimitSize(MediaPickerControlable control, long limitSize);

    void beforeSetLimitDuration(MediaPickerControlable control, long limitDuration);

    void afterSetLimitDuration(MediaPickerControlable control, long limitDuration);

    void beforeSetFilterLimitMedia(MediaPickerControlable control, boolean filterLimitMedia);

    void afterSetFilterLimitMedia(MediaPickerControlable control, boolean filterLimitMedia);

    void beforeSetColumnCount(MediaPickerControlable control, int columnCount);

    void afterSetColumnCount(MediaPickerControlable control, int columnCount);

    void beforeSetStartPreviewByThird(boolean startPreviewByThird);

    void afterSetStartPreviewByThird(boolean startPreviewByThird);

    void beforeSetImageEngine(MediaPickerControlable control, ImageEngine engine);

    void afterSetImageEngine(MediaPickerControlable control, ImageEngine engine);

    void beforeSetThemeConfig(MediaPickerThemeConfig config);

    void afterSetThemeConfig(MediaPickerThemeConfig config);

    void beforeGetImageEngine(MediaPickerControlable control);

    void afterGetImageEngine(MediaPickerControlable control, ImageEngine engine);

    void getMediaPickerStore(MediaPickerControlable control, MediaPickerStoreable store);

    void pick(MediaPickerControlable control);

    void release(MediaPickerControlable control);
}
