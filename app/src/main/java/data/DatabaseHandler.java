package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import model.MyWish;

public class DatabaseHandler extends SQLiteOpenHelper{

    private final ArrayList<MyWish> theWishList = new ArrayList<>();

    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_WISHES_TABLE = "CREATE TABLE " + Constants.TABLE_NAME +
                "(" + Constants.KEY_ID + " INTEGER PRIMARY KEY, " +
                Constants.TITLE_NAME + " TEXT, " + Constants.CONTENT_NAME + " TEXT, " +
                Constants.DATE_NAME + " LONG);";

        db.execSQL(CREATE_WISHES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS" + Constants.TABLE_NAME);

        //create a new one
        onCreate(db);

    }

    public void deleteWish(int id){

        SQLiteDatabase deletefromDB = this.getWritableDatabase();

        deletefromDB.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ? ",
                new String[]{String.valueOf(id)});

        deletefromDB.close();


    }

    //add content to table
    public void addWishes(MyWish wish){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues myValues = new ContentValues();

        myValues.put(Constants.TITLE_NAME, wish.getTitle());

        myValues.put(Constants.CONTENT_NAME, wish.getContent());

        myValues.put(Constants.DATE_NAME, System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME, null, myValues);

        Log.v("Wish added!", "fuck yeah!");

        db.close();

    }

    //Get all of the Wishes
    public ArrayList<MyWish> getWishes(){

        String selectQuery = "SELECT * FROM" + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor theCursor = db.query(Constants.TABLE_NAME, new String[]{
                Constants.KEY_ID, Constants.TITLE_NAME, Constants.CONTENT_NAME, Constants.DATE_NAME}
                , null, null, null, null, Constants.DATE_NAME + "   DESC");

        if (theCursor.moveToFirst()){

            do{

               MyWish wish = new MyWish();

                wish.setTitle(theCursor.getString(theCursor.getColumnIndex(Constants.TITLE_NAME)));     //--//

                wish.setContent(theCursor.getString(theCursor.getColumnIndex(Constants.CONTENT_NAME))); //--//

                wish.setItemId(theCursor.getInt(theCursor.getColumnIndex(Constants.KEY_ID)));           //--//

                java.text.DateFormat myDateFormat = java.text.DateFormat.getDateInstance();

                String dateData = myDateFormat.format(new Date(theCursor.getLong(theCursor.getColumnIndex
                        (Constants.DATE_NAME))).getTime());

                wish.setRecordDate(dateData);                                                           //--//

                theWishList.add(wish);

            } while (theCursor.moveToNext());

        }

        theCursor.close();
        db.close();

        return theWishList;

    }

}
