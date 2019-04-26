package com.mingyuechunqiu.mediapicker.feature.main.detail;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class MediaPickerPresenter extends MediaPickerContract.Presenter<MediaPickerContract.View> {

    private MediaPickerConfig mConfig;
    private Handler mHandler;

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
        ToolbarUtils.initToolbar(activity, toolbar, mConfig.getMediaPickerType());
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
        //延迟加载数据，避免页面卡顿
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
        }, 100);
    }

    @Override
    public void release() {
        mConfig = null;
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
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
        List<MediaAdapterItem> list = new ArrayList<>();
        switch (mConfig.getMediaPickerType()) {
            case TYPE_IMAGE:
                list = getImages(rvRef.get().getContext());
                break;
            case TYPE_AUDIO:
                list = getAudios(rvRef.get().getContext());
                break;
            case TYPE_VIDEO:
                list = getVideos(rvRef.get().getContext());
                break;
            default:
                break;
        }
        final MediaPickerMainAdapter adapter = new MediaPickerMainAdapter(R.layout.mp_rv_media_item, list,
                mConfig.getMaxSelectMediaCount(), mConfig.getLimitSize(), mConfig.getLimitDuration(),
                new MediaPickerMainAdapter.OnItemSelectChangedListener() {
                    @Override
                    public void onItemSelectChanged(boolean canConfirm, int selectedCount, int maxSelectedCount,
                                                    MediaAdapterItem item) {
                        handelConfirmView(canConfirm, selectedCount, maxSelectedCount, tvConfirm);
                    }
                });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (checkViewRefIsNull()) {
                    return;
                }
                //noinspection unchecked
                mViewRef.get().showPreview(adapter.getData(), position);
            }
        });
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                rvRef.get().setAdapter(adapter);
                if (!checkViewRefIsNull()) {
                    mViewRef.get().hideLoading();
                }
            }
        });
    }

    private List<MediaAdapterItem> getImages(Context context) {
        final List<MediaAdapterItem> list = new ArrayList<>();
        if (context == null) {
            return list;
        }
        MediaUtils.getImages(context, new MediaUtils.BrowseMediaInfoCallback() {
            @Override
            public void onBrowseMediaInfo(@NonNull MediaInfo info) {
                boolean hide = mConfig.isFilterLimitMedia() &&
                        (mConfig.getLimitSize() != SET_INVALID && info.getSize() > mConfig.getLimitSize());
                if (!hide) {
                    addMediaInfoItem(list, info);
                }
            }
        });
        return list;
    }

    private List<MediaAdapterItem> getAudios(Context context) {
        final List<MediaAdapterItem> list = new ArrayList<>();
        if (context == null) {
            return list;
        }
        MediaUtils.getAudios(context, new MediaUtils.BrowseMediaInfoCallback() {
            @Override
            public void onBrowseMediaInfo(@NonNull MediaInfo info) {

                boolean hide = mConfig.isFilterLimitMedia() &&
                        ((mConfig.getLimitSize() != SET_INVALID && info.getSize() > mConfig.getLimitSize()) ||
                                (mConfig.getLimitDuration() != SET_INVALID && info.getDuration() > mConfig.getLimitDuration()));
                if (!hide) {
                    addMediaInfoItem(list, info);
                }
            }
        });
        return list;
    }

    private List<MediaAdapterItem> getVideos(Context context) {
        final List<MediaAdapterItem> list = new ArrayList<>();
        if (context == null) {
            return list;
        }
        MediaUtils.getVideos(context, new MediaUtils.BrowseMediaInfoCallback() {
            @Override
            public void onBrowseMediaInfo(@NonNull MediaInfo info) {
                boolean hide = mConfig.isFilterLimitMedia() &&
                        ((mConfig.getLimitSize() != SET_INVALID && info.getSize() > mConfig.getLimitSize()) ||
                                (mConfig.getLimitDuration() != SET_INVALID && info.getDuration() > mConfig.getLimitDuration()));
                if (!hide) {
                    addMediaInfoItem(list, info);
                }
            }
        });
        return list;
    }

    private void addMediaInfoItem(@NonNull List<MediaAdapterItem> list, MediaInfo info) {
        MediaAdapterItem item = new MediaAdapterItem();
        item.setInfo(info);
        list.add(item);
    }
}
