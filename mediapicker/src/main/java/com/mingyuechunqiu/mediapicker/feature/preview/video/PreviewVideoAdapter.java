package com.mingyuechunqiu.mediapicker.feature.preview.video;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;
import com.mingyuechunqiu.mediapicker.ui.adapter.BasePreviewAdapter;

import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/28
 *     desc   : 预览视频列表适配器
 *              继承自BasePreviewAdapter
 *     version: 1.0
 * </pre>
 */
class PreviewVideoAdapter extends BasePreviewAdapter<MediaAdapterItem, BaseViewHolder> {

    PreviewVideoAdapter(int layoutResId, @Nullable List<MediaAdapterItem> data, int maxSelectCount, long limitSize, long limitDuration, OnItemSelectChangedListener listener) {
        super(layoutResId, data, maxSelectCount, limitSize, limitDuration, listener);
    }

    @Override
    protected void convert(BaseViewHolder helper, MediaAdapterItem item) {
        super.convert(helper, item);
        helper.setVisible(R.id.iv_mp_preview_item_play, true)
                .addOnClickListener(R.id.iv_mp_preview_item_play);
    }
}
