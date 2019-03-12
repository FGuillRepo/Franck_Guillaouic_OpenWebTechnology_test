package com.guillaouic.test.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.guillaouic.test.fragment.callback.RecyclerViewClickCallback;
import com.guillaouic.test.pojo.Item;
import com.squareup.picasso.Picasso;


import java.util.List;

import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.databinding.RowRecyclerLayoutitemBinding;

/*
 *  Books Adapter : Bind and show books in Recyclerview.
 * */

public class RepositoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RowRecyclerLayoutitemBinding binding;
    private List<? extends Item> bookList;

    @Nullable
    private final RecyclerViewClickCallback mReclyclerClickCallback;


    public RepositoryAdapter(Context context, @Nullable RecyclerViewClickCallback mReclyclerClickCallback) {
        this.mReclyclerClickCallback = mReclyclerClickCallback;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_recycler_layoutitem, parent, false);

         if (mReclyclerClickCallback!=null) {
             binding.setCallback(mReclyclerClickCallback);
         }

        return new BooksViewHolder(binding);
                }

    @Override
    public int getItemCount() {
            return bookList == null ? 0 : bookList.size();
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Item bo = (Item) getItem(position);
        ((BooksViewHolder) holder).rowItemBinding.setBook(bo);
        ((BooksViewHolder) holder).rowItemBinding.executePendingBindings();

    }


    public class BooksViewHolder extends RecyclerView.ViewHolder{
        public RowRecyclerLayoutitemBinding rowItemBinding;
        public View itemView;

        public BooksViewHolder(RowRecyclerLayoutitemBinding rowItemBinding) {
            super(rowItemBinding.getRoot());
            itemView=rowItemBinding.getRoot();
            this.rowItemBinding = rowItemBinding;

        }
    }


    public void setBookList(final List<? extends Item> item) {
        if (bookList==null) {
            bookList = item;
            notifyItemRangeInserted(0, item.size());
        }else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return bookList.size();
                }

                @Override
                public int getNewListSize() {
                    return item.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    Item old = bookList.get(oldItemPosition);
                    Item comment = item.get(newItemPosition);
                    return old.getId() == comment.getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Item old = bookList.get(oldItemPosition);
                    Item comment = item.get(newItemPosition);
                    return old.getId() == comment.getId();
                }
            });
            bookList = item;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    public Item getItem(int position) {
        return bookList.get(position);
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
