/************************************************************************
 *                                                                      *
 *      Class Name: Amenities                                           *
 *      Purpose: This class sets the view of activity_amenities.xml.    *
 * Then we have two methods that has intents, which help in navigating  *
 * to other java classes on click of certain buttons by the user.       *
 ************************************************************************/

package edu.niu.android.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Amenities extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amenities);

        //Enabling the home button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_home_24);}
    }
    public void familyActivity(View v){
        Intent i = new Intent(this, Activities.class);
        this.startActivity(i); // starts to InsertActivity class

    }
    public void ReservePage(View v){
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
