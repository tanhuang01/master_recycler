package com.congguangzi.master_recycler._2_loadmore_with_loading;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.congguangzi.master_recycler.R;
import com.congguangzi.master_recycler._1_loadmore.Item_1;
import com.congguangzi.master_recycler._2_loadmore_with_loading.NormalAdapter_2.WithLoadingViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author congguangzi (congspark@163.com) 2017/12/8.
 */

public class NormalAdapter_2 extends RecyclerView.Adapter<WithLoadingViewHolder> implements LoadMore_2<Item_1> {

    private List<Item_1> data = new ArrayList<>();

    @Override
    public WithLoadingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WithLoadingViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout._1_load_more_item, parent, false));
    }

    @Override
    public void onBindViewHolder(WithLoadingViewHolder holder, int position) {
        Item_1 item = data.get(position);
        holder.title.setText(item.getTitle());
        holder.detail.setText(item.getDetail());
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public void append(@NotNull List<Item_1> set) {
        data.addAll(set);
    }

    static class WithLoadingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title)
        TextView title;

        @BindView(R.id.item_detail)
        TextView detail;

        WithLoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
