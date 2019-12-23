package com.example.test;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private List<String> mViewImages;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
//    private Map<Integer, ViewHolder> viewHolderMap = new HashMap<>();

    // data is passed into the constructor
    public ImageListAdapter(Context context, List<String> images) {
        this.mInflater = LayoutInflater.from(context);
        this.mViewImages = images;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.image_list, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        viewHolderMap.put(position, holder);
        String pic = mViewImages.get(position);
        holder.picture.setImageURI(Uri.parse(pic));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mViewImages.size();
    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView picture;
        ImageView remove;

        ViewHolder(View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.imageView_il);
            remove = itemView.findViewById(R.id.remove_il);
            remove.setOnClickListener(this);
        }

        //TODO
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mViewImages.get(id);
    }

    public void removeItem(int id) {
        mViewImages.remove(id);
    }



    //TODO
    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}