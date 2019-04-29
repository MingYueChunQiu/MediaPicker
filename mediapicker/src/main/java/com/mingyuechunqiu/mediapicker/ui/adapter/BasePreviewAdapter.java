package com.mingyuechunqiu.mediapicker.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseViewHolder;
import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;
import com.mingyuechunqiu.mediapicker.feature.picker.MediaPicker;

import java.io.File;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/28
 *     desc   : 预览功能列表适配器基类
 *              继承自BaseMediaPickerAdapter
 *     version: 1.0
 * </pre>
 */
public abstract class BasePreviewAdapter<T extends MediaAdapterItem, K extends BaseViewHolder> extends BaseMediaPickerAdapter<T, K> {

    public BasePreviewAdapter(int layoutResId, @Nullable List<T> data, int maxSelectCount, long limitSize, long limitDuration, OnItemSelectChangedListener listener) {
        super(layoutResId, data, maxSelectCount, limitSize, limitDuration, listener);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MediaAdapterItem item) {
        AppCompatImageView ivShow = helper.getView(R.id.iv_mp_preview_item_show);
        if (ivShow != null) {
            MediaPicker.getImageEngine().showImage(mContext,
                    new File(item.getInfo().getFilePath()), R.drawable.mp_back,
                    R.drawable.mp_back, ivShow);
        }
        helper.setChecked(R.id.cb_mp_preview_item_checked, item.isChecked())
                .setOnCheckedChangeListener(R.id.cb_mp_preview_item_checked, new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        handleItemChecked(buttonView, isChecked, item);
                    }
                });
    }
}
