package com.mingyuechunqiu.mediapicker.feature.main.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mingyuechunqiu.mediapicker.base.presenter.BaseAbstractPresenter;
import com.mingyuechunqiu.mediapicker.base.view.IBaseView;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;
import com.mingyuechunqiu.mediapicker.data.constants.MediaPickerType;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/25
 *     desc   : 主界面相关契约类，约定相互能实现调用的api
 *     version: 1.0
 * </pre>
 */
interface MediaPickerContract {

    interface View<P extends BaseAbstractPresenter> extends IBaseView<P> {

        void handleBack();

        void hideLoading();

        void showPreview(List<MediaAdapterItem> list, int index, MediaPickerType type);
    }

    abstract class Presenter<V extends View> extends BaseAbstractPresenter<V> {

        abstract void initToolbar(FragmentActivity activity, @NonNull Toolbar toolbar);

        /**
         * 初始化多媒体Item列表
         *
         * @param recyclerView 列表
         * @param textView     确认选择控件
         */
        abstract void initMediaItemList(RecyclerView recyclerView, AppCompatTextView textView);

        abstract void handelConfirmView(boolean canConfirm, int selectedCount, int maxSelectedCount, @NonNull WeakReference<AppCompatTextView> tvConfirm);

        abstract String getBucketName(Context context);

        abstract void setBucketViewDrawableBounds(AppCompatTextView tvName, int drawableResId);

        /**
         * 处理多媒体所属文件夹名称控件点击事件
         *
         * @param context     上下文
         * @param v           被选中的View
         * @param tvBucket    显示已选择的Bucket名称控件
         * @param vBucket     底部Bucket依附View
         * @param rvMediaList 多媒体列表
         */
        abstract void handleOnClickBucketName(Context context, android.view.View v, final AppCompatTextView tvBucket,
                                              android.view.View vBucket, RecyclerView rvMediaList);
    }
}
