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
        holder.tvCourseDesc.setText(course.getDeskripsi());
        
        // Handle images (for now using placeholder based on name or generic)
        if (course.getNama_course().toLowerCase().contains("coding")) {
            holder.imgCourse.setImageResource(R.drawable.course_coding);
        } else if (course.getNama_course().toLowerCase().contains("ui/ux")) {
            holder.imgCourse.setImageResource(R.drawable.course_uiux);
        } else if (course.getNama_course().toLowerCase().contains("cybersecurity")) {
            holder.imgCourse.setImageResource(R.drawable.course_cybersecurity);
        } else {
            holder.imgCourse.setImageResource(R.drawable.course_coding); // Default
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(course));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseName, tvCourseDesc;
        ImageView imgCourse;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvCourseDesc = itemView.findViewById(R.id.tvCourseDesc);
            imgCourse = itemView.findViewById(R.id.imgCourse);
        }
    }
}