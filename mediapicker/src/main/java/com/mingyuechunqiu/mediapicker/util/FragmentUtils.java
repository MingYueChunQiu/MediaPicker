package com.mingyuechunqiu.mediapicker.util;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
