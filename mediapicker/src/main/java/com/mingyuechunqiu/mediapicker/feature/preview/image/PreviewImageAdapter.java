package com.mingyuechunqiu.mediapicker.feature.preview.image;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;
import com.mingyuechunqiu.mediapicker.ui.adapter.BasePreviewAdapter;

import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/25
 *     desc   : 预览图片列表适配器
 *              继承自BasePreviewAdapter
 *     version: 1.0
 * </pre>
 */
class PreviewImageAdapter extends BasePreviewAdapter<MediaAdapterItem, BaseViewHolder> {

    PreviewImageAdapter(int layoutResId, @Nullable List<MediaAdapterItem> data, OnItemSelectChangedListener listener) {
        super(layoutResId, data, listener);
    }
}
