package com.blazhrusovar.starwarsplanets.model;

import android.os.Parcel;
import android.os.Parcelable;


public class PlanetsData implements Parcelable {

    private String name;
    private int rotation_period;
    private int orbital_period;
    private int diameter;
    private String gravity;
    private String terrain;
    private String population;

    public PlanetsData(String name, int rotation_period, int orbital_period, int diameter, String gravity, String terrain, String population) {
        this.name = name;
        this.rotation_period = rotation_period;
        this.orbital_period = orbital_period;
        this.diameter = diameter;
        this.gravity = gravity;
        this.terrain = terrain;
        this.population = population;
    }

    protected PlanetsData(Parcel in) {
        name = in.readString();
        rotation_period = in.readInt();
        orbital_period = in.readInt();
        diameter = in.readInt();
        gravity = in.readString();
        terrain = in.readString();
        population = in.readString();
    }

    public static final Creator<PlanetsData> CREATOR = new Creator<PlanetsData>() {
        @Override
        public PlanetsData createFromParcel(Parcel in) {
            return new PlanetsData(in);
        }

        @Override
        public PlanetsData[] newArray(int size) {
            return new PlanetsData[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getRotation_period() {
        return rotation_period;
    }

    public int getOrbital_period() {
        return orbital_period;
    }

    public int getDiameter() {
        return diameter;
    }

    public String getGravity() {
        return gravity;
    }

    public String getTerrain() {
        return terrain;
    }

    public String getPopulation() {
        return population;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(rotation_period);
        parcel.writeInt(orbital_period);
        parcel.writeInt(diameter);
        parcel.writeString(gravity);
        parcel.writeString(terrain);
        parcel.writeString(population);

    }
}
