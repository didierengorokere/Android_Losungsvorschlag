/*
 * Copyright © 2017-2022
 * Merz Information and Communication Technology AG.
 * All rights reserved.
 *
 * C015-DE
 * Entwicklung von Mobile Apps mit Android: Programmiersprache Java
 */

package lab.app.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import lab.app.BuildConfig;
import lab.app.HomeActivity;
import lab.app.R;
import lab.app.config.SettingsActivity;
import lab.app.rating.DetailActivity;
import lab.app.rating.LegendActivity;
import lab.app.util.BMI;

public class ResultActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
        case R.id.menu_general_home:
            startActivity(new Intent(this, HomeActivity.class));
            return true;
        case R.id.menu_general_calculator:
            startActivity(new Intent(this, EntryActivity.class));
            return true;
        case R.id.menu_general_rating:
            startActivity(new Intent(this, LegendActivity.class));
            return true;
        case R.id.menu_general_history:
            Toast.makeText(this, "History chosen (not implemented)", Toast.LENGTH_LONG).show();
            return true;
        case R.id.menu_general_settings:
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        default:
            if (BuildConfig.DEBUG) {
                throw new AssertionError("Missing options menu item.");
            }
            return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_result);
        var values = (BMI) getIntent().getSerializableExtra("values");
        double bmi = values.getBMI();
        var rating = values.getRating();
        var textViewHeight = (TextView) findViewById(R.id.output_calculator_result_height);
        textViewHeight.setText(String.format("%.2f cm", values.getHeight()));
        var textViewWeight = (TextView) findViewById(R.id.output_calculator_result_weight);
        textViewWeight.setText(String.format("%.2f kg", values.getWeight()));
        var textViewBMI = (TextView) findViewById(R.id.output_calculator_result_bmi);
        textViewBMI.setText(String.format("%.2f (rounded)", bmi));
        var textViewRating = (TextView) findViewById(R.id.output_calculator_result_rating);
        textViewRating.setText(rating.getDescription(this));
        var buttonSaveData = findViewById(R.id.button_calculator_result_save_data);
        buttonSaveData.setOnClickListener(view -> {
            Toast.makeText(this, "BMI data saved (not implemented)", Toast.LENGTH_LONG).show();
        });
        var buttonShowDetails = findViewById(R.id.button_calculator_result_show_details);
        buttonShowDetails.setOnClickListener(view -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("position", rating.ordinal());
            startActivity(intent);
        });
    }
}
