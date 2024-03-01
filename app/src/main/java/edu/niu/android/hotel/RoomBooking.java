/****************************************************************************
 *                                                                          *
 *      Class Name: RoomBooking                                             *
 *      Purpose: This class is used to set the view of activity_booking     *
 * .xml file.We get the extra string passed by the intent of RoomReservation*
 * class and based on the roomtype we set the value of the price variable.  *
 * Then we use a method to validate the user entered details and then insert*
 * the records to the table. We validate the phone number, by checking if   *
 * there are 10 numbers. Then we validate email id, by checking for alpha-  *
 * numeric characters in the beginning followed by @, which is followed by . *
 * and 2-7 letters at the end. Then we check if the dates are in a specific *
 * format only and we also check if the date is current date or a future    *
 * date but not the past. We do this with the help of Calendar class, we get*
 * the current date and check if the date entered by the user is past date. *
 * Then we check the availability of the room. If the user entered values   *
 * pass all these conditions then we insert the record into the database.   *
 **************************************************************************/

package edu.niu.android.hotel;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.SimpleDateFormat;
import java.text.ParseException;
public class RoomBooking extends AppCompatActivity {
    private DatabaseManager dbManager;
    String roomType;
    double price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Intent intent = getIntent();
        roomType = intent.getStringExtra(RoomReservation.EXTRA_MESSAGE);
        if("Twin".equals(roomType)){
            price = 600;
        }
        else{
            price = 800;
        }
        // Initialize the DatabaseManager
        dbManager = new DatabaseManager(this);
        // Enabling the home button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_home_24);}
    }
    public void insertrecord(View v)
    {
        // Retrieve the details of the customer
        EditText nameEditText = (EditText) findViewById(R.id.name_in);
        EditText phoneEditText = (EditText) findViewById(R.id.num_in);
        EditText emailEditText = (EditText) findViewById(R.id.email_in);
        EditText fdEditText = (EditText) findViewById(R.id.FD_in);
        EditText tdEditText = (EditText) findViewById(R.id.TD_in);
        // Convert the values into string type.
        String name = nameEditText.getText().toString();
        String number = phoneEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String fd = fdEditText.getText().toString();
        String td = tdEditText.getText().toString();

        /*if (dbManager == null) {
            dbManager = new DatabaseManager(this, "room1DB", null, 1);
        }*/
        if (!isValidPhone(number)) {
            Toast.makeText(this, "Invalid phone number. It should be 10 digits long.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Invalid email. It should contain an '@' symbol.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isValidDate(fd)) {
            Toast.makeText(this, "Invalid date. Format should be MM/dd/yyyy. Date must be today or a future date.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isValidDate(td)) {
            Toast.makeText(this, "Invalid date. Format should be MM/dd/yyyy. Date must be today or a future date.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!checkRoomAvailability(fd,td,roomType)) {
            Toast.makeText(this, "Sorry, there are no available rooms for the specified dates.", Toast.LENGTH_SHORT).show();
            return;
        }



        BookingRecord br = new BookingRecord(name, number, email, fd, td, price, roomType);
        if (dbManager != null) {
           // Log.w("xxxxxxxxx",br.toString());
            dbManager.insert(br);
            Toast.makeText(this, "Booking Successful!!! You will get an email with the payment link. Kindly pay for the room at the max 1 day before your stay, otherwise the booking will be cancelled.", Toast.LENGTH_LONG).show();
            nameEditText.setText("");
            phoneEditText.setText("");
            emailEditText.setText("");
            fdEditText.setText("");
            tdEditText.setText("");
        } else {
            Toast.makeText(this, "DatabaseManager not initialized", Toast.LENGTH_LONG).show();
        }
    }
    private boolean isValidPhone(String number) {
        return number != null && number.matches("\\d{10}");
    }

    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
    private boolean isValidDate(String date) {
        String dateRegex = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/(19|20)\\d{2}$";
        Pattern pattern = Pattern.compile(dateRegex);
        Matcher matcher = pattern.matcher(date);

        if (!matcher.matches()) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false); // this will ensure strict parsing to validate the date
        try {
            Date enteredDate = dateFormat.parse(date);
            Calendar currentDate = Calendar.getInstance();
            currentDate.set(Calendar.HOUR_OF_DAY, 0);
            currentDate.set(Calendar.MINUTE, 0);
            currentDate.set(Calendar.SECOND, 0);
            currentDate.set(Calendar.MILLISECOND, 0);
            if (enteredDate.before(currentDate.getTime())) {
                return false; // The entered date is in the past
            }
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    //Checking the availability of the rooms.
    public boolean checkRoomAvailability(String fd, String td, String roomType) {
        String fromDate = fd;
        String toDate = td;
        String type = roomType;

        boolean isAvailable = dbManager.isRoomAvailable(fromDate, toDate, type);

        if (!isAvailable) {
            // Show message to the user
            Toast.makeText(this, "There are no available rooms for the specified dates.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
    public void goBack(View v)
    {
        this.finish();
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
