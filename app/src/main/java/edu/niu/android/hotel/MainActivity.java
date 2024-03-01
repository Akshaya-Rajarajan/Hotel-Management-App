/************************************************************************
 *                                                                      *
 * CSCI 322/522             Final Project              current semester *
 *                                                                      *
 *          App Name: Hotel                                             *
 *                                                                      *
 *          Class Name:MainActivity.java                                *
 *                                                                      *
 *         Developer(s): Akshaya Rajarajan and Mohan Krishna Pasupuleti *
 *                                                                      *
 *          Due Date:     12/01/2023                                    *
 *                                                                      *
 *      Purpose: MainActivity.java is a Java file that serves as the    *
 *  main entry point for an Android app. This file is used to set the   *
 *  layout and inflate the action bar with menus. The intent is also    *
 *  set for all the menu items in this java class. Then few methods have*
 *  been created for buttons and icons that have to navigate or perform *
 *  some operation on click. Onclick listener has been set to the button*
 *  in activity_main.xml. When the button is clicked, the next image    *
 *  will be displayed and we have coded it in such a way that once the  *
 *  last image is displayed and the user clicks on the next button, it  *
 *  will again show from the 1st image. Then we have created intent for *
 *  navigation to a hyperlink in this class.                            *
 ************************************************************************/

package edu.niu.android.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.view.Menu;
import android.net.Uri;
public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private int currentImageIndex = 1;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        nextButton = findViewById(R.id.button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increment the index and change the image
                currentImageIndex++;
                if (currentImageIndex > 6) { // Change 3 to the total number of images
                    currentImageIndex = 1;
                }
                int resourceId = getResources().getIdentifier(
                        "kaldan_" + currentImageIndex, "drawable", getPackageName());
                imageView.setImageResource(resourceId);
            }
        });
    }
    // Inflate the menu; this adds items to the action bar if it is present.
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.room) {
            Intent insertIntent = new Intent(this, RoomReservation.class);
            this.startActivity(insertIntent); // starts to InsertActivity class
            return true;
        }
        if(id == R.id.restaurant){
            Intent insertIntent = new Intent(this, Restaurant.class);
            this.startActivity(insertIntent); // starts to InsertActivity class
            return true;
        }
        if(id == R.id.amenities){
            Intent insertIntent = new Intent(this, Amenities.class);
            this.startActivity(insertIntent); // starts to InsertActivity class
            return true;
        }
        if(id == R.id.check){
            Intent insertIntent = new Intent(this, CheckSubscriptionActivity.class);
            this.startActivity(insertIntent); // starts to InsertActivity class
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    // Navigation to external links.
    public void Link(View v){
        String url = "https://www.google.com/maps/dir//Mackinac+Island,+MI+49757/@45.8515824,-84.6614097,13z/data=!4m8!4m7!1m0!1m5!1m1!1s0x4d35f189aad9ae85:0xdfba14915cd6c1f7!2m2!1d-84.6189339!2d45.8491796?entry=ttu";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    public void fbLink(View v){
        String url = "https://www.facebook.com/kaldansamudhra";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    public void instaLink(View v){
        String url = "https://www.instagram.com/kaldanhotels/?igshid=YmMyMTA2M2Y%3D";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    public void tripLink(View v){
        String url = "https://www.tripadvisor.in/Hotel_Review-g1162480-d24019190-Reviews-Kaldan_Samudhra_Palace-Mahabalipuram_Kanchipuram_District_Tamil_Nadu.html";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


}