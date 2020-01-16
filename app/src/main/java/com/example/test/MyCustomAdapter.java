package com.example.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.accounts.AccountPage;
import com.example.test.accounts.Login;

import java.util.ArrayList;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    SqliteHelper dbHelper;


    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
        dbHelper = new SqliteHelper(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
//        return list.get(pos).getId();
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_admin, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.item_admin);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        ImageView edit = view.findViewById(R.id.item_edit_admin);
        ImageView delete = view.findViewById(R.id.item_delete_admin);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SpotInfo.class);
                intent.putExtra("TITLE", list.get(position));
                context.startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure to delete this Geo point?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // delete
                        dbHelper.removeGeoPics(list.get(position));
                        dbHelper.removeGeoPoint(list.get(position));
                        Intent intent = new Intent(context, SpotlistActivity.class);
                        context.startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //canceled
                    }
                });
                builder.show();
            }
        });

        listItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO elevator stuff
                Intent intent = new Intent(context, ElevatorB1.class);
                intent.putExtra("TITLE", list.get(position));
                intent.putExtra("IS_LOGIN", false);
                context.startActivity(intent);

//                Intent intent = new Intent(context, MapsActivity.class);
//                intent.putExtra("TITLE", list.get(position));
//                intent.putExtra("POP_UP", true);
//                context.startActivity(intent);
            }
        });


        return view;
    }
}
