package com.example.wif;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private Context mContext;
    private ArrayList<ExampleRecipe> mExampleList;

    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RecipeAdapter(Context context, ArrayList<ExampleRecipe> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.example_recipe, parent, false);
        return new RecipeViewHolder(v);
    }
    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        ExampleRecipe currentItem = mExampleList.get(position);
        String imageUrl = currentItem.getImageResource();
        String creatorName = currentItem.getLine1();
        String likeCount = currentItem.getLine2();
        String healthCount = currentItem.getLine3();
        holder.mTextViewCreator.setText(creatorName);
        holder.mTextViewHealth.setText("Health: \n" + healthCount);
        holder.mTextViewLikes.setText("Ingredients: \n" + likeCount);
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public ImageView mImageShare;
        public TextView mTextViewCreator;
        public TextView mTextViewLikes;
        public TextView mTextViewHealth;
        public RecipeViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
            mTextViewLikes = itemView.findViewById(R.id.text_view_likes);
            mTextViewHealth = itemView.findViewById(R.id.text_view_health);
            mImageShare = itemView.findViewById(R.id.image_share);
            mImageShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, mExampleList.get(position).getrecipeURL());
                    sendIntent.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    mContext.startActivity(shareIntent);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}