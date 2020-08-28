package com.lib.base.view.bottom;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import com.lib.base.utils.LTools;
import com.lib.base.view.R;


/**
 * Created by Mouse on 2017/10/11.
 */

public class LBottomSheet extends Dialog {

    // 动画时长
    protected final static int mAnimationDuration = 200;
    // 持有 ContentView，为了做动画
    protected View mContentView;
    protected boolean mIsAnimating = false;

    protected OnBottomSheetShowListener mOnBottomSheetShowListener;

    public LBottomSheet(@NonNull Context context) {
        super(context, R.style.LBottomSheet);
    }

    protected void initWindow() {
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 在底部，宽度撑满
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;

        int[] screenSize = LTools.getScreenSize(getContext());
        int screenWidth = screenSize[0];
        int screenHeight = screenSize[1];
        params.width = screenWidth < screenHeight ? screenWidth : screenHeight;
        getWindow().setAttributes(params);
        setCanceledOnTouchOutside(true);
    }

    public void setOnBottomSheetShowListener(OnBottomSheetShowListener onBottomSheetShowListener) {
        mOnBottomSheetShowListener = onBottomSheetShowListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
    }

    @Override
    public void setContentView(int layoutResID) {
        mContentView = LayoutInflater.from(getContext()).inflate(layoutResID, null);
        super.setContentView(mContentView);
    }

    @Override
    public void setContentView(@NonNull View view) {
        mContentView = view;
        super.setContentView(view);
    }

    @Override
    public void setContentView(@NonNull View view, ViewGroup.LayoutParams params) {
        mContentView = view;
        super.setContentView(view, params);
    }

    /**
     * BottomSheet升起动画
     */
    private void animateUp() {
        if (mContentView == null) {
            return;
        }
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f
        );
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translate);
        set.addAnimation(alpha);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(mAnimationDuration);
        set.setFillAfter(true);
        mContentView.startAnimation(set);
        Log.d("lui_base", " show onAnimationStart");
    }

    /**
     * BottomSheet降下动画
     */
    private void animateDown() {
        if (mContentView == null) {
            return;
        }
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f
        );
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translate);
        set.addAnimation(alpha);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(mAnimationDuration);
        set.setFillAfter(true);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mIsAnimating = true;
                Log.d("lui_base", " dismiss onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIsAnimating = false;
                Log.d("lui_base", "dismiss onAnimationEnd");
                /**
                 * Bugfix： Attempting to destroy the window while drawing!
                 */
                mContentView.post(() -> {
                    try {
                        LBottomSheet.super.dismiss();
                    } catch (Exception e) {
                        Log.e("error", Log.getStackTraceString(e));
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mContentView.startAnimation(set);
    }

    @Override
    public void show() {
        super.show();
        animateUp();
        if (mOnBottomSheetShowListener != null) {
            mOnBottomSheetShowListener.onShow();
        }
    }

    public void show(int maxHeight) {
        super.show();
        mContentView.setVisibility(View.INVISIBLE);
        mContentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mContentView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                int height = mContentView.getHeight();
                if (height > maxHeight) {
                    mContentView.getLayoutParams().height = maxHeight;
                }
                animateUp();
            }
        });

        if (mOnBottomSheetShowListener != null) {
            mOnBottomSheetShowListener.onShow();
        }
    }

    @Override
    public void dismiss() {
        if (mIsAnimating) {
            return;
        }
        animateDown();
    }

    public void dismissNoAnim() {
        dismiss();
    }

    public interface OnBottomSheetShowListener {
        void onShow();
    }
}
