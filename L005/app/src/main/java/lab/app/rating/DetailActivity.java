/*
 * Copyright © 2017-2022
 * Merz Information and Communication Technology AG.
 * All rights reserved.
 *
 * C015-DE
 * Entwicklung von Mobile Apps mit Android: Programmiersprache Java
 */

package lab.app.rating;

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
import lab.app.calculator.EntryActivity;
import lab.app.config.SettingsActivity;
import lab.app.util.BMI;

public class DetailActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_rating_detail);
        var position = getIntent().getIntExtra("position", 0);
        var rating = BMI.Rating.values()[position];
        var low = rating.getLow();
        var high = rating.getHigh();
        var range = "";
        if (low == Double.MIN_VALUE) {
            range = String.format("Values less than %.2f.", high);
        } else if (high == Double.MAX_VALUE) {
            range = String.format("Values from %.2f.", low);
        } else {
            range = String.format("Values from %.2f to less than %.2f.", low,high);
        }
        var textViewDescription = (TextView) findViewById(R.id.output_rating_detail_description);
        textViewDescription.setText(rating.getDescription(this));
        var textViewRange = (TextView) findViewById(R.id.output_rating_detail_range);
        textViewRange.setText(range);
    }
}
