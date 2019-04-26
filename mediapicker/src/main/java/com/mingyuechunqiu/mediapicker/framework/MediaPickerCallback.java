package com.mingyuechunqiu.mediapicker.framework;

import com.mingyuechunqiu.mediapicker.data.bean.MediaInfo;

import java.util.ArrayList;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/24
 *     desc   : 多媒体选择器回调接口
 *     version: 1.0
 * </pre>
 */
public interface MediaPickerCallback {

    /**
     * 当多媒体选择完成确认时回调
     *
     * @param list 已选择的多媒体数据集合
     */
    void onConfirmMediaPicked(ArrayList<MediaInfo> list);
}
