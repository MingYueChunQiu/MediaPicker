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

    /**
     * 获取选择过滤Item时的提示文本（在hideFiltered为false时，使用才有效）
     * 返回null或""无效，不设置时，默认显示"该项已被过滤，不能选择"
     * 可以使用MediaPickerFilterAdapter，提供了默认实现，只重新自己需要的方法
     *
     * @return 返回提示字符串
     */
    String getFilteredHint();

    /**
     * 隐藏被过滤的Item
     *
     * @return 返回true表示隐藏，否则返回false
     */
    boolean hideFiltered();
}
