package com.example.myemissions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Home extends AppCompatActivity {

    String username;
    TextView emissionsTotal, usernameDisplay, currentDate;
    FileInterface emissions;
    Button addEmissionsButton;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        WindowInsetsControllerCompat windowInsetsController = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        Bundle extras = getIntent().getExtras();
        try {
            username = extras.getString("username", "SampleName");
        }
        catch (Exception e){
            username = "TestUser";
        }

        addEmissionsButton = (Button) findViewById(R.id.mainPage_addEmissionsButton);

        final float scale = getResources().getDisplayMetrics().scaledDensity;

        emissionsTotal = (TextView) findViewById(R.id.mainPage_emissionsDisplayNumber);
        usernameDisplay = (TextView) findViewById(R.id.mainPage_name);
        currentDate = (TextView) findViewById(R.id.mainPage_date);

        String currentDateString = java.text.DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().getTime());
        currentDate.setText(currentDateString);
        usernameDisplay.setText("Hi, " + username + "!");

        emissions = new FileInterface(this, username);
        ArrayList<String> emissionsList = emissions.getEmissionsFromCurrentDate();

        TextView noEmissionsMessage = (TextView) findViewById(R.id.no_emissions_text);

        if(emissionsList.isEmpty()) {
            noEmissionsMessage.setText("No recent emissions to show.");
        }
        else {
            noEmissionsMessage.setText("");
            double totalEmissions = 0;

            RelativeLayout rl = (RelativeLayout) findViewById(R.id.scroll_relative);
            CardView card;
            int verticalOffset = (int)(120 * scale + 0.5f);

            RelativeLayout.LayoutParams layoutParams;

            for(String line : emissionsList) {

                layoutParams = new RelativeLayout.LayoutParams
                        (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.width = (int)(360 * scale + 0.5f);
                layoutParams.height = (int)(70 * scale + 0.5f);
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                layoutParams.topMargin = verticalOffset;

                card = new CardView(Home.this);
                card.setCardBackgroundColor(getResources().getColor(R.color.white));
                card.setRadius(15 * scale);
                card.setPreventCornerOverlap(false);
                card.setLayoutParams(layoutParams);
                rl.addView(card);
                verticalOffset += (int)(80 * scale + 0.5f);

                TextView description = new TextView(Home.this);
                description.setTextColor(getResources().getColor(R.color.medium_gray));
                description.setTypeface(ResourcesCompat.getFont(Home.this, R.font.sans_serif_bold));
                description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                layoutParams = new RelativeLayout.LayoutParams
                        (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                layoutParams.topMargin = (int)(10 * scale + 0.5f);
                layoutParams.leftMargin = (int)(20 * scale + 0.5f);
                description.setLayoutParams(layoutParams);

                TextView dateTime = new TextView(Home.this);
                dateTime.setTextColor(getResources().getColor(R.color.light_gray));
                dateTime.setTypeface(ResourcesCompat.getFont(Home.this, R.font.sans_serif_bold));
                dateTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                layoutParams = new RelativeLayout.LayoutParams
                        (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                layoutParams.topMargin = (int)(40 * scale + 0.5f);
                layoutParams.leftMargin = (int)(20 * scale + 0.5f);
                dateTime.setLayoutParams(layoutParams);

                TextView emissionData = new TextView(Home.this);
                emissionData.setTextColor(getResources().getColor(R.color.dark_blue));
                emissionData.setTypeface(ResourcesCompat.getFont(Home.this, R.font.sans_serif));
                emissionData.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
                layoutParams = new RelativeLayout.LayoutParams
                        (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                emissionData.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
                layoutParams.setMarginEnd((int)(20 * scale + 0.5f));
                emissionData.setLayoutParams(layoutParams);

                EmissionCalculator.SourceName emissionType = EmissionCalculator.SourceName.valueOf(StringUtil.getBetween(line, "Source:", "\t"));
                EmissionCalculator calculator = new EmissionCalculator();
                double value = Double.parseDouble(StringUtil.getBetween(line, "Value:", "\t"));
                double emissionCalculation = calculator.transportation(EmissionCalculator.Source.valueOf(emissionType.name()), value);
                description.setText(emissionType.emissionName + " - "
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

                emissionData.setText("+" + Double.toString(emissionCalculation).substring(0, 4) + " kg");
                totalEmissions += emissionCalculation;
                TextView emissionTotal = findViewById(R.id.mainPage_emissionsDisplayNumber);
                emissionTotal.setText(Double.toString(totalEmissions).substring(0, 5));
                card.addView(description);
                card.addView(dateTime);
                card.addView(emissionData);
            }

        }

        addEmissionsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent goToAddEmissionsPage = new Intent(getApplicationContext(), AddEmission.class);
                goToAddEmissionsPage.putExtra("username", username);
                startActivity(goToAddEmissionsPage);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }

}