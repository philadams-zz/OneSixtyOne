package net.philadams.onesixtyone;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Simple content provider for OneSixtyOne data
 */
public class OneSixtyOneProvider extends ContentProvider {

  private OneSixtyOneDbHelper dbHelper;

  private static final int ONESIXTYONE_THINGS = 10;
  private static final int ONESIXTYONE_THING_ID = 20;
  private static final String AUTHORITY = "net.philadams.onesixtyone.contentprovider";
  private static final String BASE_PATH = "onesixtyonething";
  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
  public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + BASE_PATH;
  public static final String CONTENT_ITEM_TYPE =
      ContentResolver.CURSOR_ITEM_BASE_TYPE + "/onesixtyonething";

  private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    uriMatcher.addURI(AUTHORITY, BASE_PATH, ONESIXTYONE_THINGS);
    uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", ONESIXTYONE_THING_ID);
  }

  @Override
  public boolean onCreate() {
    dbHelper = new OneSixtyOneDbHelper(getContext());
    return false;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
      String sortOrder) {
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    String[] rawArgs = {
        OneSixtyOneContract.OneSixtyOneThing._ID
    };
    Cursor cursor = db.rawQuery("select * from " + OneSixtyOneContract.OneSixtyOneThing.TABLE_NAME + " order by ? asc", rawArgs);
    return cursor;
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    throw new UnsupportedOperationException();
  }

  @Override
  // TODO:phil implement this for 161Thing status updates!
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getType(Uri uri) {
    return null;
  }
}
