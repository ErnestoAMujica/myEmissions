package com.example.myemissions;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class AddEmission extends AppCompatActivity{

    private double value = 0.00, emission = 0.00;
    private String item = "";
    TextView emissionCount, usage;
    Spinner source, method, size, method2;
    EmissionCalculator.Source type;
    CardView addButton;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addemission);

        Bundle extras = getIntent().getExtras();
        try {
            username = extras.getString("username", "SampleName");
        }
        catch (Exception e){
            username = "TestUser";
        }

        //page content
        EmissionCalculator calc = new EmissionCalculator();
        emissionCount = findViewById(R.id.textView);
        source = findViewById(R.id.spinner);
        method = findViewById(R.id.spinner1);
        method2 = findViewById(R.id.spinner3);
        size = findViewById(R.id.spinner2);
        usage = findViewById(R.id.editTextNumberDecimal2);
        addButton = findViewById(R.id.add_card);

        //initialize visibility of elements
        method.setVisibility(View.GONE);
        method2.setVisibility(View.GONE);
        size.setVisibility(View.GONE);
        usage.setVisibility(View.GONE);

        //set spinner values (method2 can change but I wanted it to be initialized to something)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.source_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        source.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.transportation_items,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        method.setAdapter(adapter);

        //vehicle setting for method2
        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(this, R.array.vehicle_types,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        method2.setAdapter(adp);

        //flight setting for method2
        ArrayAdapter<CharSequence> ad = ArrayAdapter.createFromResource(this,
                R.array.flight_type, android.R.layout.simple_spinner_item);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter = ArrayAdapter.createFromResource(this, R.array.vehicle_sizes,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        size.setAdapter(adapter);

        //source spinner
        source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 item = (String) adapterView.getItemAtPosition(i);
                 usage.setText("");

                 if (item.equals("Transportation")) {
                     method.setVisibility(View.VISIBLE);
                     method.setSelection(0);
                     method2.setVisibility(View.GONE);
                     size.setVisibility(View.GONE);
                     usage.setVisibility(View.GONE);
                     usage.setHint(getString(R.string.usage));
                 } else if (item.equals("Energy")) {
                     method.setVisibility(View.GONE);
                     method2.setVisibility(View.GONE);
                     size.setVisibility(View.GONE);
                     usage.setHint(getString(R.string.usageE));
                     usage.setVisibility(View.VISIBLE);
                     type = EmissionCalculator.Source.energy;
                 } else if (item.equals("Natural Gas")) {
                     method.setVisibility(View.GONE);
                     method2.setVisibility(View.GONE);
                     size.setVisibility(View.GONE);
                     usage.setHint(getString(R.string.usageN));
                     usage.setVisibility(View.VISIBLE);
                     type = EmissionCalculator.Source.natural_gas;
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {

             }
        });

        //if transportation is selected, we select the kind of transportation
        method.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = (String) adapterView.getItemAtPosition(i);
                usage.setText("");

                if (item.equals("Personal Vehicle")) {
                    method2.setVisibility(View.VISIBLE);
                    method2.setAdapter(adp);
                    method2.setSelection(0);
                    size.setVisibility(View.GONE);
                    usage.setVisibility(View.GONE);
                } else if (item.equals("Taxi")) {
                    method2.setVisibility(View.GONE);
                    size.setVisibility(View.GONE);
                    usage.setVisibility(View.VISIBLE);
                    type = EmissionCalculator.Source.taxi;
                } else if (item.equals("Bus")) {
                    method2.setVisibility(View.GONE);
                    size.setVisibility(View.GONE);
                    usage.setVisibility(View.VISIBLE);
                    type = EmissionCalculator.Source.bus;
                } else if (item.equals("Train")) {
                    method2.setVisibility(View.GONE);
                    size.setVisibility(View.GONE);
                    usage.setVisibility(View.VISIBLE);
                    type = EmissionCalculator.Source.train;
                } else if (item.equals("Plane")) {
                    method2.setAdapter(ad);
                    method2.setVisibility(View.VISIBLE);
                    method2.setSelection(0);
                    size.setVisibility(View.GONE);
                    usage.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //if personal vehicle or flight is selected, select the kind
        method2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) adapterView.getItemAtPosition(i);
                usage.setText("");

                if (item.equals("Short-haul - Economy")) {
                    usage.setVisibility(View.VISIBLE);
                    size.setVisibility(View.GONE);
                    type = EmissionCalculator.Source.economy_shorthaul;
                } else if (item.equals("Short-haul - Business")) {
                    usage.setVisibility(View.VISIBLE);
                    size.setVisibility(View.GONE);
                    type = EmissionCalculator.Source.business_shorthaul;
                } else if (item.equals("Long-haul - Economy")) {
                    usage.setVisibility(View.VISIBLE);
                    size.setVisibility(View.GONE);
                    type = EmissionCalculator.Source.economy_longhaul;
                } else if (item.equals("Long-haul - Economy Plus")) {
                    usage.setVisibility(View.VISIBLE);
                    size.setVisibility(View.GONE);
                    type = EmissionCalculator.Source.economyplus_longhaul;
                } else if (item.equals("Long-haul - Business")) {
                    usage.setVisibility(View.VISIBLE);
                    size.setVisibility(View.GONE);
                    type = EmissionCalculator.Source.business_longhaul;
                } else if (item.equals("Long-haul - First Class")) {
                    usage.setVisibility(View.VISIBLE);
                    size.setVisibility(View.GONE);
                    type = EmissionCalculator.Source.first_longhaul;
                } else if (item.equals("Diesel") || item.equals("Gas") || item.equals("Electric") ||
                    item.equals("Hybrid") || item.equals("Hybrid Plugin")){
                    size.setVisibility(View.VISIBLE);
                    size.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //after vehicle type is specified we choose the size
        size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sz = (String) adapterView.getItemAtPosition(i);

                if (sz.equals("- Select Vehicle Size -")) {
                    usage.setVisibility(View.GONE);
                } else {
                    usage.setVisibility(View.VISIBLE);
                    usage.setText("");
                }

                if (item.equals("Diesel")) {
                    if (sz.equals("Small")) {
                        type = EmissionCalculator.Source.diesel_small;
                    } else if (sz.equals("Medium")) {
                        type = EmissionCalculator.Source.diesel_medium;
                    } else if (sz.equals("Large")) {
                        type = EmissionCalculator.Source.diesel_large;
                    }
                } else if (item.equals("Gas")) {
                    if (sz.equals("Small")) {
                        type = EmissionCalculator.Source.gas_small;
                    } else if (sz.equals("Medium")) {
                        type = EmissionCalculator.Source.gas_medium;
                    } else if (sz.equals("Large")) {
                        type = EmissionCalculator.Source.gas_large;
                    }
                } else if (item.equals("Hybrid")) {
                    if (sz.equals("Small")) {
                        type = EmissionCalculator.Source.hybrid_small;
                    } else if (sz.equals("Medium")) {
                        type = EmissionCalculator.Source.hybrid_medium;
                    } else if (sz.equals("Large")) {
                        type = EmissionCalculator.Source.hybrid_large;
                    }
                } else if (item.equals("Hybrid Plugin")) {
                    if (sz.equals("Small")) {
                        type = EmissionCalculator.Source.hybrid_plugin_small;
                    } else if (sz.equals("Medium")) {
                        type = EmissionCalculator.Source.hybrid_plugin_medium;
                    } else if (sz.equals("Large")) {
                        type = EmissionCalculator.Source.hybrid_plugin_large;
                    }
                } else if (item.equals("Electric")) {
                    if (sz.equals("Small")) {
                        type = EmissionCalculator.Source.electric_small;
                    } else if (sz.equals("Medium")) {
                        type = EmissionCalculator.Source.electric_medium;
                    } else if (sz.equals("Large")) {
                        type = EmissionCalculator.Source.electric_large;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //calculate the emission
        usage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    value = Double.parseDouble(String.valueOf(textView.getText()));
                    if (type.equals(EmissionCalculator.Source.energy)) {
                        emission = calc.energy(value);
                    } else if (type.equals(EmissionCalculator.Source.natural_gas)) {
                        emission = calc.natural_gas(value);
                    } else {
                        emission = calc.transportation(type, value);
                    }
                    emissionCount.setText(String.format("%.2f", emission));
                    return true;
                }
                return false;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToHome = new Intent(getApplicationContext(), Home.class);
                startActivity(goToHome);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle extras = getIntent().getExtras();
        try {
            username = extras.getString("username", "SampleName");
        } catch (Exception e) {
            username = "TestUser";
        }

        Intent goToHomePage = new Intent(getApplicationContext(), Home.class);
        goToHomePage.putExtra("username", username);
        startActivity(goToHomePage);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}