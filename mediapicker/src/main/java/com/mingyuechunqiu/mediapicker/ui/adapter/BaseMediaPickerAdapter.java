package com.mingyuechunqiu.mediapicker.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;
import com.mingyuechunqiu.mediapicker.data.bean.MediaInfo;
import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.data.config.MediaPickerFilter;
import com.mingyuechunqiu.mediapicker.feature.picker.MediaPicker;

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

    protected List<MediaInfo> mSelectedList;//已选择的Item集合
    private int mMaxSelectedCount;//最多可以选择的item数量
    private long mLimitSize;//限制大小
    private long mLimitDuration;//限制时长
    private List<String> mLimitSuffixTypeList;//限制只能显示的多媒体后缀类型列表
    private MediaPickerFilter mFilter;//多媒体过滤器
    private OnItemSelectChangedListener mListener;//Item选择监听器

    public BaseMediaPickerAdapter(int layoutResId, @Nullable List<T> data,
                                  OnItemSelectChangedListener listener) {
        super(layoutResId, data);
        MediaPickerConfig config = MediaPicker.getInstance().getMediaPickerControl().getMediaPickerStore()
                .getMediaPickerConfig();
        mMaxSelectedCount = config.getMaxSelectMediaCount();
        mSelectedList = new ArrayList<>(mMaxSelectedCount);
        if (data != null) {
            for (MediaAdapterItem item : data) {
                if (item.isChecked()) {
                    mSelectedList.add(item.getInfo());
                }
            }
        }
        mLimitSize = config.getLimitSize();
        mLimitDuration = config.getLimitDuration();
        mLimitSuffixTypeList = config.getLimitSuffixTypeList();
        mFilter = config.getMediaPickerFilter();
        mListener = listener;
    }

    public List<MediaInfo> getSelectedMediaList() {
        return mSelectedList;
    }

    /**
     * 处理Item的选中情况
     *
     * @param buttonView 选择按钮控件
     * @param item       选择的Item
     */
    protected void handleItemChecked(CompoundButton buttonView, MediaAdapterItem item) {
        if (buttonView == null || item == null) {
            return;
        }
        if (!item.isChecked()) {
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
            if (mLimitSuffixTypeList != null && mLimitSuffixTypeList.size() > 0) {
                boolean matched = false;
                for (String suffix : mLimitSuffixTypeList) {
                    if (!TextUtils.isEmpty(item.getInfo().getSuffixType()) &&
                            item.getInfo().getSuffixType().equalsIgnoreCase(suffix)) {
                        matched = true;
                        break;
                    }
                }
                if (!matched) {
                    handleLimitItem(buttonView, "不能选择该格式类型文件");
                    return;
                }
            }
            if (mFilter != null && mFilter.filter(item.getInfo())) {
                handleLimitItem(buttonView, TextUtils.isEmpty(mFilter.getFilteredHint())
                        ? "该项已被过滤，不能选择" : mFilter.getFilteredHint());
                return;
            }
            mSelectedList.add(item.getInfo());
        } else {
            mSelectedList.remove(item.getInfo());
        }
        item.setChecked(!item.isChecked());
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
