package ru.slavapmk.shtp.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.slavapmk.shtp.R;
import ru.slavapmk.shtp.io.dto.user.get.User;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.GroupViewHolder> {
    private final List<User> groups;

    public UsersAdapter(List<User> groups) {
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
    public UsersAdapter.GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_user, parent, false);
        return new GroupViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.GroupViewHolder holder, int position) {
        holder.textView.setText(groups.get(position).getFirstName() + " " + groups.get(position).getLastName());
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}
