/************************************************************************
 *                                                                      *
 *      Class Name: CheckSubscriptionActivity                           *
 *      Purpose: This class sets the view of activity_check_subscription*
 * .xml. Here we get the email id entered by the user using the         *
 * findViewById. Then we pass this value to the DatabaseManager class to*
 * check if the email id is present in the table. Based on the returned *
 * value, we set the Toast. This class is used to check if the user has *
 * subscribed to the newsletter and if the database is working properly.*
 ************************************************************************/

package edu.niu.android.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CheckSubscriptionActivity extends AppCompatActivity {
    private EditText editTextEmailCheck;
    private Button buttonCheckSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_subscription);

        editTextEmailCheck = findViewById(R.id.editTextEmailCheck);
        buttonCheckSubscription = findViewById(R.id.buttonCheckSubscription);
        //Enabling the home button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_home_24);}
    }
    public void check(View v){
        checkSubscription();
    }

    // Checking the email id with the subscription table's emails.
    private void checkSubscription() {
        String email = editTextEmailCheck.getText().toString();
        DatabaseManager dbManager = new DatabaseManager(this);
        boolean isSubscribed = dbManager.isEmailSubscribed(email);

        if (isSubscribed) {
            Toast.makeText(this, "You are already subscribed.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "You are not subscribed.", Toast.LENGTH_LONG).show();
        }
    }
    // Navigating to the Home page on clicking the home button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Finish the current activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
