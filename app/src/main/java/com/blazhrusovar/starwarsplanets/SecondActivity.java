package com.blazhrusovar.starwarsplanets;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blazhrusovar.starwarsplanets.model.PlanetsData;

public class SecondActivity extends AppCompatActivity {

    public LinearLayout linearLayout;

    private PlanetsData planetsData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        Bundle extras = getIntent().getExtras();
        planetsData = extras.getParcelable(MainActivity.DATA);


        if (planetsData != null) {

            setTitle(planetsData.getName());

            addNewTextView("Rotation period: " + String.valueOf(planetsData.getRotation_period()));
            addNewTextView("Orbital period: " + String.valueOf(planetsData.getOrbital_period()));
            addNewTextView("Diameter: " + String.valueOf(planetsData.getDiameter()));
            addNewTextView("Gravity: " + String.valueOf(planetsData.getGravity()));
            addNewTextView("Terrain: " + String.valueOf(planetsData.getTerrain()));
            addNewTextView("Population: " + String.valueOf(planetsData.getPopulation()));

        }

    }

    private void addNewTextView(String string){

        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        textView.setText(string);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        linearLayout.addView(textView);

    }

}
