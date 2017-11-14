package com.example.nix.testtaskforyandex;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.nix.testtaskforyandex.model.LocalBDItem;
import java.util.ArrayList;

/**
 * Created by Nix on 02.11.2017.
 */

public class LocalBDItemAdapter extends RecyclerView.Adapter<LocalBDItemAdapter.HistoryViewHolder> {

    private ArrayList<LocalBDItem> mItems;

    public LocalBDItemAdapter(ArrayList<LocalBDItem> items) {
        mItems = items;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.mSource.setText(mItems.get(position).getSource());
        holder.mResult.setText(mItems.get(position).getResult());
        holder.mFLangCode.setText(mItems.get(position).getFCode());
        holder.mSLangCode.setText(mItems.get(position).getSCode());
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_card, parent, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView mSource, mResult, mFLangCode, mSLangCode;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            mSource = (TextView) itemView.findViewById(R.id.source);
            mResult = (TextView) itemView.findViewById(R.id.result);
            mFLangCode = (TextView) itemView.findViewById(R.id.source_code_lan);
            mSLangCode = (TextView) itemView.findViewById(R.id.result_code_lan);
        }
    }
}
