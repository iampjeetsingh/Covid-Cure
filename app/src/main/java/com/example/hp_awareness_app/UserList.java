package com.example.hp_awareness_app;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserList extends ArrayAdapter<User> {

    private Activity context;
    private List<User> users;


    public UserList(Activity context, List<User> users) {
        super(context, R.layout.notifications, users);
        this.context = context;
        this.users = users;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.notifications, null, true);

        TextView name = (TextView) view.findViewById(R.id.nameHead);
        TextView date = (TextView) view.findViewById(R.id.messageDate);
        ImageView arrow = (ImageView) view.findViewById(R.id.Arrow);

        User user = users.get(position);
        name.setText(user.getName());
        date.setText(user.getDateTime());

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = user.getId();
                String name = user.getName();
                String phn = user.getContact();
                String add = user.getAddress();
                String msg = user.getMessage();
                String time = user.getDateTime();

                Intent intent = new Intent(context, SendMessage.class);
                intent.putExtra("User Id", id);
                intent.putExtra("name", name);
                intent.putExtra("phn", phn);
                intent.putExtra("add", add);
                intent.putExtra("msg", msg);
                intent.putExtra("time", time);
                context.startActivity(intent);

            }
        });

        return view;
    }
}

