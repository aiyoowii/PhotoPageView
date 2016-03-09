package com.cegrano.android.photopageview;

import android.support.v7.internal.view.ViewPropertyAnimatorCompatSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cegrano
 */
public abstract class SimplePhotoPageAdapter<T> extends BasePhotoPageAdapter {

    private static final String TAG = "SimplePageAdapter";
    private List<T> mData;

    public void setData(List<T> data){
        mData = data;
    }

    public void addAllData(List<T> data){
        if (mData == null)
            mData = new ArrayList<>();
        mData.addAll(data);
    }

    public void addData(T data){
        if (mData == null)
            mData = new ArrayList<>();
        mData.add(data);
    }

    @Override
    public ViewPropertyAnimator getViewAnimation(View view, int position, int currentPosition) {
        if (view == null)
            return null;
        ViewPropertyAnimator animate = view.animate();
//        ViewPropertyAnimator curAnimate = getViewAnimation(view, currentPosition, currentPosition);
        long delay = 2000;
//        if (curAnimate != null)
//            delay = getViewAnimation(view,currentPosition,currentPosition).getDuration()/10*8;
        Log.d(TAG,"getViewAnimation pos:" + position);
        if (position == currentPosition) {
            animate.alpha(0);
            animate.setDuration(2000);
//        } else if (currentPosition == getItemCount() - 1 && position<currentPosition) {
//            animate = getViewAnimation(view, currentPosition + 1 + position, currentPosition);
        } else if (position == currentPosition + 1) {
            setAnimForOther(view, animate, position,delay);
        } else if (position == currentPosition + 2) {
            setAnimForOther(view, animate, position,delay);
        }
        return animate;
    }

    private void setAnimForOther(View view, ViewPropertyAnimator animate, int position, long delay) {
        animate.setStartDelay(delay);
        float scale = (float) Math.pow(0.9, position-1);
//        animate.y(DensityUtil.dip2px(view.getContext(), 10 + 30 * scale ) * position);
        animate.scaleX(scale);
        animate.scaleY(scale);
        animate.setDuration(1000);
        animate.setStartDelay(delay);
    }

    @Override
    public void layoutView(View child, int position) {
        if (child == null)
            return;


//        ViewPropertyAnimator animate = child.animate();
//        animate.y(DensityUtil.dip2px(child.getContext(), 5 + 300 * 0.1f) * position);
//        animate.scaleX((float) Math.pow(0.9, position));
//        animate.scaleY((float) Math.pow(0.9,position));
//        animate.setDuration(100);
//        animate.start();
        float scale = (float) Math.pow(0.9, position);
        child.setScaleX(scale);
        child.setScaleY(scale);
//        child.setY(DensityUtil.dip2px(child.getContext(), 10 + 30 * scale ) * position);
        Log.d(TAG,"layout view pos:"+position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getViewCount() {
        if (mData == null)
            return 0;
        return mData.size() > 3 ? 3 : mData.size();
    }
}
