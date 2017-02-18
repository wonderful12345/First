package com.example.asus.thesmartofairing;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ASUS on 2017/2/17.
 */

public class StoreAdapt extends RecyclerView.Adapter<MyViewHolder> {
    private LayoutInflater mLayoutInflater ;
    private Context mContext;
    private List<String> tv_list;
    private List<String> iv_list1;
    private List<String> iv_list2;
    private List<String> iv_list3;
    private int Type_R = 1;
    private int Type_L = 0;

    public StoreAdapt(Context context, List<String> mtv_list,List<String> miv_list1,List<String> miv_list2,List<String> miv_list3){
        this.mContext = context;
        this.tv_list = mtv_list;
        this.iv_list1 = miv_list1;
        this.iv_list2 = miv_list2;
        this.iv_list3 = miv_list3;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType==Type_R){
            return new MyViewHolder(mLayoutInflater.inflate(R.layout.set_store_cardview1,parent,false));
        }else {
            return new MyViewHolder(mLayoutInflater.inflate(R.layout.store_cardview,parent,false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position%2==0){
            return Type_R;
        }
        else return Type_L;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(tv_list.get(position));
        Uri uri1 = Uri.parse(iv_list1.get(position));
        holder.mImageView1.setImageURI(uri1);
        Uri uri2 = Uri.parse(iv_list2.get(position));
        holder.mImageView2.setImageURI(uri2);
        Uri uri3 = Uri.parse(iv_list3.get(position));
        holder.mImageView3.setImageURI(uri3);
    }
    @Override
    public int getItemCount() {
        return tv_list.size();
    }
}
