package com.nataedu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.MaterialViewHolder> {

    private List<Course> materialList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Course material);
    }

    public MaterialAdapter(List<Course> materialList, OnItemClickListener listener) {
        this.materialList = materialList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_material, parent, false);
        return new MaterialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {
        Course material = materialList.get(position);
        holder.tvName.setText(material.getNama_course());
        holder.tvAuthor.setText("by " + material.getAuthor());
        
        // Handle images
        if (material.getNama_course().toLowerCase().contains("javascript")) {
            holder.img.setImageResource(R.drawable.javascript_logo);
        } else if (material.getNama_course().toLowerCase().contains("ui design") || material.getNama_course().toLowerCase().contains("ui/ux")) {
            holder.img.setImageResource(R.drawable.ui_design_course_icon);
        } else {
            holder.img.setImageResource(R.drawable.nataedu_icon);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(material));
    }

    @Override
    public int getItemCount() {
        return materialList.size();
    }

    public static class MaterialViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAuthor;
        ImageView img;

        public MaterialViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvMaterialName);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            img = itemView.findViewById(R.id.imgMaterial);
        }
    }
}
