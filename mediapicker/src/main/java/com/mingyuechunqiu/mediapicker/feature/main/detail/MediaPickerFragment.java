package com.mingyuechunqiu.mediapicker.feature.main.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;
import com.mingyuechunqiu.mediapicker.data.bean.MediaInfo;
import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.feature.main.container.MediaPickerActivity;
import com.mingyuechunqiu.mediapicker.feature.preview.image.PreviewImageFragment;
import com.mingyuechunqiu.mediapicker.framework.MediaPickerCallback;
import com.mingyuechunqiu.mediapicker.ui.fragment.BasePresenterFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.mingyuechunqiu.mediapicker.data.constants.Constants.MP_HIDE_LOADING;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/23
 *     desc   : 多媒体选择界面
 *              继承自BasePresenterFragment
 *     version: 1.0
 * </pre>
 */
public class MediaPickerFragment extends BasePresenterFragment<MediaPickerContract.View<MediaPickerContract.Presenter>, MediaPickerContract.Presenter> implements MediaPickerFragmentable, MediaPickerContract.View<MediaPickerContract.Presenter> {

    private RecyclerView rvList;

    private MediaPickerConfig mConfig;
    private boolean beInPreview;//标记是否处于预览状态
    private PreviewImageFragment mPreviewImageFg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mp_fragment_media_picker, container, false);
        Toolbar toolbar = view.findViewById(R.id.tb_mp_media_picker_bar);
        mPresenter.initToolbar(getActivity(), toolbar);
        final AppCompatTextView tvConfirm = view.findViewById(R.id.tv_mp_media_picker_confirm);
        rvList = view.findViewById(R.id.rv_mp_media_picker_list);

        tvConfirm.setEnabled(false);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rvList != null && rvList.getAdapter() instanceof MediaPickerMainAdapter) {
                    List<MediaInfo> list = ((MediaPickerMainAdapter) rvList.getAdapter()).getSelectedMediaList();
                    if (getActivity() instanceof MediaPickerCallback) {
                        ((MediaPickerCallback) getActivity()).onConfirmMediaPicked((ArrayList<MediaInfo>) list);
                    }
                }
            }
        });
        mPresenter.initMediaItemList(rvList, tvConfirm);
        ViewModelProviders.of(this).get(MediaPickerMainViewModel.class)
                .getSelectedCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer == null || mConfig == null) {
                    return;
                }
                mPresenter.handelConfirmView(integer != 0, integer,
                        mConfig.getMaxSelectMediaCount(), new WeakReference<>(tvConfirm));
                if (rvList != null && rvList.getAdapter() != null) {
                    rvList.getAdapter().notifyDataSetChanged();
                }
            }
        });
        return view;
    }

    @Override
    protected MediaPickerContract.Presenter<MediaPickerContract.View> initPresenter() {
        return new MediaPickerPresenter(mConfig);
    }

    @Override
    public void setPresenter(@NonNull MediaPickerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void releaseOnDestroyView() {
        mConfig = null;
        removePreviewFragment(mPreviewImageFg);
    }

    @Override
    protected void releaseOnDestroy() {

    }

    public static MediaPickerFragment newInstance(MediaPickerConfig config) {
        MediaPickerFragment fragment = new MediaPickerFragment();
        fragment.mConfig = config;
        if (fragment.mConfig == null) {
            fragment.mConfig = new MediaPickerConfig();
        }
        return fragment;
    }

    @Override
    public void handleBack() {
        if (beInPreview) {
            removePreviewFragment(mPreviewImageFg);
        } else {
            if (getActivity() instanceof MediaPickerActivity) {
                ((MediaPickerActivity) getActivity()).finishActivity();
            }
        }
    }

    @Override
    public void hideLoading() {
        if (getActivity() instanceof FragmentCallback) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(MP_HIDE_LOADING, true);
            ((FragmentCallback) getActivity()).onCall(this, bundle);
        }
    }

    @Override
    public void showPreview(List<MediaAdapterItem> list, int index) {
        if (list == null) {
            return;
        }
        mPreviewImageFg = PreviewImageFragment.newInstance(list, index);
        getChildFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.mp_scale_in_magnify, R.anim.mp_scale_out_shrink)
                .add(R.id.fl_mp_media_picker_preview_container, mPreviewImageFg)
                .commitAllowingStateLoss();
        beInPreview = true;
    }

    /**
     * 移除预览界面
     *
     * @param fragments 要被移除的界面
     */
    private void removePreviewFragment(Fragment... fragments) {
        if (fragments == null) {
            return;
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.mp_scale_in_magnify, R.anim.mp_scale_out_shrink);
        for (Fragment f : fragments) {
            if (f != null) {
                transaction.remove(f);
            }
        }
        transaction.commitAllowingStateLoss();
        beInPreview = false;
    }
}
