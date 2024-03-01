/**************************************************************************
 *                                                                        *
 *      Class Name: RoomReservation                                       *
 *      Purpose: This class is used to set the view of activity_rooms.xml *
 * file. We use two methods to navigate to other java classes using the   *
 * intent. Here we also pass an EXTRA string to differentiate between the *
 * different types of rooms.                                              *
 * **************************************************************************/

package edu.niu.android.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RoomReservation extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_home_24);}
    }
    public void Reserve(View v){
        String message = "Twin";
        Intent i = new Intent(this, RoomBooking.class);
        i.putExtra(EXTRA_MESSAGE, message);
        this.startActivity(i); // starts to InsertActivity class

    }
    public void ReserveRoom(View v){
        String message = "King";
        Intent i = new Intent(this, RoomBooking.class);
        i.putExtra(EXTRA_MESSAGE, message);
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
