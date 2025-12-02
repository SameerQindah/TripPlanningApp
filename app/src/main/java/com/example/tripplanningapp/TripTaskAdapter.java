package com.example.tripplanningapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TripTaskAdapter extends RecyclerView.Adapter<TripTaskAdapter.TaskViewHolder> {

    private Context context;
    private List<TripTask> taskList;      
    private OnItemClickListener listener; 

    public TripTaskAdapter(Context context, List<TripTask> taskList, OnItemClickListener listener) {
        this.context = context;
        this.taskList = taskList != null ? taskList : new ArrayList<TripTask>();
        this.listener = listener;
    }

    public void updateList(List<TripTask> newList) {
        taskList = newList != null ? newList : new ArrayList<TripTask>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TripTask task = taskList.get(position);

        holder.txtTitle.setText(task.getTitle());
        holder.txtCity.setText("City: " + task.getCity());
        holder.txtDate.setText("Date: " + task.getDate());
        holder.txtType.setText("Type: " + task.getType());

        holder.bind(task, listener);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtCity, txtDate, txtType;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtCity = itemView.findViewById(R.id.txtCity);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtType = itemView.findViewById(R.id.txtType);
        }

        public void bind(TripTask task, OnItemClickListener listener) {
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(task);
                }
            });

            itemView.setOnLongClickListener(v -> {
                if (listener != null) {
                    listener.onItemLongClick(task);
                }
                return true;
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(TripTask task);
        void onItemLongClick(TripTask task);
    }
}
