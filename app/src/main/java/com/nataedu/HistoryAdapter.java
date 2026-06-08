package com.nataedu;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<History> historyList;

    public HistoryAdapter(List<History> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History item = historyList.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvAuthor.setText("by " + item.getAuthor());

        // Format waktu secara relatif (misal: "2 hours ago")
        if (item.getTimestamp() != null) {
            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                    item.getTimestamp().toDate().getTime(),
                    System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS);
            holder.tvTime.setText("Opened " + timeAgo);
        }

        // Logika Logo Otomatis
        String name = item.getTitle().toLowerCase();
        if (name.contains("javascript")) {
            holder.imgHistory.setImageResource(R.drawable.js_logo);
        } else if (name.contains("ui design") || name.contains("ui/ux")) {
            holder.imgHistory.setImageResource(R.drawable.ui_course_logo);
        } else if (name.contains("network")) {
            holder.imgHistory.setImageResource(R.drawable.network_course_logo);
        } else {
            holder.imgHistory.setImageResource(R.drawable.nataedu_icon);
        }
    }

    @Override
    public int getItemCount() { return historyList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAuthor, tvTime;
        ImageView imgHistory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvHistoryTitle);
            tvAuthor = itemView.findViewById(R.id.tvHistoryAuthor);
            tvTime = itemView.findViewById(R.id.tvHistoryTime);
            imgHistory = itemView.findViewById(R.id.imgHistory);
        }
    }
}