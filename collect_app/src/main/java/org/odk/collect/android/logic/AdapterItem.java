package org.odk.collect.android.logic;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static org.odk.collect.android.R.id;
import static org.odk.collect.android.R.layout;

/**
 * Created by Administrador1 on 15/2/2018.
 */

public class AdapterItem extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Category> items;

    public AdapterItem(Activity activity, ArrayList<Category> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Category> category) {
        for (int i = 0; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(layout.activity_item_category, null);
        }

        Category dir = items.get(position);

        TextView title = (TextView) v.findViewById(id.category);
        title.setText(dir.getTitle());

        TextView description = (TextView) v.findViewById(id.texto);
        description.setText(dir.getDescription());
        ImageView imagen = (ImageView) v.findViewById(id.imageView4);
        imagen.setImageDrawable(dir.getImage());

        return v;
    }
}