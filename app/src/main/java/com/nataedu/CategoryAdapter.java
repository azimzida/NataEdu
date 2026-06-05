package com.nataedu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Category category);
    }

    public CategoryAdapter(List<Category> categoryList, OnItemClickListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category cat = categoryList.get(position);
        holder.tvName.setText(cat.getName());
        holder.tvDesc.setText(cat.getDescription());
        holder.img.setImageResource(cat.getImageRes());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(cat));
        holder.btnSeeAll.setOnClickListener(v -> listener.onItemClick(cat));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc, btnSeeAll;
        ImageView img;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCourseName);
            tvDesc = itemView.findViewById(R.id.tvCourseDesc);
            img = itemView.findViewById(R.id.imgCourse);
            btnSeeAll = itemView.findViewById(R.id.btnSeeAll);
        }
    }
}
