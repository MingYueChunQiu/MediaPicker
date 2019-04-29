package com.mingyuechunqiu.mediapicker.util;

import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/29
 *     desc   : Fragment工具类
 *     version: 1.0
 * </pre>
 */
public class FragmentUtils {

    /**
     * 移除Fragment
     *
     * @param fragmentManager Fragment管理器
     * @param enterResId      进入动画
     * @param exitResId       退出动画
     * @param fragments       要被移除的Fragment
     */
    public static void removeFragments(FragmentManager fragmentManager, @AnimRes @AnimatorRes int enterResId,
                                       @AnimRes @AnimatorRes int exitResId,
                                       Fragment... fragments) {
        if (fragmentManager == null || fragments == null) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction()
                .setCustomAnimations(enterResId, exitResId);
        for (Fragment f : fragments) {
            if (f != null) {
                transaction.remove(f);
            }
        }
        transaction.commitAllowingStateLoss();
    }
}
