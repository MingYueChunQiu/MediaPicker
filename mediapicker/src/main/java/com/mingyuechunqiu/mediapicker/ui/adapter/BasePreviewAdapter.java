package com.mingyuechunqiu.mediapicker.ui.adapter;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import android.view.View;
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

    public BasePreviewAdapter(int layoutResId, @Nullable List<T> data, OnItemSelectChangedListener listener) {
        super(layoutResId, data, listener);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MediaAdapterItem item) {
        AppCompatImageView ivShow = helper.getView(R.id.iv_mp_preview_item_show);
        if (ivShow != null) {
            MediaPicker.getImageEngine().showImage(mContext,
                    new File(item.getInfo().getFilePath()), R.drawable.mp_media_placeholder,
                    R.drawable.mp_media_error, ivShow);
        }
        //防止控件复用，导致setChecked触发上一个移除界面的控件的onCheckedChanged时间
        //用setOnClickListener而不用setOnCheckedChangeListener
        //noinspection deprecation
        helper.setChecked(R.id.cb_mp_preview_item_checked, item.isChecked())
                .setOnClickListener(R.id.cb_mp_preview_item_checked, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleItemChecked((CompoundButton) v, item);
                    }
                });
    }
}
