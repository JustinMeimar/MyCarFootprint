package com.example.mycarfootprint;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavArgs;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class EditFragment extends Fragment {

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(String param1, String param2) {
        EditFragment fragment = new EditFragment(); Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_edit, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        int index = EditFragmentArgs.fromBundle(getArguments()).getVisitIndex();

        GasVisit selectedVisit;
        if (index < mainActivity.list.size()) {
            selectedVisit = mainActivity.list.get(index);
        } else {
            selectedVisit = new GasVisit();
        }

        setTextToVisitFields(contentView, selectedVisit);

        Button deleteButton = (Button) contentView.findViewById(R.id.button_delete_visit);
        Button editButton = (Button) contentView.findViewById(R.id.button_edit_vist);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index < mainActivity.list.size()) {
                    mainActivity.list.remove(index);
                }

                Navigation.findNavController(contentView).navigate(R.id.action_editFragment_to_listFragment);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(contentView).navigate(
                        EditFragmentDirections.actionEditFragmentToAddGasFragment(index)
                );
            }
        });

        return contentView;
    }

    public void setTextToVisitFields(View contentView, GasVisit selectedVisit) {

        TextView gasName = (TextView) contentView.findViewById(R.id.edit_view_gas_name);
        TextView date = (TextView) contentView.findViewById(R.id.edit_view_date);
        TextView gasAmount = (TextView) contentView.findViewById(R.id.edit_view_gas_amount);
        TextView gasPrice = (TextView) contentView.findViewById(R.id.edit_view_gas_price);

        if  (selectedVisit.getGasStationName() != null) {
            gasName.setText(selectedVisit.getGasStationName());
        }
        if (selectedVisit.getDate() != null) {
            date.setText(selectedVisit.getDate());
        }
        if (selectedVisit.getFuelAmount() != null) {
            gasAmount.setText(selectedVisit.getFuelAmount().toString());
        }
        if (selectedVisit.getFuelPrice() != null) {
            gasPrice.setText(selectedVisit.getFuelPrice().toString());
        }
    }
}