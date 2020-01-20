package com.example.test.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.test.R;
import com.example.test.SpotListAdapter;
import com.example.test.SpotlistActivity;
import com.example.test.SqliteHelper;
import com.example.test.Upload;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;



    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_testing, container, false);

        final Button add = root.findViewById(R.id.addSpot_testing);
        final ListView listView = root.findViewById(R.id.spotList_testing);

//        final SwipeRefreshLayout pullToRefresh = root.findViewById(R.id.refresh_testing);
//        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshData(root, listView); // your code
//                pullToRefresh.setRefreshing(false);
//            }
//        });


        SqliteHelper dbHelper = new SqliteHelper(root.getContext());
//        if (dbHelper.isAdmin(dbHelper.getCurrent())) {
//            add.setVisibility(View.VISIBLE);
//        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), Upload.class);
                startActivity(intent);
            }
        });
        pageViewModel.getCode().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                SpotListAdapter adapter = new SpotListAdapter(s, root.getContext());
                if (dbHelper.isAdmin(dbHelper.getCurrent()) && s == 150) {
                    add.setVisibility(View.VISIBLE);
                }
                //handle listview and assign adapter
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        return root;
    }


//    public void refreshData(View root, ListView listView) {
//        pageViewModel.getCode().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(@Nullable Integer s) {
//                SpotListAdapter adapter = new SpotListAdapter(s, root.getContext());
//                //handle listview and assign adapter
//                listView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }

}