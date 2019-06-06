package com.mingyuechunqiu.mediapicker.data.config;

import com.mingyuechunqiu.mediapicker.data.bean.MediaInfo;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/6
 *     desc   : 多媒体过滤器接口适配器，提供默认实现
 *              实现MediaPickerFilter
 *     version: 1.0
 * </pre>
 */
public class MediaPickerFilterAdapter implements MediaPickerFilter {

    @Override
    public boolean filter(MediaInfo info) {
        return false;
    }

    @Override
    public String getFilteredHint() {
        return null;
    }

    @Override
    public boolean hideFiltered() {
        return false;
    }
}
