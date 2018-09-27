package com.tumblr.view.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.tumblr.R;
import com.tumblr.model.Photo;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private List<Photo> mItems;
    private Context mContext;
    private PostItemListener mItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public SimpleDraweeView image;
        PostItemListener mItemListener;

        public ViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            image = (SimpleDraweeView) itemView.findViewById(R.id.image_view);
            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Photo photo = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(photo.getId());

            notifyDataSetChanged();
        }
    }

    public PhotoAdapter(Context context, List<Photo> posts, PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_photo_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PhotoAdapter.ViewHolder holder, int position) {

        Photo photo = mItems.get(position);
        SimpleDraweeView image = holder.image;
        image.setImageURI(photo.getUrlM());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateAnswers(List<Photo> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private Photo getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(String id);
    }

}
