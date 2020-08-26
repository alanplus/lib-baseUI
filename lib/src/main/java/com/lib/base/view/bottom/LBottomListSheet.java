package com.lib.base.view.bottom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class LBottomListSheet extends LBottomSheet {

    protected RecyclerView mRecyclerView;
    protected RecyclerView.Adapter adapter;

    protected int itemHeight;
    protected int maxHeight;

    protected OnBottomListSheetListener onBottomListSheetListener;


    public LBottomListSheet(@NonNull Context context) {
        super(context);
        this.itemHeight = LTools.dip2px(56);
        this.maxHeight = LTools.getScreenSize(getContext())[1] * 3 / 5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lui_bottom_listview);
        mRecyclerView = findViewById(R.id.lui_recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
        resetHeight();
        if (null != onBottomListSheetListener) {
            onBottomListSheetListener.onInflaterFinish(mRecyclerView);
        }
    }

    public void resetHeight() {
        if (null == adapter) {
            return;
        }
        int itemCount = adapter.getItemCount();
        if (itemHeight * itemCount > maxHeight) {
            mRecyclerView.getLayoutParams().height = maxHeight;
        }
    }


    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }


    public void setOnBottomListSheetListener(OnBottomListSheetListener onBottomListSheetListener) {
        this.onBottomListSheetListener = onBottomListSheetListener;
    }

    public interface OnBottomListSheetListener {
        void onInflaterFinish(RecyclerView recyclerView);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
