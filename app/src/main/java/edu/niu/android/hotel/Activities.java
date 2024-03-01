/************************************************************************
 *                                                                      *
 *      Class Name: Activities                                          *
 *      Purpose: This class sets the view of activity_activities.xml.   *
 * then we build an alert dialog box that pops up when the user clicks  *
 * on the subscribe button. We create a submit and cancel button in the *
 * the dialogbox. On clicking the submit button, the entered values are *
 * got using findViewById and converted into strings and are used to    *
 * update the subscription table.                                       *
 ************************************************************************/

package edu.niu.android.hotel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Activities extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        // Enabling the home button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_home_24);}

    }
    public void Subscription(View v){
        showUserInfoDialog();
    }

    // Building Alert Dialog Box and inflating the dialog box with the activity_dialogbox_info.xml content.
    private void showUserInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_dialogbox_info, null);
        builder.setView(dialogView);

        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextEmail = dialogView.findViewById(R.id.editTextEmail);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                // Store in database
                storeUserInfoInDatabase(name, email);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    // storing the user details into the database.
    private void storeUserInfoInDatabase(String name, String email) {
        DatabaseManager dbManager = new DatabaseManager(this);
        dbManager.addUserInfo(name, email);
        Toast.makeText(this, "Yaaay!!! You have Successfully Subscribed to our News Letter", Toast.LENGTH_LONG).show();
    }
    public void bookingPage(View v){
        Intent i = new Intent(this, RoomReservation.class);
        this.startActivity(i); // starts to InsertActivity class
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
