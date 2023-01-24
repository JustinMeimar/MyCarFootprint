package com.example.mycarfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<GasVisit> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<GasVisit>();

        GasVisit visit1 = new GasVisit();
        GasVisit visit2 = new GasVisit();

        visit1.setDate("2022-12-12");
        visit1.setFuelAmount(12);
        visit1.setFuelUnitPrice((float) 12.3);
        visit1.setGasStationName("Shell");

        visit2.setGasStationName("Esso");
        visit2.setDate("2022-12-12");
        visit2.setFuelAmount(12);
        visit2.setFuelUnitPrice((float) 12.3);

        list.add(visit1);
        list.add(visit2);

    }
}