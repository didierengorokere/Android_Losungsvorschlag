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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import lab.app.BuildConfig;
import lab.app.HomeActivity;
import lab.app.R;
import lab.app.calculator.EntryActivity;
import lab.app.config.SettingsActivity;
import lab.app.util.BMI;

public class LegendActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_general, menu);
        menu.findItem(R.id.menu_general_rating).setEnabled(false);
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
        setContentView(R.layout.activity_rating_legend);
        var listViewBMIDescriptions = (ListView) findViewById(R.id.list_rating_legend_descriptions);
        var adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                BMI.Rating.getDescriptions(this));
        listViewBMIDescriptions.setAdapter(adapter);
        listViewBMIDescriptions.setOnItemClickListener((parent, view, position, id) -> {
            var intent = new Intent(this, DetailActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        });
    }
}
