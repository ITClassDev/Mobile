package ru.slavapmk.shtp.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.slavapmk.shtp.R;
import ru.slavapmk.shtp.io.dto.user.get.UserGroup;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private final List<UserGroup> groups;

    public GroupAdapter(List<UserGroup> groups) {
        this.groups = groups;
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name);
        }
    }

    @NonNull
    @Override
    public GroupAdapter.GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_group, parent, false);
        return new GroupViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.GroupViewHolder holder, int position) {
        holder.textView.setText(groups.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}
