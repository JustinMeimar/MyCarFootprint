package com.example.mycarfootprint;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class addGasFragment extends Fragment {

    private GasVisit gasVisit;
    private MainActivity mainActivity;

    private Button submitButton;
    private RadioButton regularButton;
    private RadioButton dieselButton;
    private EditText gasStationText;
    private EditText dateText;
    private EditText fuelAmountText;
    private EditText fuelPriceText;
    private TextView fuelCostText;      //non editable by user
    private TextView fuelEmissionsText; //non editable by user
    private boolean editExisting = false;

    static final float dieselEmissions = (float) 2.69;
    static final float regularEmissions = (float) 2.32;

    public boolean gasStationValid, dateValid, fuelAmountValid, fuelPriceValid, fuelTypeValid;

    public addGasFragment() {
        // Required empty public constructor
    }

    public static addGasFragment newInstance(String param1, String param2) {
        addGasFragment fragment = new addGasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean validateFormStatus() {

        if (gasStationValid && dateValid && fuelAmountValid && fuelPriceValid && fuelTypeValid) {
            return true;
        }
        return false;
    }

    public void setFormStatus(boolean state) {
        gasStationValid = state;
        dateValid = state;
        fuelAmountValid = state;
        fuelPriceValid = state;
        fuelTypeValid = state;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_gas, container, false);

        //buttons
        submitButton =      rootView.findViewById(R.id.button_submit_fillup);
        regularButton =     rootView.findViewById(R.id.visit_form_fuel_type_radio_regular);
        dieselButton =      rootView.findViewById(R.id.visit_form_fuel_type_radio_diesel);

        // edit text
        gasStationText =    rootView.findViewById(R.id.visit_form_gas_station_name);
        dateText =          rootView.findViewById(R.id.visit_form_date);
        fuelAmountText =    rootView.findViewById(R.id.visit_form_fuel_amount);
        fuelPriceText =     rootView.findViewById(R.id.visit_form_fuel_unit_price);

        // diplsay statistics
        fuelCostText =      rootView.findViewById(R.id.visit_form_fuel_cost_value);
        fuelEmissionsText = rootView.findViewById(R.id.visit_form_total_footprint_value);


        mainActivity = (MainActivity) getActivity();
        GasVisit joinGasVisit = new GasVisit();

        int index = addGasFragmentArgs.fromBundle(getArguments()).getEditIndex();
        if (index != -1) { //if coming from edit button
            joinGasVisit = mainActivity.list.get(index);
            initializeExistingFields(joinGasVisit);
            editExisting = true;
            setFormStatus(true);
        }

        gasVisit = joinGasVisit;

        gasStationText.addTextChangedListener(new TextWatcher() {

            @Override // non optional override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { /* do nothing */ }

            @Override //non optional override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    String gasStationName = gasStationText.getText().toString();
                    gasVisit.setGasStationName(gasStationName);
                    gasStationValid = true;
                } catch (Exception e) {
                    gasStationText.setError("Invalid Gas Station Name");
                    gasStationValid = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { /* do nothing */ }
        });

        dateText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    String date = dateText.getText().toString();
                    gasVisit.setDate(date);
                    dateValid = true;
                } catch (Exception e) {
                    dateText.setError("Invalid Date Format, please follow \"yyyy/mm/dd\" format");
                    dateValid = false;
                }
            }
        });

        fuelAmountText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { /* do nothing */ }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try { // Fuel Amount
                    Integer fuelAmount = Integer.parseInt(fuelAmountText.getText().toString());
                    gasVisit.setFuelAmount(fuelAmount);
                    fuelAmountValid = true;
                } catch (Exception e) {
                    fuelAmountText.setError("Invalid Fuel Amount");
                    fuelAmountValid = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculateCarbonStats();
            }
        });

        fuelPriceText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { /* do nothing */ }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try { // Fuel Unit Price
                    Float fuelUnitPrice = Float.parseFloat(fuelPriceText.getText().toString());
                    gasVisit.setFuelUnitPrice(fuelUnitPrice);
                    fuelPriceValid = true;
                } catch (Exception e) {
                    fuelPriceText.setError("Invalid Fuel Unit Price");
                    fuelPriceValid = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculateCarbonStats();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validate form status
                MainActivity mainActivity = (MainActivity) getActivity();

                if(!validateFormStatus()) {
                    Log.d("FAILED SUBMIT", "onClick: ");
                    Toast.makeText(mainActivity, "Missing or Incorrect Field!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (editExisting)  {
                    mainActivity.list.remove(index);
                    mainActivity.list.add(index, gasVisit);

                    Log.d("Current List", mainActivity.list.toString());
                } else {
                    Log.d("Fuel tpye of gas visit about to be added: ", gasVisit.getFuelType().name());
                    mainActivity.list.add(gasVisit);
                }
                Navigation.findNavController(view).navigate(R.id.action_addGasFragment_to_successAddFragment);
            }
        });

        regularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = regularButton.isChecked();
                if (checked) {
                    gasVisit.setFuelType(GasVisit.FuelType.REGULAR);
                    if (dieselButton.isChecked()) {
                        dieselButton.setChecked(false);
                    }
                }
                fuelTypeValid = true;
                calculateCarbonStats();
            }
        });

        dieselButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = dieselButton.isChecked();
                if (checked) {
                    gasVisit.setFuelType(GasVisit.FuelType.DIESEL);
                    if (regularButton.isChecked()) {
                        regularButton.setChecked(false);
                    }
                }
                fuelTypeValid = true;
                calculateCarbonStats();
            }
        });

        return rootView;
    }

    // https://stackoverflow.com/questions/153724/how-to-round-a-number-to-n-decimal-places-in-java
    private void calculateCarbonStats() {
        float coefficient;

        if (regularButton.isChecked()) { coefficient = regularEmissions; }
        else { coefficient = dieselEmissions; }

        float fuelCost = ( gasVisit.getFuelPrice() == null || gasVisit.getFuelAmount() == null) ? 0 : (gasVisit.getFuelPrice() * gasVisit.getFuelAmount());
        float fuelEmissions = ( gasVisit.getFuelType() == null || gasVisit.getFuelAmount() == null) ? 0 : gasVisit.getFuelAmount() * coefficient;

        gasVisit.setTotalCost(fuelCost);
        gasVisit.setTotalFootprint(fuelEmissions);

        fuelCostText.setText(
                mainActivity.roundFloatString(Float.toString(fuelCost))
        );
        fuelEmissionsText.setText(
                mainActivity.roundFloatString(Float.toString(fuelEmissions))
        );
    }

    // Reusing interface for editing existing gas entry
    private void initializeExistingFields(GasVisit gasVisit) {

        gasStationText.setText(gasVisit.getGasStationName());
        dateText.setText(gasVisit.getDate());
        fuelAmountText.setText(gasVisit.getFuelAmount().toString());
        fuelPriceText.setText(gasVisit.getFuelPrice().toString());

        if (gasVisit.getFuelType() == GasVisit.FuelType.DIESEL) {
            dieselButton.setChecked(true);
            regularButton.setChecked(false);
        } else {
            dieselButton.setChecked(false);
            regularButton.setChecked(true);
        }
    }
}