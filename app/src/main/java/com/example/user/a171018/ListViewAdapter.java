package com.example.user.a171018;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by fch21 on 2017-10-17.
 */

public class ListViewAdapter extends BaseAdapter {
    ArrayList<ListViewSet> listViewItemList = new ArrayList<ListViewSet>();

    public ListViewAdapter() {}

    @Override
    public int getCount() {
        return listViewItemList.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_item, parent, false);
        }

        ImageView iconImage = (ImageView)convertView.findViewById(R.id.listImage);
        TextView title = (TextView)convertView.findViewById(R.id.titleText);
        TextView type = (TextView)convertView.findViewById(R.id.typeText);
        TextView sub = (TextView)convertView.findViewById(R.id.subText);

        ListViewSet listViewSet = listViewItemList.get(position);

        iconImage.setImageBitmap(listViewSet.getBitmap());
        title.setText(listViewSet.getTitle());
        type.setText(listViewSet.getType());
        sub.setText(listViewSet.getSub());

        return convertView;
    }

    public void addItem(Bitmap icon, String title, String type, String sub) {
        ListViewSet item = new ListViewSet();

        item.setBitmap(icon);
        item.setTitle(title);
        item.setType(type);
        item.setSub(sub);

        listViewItemList.add(item);
    }
}
