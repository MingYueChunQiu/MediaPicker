package com.mingyuechunqiu.mediapicker.feature.main.detail;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseViewHolder;
import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;
import com.mingyuechunqiu.mediapicker.data.bean.MediaInfo;
import com.mingyuechunqiu.mediapicker.data.constants.MediaPickerType;
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

    MediaPickerMainAdapter(int layoutResId, @Nullable List<MediaAdapterItem> data, BaseMediaPickerAdapter.OnItemSelectChangedListener listener) {
        super(layoutResId, data, listener);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MediaAdapterItem item) {
        if (helper == null || item == null) {
            return;
        }
        //noinspection deprecation
        helper.setOnClickListener(R.id.cb_mp_media_item_check, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleItemChecked((CompoundButton) v, item);
            }
        });
        helper.setChecked(R.id.cb_mp_media_item_check, item.isChecked())
                .addOnClickListener(R.id.iv_mp_media_item_show);
        AppCompatImageView ivShow = helper.getView(R.id.iv_mp_media_item_show);
        MediaInfo itemInfo = item.getInfo();
        if (ivShow != null && itemInfo != null) {
            if (MediaPickerType.TYPE_AUDIO.equals(itemInfo.getType())) {
                ivShow.setImageResource(R.drawable.music);
            } else {
                MediaPicker.getImageEngine().showImage(mContext,
                        new File(itemInfo.getFilePath()),
                        R.drawable.mp_media_placeholder, R.drawable.mp_media_error, ivShow);
            }
        }
        if (itemInfo != null && MediaPickerType.TYPE_AUDIO.equals(itemInfo.getType())) {
            AppCompatTextView textView = helper.getView(R.id.tv_mp_media_item_content);
            textView.setText(itemInfo.getName().split(itemInfo.getSuffixType())[0]);
        }
        if (item.isChecked()) {
            mSelectedList.add(itemInfo);
        } else {
            mSelectedList.remove(itemInfo);
        }
    }
}
