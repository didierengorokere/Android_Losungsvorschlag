/*
 * Copyright © 2017-2022
 * Merz Information and Communication Technology AG.
 * All rights reserved.
 *
 * C015-DE
 * Entwicklung von Mobile Apps mit Android: Programmiersprache Java
 */

package lab.app.util;

import android.content.Context;

import java.io.Serializable;

import lab.app.R;

public class BMI implements Serializable {

    private double height;
    private double weight;

    public BMI(double height, double weight) {
        this.height = height;
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double getBMI() {
        return weight / Math.pow(height / 100, 2);
    }

    public Rating getRating() {
        var bmi = getBMI();
        if (bmi < Rating.UNDERWEIGHT_VERY_SEVERELY.high) {
            return Rating.UNDERWEIGHT_VERY_SEVERELY;
        } else if (bmi < Rating.UNDERWEIGHT_SEVERELY.high) {
            return Rating.UNDERWEIGHT_SEVERELY;
        } else if (bmi < Rating.UNDERWEIGHT_MODERATELY.high) {
            return Rating.UNDERWEIGHT_MODERATELY;
        } else if (bmi < Rating.UNDERWEIGHT_SLIGHTLY.high) {
            return Rating.UNDERWEIGHT_SLIGHTLY;
        } else if (bmi < Rating.NORMAL.high) {
            return Rating.NORMAL;
        } else if (bmi < Rating.OVERWEIGHT.high) {
            return Rating.OVERWEIGHT;
        } else if (bmi < Rating.OBESE_MODERATELY.high) {
            return Rating.OBESE_MODERATELY;
        } else if (bmi < Rating.OBESE_SEVERELY.high) {
            return Rating.OBESE_SEVERELY;
        } else {
            return Rating.OBESE_VERY_SEVERELY;
        }
    }

    public enum Rating {

        UNDERWEIGHT_VERY_SEVERELY(Double.MIN_VALUE, 15.0),
        UNDERWEIGHT_SEVERELY(15.0, 16.0),
        UNDERWEIGHT_MODERATELY(16.0, 17.0),
        UNDERWEIGHT_SLIGHTLY(17.0, 18.5),
        NORMAL(18.5, 25.0),
        OVERWEIGHT(25.0, 30.0),
        OBESE_MODERATELY(30.0, 35.0),
        OBESE_SEVERELY(35.0, 40.0),
        OBESE_VERY_SEVERELY(40.0, Double.MAX_VALUE);

        private double low;
        private double high;

        Rating(double low, double high) {
            this.low = low;
            this.high = high;
        }

        public static String[] getDescriptions(Context context) {
            return context.getResources().getStringArray(R.array.bmi_rating_descriptions);
        }

        public String getDescription(Context context) {
            return getDescriptions(context)[ordinal()];
        }

        public double getLow() {
            return low;
        }

        public double getHigh() {
            return high;
        }
    }
}
