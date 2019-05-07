package com.mingyuechunqiu.mediapicker.feature.main.detail;

import android.support.annotation.Nullable;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingyuechunqiu.mediapicker.R;

import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/30
 *     desc   : 多媒体所属文件夹下拉列表适配器
 *              继承自BaseQuickAdapter
 *     version: 1.0
 * </pre>
 */
class MediaBucketAdapter extends BaseQuickAdapter<MediaBucketAdapter.BucketAdapterItem, BaseViewHolder> {

    private int mSelectedPosition;//选中的item索引位置
    private MediaPickerPresenter.OnSelectedBucketItemListener mListener;

    MediaBucketAdapter(int layoutResId, @Nullable List<BucketAdapterItem> data, MediaPickerPresenter.OnSelectedBucketItemListener listener) {
        super(layoutResId, data);
        mSelectedPosition = 0;
        mListener = listener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BucketAdapterItem item) {
        if (helper == null || item == null) {
            return;
        }
        helper.setText(R.id.tv_mp_rv_bucket_item_name, item.bucketName)
                .setChecked(R.id.rb_mp_rv_bucket_item_selected, item.selected);
        helper.setOnCheckedChangeListener(R.id.rb_mp_rv_bucket_item_selected, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && mSelectedPosition != helper.getAdapterPosition()) {
                    BucketAdapterItem selectedItem = getItem(mSelectedPosition);
                    if (selectedItem != null) {
                        selectedItem.setSelected(false);
                    }
                    item.setSelected(true);
                    mSelectedPosition = helper.getAdapterPosition();
                    notifyDataSetChanged();
                    if (mListener != null) {
                        mListener.onSelectedBucketItem(item);
                    }
                }
            }
        });
    }

    /**
     * 获取被选中的item索引位置
     *
     * @return 返回索引位置
     */
    int getSelectedPosition() {
        return mSelectedPosition;
    }

    /**
     * 设置被选中的item索引位置
     */
    void setSelectedPosition(int selectedPosition) {
        mSelectedPosition = selectedPosition;
    }

    static class BucketAdapterItem {

        private String bucketId;//多媒体所属文件夹ID
        private String bucketName;//多媒体所属文件夹名称
        private boolean selected;//是否被选中

        public String getBucketId() {
            return bucketId;
        }

        public void setBucketId(String bucketId) {
            this.bucketId = bucketId;
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}
