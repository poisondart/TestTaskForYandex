package com.example.nix.testtaskforyandex;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Nix on 02.11.2017.
 */

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder> {

    private List<String> mStrings;

    public ResultsAdapter(List<String> strings) {
        mStrings = strings;
    }

    @Override
    public void onBindViewHolder(ResultsViewHolder holder, int position) {
        holder.mTextView.setText(mStrings.get(position));
    }

    @Override
    public ResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_card, parent, false);
        return new ResultsViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    public static class ResultsViewHolder extends RecyclerView.ViewHolder{

        TextView mTextView;
        ImageView mImageView;

        public ResultsViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView)itemView.findViewById(R.id.textview_result);
            mImageView = (ImageView)itemView.findViewById(R.id.button_to_fav);
        }
    }
}
