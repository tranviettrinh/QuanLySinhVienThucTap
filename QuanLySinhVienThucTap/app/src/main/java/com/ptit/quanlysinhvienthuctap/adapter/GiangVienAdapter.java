package com.ptit.quanlysinhvienthuctap.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptit.quanlysinhvienthuctap.R;
import com.ptit.quanlysinhvienthuctap.database.DatabaseHandler;
import com.ptit.quanlysinhvienthuctap.model.GiangVien;

import java.util.List;

public class GiangVienAdapter extends BaseAdapter {
    List<GiangVien> data;
    Context context;
    LayoutInflater layoutInflater;

    public GiangVienAdapter(List<GiangVien> data, Context context) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_row, null);
            holder = new ViewHolder();
            holder.txtName = convertView.findViewById(R.id.tv_name);
            holder.imgDelete = convertView.findViewById(R.id.img_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final GiangVien giangVien = data.get(position);

        holder.txtName.setText(giangVien.getName());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Xác nhận xóa");
                builder1.setMessage("bạn có muốn xóa " + giangVien.getName() + " ?");
                builder1.setCancelable(true);

                builder1.setNegativeButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseHandler databaseHelper = new DatabaseHandler(context);
                                databaseHelper.deleteGiangVien(giangVien.getId());
                                data.remove(giangVien);
                                notifyDataSetChanged();
                            }
                        });
                builder1.setPositiveButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        TextView txtName;
        ImageView imgDelete;
    }

    public void updateReceiptsList(List<GiangVien> newlist) {
        data.clear();
        data.addAll(newlist);
        this.notifyDataSetChanged();
    }
}
