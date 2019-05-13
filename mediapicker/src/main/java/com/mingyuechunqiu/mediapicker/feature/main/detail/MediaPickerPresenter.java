package com.mingyuechunqiu.mediapicker.feature.main.detail;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;
import com.mingyuechunqiu.mediapicker.data.bean.MediaInfo;
import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.util.MediaUtils;
import com.mingyuechunqiu.mediapicker.util.ThreadPoolUtils;
import com.mingyuechunqiu.mediapicker.util.ToolbarUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.mingyuechunqiu.mediapicker.data.constants.Constants.SET_INVALID;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/25
 *     desc   : 多媒体选择模块MVP中P层
 *              继承自MediaPickerContract.Presenter
 *     version: 1.0
 * </pre>
 */
class MediaPickerPresenter extends MediaPickerContract.Presenter<MediaPickerContract.View> {

    private MediaPickerConfig mConfig;
    private Handler mHandler;
    private List<MediaAdapterItem> mAllData;//所选类型所有文件夹中资源集合
    private PopupWindow pwBuckets;

    MediaPickerPresenter(MediaPickerConfig config) {
        mConfig = config;
        checkOrCreateMediaPickerConfig();
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void initToolbar(FragmentActivity activity, @NonNull Toolbar toolbar) {
        if (!(activity instanceof AppCompatActivity)) {
            return;
        }
        ToolbarUtils.initToolbar(activity, toolbar, mConfig);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkViewRefIsNull()) {
                    return;
                }
                mViewRef.get().handleBack();
            }
        });
    }

    @Override
    public void initMediaItemList(RecyclerView recyclerView, AppCompatTextView textView) {
        if (recyclerView == null || textView == null) {
            return;
        }
        final WeakReference<RecyclerView> rvRef = new WeakReference<>(recyclerView);
        final WeakReference<AppCompatTextView> tvConfirm = new WeakReference<>(textView);
        rvRef.get().setLayoutManager(new GridLayoutManager(rvRef.get().getContext(), mConfig.getColumnCount()));
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ThreadPoolUtils.executeAction(new Runnable() {
                    @Override
                    public void run() {
                        getMediaItemList(rvRef, tvConfirm);
                    }
                });
            }
        }, 20);
    }

    @Override
    public void release() {
        if (pwBuckets != null) {
            pwBuckets.dismiss();
            pwBuckets = null;
        }
        mConfig = null;
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (mAllData != null) {
            mAllData.clear();
            mAllData = null;
        }
    }

    /**
     * 处理确认按钮情况
     *
     * @param canConfirm       是否可以进行确认
     * @param selectedCount    已选择数量
     * @param maxSelectedCount 最大选择数量
     * @param tvConfirm        确认选择控件
     */
    @Override
    void handelConfirmView(boolean canConfirm, int selectedCount, int maxSelectedCount, @NonNull WeakReference<AppCompatTextView> tvConfirm) {
        if (tvConfirm.get() == null) {
            return;
        }
        if (canConfirm) {
            tvConfirm.get().setText(
                    String.format(Locale.getDefault(),
                            "确认(%d/%d)", selectedCount, maxSelectedCount));
            tvConfirm.get().setEnabled(true);
        } else {
            tvConfirm.get().setEnabled(false);
            tvConfirm.get().setText(R.string.mp_confirm);
        }
    }

    /**
     * 获取多媒体所属文件夹名称
     *
     * @param context 上下文
     * @return 返回名称字符串
     */
    @Override
    String getBucketName(Context context) {
        String bucketName = "";
        if (context == null) {
            return bucketName;
        }
        switch (mConfig.getMediaPickerType()) {
            case TYPE_IMAGE:
                bucketName = context.getString(R.string.mp_all_images);
                break;
            case TYPE_AUDIO:
                bucketName = context.getString(R.string.mp_all_audios);
                break;
            case TYPE_VIDEO:
                bucketName = context.getString(R.string.mp_all_videos);
                break;
        }
        return bucketName;
    }

    @Override
    void setBucketViewDrawableBounds(AppCompatTextView tvName, int drawableResId) {
        if (tvName == null) {
            return;
        }
        int size = (int) getPxFromDp(tvName.getResources(), 14);
        Drawable drawable = tvName.getResources().getDrawable(drawableResId);
        drawable.setBounds(0, 0, size, size);
        tvName.setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    void handleOnClickBucketName(Context context, View v, final AppCompatTextView tvBucket,
                                 View vBucket, final RecyclerView rvMediaList) {
        if (context == null || context.getResources() == null ||
                v == null || tvBucket == null || vBucket == null) {
            return;
        }
        if (v.isSelected()) {
            dismissBucketList(tvBucket);
        } else {
            if (pwBuckets == null) {
                RecyclerView rvBucket = new RecyclerView(context);
                rvBucket.setOverScrollMode(View.OVER_SCROLL_NEVER);
                rvBucket.setLayoutManager(new LinearLayoutManager(context));
                initBucketList(context, rvBucket, (MediaPickerMainAdapter) rvMediaList.getAdapter(),
                        new MediaPickerPresenter.OnSelectedBucketItemListener() {

                            @Override
                            public void onSelectedBucketItem(MediaBucketAdapter.BucketAdapterItem item) {
                                handleOnSelectedBucketItem(item, rvMediaList, tvBucket);
                            }
                        });
                int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 2.2f / 10);
                if (rvBucket.getAdapter() != null) {
                    Paint paint = new Paint();
                    paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16, context.getResources().getDisplayMetrics()));
                    height = (int) ((paint.getFontMetrics().bottom - paint.getFontMetrics().top +
                            getPxFromDp(context.getResources(), 10) * 2) *
                            rvBucket.getAdapter().getItemCount());
                }
                pwBuckets = new PopupWindow(rvBucket, ViewGroup.LayoutParams.WRAP_CONTENT, height, true);
                pwBuckets.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        tvBucket.setSelected(false);
                        setBucketViewDrawableBounds(tvBucket, mConfig.getThemeConfig().getUpTriangleIconResId());
                    }
                });
            }
            if (pwBuckets != null) {
                pwBuckets.showAsDropDown(vBucket, (int) getPxFromDp(context.getResources(), 10), 0);
            }
            setBucketViewDrawableBounds(tvBucket, mConfig.getThemeConfig().getDownTriangleIconResId());
        }
        v.setSelected(!v.isSelected());
    }

    private void initBucketList(Context context, RecyclerView rvList, MediaPickerMainAdapter adapter, final OnSelectedBucketItemListener listener) {
        if (context == null || rvList == null || adapter == null || listener == null) {
            return;
        }
        final List<MediaBucketAdapter.BucketAdapterItem> list = new ArrayList<>();
        MediaBucketAdapter.BucketAdapterItem firstItem = new MediaBucketAdapter.BucketAdapterItem();
        String bucketName = getBucketName(context);
        firstItem.setBucketName(bucketName);
        firstItem.setSelected(true);
        list.add(firstItem);
        for (MediaAdapterItem item : adapter.getData()) {
            if (item.getInfo() != null) {
                String itemBucketId = item.getInfo().getBucketId();
                String itemBucketName = item.getInfo().getBucketName();
                if (itemBucketId != null && itemBucketName != null &&
                        !checkBucketInList(itemBucketId, list)) {
                    MediaBucketAdapter.BucketAdapterItem bucketAdapterItem = new MediaBucketAdapter.BucketAdapterItem();
                    bucketAdapterItem.setBucketId(itemBucketId);
                    bucketAdapterItem.setBucketName(itemBucketName);
                    list.add(bucketAdapterItem);
                }
            }
        }
        MediaBucketAdapter bucketAdapter = new MediaBucketAdapter(R.layout.mp_rv_bucket_item, list, listener);
        bucketAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MediaBucketAdapter mediaBucketAdapter = (MediaBucketAdapter) adapter;
                if (mediaBucketAdapter == null || mediaBucketAdapter.getSelectedPosition() == position) {
                    return;
                }
                MediaBucketAdapter.BucketAdapterItem selectedItem = mediaBucketAdapter.getItem(
                        mediaBucketAdapter.getSelectedPosition());
                if (selectedItem != null) {
                    selectedItem.setSelected(false);
                }
                MediaBucketAdapter.BucketAdapterItem item = mediaBucketAdapter.getItem(position);
                if (item != null) {
                    item.setSelected(true);
                }
                mediaBucketAdapter.setSelectedPosition(position);
                adapter.notifyDataSetChanged();
                listener.onSelectedBucketItem(item);
            }
        });
        rvList.setAdapter(bucketAdapter);
    }

    /**
     * 处理多媒体所属文件夹被选择点击事件
     *
     * @param item        被选择的多媒体所属文件夹Item
     * @param rvMediaList 多媒体列表
     * @param tvBucket    显示已选择的Bucket名称控件
     */
    private void handleOnSelectedBucketItem(MediaBucketAdapter.BucketAdapterItem item, RecyclerView rvMediaList, AppCompatTextView tvBucket) {
        if (item == null || rvMediaList == null || tvBucket == null) {
            return;
        }
        String bucketId = item.getBucketId();
        MediaPickerMainAdapter adapter = (MediaPickerMainAdapter) rvMediaList.getAdapter();
        if (adapter == null) {
            return;
        }
        if (mAllData == null) {
            mAllData = adapter.getData();
        }
        List<MediaAdapterItem> showList = new ArrayList<>();
        if (TextUtils.isEmpty(bucketId)) {
            showList = mAllData;
        } else {
            for (MediaAdapterItem mediaAdapterItem : mAllData) {
                if (mediaAdapterItem != null) {
                    MediaInfo info = mediaAdapterItem.getInfo();
                    if (info != null && info.getBucketId().equals(item.getBucketId())) {
                        showList.add(mediaAdapterItem);
                    }
                }
            }
        }
        adapter.setNewData(showList);
        tvBucket.setSelected(false);
        dismissBucketList(tvBucket);
    }

    private void dismissBucketList(AppCompatTextView tvBucket) {
        if (pwBuckets != null) {
            pwBuckets.dismiss();
        }
        setBucketViewDrawableBounds(tvBucket, mConfig.getThemeConfig().getUpTriangleIconResId());
    }

    private void checkOrCreateMediaPickerConfig() {
        if (mConfig == null) {
            mConfig = new MediaPickerConfig();
        }
    }

    /**
     * 获取多媒体数据列表
     *
     * @param rvRef     列表控件
     * @param tvConfirm 确认选择控件
     */
    private void getMediaItemList(final WeakReference<RecyclerView> rvRef, final WeakReference<AppCompatTextView> tvConfirm) {
        final List<MediaAdapterItem> list = new ArrayList<>();
        final MediaPickerMainAdapter[] adapter = new MediaPickerMainAdapter[1];
        MediaUtils.BrowseMediaInfoCallback callback = new MediaUtils.BrowseMediaInfoCallback() {
            @Override
            public void onPrepareBrowseMediaInfo() {
            }

            @Override
            public void onStartBrowseMediaInfo(int count) {
                for (int i = 0; i < count; i++) {
                    list.add(new MediaAdapterItem());
                }
                adapter[0] = new MediaPickerMainAdapter(R.layout.mp_rv_media_item, list,
                        mConfig.getMaxSelectMediaCount(), mConfig.getLimitSize(), mConfig.getLimitDuration(),
                        new MediaPickerMainAdapter.OnItemSelectChangedListener() {
                            @Override
                            public void onItemSelectChanged(boolean canConfirm, int selectedCount, int maxSelectedCount,
                                                            MediaAdapterItem item) {
                                handelConfirmView(canConfirm, selectedCount, maxSelectedCount, tvConfirm);
                            }
                        });
                adapter[0].setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        if (checkViewRefIsNull()) {
                            return;
                        }
                        //noinspection unchecked
                        mViewRef.get().showPreview(adapter.getData(), position, mConfig.getMediaPickerType());
                    }
                });
                adapter[0].openLoadAnimation(mConfig.getLoadAnimation());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        rvRef.get().setAdapter(adapter[0]);
                    }
                });

            }

            @Override
            public void onBrowseMediaInfo(int index, @NonNull MediaInfo info) {
                boolean hide = false;
                switch (mConfig.getMediaPickerType()) {
                    case TYPE_IMAGE:
                        hide = mConfig.isFilterLimitMedia() &&
                                (mConfig.getLimitSize() != SET_INVALID && info.getSize() > mConfig.getLimitSize());
                        break;
                    case TYPE_AUDIO:
                        hide = mConfig.isFilterLimitMedia() &&
                                ((mConfig.getLimitSize() != SET_INVALID && info.getSize() > mConfig.getLimitSize()) ||
                                        (mConfig.getLimitDuration() != SET_INVALID && info.getDuration() > mConfig.getLimitDuration()));
                        break;
                    case TYPE_VIDEO:
                        hide = mConfig.isFilterLimitMedia() &&
                                ((mConfig.getLimitSize() != SET_INVALID && info.getSize() > mConfig.getLimitSize()) ||
                                        (mConfig.getLimitDuration() != SET_INVALID && info.getDuration() > mConfig.getLimitDuration()));
                        break;
                    default:
                        break;
                }
                if (!hide) {
                    addMediaInfoItem(list, index, info);
                }
            }

            @Override
            public void onEndBrowseMediaInfo() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter[0].notifyDataSetChanged();
                        if (!checkViewRefIsNull()) {
                            mViewRef.get().hideLoading();
                        }
                    }
                });

            }
        };
        switch (mConfig.getMediaPickerType()) {
            case TYPE_IMAGE:
                MediaUtils.getImages(rvRef.get().getContext(), callback);
                break;
            case TYPE_AUDIO:
                MediaUtils.getAudios(rvRef.get().getContext(), callback);
                break;
            case TYPE_VIDEO:
                MediaUtils.getVideos(rvRef.get().getContext(), callback);
                break;
            default:
                break;
        }
    }

    /**
     * 向集合中添加多媒体信息
     *
     * @param list  多媒体适配器集合
     * @param index Item索引位置
     * @param info  多媒体信息
     */
    private void addMediaInfoItem(@NonNull List<MediaAdapterItem> list, int index, MediaInfo info) {
        if (index < 0 || index > list.size() - 1) {
            return;
        }
        MediaAdapterItem adapterItem = list.get(index);
        if (adapterItem == null) {
            return;
        }
        list.get(index).setInfo(info);
//        MediaAdapterItem item = new MediaAdapterItem();
//        item.setInfo(info);
//        list.add(item);
    }

    /**
     * 检查Bucket是否在列表中
     *
     * @param bucketId 要检测的BucketID
     * @param list     Bucket列表
     * @return 如果在列表中返回true，否则返回false
     */
    private boolean checkBucketInList(String bucketId, List<MediaBucketAdapter.BucketAdapterItem> list) {
        if (bucketId == null || list == null) {
            return false;
        }
        for (MediaBucketAdapter.BucketAdapterItem item : list) {
            if (bucketId.equals(item.getBucketId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将dp转换为px
     *
     * @param resources 资源管理器
     * @param dpVal     dp值
     * @return 返回px值
     */
    private float getPxFromDp(@NonNull Resources resources, float dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, resources.getDisplayMetrics());
    }

    /**
     * 选择多媒体文件所属文件夹监听器
     */
    interface OnSelectedBucketItemListener {

        /**
         * 当选择多媒体文件所属文件夹时回调
         *
         * @param item 多媒体所属文件夹item
         */
        void onSelectedBucketItem(MediaBucketAdapter.BucketAdapterItem item);
    }
}
