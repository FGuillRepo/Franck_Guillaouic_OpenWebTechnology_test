package com.guillaouic.test.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.guillaouic.test.model.bookModel.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.databinding.RowRecyclerLayoutitemBinding;


public class RepositoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   private RowRecyclerLayoutitemBinding binding;
    private LayoutInflater mInflater;
    private Context mContext;
    private int selectedPosition = 0;
    //test

    private List<? extends Item> mCommentList;

    @Nullable
    private final RecyclerViewClickCallback mReclyclerClickCallback;


    public interface OnLoadMoreListener {
        void onLoadMore();
    }
    public RepositoryAdapter(Context context, @Nullable RecyclerViewClickCallback mReclyclerClickCallback) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.mReclyclerClickCallback = mReclyclerClickCallback;
    }

    public void setBookList(final List<? extends Item> comments) {
        if (mCommentList==null) {
            mCommentList = comments;
            notifyItemRangeInserted(0, comments.size());
        }else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mCommentList.size();
                }

                @Override
                public int getNewListSize() {
                    return comments.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    Item old = mCommentList.get(oldItemPosition);
                    Item comment = comments.get(newItemPosition);
                    return old.getId() == comment.getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Item old = mCommentList.get(oldItemPosition);
                    Item comment = comments.get(newItemPosition);
                    return old.getId() == comment.getId();
                }
            });
            mCommentList = comments;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_recycler_layoutitem, parent, false);

         if (mReclyclerClickCallback!=null) {
             binding.setCallback(mReclyclerClickCallback);
         }

        return new TipsViewHolder(binding);
                }

    @Override
    public int getItemCount() {
            return mCommentList == null ? 0 : mCommentList.size();
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Item bo = (Item) getItem(position);
        ((TipsViewHolder) holder).rowItemBinding.setBook(bo);
        ((TipsViewHolder) holder).rowItemBinding.executePendingBindings();

    }


    public class TipsViewHolder extends RecyclerView.ViewHolder{
        public RowRecyclerLayoutitemBinding rowItemBinding;
        public View itemView;

        public TipsViewHolder(RowRecyclerLayoutitemBinding rowItemBinding) {
            super(rowItemBinding.getRoot());
            itemView=rowItemBinding.getRoot();
            this.rowItemBinding = rowItemBinding;

        }
    }
    

    protected class LoadingVH extends RecyclerView.ViewHolder {
        ProgressBar progress;
        public LoadingVH(View itemView) {
            super(itemView);
            progress = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            if (progress.getIndeterminateDrawable()!=null){
                progress.getIndeterminateDrawable().setColorFilter(mContext.getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN);
            }
        }
    }


    /*
     *   Function Adapter
     */
    public void clear() {
        mCommentList.clear();
        notifyDataSetChanged();
    }


    public void remove(Item city) {
        int position = mCommentList.indexOf(city);
        if (position > -1) {
            mCommentList.remove(position);
            notifyItemRemoved(position);
        }
    }


    public void removeLoadingFooter() {
        mCommentList.remove(mCommentList.size() - 1);
        notifyItemRemoved(mCommentList.size());
    }


    public int getCount() {
        return mCommentList.size();
    }

    public Item getItem(int position) {
        return mCommentList.get(position);
    }


    public long getItemId(int position) {
        return position;
    }

    @BindingAdapter({"bind:url"})
    public static void setImage(ImageView imageView, String url) {
        if (url != null && url.trim().length() > 0) {
            Picasso.with(imageView.getContext()).load(url).error(R.mipmap.ic_launcher).into(imageView);
        }
        else
            imageView.setImageResource(R.mipmap.ic_launcher);
    }

}
