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
import com.ptit.quanlysinhvienthuctap.model.SinhVien;

import java.util.List;

public class SinhVienAdapter extends BaseAdapter {
    List<SinhVien> data;
    List<GiangVien> listGV;
    Context context;
    LayoutInflater layoutInflater;

    public SinhVienAdapter(List<SinhVien> data, Context context, List<GiangVien> listGV) {
        this.data = data;
        this.context = context;
        this.listGV = listGV;
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
            convertView = layoutInflater.inflate(R.layout.item_sv_row, null);
            holder = new ViewHolder();
            holder.txtName = convertView.findViewById(R.id.tv_ten);
            holder.txtGV = convertView.findViewById(R.id.tv_gv);
            holder.imgDelete = convertView.findViewById(R.id.img_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SinhVien sinhVien = data.get(position);

        holder.txtName.setText(sinhVien.getName());
        holder.txtGV.setText(getTenGV(sinhVien.getTeacherId()));
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Xác nhận xóa");
                builder1.setMessage("bạn có muốn xóa " + sinhVien.getName() + " ?");
                builder1.setCancelable(true);

                builder1.setNegativeButton(
                        "Có",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseHandler databaseHelper = new DatabaseHandler(context);
                                databaseHelper.deleteStudent(sinhVien.getId());
                                data.remove(sinhVien);
                                notifyDataSetChanged();

                            }
                        });
                builder1.setPositiveButton(
                        "Không",
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

    String getTenGV(Integer id) {
        if ( id == null || id == 0)
            return "Chưa có giáo viên hướng dẫn";
        else {
            for (GiangVien gv : listGV) {
                if (id == gv.getId()) return "Giáo viên:" + gv.getName();
            }
        }
        return "Chưa có giáo viên hướng dẫn";

    }

    public static class ViewHolder {
        TextView txtName;
        TextView txtGV;
        ImageView imgDelete;
    }

    public void updateReceiptsList(List<SinhVien> newlist) {
        data.clear();
        data.addAll(newlist);
        this.notifyDataSetChanged();
    }
}
