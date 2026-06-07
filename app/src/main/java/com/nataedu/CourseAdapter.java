package com.nataedu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> courseList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Course course);
    }

    public CourseAdapter(List<Course> courseList, OnItemClickListener listener) {
        this.courseList = courseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.tvCourseName.setText(course.getNama_course());
        
        // Handle Author
        if (course.getAuthor() != null) {
            holder.tvAuthor.setText("by " + course.getAuthor());
        } else {
            holder.tvAuthor.setText("");
        }

        // Handle images
        String name = course.getNama_course().toLowerCase();
        if (name.contains("javascript")) {
            holder.imgCourse.setImageResource(R.drawable.javascript_logo);
        } else if (name.contains("ui design") || name.contains("ui/ux")) {
            holder.imgCourse.setImageResource(R.drawable.ui_design_course_icon);
        } else {
            holder.imgCourse.setImageResource(R.drawable.nataedu_icon);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(course));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseName, tvAuthor;
        ImageView imgCourse;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            imgCourse = itemView.findViewById(R.id.imgCourse);
        }
    }
}
