package com.mingyuechunqiu.mediapicker.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;
import com.mingyuechunqiu.mediapicker.data.bean.MediaInfo;

import java.util.ArrayList;
import java.util.List;

import static com.mingyuechunqiu.mediapicker.data.constants.Constants.SET_INVALID;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/26
 *     desc   : 多媒体选择器适配器基类
 *              继承自BaseQuickAdapter
 *     version: 1.0
 * </pre>
 */
public abstract class BaseMediaPickerAdapter<T extends MediaAdapterItem, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    private int mMaxSelectedCount;//最多可以选择的item数量
    private long mLimitSize;//限制大小
    private long mLimitDuration;//限制时长
    private List<MediaInfo> mSelectedList;//已选择的Item集合
    private OnItemSelectChangedListener mListener;//Item选择监听器

    public BaseMediaPickerAdapter(int layoutResId, @Nullable List<T> data, int maxSelectCount,
                                  long limitSize, long limitDuration,
                                  OnItemSelectChangedListener listener) {
        super(layoutResId, data);
        mMaxSelectedCount = maxSelectCount;
        if (mMaxSelectedCount < 1) {
            mMaxSelectedCount = 1;
        }
        mSelectedList = new ArrayList<>(mMaxSelectedCount);
        if (data != null) {
            for (MediaAdapterItem item : data) {
                if (item.isChecked()) {
                    mSelectedList.add(item.getInfo());
                }
            }
        }
        mLimitSize = limitSize;
        mLimitDuration = limitDuration;
        mListener = listener;
    }

    public List<MediaInfo> getSelectedMediaList() {
        return mSelectedList;
    }

    /**
     * 处理Item的选中情况
     *
     * @param buttonView 选择按钮控件
     * @param isChecked  是否被选中
     * @param item       对应Item
     */
    protected void handleItemChecked(CompoundButton buttonView, boolean isChecked, MediaAdapterItem item) {
        if (buttonView == null || item == null) {
            return;
        }
        item.setChecked(isChecked);
        if (isChecked) {
            if (mSelectedList.size() >= mMaxSelectedCount) {
                handleLimitItem(buttonView, "最多只能选择" + mMaxSelectedCount + "项");
                return;
            }
            if (mLimitSize != SET_INVALID && item.getInfo().getSize() > mLimitSize) {
                handleLimitItem(buttonView, "大小不能超过" + (mLimitSize / 1024 / 1024) + "M");
                return;
            }
            if (mLimitDuration != SET_INVALID && item.getInfo().getDuration() > mLimitDuration) {
                handleLimitItem(buttonView, "时长不能超过" + (mLimitDuration / 1000) + "秒");
                return;
            }
            mSelectedList.add(item.getInfo());
        } else {
            mSelectedList.remove(item.getInfo());
        }
        if (mListener != null) {
            mListener.onItemSelectChanged(mSelectedList.size() != 0,
                    mSelectedList.size(), mMaxSelectedCount, item);
        }
    }

    /**
     * 处理被限制的Item
     *
     * @param buttonView 选择按钮控件
     * @param hint       提示文本
     */
    private void handleLimitItem(CompoundButton buttonView, String hint) {
        if (buttonView == null || TextUtils.isEmpty(hint) || mContext == null) {
            return;
        }
        Toast.makeText(mContext, hint, Toast.LENGTH_SHORT).show();
        buttonView.setChecked(false);
    }

    /**
     * Item选择监听器
     */
    public interface OnItemSelectChangedListener {

        /**
         * 当Item被选择时调用
         *
         * @param canConfirm       是否满足确认条件
         * @param selectedCount    已选择数量
         * @param maxSelectedCount 最大可选择数量
         * @param item             被选择的Item
         */
        void onItemSelectChanged(boolean canConfirm, int selectedCount, int maxSelectedCount, MediaAdapterItem item);
    }
}
