package com.nataedu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdminMaterialAdapter extends RecyclerView.Adapter<AdminMaterialAdapter.AdminViewHolder> {

    private List<Course> materialList;
    private OnMaterialActionListener listener;

    public interface OnMaterialActionListener {
        void onEdit(Course course);
        void onDelete(Course course);
    }

    public AdminMaterialAdapter(List<Course> materialList, OnMaterialActionListener listener) {
        this.materialList = materialList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_material, parent, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        Course course = materialList.get(position);
        holder.tvName.setText(course.getNama_course());
        holder.tvAuthor.setText("by " + course.getAuthor());
        
        String info = "Category : " + course.getKategori() + "\nPrice : " + course.getPrice();
        holder.tvInfo.setText(info);

        // --- SET GAMBAR DINAMIS UNTUK ADMIN ---
        String name = course.getNama_course().toLowerCase();
        if (name.contains("ui/ux") || name.contains("ui design")) {
            holder.img.setImageResource(R.drawable.ui_design_course_icon);
        } else if (name.contains("javascript")) {
            holder.img.setImageResource(R.drawable.javascript_logo);
        } else {
            holder.img.setImageResource(R.drawable.nataedu_icon);
        }

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(course));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(course));
    }

    @Override
    public int getItemCount() {
        return materialList.size();
    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAuthor, tvInfo;
        ImageView img;
        View btnEdit, btnDelete;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvMaterialName);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvInfo = itemView.findViewById(R.id.tvMaterialInfo);
            img = itemView.findViewById(R.id.imgMaterial);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
