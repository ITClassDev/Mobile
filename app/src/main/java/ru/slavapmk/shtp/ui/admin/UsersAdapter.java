package ru.slavapmk.shtp.ui.admin;

import static ru.slavapmk.shtp.Values.ENDPOINT_URL;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.function.Consumer;

import ru.slavapmk.shtp.R;
import ru.slavapmk.shtp.Values;
import ru.slavapmk.shtp.io.dto.user.get.User;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.GroupViewHolder> {
    private final List<User> groups;
    private final Consumer<User> onDeleteUser;

    public UsersAdapter(List<User> groups, Consumer<User> onDeleteUser) {
        this.groups = groups;
        this.onDeleteUser = onDeleteUser;
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;
        public final ImageView imageView;
        public final MaterialButton delete;
        public final RequestManager glide;
        private final Resources resources;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            resources = itemView.getResources();
            textView = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.avatar);
            glide = Glide.with(itemView.getContext());
            delete = itemView.findViewById(R.id.delete);
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
        User user = groups.get(position);
        holder.textView.setText(holder.resources.getString(R.string.user_name, user.getFirstName(), user.getLastName()));
        String avatarWebPath = ENDPOINT_URL + "storage/avatars/" + user.getUserAvatarPath();
        if (Values.user.getUserAvatarPath().endsWith(".gif"))
            holder.glide.asGif().circleCrop().load(avatarWebPath).into(holder.imageView);
        else
            holder.glide.asBitmap().circleCrop().load(avatarWebPath).into(holder.imageView);
        holder.delete.setOnClickListener(view -> onDeleteUser.accept(user));
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}
