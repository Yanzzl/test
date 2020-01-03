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

import com.example.test.MapsActivity;
import com.example.test.R;
import com.example.test.SpotInfo;
import com.example.test.SpotlistActivity;
import com.example.test.SqliteHelper;
import com.example.test.accounts.AccountPage;
import com.example.test.accounts.Login;

import java.util.ArrayList;
public class NewsCardAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list;
    private Context context;
    private boolean isAdmin;

    SqliteHelper dbHelper;


    public NewsCardAdapter(ArrayList<String> list, Context context, boolean isAdmin) {
        this.list = list;
        this.context = context;
        this.isAdmin = isAdmin;
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
            view = inflater.inflate(R.layout.news_cardview, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.title_news);
        listItemText.setText(list.get(position));

        ImageView imageView = view.findViewById(R.id.picture_news);
        imageView.setImageURI(dbHelper.getNewsPic(list.get(position)));


        //Handle buttons and add onClickListeners
        ImageView edit = view.findViewById(R.id.edit_news);
        ImageView delete = view.findViewById(R.id.delete_news);

        listItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsPage.class);
                intent.putExtra("TITLE", list.get(position));
                context.startActivity(intent);
            }
        });

        if (!isAdmin) {
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditNews.class);
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
                        dbHelper.removeNews(list.get(position));
                        Intent intent = new Intent(context, NewsActivity.class);
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






        return view;
    }
}
