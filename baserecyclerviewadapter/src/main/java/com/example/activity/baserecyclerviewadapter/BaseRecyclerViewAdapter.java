package com.example.activity.baserecyclerviewadapter;

/**
 * Created by allen on 1/14/2016 17:25.
 */

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<T> datas;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private int defaultLoadItem = 5;
    private OnItemClickListener mListener;


    public BaseRecyclerViewAdapter(List<T> datas, RecyclerView recyclerView) {
        this.datas = datas;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold + defaultLoadItem)) {
                                // End has been reached
                                // Do something
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
    public int getItemViewType(int position) {
        return datas.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {

            View view = createView(parent, viewType);
            vh = createViewHolder(view);

        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ProgressViewHolder) {

            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);

        } else {
            showData(holder, position, datas);
        }

        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }


    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.mListener = listener;
    }

    /**
     * 设置从倒数第几个Item进行预加载,默认加载为5
     *
     * @param count 表示倒数第几个item
     */
    public void setPreItemLoad(int count) {
        if (count >= 0) {
            defaultLoadItem = count;
        }
    }

    /**
     * 通过holder，绑定数据与view
     *
     * @param holder
     * @param position
     * @param studentList
     */
    protected abstract void showData(RecyclerView.ViewHolder holder, int position, List studentList);


    /**
     * 通过holder对象
     *
     * @param view
     * @return
     */
    protected abstract RecyclerView.ViewHolder createViewHolder(View view);

    /**
     * 创建item的view
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract View createView(ViewGroup parent, int viewType);
}