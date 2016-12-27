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
    private ArrayList<String> allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupCities();
        setupAdapter(allItems);
        setupListeners();
    }

    private void setupCities() {
        // setup allItems
        allItems = new ArrayList<>();
        allItems.add("paris");
        allItems.add("tijuana");
        allItems.add("milan");
        allItems.add("rome");
        allItems.add("amsterdam");
        allItems.add("dubai");

        // setup MultiAutocompleteTextView
        binding.multiview.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }

    private void setupAdapter(ArrayList<String> items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, items);
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
                    setupAdapter(allItems);

                } else if (text.contains(",") && text.endsWith(",")) {
                    // get all selected item first
                    ArrayList<String> selectedItems = getSelectedItems(text);
                    ArrayList<String> unSelectedItems = getUnselectedItems(selectedItems);
                    setupAdapter(unSelectedItems);
                }
            }
        });
    }

    public ArrayList<String> getSelectedItems(String text) {
        ArrayList<String> items = new ArrayList<>();

        // split items
        String[] itemArr = text.split(",");

        // remove starting & trailing spaces
        for (int i = 0; i < itemArr.length; i++) {
            itemArr[i] = itemArr[i].trim();
        }

        // add it to list
        Collections.addAll(items, itemArr);
        Log.d(TAG, "getSelectedItems() called");
        Log.d(TAG, "getSelectedItems() size: " + items.size());
        for (String item : items) {
            Log.d(TAG, "getSelectedItems: [" + item + "]");
        }
        return items;
    }

    public ArrayList<String> getUnselectedItems(ArrayList<String> selectedCities) {
        ArrayList<String> items = new ArrayList<>();
        for (String item : allItems) {
            if (!selectedCities.contains(item)) {
                items.add(item);
            }
        }
        Log.d(TAG, "getUnselectedItems() called");
        Log.d(TAG, "getUnselectedItems() size: " + items.size());
        for (String item : items) {
            Log.d(TAG, "getSelectedItems: [" + item + "]");
        }
        return items;
    }
}
