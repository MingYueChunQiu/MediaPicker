package com.mingyuechunqiu.mediapicker.data.config;

import com.mingyuechunqiu.mediapicker.data.bean.MediaInfo;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/5/20
 *     desc   : 多媒体过滤器接口
 *     version: 1.0
 * </pre>
 */
public interface MediaPickerFilter {

    /**
     * 设置条件过滤多媒体Item
     *
     * @param info 多媒体信息对象
     * @return 满足过滤条件的Item返回true，否则返回false
     */
    boolean filter(MediaInfo info);
}
