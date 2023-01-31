package com.example.mycarfootprint;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class listFragment extends Fragment {

    private MainActivity mainActivity;
    private Float totalFuelCost;
    private Float totalFootprint;
    private TextView totalCostView;
    private TextView totalFootprintView;

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
        mainActivity = (MainActivity) getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View contentView = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = contentView.findViewById(R.id.list_view);

        //total cost and total footprint texts
        totalCostView = contentView.findViewById(R.id.list_view_total_cost_value);
        totalFootprintView = contentView.findViewById(R.id.list_view_total_footprint_value);

        MainActivity mainActivity = (MainActivity) getActivity();

        ArrayList<GasVisit> globalList = mainActivity.list;
        ArrayAdapter<GasVisit> listAdapter = new ArrayAdapter<>(contentView.getContext(), R.layout.list_item, globalList);
        listView.setAdapter(listAdapter);

        calculateTotals(globalList);

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

    public void calculateTotals(ArrayList<GasVisit> visits) {
        // TODO, use members totalFootprint and totalCost to make simple. Need to implement this first in GasVisit
        float totalCost = 0;
        float totalFootprint = 0;

        for (int i = 0; i < visits.size(); i++) {
            GasVisit curVisit = visits.get(i);
            totalCost += curVisit.getTotalCost();
            totalFootprint += curVisit.getTotalFootprint();
        }
        totalCostView.setText(mainActivity.roundFloatString(Float.toString(totalCost)));
        totalFootprintView.setText(mainActivity.roundFloatString(Float.toString(totalFootprint)));
    }
}