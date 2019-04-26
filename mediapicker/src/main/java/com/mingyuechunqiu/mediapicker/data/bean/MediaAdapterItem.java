package com.mingyuechunqiu.mediapicker.data.bean;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/25
 *     desc   : 多媒体列表Item类
 *     version: 1.0
 * </pre>
 */
public class MediaAdapterItem {

    private MediaInfo info;//多媒体信息对象
    private boolean checked;//标记是否被选中

    public MediaInfo getInfo() {
        return info;
    }

    public void setInfo(MediaInfo info) {
        this.info = info;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
