package com.cegrano.android.photopageview;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

/**
 * Created by cegrano
 * interface
 */
public abstract class BasePhotoPageAdapter {
    
    DataChangeListener dataChangeListener;


    public void setDataChangeListener(DataChangeListener dataChangeListener) {
        this.dataChangeListener = dataChangeListener;
    }

    //set animation
    public ViewPropertyAnimator getViewAnimation(View view,int position,int currentPosition){
        return null;
    }

    //view type
    public int getItemViewType(int position){
        return -1;
    }

    //set view
    public abstract PhotoPageViewHolder onCreateViewHolder(ViewGroup parent,int viewType);

    //bind data
    public abstract void onBindViewHolder(PhotoPageViewHolder holder, int position);

    //view count
    public int getItemCount(){
        return getViewCount();
    }
    public abstract int getViewCount();

    public abstract void layoutView(View child, int position);

    public void notifyDataChange(){
        if (dataChangeListener!=null)
            dataChangeListener.onChange();
    }

    public interface DataChangeListener{
        void onChange();
    }

    public static class PhotoPageViewHolder{
        public View itemView;
        public int type;

        public PhotoPageViewHolder(View itemView,int viewType) {
            this.itemView = itemView;
            this.type = viewType;
            this.itemView.setTag(this);
        }
    }
}
