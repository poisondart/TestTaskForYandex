package com.example.nix.testtaskforyandex;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.nix.testtaskforyandex.model.Language;
import java.util.List;

/**
 * Created by Nix on 12.10.2017.
 */

public class LangAdapter extends RecyclerView.Adapter<LangAdapter.LangViewHolder> {
    List<Language> mLanguageList;
    private Context mContext;
    private Byte mType;

    public LangAdapter(List<Language> languageList, Context context, byte type) {
        mLanguageList = languageList;
        mContext = context;
        mType = type;
        Preferences.initialize(context);
    }

    @Override
    public LangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_item, parent, false);
        return new LangViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final LangViewHolder holder, int position) {
        holder.mTextView.setText(mLanguageList.get(position).getName());
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mType.equals(CLHostActivity.FIRST_LANGUAGE_CHOOSE)){
                    if (Preferences.getSecondLanguageCode(mContext)
                            .equals(mLanguageList.get(holder.getAdapterPosition()).getCode())){
                        Preferences.switchLangs(mContext);
                    }else{
                        Preferences.setFirstLanguage(mContext, mLanguageList.get(holder.getAdapterPosition()).getName());
                        Preferences.setFirstLanguageCode(mContext, mLanguageList.get(holder.getAdapterPosition()).getCode());
                    }
                }
                if (mType.equals(CLHostActivity.SECOND_LANGUAGE_CHOOSE)){
                    if(Preferences.getFirstLanguageCode(mContext)
                            .equals(mLanguageList.get(holder.getAdapterPosition()).getCode())){
                        Preferences.switchLangs(mContext);
                    }else{
                        Preferences.setSecondLanguage(mContext, mLanguageList.get(holder.getAdapterPosition()).getName());
                        Preferences.setSecondLanguageCode(mContext, mLanguageList.get(holder.getAdapterPosition()).getCode());
                    }
                }
                ((Activity)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLanguageList.size();
    }

    public static class LangViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        RelativeLayout mRelativeLayout;
        public LangViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.lang_name);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.click_view);
        }
    }
}
