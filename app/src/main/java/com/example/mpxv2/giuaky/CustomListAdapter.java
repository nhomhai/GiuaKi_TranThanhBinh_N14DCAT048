package com.example.mpxv2.giuaky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private List<Account> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListAdapter(Context aContext,  List<Account> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView_name);
            holder.password = (TextView) convertView.findViewById(R.id.textView_pass);
            holder.delete = (Button) convertView.findViewById(R.id.button_delete);
            holder.edit = (Button) convertView.findViewById(R.id.button_edit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Account account = this.listData.get(position);
        holder.delete.setTag((Integer)position);
        holder.edit.setTag((Integer)position);
        holder.name.setText(account.getName());
        holder.password.setText(account.getPass());

        return convertView;
    }
    static class ViewHolder {
        TextView name;
        TextView password;
        Button edit;
        Button delete;
    }

}

