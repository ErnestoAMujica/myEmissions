package com.example.myemissions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShowEmissions extends AppCompatActivity {

    String username, givenDate;
    TextView recentEmissionsText, emissionsTotal;
    Button goHomeButton;
    FileInterface emissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showemissions);

        WindowInsetsControllerCompat windowInsetsController = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        Bundle extras = getIntent().getExtras();
        try {
            username = extras.getString("username", "TestUser");
        }
        catch (Exception e){
            username = "TestUser";
        }
        try {
            givenDate = extras.getString("date", "N/A");
        }
        catch (Exception e){
            givenDate = "N/A";
        }

        recentEmissionsText = (TextView) findViewById(R.id.recent_emissions_text);
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        emissions = new FileInterface(ShowEmissions.this, username);
        goHomeButton = (Button) findViewById(R.id.goHomeButton);

        ArrayList<String> emissionsList;
        if(givenDate.equals("N/A"))
        {
            emissionsList = emissions.getAllEmissions();
            recentEmissionsText.setText("All lifetime emissions:");
        }
        else{
            emissionsList = emissions.getEmissionsFromDate(givenDate);
            recentEmissionsText.setText("Emissions from date: " + givenDate);
        }

        recentEmissionsText = (TextView) findViewById(R.id.recent_emissions_text);

        if(!emissionsList.isEmpty()) {
            double totalEmissions = 0;

            RelativeLayout rl = (RelativeLayout) findViewById(R.id.scroll_relative);
            CardView card;
            int verticalOffset = (int) (217 * scale + 0.5f);

            RelativeLayout.LayoutParams layoutParams;

            for (String line : emissionsList) {

                layoutParams = new RelativeLayout.LayoutParams
                        (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.width = (int) (360 * scale + 0.5f);
                layoutParams.height = (int) (70 * scale + 0.5f);
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                layoutParams.topMargin = verticalOffset;

                card = new CardView(ShowEmissions.this);
                card.setCardBackgroundColor(getResources().getColor(R.color.white));
                card.setRadius(15 * scale);
                card.setPreventCornerOverlap(false);
                card.setLayoutParams(layoutParams);
                rl.addView(card);
                verticalOffset += (int) (80 * scale + 0.5f);

                TextView description = new TextView(ShowEmissions.this);
                description.setTextColor(getResources().getColor(R.color.medium_gray));
                description.setTypeface(ResourcesCompat.getFont(ShowEmissions.this, R.font.sans_serif_bold));
                description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                layoutParams = new RelativeLayout.LayoutParams
                        (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                layoutParams.topMargin = (int) (10 * scale + 0.5f);
                layoutParams.leftMargin = (int) (20 * scale + 0.5f);
                description.setLayoutParams(layoutParams);

                TextView dateTime = new TextView(ShowEmissions.this);
                dateTime.setTextColor(getResources().getColor(R.color.light_gray));
                dateTime.setTypeface(ResourcesCompat.getFont(ShowEmissions.this, R.font.sans_serif_bold));
                dateTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                layoutParams = new RelativeLayout.LayoutParams
                        (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                layoutParams.topMargin = (int) (40 * scale + 0.5f);
                layoutParams.leftMargin = (int) (20 * scale + 0.5f);
                dateTime.setLayoutParams(layoutParams);

                TextView emissionData = new TextView(ShowEmissions.this);
                emissionData.setTextColor(getResources().getColor(R.color.dark_blue));
                emissionData.setTypeface(ResourcesCompat.getFont(ShowEmissions.this, R.font.sans_serif));
                emissionData.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                layoutParams = new RelativeLayout.LayoutParams
                        (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                emissionData.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
                layoutParams.setMarginEnd((int) (20 * scale + 0.5f));
                emissionData.setLayoutParams(layoutParams);

                EmissionCalculator.Source emissionType = EmissionCalculator.Source.valueOf(StringUtil.getBetween(line, "Source:", "\t"));
                EmissionCalculator calculator = new EmissionCalculator();
                double value = Double.parseDouble(StringUtil.getBetween(line, "Value:", "\t"));
                double emissionCalculation = calculator.transportation(emissionType, value);
                description.setText(emissionType.name + " - "
                        + StringUtil.getBetween(line, "Value:", "\t") + " "
                        + StringUtil.getBetween(line, "Unit:", "\t"));

                String date = StringUtil.getBetween(line, "Date:", "\t");
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                Date newDate = null;
                try {
                    newDate = dateFormat.parse(date);
                    dateFormat = new SimpleDateFormat("MMM dd");
                    date = dateFormat.format(newDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                dateTime.setText(date + " - " + StringUtil.getBetween(line, "Time:", "\t"));
                String emissionCalculationString = Double.toString(emissionCalculation);
                if(emissionCalculationString.length() > 4){
                    emissionCalculationString = emissionCalculationString.substring(0, 4);
                }
                emissionData.setText("+" + emissionCalculationString + " kg");
                totalEmissions += emissionCalculation;
                emissionCalculationString = Double.toString(totalEmissions);
                if(emissionCalculationString.length() > 4){
                    emissionCalculationString = emissionCalculationString.substring(0, 4);
                }
                TextView emissionTotal = findViewById(R.id.emissionsDisplayNumber);
                emissionTotal.setText(emissionCalculationString);
                card.addView(description);
                card.addView(dateTime);
                card.addView(emissionData);
            }
        }

        goHomeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent goHome = new Intent(getApplicationContext(), Home.class);
                goHome.putExtra("username", username);
                startActivity(goHome);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent goHome = new Intent(getApplicationContext(), Home.class);
        goHome.putExtra("username", username);
        startActivity(goHome);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}