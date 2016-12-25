package me.vivekpatel.myapplication;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;
import java.util.Collections;

import me.vivekpatel.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final String TAG;

    static {
        TAG = MainActivity.class.getSimpleName();
    }

    private ActivityMainBinding binding;
    private ArrayList<String> allCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupCities();
        setupAdapter(allCities);
        setupListeners();
    }

    private void setupCities() {
        // setup allCities
        allCities = new ArrayList<>();
        allCities.add("paris");
        allCities.add("tijuana");
        allCities.add("milan");
        allCities.add("rome");
        allCities.add("amsterdam");
        allCities.add("dubai");

        // setup MultiAutocompleteTextView
        binding.multiview.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }

    private void setupAdapter(ArrayList<String> cities) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, cities);
        binding.multiview.setAdapter(adapter);
    }

    private void setupListeners() {
        binding.multiview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Log.d(TAG, "afterTextChanged() called:");
                String text = editable.toString().trim().toLowerCase();
                Log.d(TAG, "afterTextChanged: text is: " + text);
                if (text.isEmpty()) {
                    // if textBox is empty
                    // reset adapter
                    setupAdapter(allCities);

                } else if (text.contains(",") && text.endsWith(",")) {
                    // get all selected city first
                    ArrayList<String> selectedCities = getSelectedCities(text);
                    ArrayList<String> unSelectedCities = getUnselectedCities(selectedCities);
                    setupAdapter(unSelectedCities);
                }
            }
        });
    }

    public ArrayList<String> getSelectedCities(String text) {
        ArrayList<String> cities = new ArrayList<>();

        // split cities
        String[] cityArr = text.split(",");

        // remove starting & trailing spaces
        for (int i = 0; i < cityArr.length; i++) {
            cityArr[i] = cityArr[i].trim();
        }

        // add it to list
        Collections.addAll(cities, cityArr);
        Log.d(TAG, "getSelectedCities() called");
        Log.d(TAG, "getSelectedCities() size: " + cities.size());
        for (String city : cities) {
            Log.d(TAG, "getSelectedCities: [" + city + "]");
        }
        return cities;
    }

    public ArrayList<String> getUnselectedCities(ArrayList<String> selectedCities) {
        ArrayList<String> cities = new ArrayList<>();
        for (String city : allCities) {
            if (!selectedCities.contains(city)) {
                cities.add(city);
            }
        }
        Log.d(TAG, "getUnselectedCities() called");
        Log.d(TAG, "getUnselectedCities() size: " + cities.size());
        for (String city : cities) {
            Log.d(TAG, "getSelectedCities: [" + city + "]");
        }
        return cities;
    }
}
