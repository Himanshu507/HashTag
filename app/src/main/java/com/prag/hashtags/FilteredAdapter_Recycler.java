package com.prag.hashtags;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

public class FilteredAdapter_Recycler extends FirestoreRecyclerAdapter<Add_item_model, FilteredAdapter_Recycler.MyViewHolder> {
    private Context mContext;

    public FilteredAdapter_Recycler(@NonNull FirestoreRecyclerOptions<Add_item_model> options, Context mContext) {
        super(options);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        view = mInflater.inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i, @NonNull final Add_item_model add_item_model) {
        String text = add_item_model.getText();
        HashTagHelper mTextHashTagHelper;
        char[] additionalSymbols = new char[]{
                '_',
                '$'
        };
        mTextHashTagHelper = HashTagHelper.Creator.create(mContext.getResources().getColor(R.color.blue), new HashTagHelper.OnHashTagClickListener() {
            @Override
            public void onHashTagClicked(String hashTag) {
                Toast.makeText(mContext, hashTag, Toast.LENGTH_SHORT).show();
            }
        }, additionalSymbols);
        mTextHashTagHelper.handle(myViewHolder.title);
        myViewHolder.title.setText(text);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text1);
        }
    }

}

