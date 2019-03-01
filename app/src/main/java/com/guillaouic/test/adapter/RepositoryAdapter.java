package com.guillaouic.test.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import com.guillaouic.test.Model.Repository;
import instagallery.app.com.gallery.R;


public class RepositoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    private OnItemRepoClickListener listener;
    private LayoutInflater mInflater;
    private ArrayList<Repository> AL_id_text = new ArrayList<Repository>();
    private Context mContext;
    private int selectedPosition = 0;
    //test

    public interface OnItemRepoClickListener {
        void RepoClickEvent(Repository repository);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
    public RepositoryAdapter(Context context, ArrayList<Repository> AL_id_text, RecyclerView recyclerView, OnItemRepoClickListener listener) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.AL_id_text = AL_id_text;
        this.listener = listener;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                Log.d("page", "successs");

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_repositoryitem, parent, false);
                return new TipsViewHolder(view);

            case 1:
                view  =  LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recylcer_itemprogress, parent, false);
                return new LoadingVH(view);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return AL_id_text.size();
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Repository bo = (Repository) getItem(position);
        if (holder instanceof TipsViewHolder) {
            ((TipsViewHolder) holder).bind(position,listener,bo);

            if (bo.getName()!=null) {
                ((TipsViewHolder) holder).title.setText(bo.getName());
            }
            if (bo.getLanguage()!=null) {
                ((TipsViewHolder) holder).language.setText(bo.getLanguage());
            }
            if (bo.getWatchers()!=null) {
                ((TipsViewHolder) holder).watcher.setText(String.valueOf(bo.getWatchers()));
            }

        }else{
            ((LoadingVH) holder).progress.setIndeterminate(true);
        }
    }



    /*
    *   View Holder
    * */
        public class TipsViewHolder extends RecyclerView.ViewHolder {
            TextView title,language,watcher;
            public TipsViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title);
                language = (TextView) itemView.findViewById(R.id.language);
                watcher = (TextView) itemView.findViewById(R.id.watcher);

            }
            public void bind(final int item, final OnItemRepoClickListener listener, final Repository repository) {
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

    @Override
    public int getItemViewType(int position) {
        return AL_id_text.get(position) != null ? 0 : 1;
    }

    public void add(Repository mc) {
        AL_id_text.add(mc);
        notifyItemInserted(AL_id_text.size() - 1);
    }

    public void addAll(List<Repository> newUsers) {
        int initialSize = AL_id_text.size();
        AL_id_text.addAll(newUsers);
        notifyItemRangeInserted(initialSize, newUsers.size());
    }

    public void remove(Repository city) {
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

    public Repository getItem(int position) {
        return AL_id_text.get(position);
    }


    public long getItemId(int position) {
        return position;
    }

   public void setLoaded() {
       loading = false;
   }

}
