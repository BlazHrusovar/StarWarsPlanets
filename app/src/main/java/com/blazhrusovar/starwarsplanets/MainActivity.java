package com.blazhrusovar.starwarsplanets;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blazhrusovar.starwarsplanets.model.PlanetsData;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String DATA = "planets_data";

    Spinner spinnerPlanets;
    Button weightCalculate;
    EditText weightInput;
    TextView result, planetClick;
    ImageView planetImg;
    SweetAlertDialog pDialog;
    int lastClickedPlanet;
    public double planetWeight;

    private final OkHttpClient client = new OkHttpClient();

    public PlanetsData[] planetsData;

    final DecimalFormat d = new DecimalFormat("#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        requestPlanets();


        planetImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(DATA, planetsData[lastClickedPlanet]);

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, planetImg, "planetImg");
                ActivityCompat.startActivity(MainActivity.this,intent,options.toBundle());
            }
        });


        //Spinner
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.planets_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlanets.setAdapter(adapter);

    }


    private void init(){
        spinnerPlanets = (Spinner) findViewById(R.id.spinnerPlanets);
        weightCalculate = (Button) findViewById(R.id.weightCalculate);
        weightInput = (EditText) findViewById(R.id.weightInput);
        result = (TextView) findViewById(R.id.result);
        planetClick = (TextView) findViewById(R.id.planetClick);
        planetImg = (ImageView) findViewById(R.id.planetImg);


        result.setVisibility(View.GONE);
        planetClick.setVisibility(View.GONE);

        weightCalculate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        try {

            double dblWeight = Double.valueOf(weightInput.getText().toString());
            String strPlanet = spinnerPlanets.getSelectedItem().toString();
            lastClickedPlanet = spinnerPlanets.getSelectedItemPosition();


            result.setVisibility(View.VISIBLE);
            planetClick.setVisibility(View.VISIBLE);



            //Nisem našel mase planetov, zato sem uporabil kar dummy podatke.

            switch (strPlanet) {
                case "Alderaan":
                    planetWeight = (dblWeight * 1.5) / getPlanetGravity(planetsData[0]);
                    Glide.with(this)
                            .load(R.drawable.alderaan)
                            .into(planetImg);
                    break;
                case "Yavin IV":
                    planetWeight = (dblWeight * 2.369) / getPlanetGravity(planetsData[1]);
                    Glide.with(this)
                            .load(R.drawable.yavin)
                            .into(planetImg);
                    break;
                case "Hoth":
                    planetWeight = (dblWeight * 3.387) / getPlanetGravity(planetsData[2]);
                    Glide.with(this)
                            .load(R.drawable.hoth)
                            .into(planetImg);
                    break;
                case "Dagobah":
                    planetWeight = (dblWeight * 2.843) / 1; //V JSON-u N/A
                    Glide.with(this)
                            .load(R.drawable.dagobah)
                            .into(planetImg);
                    break;
                case "Bespin":
                    planetWeight = (dblWeight * 1.104) / getPlanetGravity(planetsData[4]);
                    Glide.with(this)
                            .load(R.drawable.bespin)
                            .into(planetImg);
                    break;
                case "Endor":
                    planetWeight = (dblWeight * 2.669) / getPlanetGravity(planetsData[5]);
                    Glide.with(this)
                            .load(R.drawable.endor)
                            .into(planetImg);
                    break;
                case "Naboo":
                    planetWeight = (dblWeight * 3.114) / getPlanetGravity(planetsData[6]);
                    Glide.with(this)
                            .load(R.drawable.naboo)
                            .into(planetImg);
                    break;
                case "Coruscant":
                    planetWeight = (dblWeight * 2.37) / getPlanetGravity(planetsData[7]);
                    Glide.with(this)
                            .load(R.drawable.coruscant)
                            .into(planetImg);
                    break;
                case "Kamino":
                    planetWeight = (dblWeight * 1.98) / getPlanetGravity(planetsData[8]);
                    Glide.with(this)
                            .load(R.drawable.kamino)
                            .into(planetImg);
                    break;
                case "Geonosis":
                    planetWeight = (dblWeight * 2.171) / getPlanetGravity(planetsData[9]);
                    Glide.with(this)
                            .load(R.drawable.geonosis)
                            .into(planetImg);
                    break;
            }

            result.setText("Your weight on " + strPlanet + " is: " + d.format(planetWeight) + " kg!");

        } catch (Exception e){
            Toast.makeText(this, "Please enter your weight.", Toast.LENGTH_LONG).show();
        }
    }

    private Float getPlanetGravity(PlanetsData planet){
        String gravity = planet.getGravity();
        String[] gravitySplit = gravity.split("\\s+");
        return Float.parseFloat(gravitySplit[0]);
    }


    private void requestPlanets(){

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                .url("http://www.json-generator.com/api/json/get/bQAFhRYgpu?indent=2")
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(com.squareup.okhttp.Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.dismiss();
                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Connection not found!")
                                .setConfirmText("Retry")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        requestPlanets();
                                    }
                                })
                                .show();
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()){
                    throw new IOException("unexpected code: " + response);
                }

                Gson gson = new Gson();
                planetsData = gson.fromJson(response.body().string(), PlanetsData[].class);

                pDialog.dismiss();
            }
        });
    }

}


