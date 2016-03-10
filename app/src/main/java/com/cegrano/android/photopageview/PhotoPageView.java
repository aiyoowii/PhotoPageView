package com.cegrano.android.photopageview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;

/**
 * Created by cegrano
 */
public class PhotoPageView extends FrameLayout {
    public static final String TAG = "PhotoPage";
    private BasePhotoPageAdapter mPageAdapter;

    private int currentIndex;
    private boolean isLoop = true;
    private boolean touch,cycling;
    private long delay = 3000;
    private boolean canCycle = true;

    public PhotoPageView(Context context) {
        super(context);
    }

    public PhotoPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoPageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PhotoPageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //draw child
    void initChild(){
        removeAllViews();
        if (mPageAdapter!=null)
        for (int i= 0;i<mPageAdapter.getViewCount();i++){
            int position = mPageAdapter.getViewCount() - i - 1;
            BasePhotoPageAdapter.PhotoPageViewHolder vh =
                    mPageAdapter.onCreateViewHolder(this, mPageAdapter.getItemViewType(position));
            addView(vh.itemView,i,new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutChild(vh.itemView, position);
            mPageAdapter.onBindViewHolder(vh, position);
            Log.d(TAG,"add view pos:"+i);
        }
    }

    void drawChild(){
//        if (getChildCount()!=mPageAdapter.getViewCount())
//            initChild();
//        else
        for (int i= 0;i<getChildCount();i++){
            if (currentIndex>mPageAdapter.getItemCount()-1)
                currentIndex = mPageAdapter.getItemCount() - mPageAdapter.getViewCount();
            bindViewAtPosition(getChildAt(i), currentIndex + i);
        }
    }

    public void setLoop(boolean loop){
        this.isLoop = loop;
    }

    public void setCanCycle(boolean canCycle){
        this.canCycle = canCycle;
    }

//    @Override
//    public boolean onDragEvent(DragEvent event) {
//        switch (event.getAction()){
//            case DragEvent.ACTION_DRAG_STARTED:
//                Log.e(TAG,"drag start");
//                break;
//            case DragEvent.ACTION_DRAG_LOCATION:
//                Log.e(TAG,"drag loc:"+event.getX()+" | " + event.getY());
//                break;
//            case DragEvent.ACTION_DRAG_ENDED:
//                Log.e(TAG,"drag start");
//                break;
//        }
//        return true;
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                touch = true;
//                removeCallbacks(mCycleAction);
//                break;
//            case MotionEvent.ACTION_UP:
//                touch = false;
//                doCycle();
//                break;
//        }
//        return super.onTouchEvent(event);
//    }

    public void setDelay(long second){
        this.delay = second;
    }

    void doCycle(){
        if (canCycle && !touch && !cycling && getChildCount()>1){
//            final ViewPropertyAnimator an = mPageAdapter.getViewAnimation(getChildAt(0), mPageAdapter.getViewCount()-1 , 0);
//            an.setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    Log.d(TAG,"animation end");
//                    an.setListener(new AnimatorListenerAdapter() {
//                    });
//                    changePage();
//                    cycling = false;
//                    Log.d(TAG,"do cycle:"+System.currentTimeMillis());
////                    doCycle();
//                }
//            });
            postDelayed(mCycleAction, delay);
        }
    }

    private Runnable mCycleAction = new Runnable() {
            @Override
            public void run() {
                if (!touch && !cycling) {
                    cycling = true;
                    cycle();
                }
//                doCycle();
            }
        };

    void cycle(){
        for (int i = 0;i<getChildCount();i++) {
            final ViewPropertyAnimator an = mPageAdapter.getViewAnimation(getChildAt(getChildCount() - i - 1), i, 0);
            //动画结束，发起下次；得写在这，提前写的话，拿到的不是同一个对象
            if (i == getChildCount() -1)
            an.setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d(TAG, "animation end");
                    an.setListener(new AnimatorListenerAdapter() {
                    });
                    changePage();
                    cycling = false;
                    Log.d(TAG, "do cycle:" + System.currentTimeMillis());
                    doCycle();
                }
            });
            an.start();
        }
    }

    //page change
    void changePage(){
        View child = getChildAt(getChildCount() - 1);
        removeView(child);
        addView(child, 0, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutChild(child, mPageAdapter.getViewCount() - 1);
        currentIndex ++;

        currentIndex%=mPageAdapter.getItemCount();

        if (isLoop){
            child.setAlpha(1);
            child.setVisibility(VISIBLE);
            bindViewAtPosition(child,(currentIndex+mPageAdapter.getViewCount()-1)%mPageAdapter.getItemCount());
        }
    }

    private void layoutChild(View child, int position) {
        mPageAdapter.layoutView(child, position);
    }

    private void bindViewAtPosition(View child, int position) {
        BasePhotoPageAdapter.PhotoPageViewHolder vh = null;
        if (child.getTag() != null)
            vh = (BasePhotoPageAdapter.PhotoPageViewHolder) child.getTag();
        if (vh == null || vh.type != mPageAdapter.getItemViewType(position)) {
            vh = mPageAdapter.onCreateViewHolder(this, mPageAdapter.getItemViewType(position));
        }
        mPageAdapter.onBindViewHolder(vh,position);
        child.setTag(vh);
    }


    public void setAdapter(BasePhotoPageAdapter adapter){
        mPageAdapter = adapter;
        mPageAdapter.setDataChangeListener(new BasePhotoPageAdapter.DataChangeListener() {
            @Override
            public void onChange() {
                drawChild();
            }
        });
        post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"setAdapter");
                initChild();
                if (canCycle)
                    doCycle();
            }
        });
    }
}
