package com.abb.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Created by Abarajithan
 */
public class Ingredient implements Parcelable {

    private String measure;
    private String ingredient;
    private double quantity;

    public Ingredient() {

    }

    protected Ingredient(Parcel in) {
        measure = in.readString();
        ingredient = in.readString();
        quantity = in.readDouble();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(measure);
        dest.writeString(ingredient);
        dest.writeDouble(quantity);
    }

    @NonNull
    @Override
    public String toString() {
        return quantity + " " + measure + " - " + ingredient;
    }
}
