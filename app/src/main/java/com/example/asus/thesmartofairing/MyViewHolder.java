package com.example.asus.thesmartofairing;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by ASUS on 2017/2/17.
 */
public class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView mTextView;
    public SimpleDraweeView mImageView1;
    public SimpleDraweeView mImageView2;
    public SimpleDraweeView mImageView3;
    public MyViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.tv_heat_activity);
        mImageView1 = (SimpleDraweeView) itemView.findViewById(R.id.iv_store_image1);
        mImageView2 = (SimpleDraweeView) itemView.findViewById(R.id.iv_store_image2);
        mImageView3 = (SimpleDraweeView) itemView.findViewById(R.id.iv_store_image3);
    }
}
