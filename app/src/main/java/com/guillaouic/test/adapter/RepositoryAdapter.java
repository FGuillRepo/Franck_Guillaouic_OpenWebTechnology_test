package com.guillaouic.test.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.guillaouic.test.model.bookModel.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.databinding.RowRecyclerLayoutitemBinding;


public class RepositoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   private RowRecyclerLayoutitemBinding itemBinding;
    private OnItemRepoClickListener listener;
    private LayoutInflater mInflater;
    private ArrayList<Item> AL_id_text = new ArrayList<Item>();
    private Context mContext;
    private int selectedPosition = 0;
    //test

    public interface OnItemRepoClickListener {
        void RepoClickEvent(Item repository);
    }


    public interface OnLoadMoreListener {
        void onLoadMore();
    }
    public RepositoryAdapter(Context context, ArrayList<Item> AL_id_text, RecyclerView recyclerView, OnItemRepoClickListener listener) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.AL_id_text = AL_id_text;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowRecyclerLayoutitemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_recycler_layoutitem, parent, false);
                return new TipsViewHolder(binding);
                }

    @Override
    public int getItemCount() {
        return AL_id_text.size();
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Item bo = (Item) getItem(position);
        ((TipsViewHolder) holder).rowItemBinding.setBook(bo);
        ((TipsViewHolder) holder).bind(position,listener,bo);
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

        public void bind(final int item, final OnItemRepoClickListener listener, final Item repository) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.RepoClickEvent(repository);
                    notifyItemChanged(selectedPosition);
                    selectedPosition = item;
                    notifyItemChanged(selectedPosition);
                }
            });
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
        AL_id_text.clear();
        notifyDataSetChanged();
    }

    public void add(Item mc) {
        AL_id_text.add(mc);
        notifyItemInserted(AL_id_text.size() - 1);
    }

    public void addAll(List<Item> newUsers) {
        int initialSize = AL_id_text.size();
        AL_id_text.addAll(newUsers);
        notifyItemRangeInserted(initialSize, newUsers.size());
    }

    public void remove(Item city) {
        int position = AL_id_text.indexOf(city);
        if (position > -1) {
            AL_id_text.remove(position);
            notifyItemRemoved(position);
        }
    }


    public void addLoadingFooter() {
        add(null);
        notifyItemInserted(AL_id_text.size() - 1);
    }

    public void removeLoadingFooter() {
        AL_id_text.remove(AL_id_text.size() - 1);
        notifyItemRemoved(AL_id_text.size());
    }


    public int getCount() {
        return AL_id_text.size();
    }

    public Item getItem(int position) {
        return AL_id_text.get(position);
    }


    public long getItemId(int position) {
        return position;
    }

    @BindingAdapter({"imageUrl", "picasso"})
    public static void setImageUrl(ImageView view, String poserPath, Picasso picasso){
        picasso.with(view.getContext()).load(poserPath).into(view);
    }

}
