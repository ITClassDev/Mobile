package ru.slavapmk.shtp.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.function.Consumer;

import ru.slavapmk.shtp.R;
import ru.slavapmk.shtp.io.dto.user.get.UserGroup;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private final List<UserGroup> groups;
    private final Consumer<UserGroup> onDelete;

    public GroupAdapter(List<UserGroup> groups, Consumer<UserGroup> onDelete) {
        this.groups = groups;
        this.onDelete = onDelete;
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;
        public final MaterialButton button;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name);
            button = itemView.findViewById(R.id.delete);
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
        holder.button.setOnClickListener(view -> onDelete.accept(groups.get(position)));
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}
