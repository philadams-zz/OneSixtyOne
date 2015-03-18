package net.philadams.onesixtyone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite helper for OneSixtyOne - right now, just the Things table.
 */
public class OneSixtyOneDbHelper extends SQLiteOpenHelper {

  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "OneSixtyOne.db";

  private static final String TEXT_TYPE = " TEXT";
  private static final String INT_TYPE = " INT";
  private static final String COMMA_SEP = ",";
  private static final String SQL_CREATE_THINGS =
      "CREATE TABLE " + OneSixtyOneContract.OneSixtyOneThing.TABLE_NAME + " (" +
          OneSixtyOneContract.OneSixtyOneThing._ID + " INTEGER PRIMARY KEY," +
          OneSixtyOneContract.OneSixtyOneThing.COLUMN_NAME_THING_ID + INT_TYPE + COMMA_SEP +
          OneSixtyOneContract.OneSixtyOneThing.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
          OneSixtyOneContract.OneSixtyOneThing.COLUMN_NAME_STATUS + TEXT_TYPE +
          ")";
  private static final String SQL_DELETE_THINGS =
      "DROP TABLE IF EXISTS " + OneSixtyOneContract.OneSixtyOneThing.TABLE_NAME;

  public OneSixtyOneDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_THINGS);
  }

  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // our upgrade policy for now is simply to leave the db alone
    //db.execSQL(SQL_DELETE_THINGS);
    //onCreate(db);
  }

  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    onUpgrade(db, oldVersion, newVersion);
  }
}
