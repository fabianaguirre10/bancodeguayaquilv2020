package org.odk.collect.android.logic;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.odk.collect.android.R;

import java.util.ArrayList;

public class AdapterDetalle extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<DetallesFormulario> items;
    public AdapterDetalle (Activity activity, ArrayList<DetallesFormulario> items) {
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

    public void addAll(ArrayList<DetallesFormulario> resultado) {
        for (int i = 0; i < resultado.size(); i++) {
            items.add(resultado.get(i));
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
            v = inf.inflate(R.layout.activity_item_detalle, null);
        }

        DetallesFormulario dir = items.get(position);

        TextView title = (TextView) v.findViewById(R.id.tit);
        title.setText(dir.getTitle());
        TextView descr = (TextView) v.findViewById(R.id.des);
        descr.setText(dir.getDescription());
        TextView cantidad = (TextView) v.findViewById(R.id.cant);
        cantidad.setText(dir.getCantidad());


        return v;
    }
}
