package com.mingyuechunqiu.mediapicker.feature.main.detail;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseViewHolder;
import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;
import com.mingyuechunqiu.mediapicker.feature.picker.MediaPicker;
import com.mingyuechunqiu.mediapicker.ui.adapter.BaseMediaPickerAdapter;

import java.io.File;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/23
 *     desc   : 多媒体选择器列表适配器
 *              继承自BaseMediaPickerAdapter
 *     version: 1.0
 * </pre>
 */
class MediaPickerMainAdapter extends BaseMediaPickerAdapter<MediaAdapterItem, BaseViewHolder> {

    MediaPickerMainAdapter(int layoutResId, @Nullable List<MediaAdapterItem> data, int maxSelectCount, long limitSize, long limitDuration, BaseMediaPickerAdapter.OnItemSelectChangedListener listener) {
        super(layoutResId, data, maxSelectCount, limitSize, limitDuration, listener);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MediaAdapterItem item) {
        helper.setChecked(R.id.cb_mp_media_item_check, item.isChecked());
        helper.setOnCheckedChangeListener(R.id.cb_mp_media_item_check, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handleItemChecked(buttonView, isChecked, item);
            }
        })
                .addOnClickListener(R.id.iv_mp_media_item_show);
        AppCompatImageView ivShow = helper.getView(R.id.iv_mp_media_item_show);
        if (ivShow != null) {
            MediaPicker.getImageEngine().showImage(mContext,
                    new File(item.getInfo().getFilePath()),
                    R.drawable.mp_back, R.drawable.mp_back, ivShow);
        }
    }
}
