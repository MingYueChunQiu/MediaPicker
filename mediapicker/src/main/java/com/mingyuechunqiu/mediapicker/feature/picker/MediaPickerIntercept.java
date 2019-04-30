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
    public void beforeSetMediaPickerType(MediaPickerControlable control, MediaPickerType mediaPickerType) {

    }

    @Override
    public void afterSetMediaPickerType(MediaPickerControlable control, MediaPickerType mediaPickerType) {

    }

    @Override
    public void beforeSetMaxSelectMediaCount(MediaPickerControlable control, int maxSelectMediaCount) {

    }

    @Override
    public void afterSetMaxSelectMediaCount(MediaPickerControlable control, int maxSelectMediaCount) {

    }

    @Override
    public void beforeSetLimitSize(MediaPickerControlable control, long limitSize) {

    }

    @Override
    public void afterSetLimitSize(MediaPickerControlable control, long limitSize) {

    }

    @Override
    public void beforeSetLimitDuration(MediaPickerControlable control, long limitDuration) {

    }

    @Override
    public void afterSetLimitDuration(MediaPickerControlable control, long limitDuration) {

    }

    @Override
    public void beforeSetFilterLimitMedia(MediaPickerControlable control, boolean filterLimitMedia) {

    }

    @Override
    public void afterSetFilterLimitMedia(MediaPickerControlable control, boolean filterLimitMedia) {

    }

    @Override
    public void beforeSetColumnCount(MediaPickerControlable control, int columnCount) {

    }

    @Override
    public void afterSetColumnCount(MediaPickerControlable control, int columnCount) {

    }

    @Override
    public void beforeSetStartThirdPreview(boolean startThirdPreview) {

    }

    @Override
    public void afterSetStartThirdPreview(boolean startThirdPreview) {

    }

    @Override
    public void beforeSetImageEngine(MediaPickerControlable control, ImageEngine engine) {

    }

    @Override
    public void afterSetImageEngine(MediaPickerControlable control, ImageEngine engine) {

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
