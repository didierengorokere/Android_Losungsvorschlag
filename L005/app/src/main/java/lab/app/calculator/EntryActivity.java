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
import android.widget.EditText;
import android.widget.Toast;

import lab.app.BuildConfig;
import lab.app.HomeActivity;
import lab.app.R;
import lab.app.config.SettingsActivity;
import lab.app.rating.LegendActivity;
import lab.app.util.BMI;

public class EntryActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_general, menu);
        menu.findItem(R.id.menu_general_calculator).setEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
        case R.id.menu_general_home:
            startActivity(new Intent(this, HomeActivity.class));
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
        setContentView(R.layout.activity_calculator_entry);
        var numberHeight = (EditText) findViewById(R.id.number_calculator_entry_height);
        var numberWeight = (EditText) findViewById(R.id.number_calculator_entry_weight);
        var buttonClearValues = findViewById(R.id.button_calculator_entry_clear_values);
        buttonClearValues.setOnClickListener(view -> {
            numberHeight.setText("");
            numberWeight.setText("");
            numberHeight.requestFocus();
        });
        var buttonComputeBMI = findViewById(R.id.button_calculator_entry_compute_bmi);
        buttonComputeBMI.setOnClickListener(view -> {
            var heightText = numberHeight.getText().toString();
            var weightText = numberWeight.getText().toString();
            if (!heightText.isEmpty() && !weightText.isEmpty()) {
                var height = Double.parseDouble(heightText);
                var weight = Double.parseDouble(weightText);
                var values = new BMI(height, weight);
                var intent = new Intent(this, ResultActivity.class);
                intent.putExtra("values", values);
                startActivity(intent);
            }
        });
    }
}
