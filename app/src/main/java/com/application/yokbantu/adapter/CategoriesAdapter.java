package com.application.yokbantu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.yokbantu.R;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private Context context;
    private List<String> categoryName;
    private List<Integer> categoryImg;
    private Integer choice;
    private final SetChoiceListener setChoiceListener;

    public CategoriesAdapter(Context context, List<String> categoryName, List<Integer> categoryImg, Integer choice, SetChoiceListener setChoiceListener) {
        this.context = context;
        this.categoryName = categoryName;
        this.categoryImg = categoryImg;
        this.choice = choice;
        this.setChoiceListener = setChoiceListener;
    }

    // INTERFACE TO PASS THE SETTER FUNCTION
    public interface SetChoiceListener {
        void setChoice(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.component_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get current category
        String name = categoryName.get(position);
        Integer img = categoryImg.get(position);

        // Bind data to views
        holder.categoryName.setText(name);
        holder.categoryImage.setImageResource(img);

        if(position == choice){
            holder.categoryContainer.setBackgroundResource(R.drawable.style_categories_selected);
            holder.categoryName.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else{
            holder.categoryContainer.setBackgroundResource(R.drawable.style_categories);
            holder.categoryName.setTextColor(Color.parseColor("#347FD6"));
        }

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            this.choice = holder.getAdapterPosition();
            setChoiceListener.setChoice(holder.getAdapterPosition());  // Call the setter method to update the choice
            notifyDataSetChanged();  // Notify the adapter to refresh the view
        });
    }

    @Override
    public int getItemCount() {
        return categoryName.size();
    }

    // VIEW HOLDER
    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout categoryContainer;
        TextView categoryName;
        ImageView categoryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryContainer = itemView.findViewById(R.id.categoryContainer);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryImage = itemView.findViewById(R.id.categoryImage);
        }
    }
}
