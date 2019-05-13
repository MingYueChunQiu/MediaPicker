package com.mingyuechunqiu.mediapicker.data.constants;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/23
 *     desc   : 常量类
 *     version: 1.0
 * </pre>
 */
public class Constants {

    /**
     * 主题类型常量类
     */
    public static class ThemeTypeConstants {
        public static final int TYPE_LIGHT = 0;//浅色主题
        public static final int TYPE_DARK = 1;//深色主题
    }

    public static final int SET_INVALID = -1;//无效设置

    public static final String PREFIX_EXTRA = "EXTRA_";//Intent数据前缀

    public static final String PREFIX_BUNDLE = "BUNDLE_";//Bundle数据前缀

    //多媒体选择器配置信息
    public static final String EXTRA_MEDIA_PICKER_CONFIG = PREFIX_EXTRA + "media_picker_config";

    //已选择的多媒体数据列表
    public static final String EXTRA_PICKED_MEDIA_LIST = PREFIX_EXTRA + "picked_media_list";

    //打开多媒体选择器界面请求码
    public static final int MP_REQUEST_START_MEDIA_PICKER = 1;

    //隐藏加载
    public static final String MP_HIDE_LOADING = PREFIX_EXTRA + "hide_loading";
}
