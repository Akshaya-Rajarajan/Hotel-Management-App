/************************************************************************
 *                                                                      *
 *      Class Name: DatabaseManager                                     *
 *      Purpose: This class is mainly used to create the database and   *
 * the required tables and to access these tables. Here we have created *
 * two tables. One table for room booking and one table to store the    *
 * users who have subscribed to the newsletter.Then we have two methods *
 * that insert the values into the respective tables. We have a method *
 * isRoomAvailable() that checks if the user entered dates are already *
 * available in the database for a specific type of room 5 times. If   *
 * yes, then we send a pop up message to the user saying the rooms are *
 * not available for those dates and we ask the users to select some   *
 * other date. We have a isEmailSubscribed() method that checks if the *
 * user entered email is already present in the table or not.          *
 ************************************************************************/


package edu.niu.android.hotel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "roomBooking1DB";
    private static final int DATABASE_VERSION = 7;
    private static final String TABLE_RECORD = "bookingtable";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String NUMBER = "Number";

    private static final String FD = "fd";
    private static final String TD = "td";
    private static final String ROOMTYPE = "roomType";

    private static final String PRICE = "price";
    private static final String TABLE_SUBSCRIPTION = "subscription";
    private static final String SUBSCRIPTION_ID = "id";
    private static final String SUBSCRIPTION_USERNAME = "username";
    private static final String SUBSCRIPTION_EMAIL = "email";

    public DatabaseManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // String together sql create statement for the table
        String sqlCreate = "create table " + TABLE_RECORD + "(" + ID;
        sqlCreate += " integer primary key autoincrement, " + NAME;
        sqlCreate += " text, "+NUMBER;
        sqlCreate +=" text, "+ EMAIL;
        sqlCreate += " text, "+ FD;
        sqlCreate += " text, " + TD;
        sqlCreate += " text, " + PRICE;
        sqlCreate += " real, " + ROOMTYPE + " text)";

        db.execSQL(sqlCreate);
        Log.w("xyz",sqlCreate);

        //code to create subscription table
        String sqlCreateSubscriptionTable = "create table " + TABLE_SUBSCRIPTION + "(" + SUBSCRIPTION_ID;
        sqlCreateSubscriptionTable += " integer primary key autoincrement, " + SUBSCRIPTION_USERNAME;
        sqlCreateSubscriptionTable += " text, " + SUBSCRIPTION_EMAIL + " text)";
        db.execSQL(sqlCreateSubscriptionTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table if it exists
        db.execSQL("drop table if exists " + TABLE_RECORD);
        db.execSQL("drop table if exists " + TABLE_SUBSCRIPTION);
        // Re-create tables
        onCreate(db);

    }

    //Inserting values into the bookingtable
    public void insert(BookingRecord br)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, br.getName());
        values.put(NUMBER, br.getNumber());
        values.put(EMAIL, br.getEmail());
        values.put(FD, br.getFd());
        values.put(TD, br.getTd());
        values.put(PRICE, br.getPrice());
        values.put(ROOMTYPE, br.getRoomType());
       // Log.w("xxxxxx",values.toString());
        db.insert(TABLE_RECORD, null, values);
        db.close();
    }

    //Inserting values into the subscription table
    public void addUserInfo(String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUBSCRIPTION_USERNAME, name);
        values.put(SUBSCRIPTION_EMAIL, email);

        long result = db.insert(TABLE_SUBSCRIPTION, null, values);

        if(result == -1) {
            Log.e("DatabaseManager", "Failed to insert data");
        } else {
            Log.i("DatabaseManager", "Data inserted successfully");
        }

        db.close();
    }

    // Checking the room availability
    public boolean isRoomAvailable(String fromDate, String toDate, String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM bookingtable WHERE ((fd <= ? AND td >= ?) OR (fd <= ? AND td >= ?)) AND roomType = ?";
        Cursor cursor = db.rawQuery(query, new String[]{toDate, fromDate, fromDate, toDate, type});

        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            Log.w("xxxxxxxxx",Integer.toString(count));
            return count <= 5;
        }

        return false;
    }

    // Checking the if email id is present in the table.
    public boolean isEmailSubscribed(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_SUBSCRIPTION + " WHERE " + SUBSCRIPTION_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            db.close();
            return count > 0;
        }

        db.close();
        return false;
    }

}
