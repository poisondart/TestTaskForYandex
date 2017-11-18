package com.example.nix.testtaskforyandex;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.nix.testtaskforyandex.model.LocalBDItem;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Nix on 02.11.2017.
 */

public class LocalBDItemAdapter extends RecyclerView.Adapter<LocalBDItemAdapter.HistoryViewHolder> {

    private RealmResults<LocalBDItem> mItems;
    private Realm mRealm;

    public LocalBDItemAdapter(RealmResults<LocalBDItem> items, Realm realm) {
        mItems = items;
        mRealm = realm;
    }

    @Override
    public void onBindViewHolder(final HistoryViewHolder holder, final int position) {
        holder.mSource.setText(mItems.get(position).getSource());
        holder.mResult.setText(mItems.get(position).getResult());
        holder.mFLangCode.setText(mItems.get(position).getFCode());
        holder.mSLangCode.setText(mItems.get(position).getSCode());

        if (mItems.get(position).isFavorite()){
            holder.mToFav.setImageResource(R.drawable.ic_to_fav_checked);
        }else{
            holder.mToFav.setImageResource(R.drawable.ic_to_fav_unchecked);
        }

        holder.mToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItems.get(holder.getAdapterPosition()).isFavorite()){
                    mRealm.beginTransaction();
                    mItems.get(holder.getAdapterPosition()).setFavorite(false);
                    mRealm.commitTransaction();
                    holder.mToFav.setImageResource(R.drawable.ic_to_fav_unchecked);
                }else{
                    mRealm.beginTransaction();
                    mItems.get(holder.getAdapterPosition()).setFavorite(true);
                    mRealm.commitTransaction();
                    holder.mToFav.setImageResource(R.drawable.ic_to_fav_checked);
                }
            }
        });
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.localdb_item_card, parent, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView mSource, mResult, mFLangCode, mSLangCode;
        ImageButton mToFav;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            mSource = (TextView) itemView.findViewById(R.id.source);
            mResult = (TextView) itemView.findViewById(R.id.result);
            mFLangCode = (TextView) itemView.findViewById(R.id.source_code_lan);
            mSLangCode = (TextView) itemView.findViewById(R.id.result_code_lan);
            mToFav = (ImageButton) itemView.findViewById(R.id.to_fav_button);
        }
    }
}
