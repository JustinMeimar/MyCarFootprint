package com.example.mycarfootprint;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mycarfootprint.listFragmentDirections;

import java.util.ArrayList;

public class listFragment extends Fragment {

    public listFragment() {
        // Required empty public constructor
    }

    public static listFragment newInstance(String param1, String param2) {
        listFragment fragment = new listFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View contentView = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = contentView.findViewById(R.id.list_view);

        MainActivity mainActivity = (MainActivity) getActivity();
        ArrayList<GasVisit> globalList = mainActivity.list;

        ArrayAdapter<GasVisit> listAdapter = new ArrayAdapter<>(contentView.getContext(), R.layout.list_item, globalList);
        listView.setAdapter(listAdapter);

        /*https://www.vogella.com/tutorials/AndroidListView/article.html*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Navigation.findNavController(contentView).navigate(
                        listFragmentDirections.actionListFragmentToEditFragment(position)
                );
            }
        });

        return contentView;
    }
}