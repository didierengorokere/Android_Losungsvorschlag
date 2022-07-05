/*
 * Copyright © 2017-2022
 * Merz Information and Communication Technology AG.
 * All rights reserved.
 *
 * C015-DE
 * Entwicklung von Mobile Apps mit Android: Programmiersprache Java
 */

package lab.app.config;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import lab.app.BuildConfig;
import lab.app.HomeActivity;
import lab.app.R;
import lab.app.calculator.EntryActivity;
import lab.app.rating.LegendActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_general, menu);
        menu.findItem(R.id.menu_general_settings).setEnabled(false);
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
        setContentView(R.layout.activity_config_settings);
        var buttonConfigureUnits = findViewById(R.id.button_config_settings_configure_units);
        buttonConfigureUnits.setOnClickListener(view ->
                Toast.makeText(this, "Configure Units chosen (not implemented)", Toast.LENGTH_LONG).show()
        );
        var buttonConfigureUsers = findViewById(R.id.button_config_settings_configure_users);
        buttonConfigureUsers.setOnClickListener(view ->
                startActivity(new Intent(this, UsersActivity.class))
        );
    }
}
