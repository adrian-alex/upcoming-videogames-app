package com.antocistudios.uv.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian Antoci.
 *
 */
public abstract class AbstractListAdapter<V, K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K> {

    protected List<V> mData = new ArrayList<>();

    @Override
    public abstract K onCreateViewHolder(ViewGroup viewGroup, int i);

    @Override
    public abstract void onBindViewHolder(K k, int i);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(final List<V> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void add(final List<V> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }
}
