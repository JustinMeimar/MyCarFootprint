package com.example.mycarfootprint;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SuccessAddFragment extends Fragment {

    public SuccessAddFragment() {
        // Required empty public constructor
    }

    public static SuccessAddFragment newInstance(String param1, String param2) {
        SuccessAddFragment fragment = new SuccessAddFragment();
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
        View contentView = inflater.inflate(R.layout.fragment_success_add, container, false);

        Button homeButton = contentView.findViewById(R.id.success_back_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(contentView).navigate(R.id.action_successAddFragment_to_homeFragment);
            }
        });


        return contentView;
    }
}