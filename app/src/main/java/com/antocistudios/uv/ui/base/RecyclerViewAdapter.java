package com.antocistudios.uv.ui.base;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antocistudios.business.entity.VideoGame;
import com.antocistudios.moviecatalog.R;
import com.antocistudios.uv.ui.adapter.AbstractListAdapter;
import com.squareup.picasso.Picasso;

/**
 * Created by Adrian Antoci.
 *
 */
public class RecyclerViewAdapter extends AbstractListAdapter<VideoGame, RecyclerViewAdapter.ViewHolder> implements View.OnClickListener  {

    public interface OnItemClickListener {
        void onItemClick(View view, VideoGame viewObject);
    }

    private final Context mContext;
    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VideoGame item = mData.get(position);

        holder.mTextTitle.setText(item.getTitle());
        holder.mTextDate.setText(item.getShortDescription());
        holder.mImage.setImageBitmap(null);

        Picasso.with(holder.mImage.getContext()).load(item.getPosterURL()).into(holder.mImage);

        holder.itemView.setTag(item);
    }

    @Override
    public void onClick(final View view) {
        if (onItemClickListener != null) {
            new Handler().postDelayed(new Runnable() {
                @Override public void run() {
                    onItemClickListener.onItemClick(view, (VideoGame) view.getTag());
                }
            }, 200);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextTitle;
        private final TextView mTextDate;
        private final ImageView mImage;

        public ViewHolder(View v) {
            super(v);
            mTextTitle = (TextView) v.findViewById(R.id.textViewTitle);
            mTextDate =  (TextView) v.findViewById(R.id.textViewDate);
            mImage = (ImageView) v.findViewById(R.id.imageView);
        }

    }
}