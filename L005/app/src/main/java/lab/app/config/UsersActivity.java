/*
 * Copyright © 2017-2022
 * Merz Information and Communication Technology AG.
 * All rights reserved.
 *
 * C015-DE
 * Entwicklung von Mobile Apps mit Android: Programmiersprache Java
 */

package lab.app.config;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Comparator;

import lab.app.BuildConfig;
import lab.app.HomeActivity;
import lab.app.R;
import lab.app.calculator.EntryActivity;
import lab.app.rating.LegendActivity;
import lab.app.util.DatabaseConnection;

public class UsersActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_config_users);
        var settings = getSharedPreferences("settings", MODE_PRIVATE);
        var currentUser = settings.getString("user", "<anonymous>");
        var spinnerUsers = (Spinner) findViewById(R.id.spinner_config_users_list);
        DatabaseConnection databaseConnection = new DatabaseConnection(this);
        databaseConnection.open();
        var userEntries = databaseConnection.selectUserEntries();
        var adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                userEntries);
        adapter.sort(Comparator.naturalOrder());
        spinnerUsers.setAdapter(adapter);
        spinnerUsers.setSelection(adapter.getPosition(currentUser));
        spinnerUsers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                var user = userEntries.get(position);
                var editor = settings.edit();
                editor.putString("user", user);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (adapter.isEmpty()) {
            spinnerUsers.setVisibility(View.INVISIBLE);
        }
        var buttonNewUser = findViewById(R.id.button_config_settings_configure_new_user);
        buttonNewUser.setOnClickListener(view -> {
            var promptView = getLayoutInflater().inflate(R.layout.prompt_config_user, null);
            new AlertDialog.Builder(this).setView(promptView)
                    .setPositiveButton("Add User", (dialog, id) -> {
                        var textNew = (EditText) promptView.findViewById(R.id.text_config_user_new);
                        var newUser = textNew.getText().toString();
                        settings.edit().putString("user", newUser).apply();
                        databaseConnection.insertUserEntry(newUser);
                        adapter.add(newUser);
                        adapter.sort(Comparator.naturalOrder());
                        spinnerUsers.setSelection(adapter.getPosition(newUser));
                        spinnerUsers.setVisibility(View.VISIBLE);
                    })
                    .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel())
                    .create().show();
        });
    }
}
