package com.example.ayb_itshare;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<Model> {

    ArrayList<Model> ModelArrayList;
    Context context;



    public Adapter(@NonNull Context context , ArrayList<Model> ModelArrayList)
    {
        super(context, 0, ModelArrayList);
        this.ModelArrayList = ModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)  getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row, null, true);

        }

        Model models = getItem(position);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.post_image);
        Picasso.get()
                .load(models.getPost_image()).into(imageView);
        TextView post_description = convertView.findViewById(R.id.post_description);
        post_description.setText(models.getPost_description());

        TextView post_user = convertView.findViewById(R.id.post_user);
        post_user.setText(models.getPost_user());

        return convertView;


    }
}
