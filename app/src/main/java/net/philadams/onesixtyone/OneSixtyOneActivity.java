package net.philadams.onesixtyone;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * OneSixtyOneActivity: list and provide interaction on all 161 things.
 * TODO:philadams refactor like a boss
 * TODO:philadams about view
 * TODO:philadams indicate completion state
 */
public class OneSixtyOneActivity extends Activity
    implements LoaderManager.LoaderCallbacks<Cursor> {

  public static final String TAG = OneSixtyOneActivity.class.getSimpleName();

  private static final int LOADER_ID = 1;
  private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks;
  private OneSixtyOneAdapter adapter;

  private static final String[] PROJECTION = new String[] { "_id", "description", "status" };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SharedPreferences settings = getSharedPreferences(Constants.SHARED_PREFERENCES_FNAME, 0);
    OneSixtyOneDbHelper dbHelper = new OneSixtyOneDbHelper(this);
    SQLiteDatabase db;

    // check to see if this is the app first run; if yes, load 161 data from raw/onesixtyone.json
    if (settings.getBoolean(Constants.KEY_IS_FIRST_RUN, true)) {

      Log.d(TAG, "First time OneSixtyOne has been run! Loading 161 things from data file");

      // grab list of 161 things from raw/onesixtyone.json
      JSONArray oneSixtyOneThings = new JSONArray();
      InputStreamReader inputStreamReader =
          new InputStreamReader(this.getResources().openRawResource(R.raw.onesixtyone));
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      try {
        while ((line = bufferedReader.readLine()) != null) {
          stringBuilder.append(line);
        }
        oneSixtyOneThings = new JSONArray(stringBuilder.toString());
      } catch (IOException | JSONException e) {
        e.printStackTrace();
      }

      // load up json data into sqlite db
      db = dbHelper.getWritableDatabase();
      ContentValues values = new ContentValues();
      String description;
      int thingId;
      long newRowId;
      for (int i = 0; i < oneSixtyOneThings.length(); i++) {
        try {
          JSONObject thing = oneSixtyOneThings.getJSONObject(i);
          description = thing.getString("title");
          thingId = thing.getInt("thing_id");
          values.put(OneSixtyOneContract.OneSixtyOneThing.COLUMN_NAME_THING_ID, thingId);
          values.put(OneSixtyOneContract.OneSixtyOneThing.COLUMN_NAME_DESCRIPTION, description);
          values.put(OneSixtyOneContract.OneSixtyOneThing.COLUMN_NAME_STATUS,
              OneSixtyOneContract.THING_STATES.NOT_DONE.name());
          newRowId = db.insert(OneSixtyOneContract.OneSixtyOneThing.TABLE_NAME, null, values);
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }

      // indicate this is no longer the first run (and therefore we've loaded the app data)
      settings.edit().putBoolean(Constants.KEY_IS_FIRST_RUN, false).apply();
    }

    // set up data loading
    ListView oneSixtyOneThingsListView = (ListView) findViewById(R.id.onesixtyone_list_view);
    String[] dataColumns = { "thing_id", "description" };
    int[] viewIds = { R.id.thing_id, R.id.description };
    adapter = new OneSixtyOneAdapter(this, null, 0);
    oneSixtyOneThingsListView.setAdapter(adapter);
    loaderCallbacks = this;
    LoaderManager loaderManager = getLoaderManager();
    loaderManager.initLoader(LOADER_ID, null, loaderCallbacks);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    String[] projection = {
        OneSixtyOneContract.OneSixtyOneThing._ID,
        OneSixtyOneContract.OneSixtyOneThing.COLUMN_NAME_THING_ID,
        OneSixtyOneContract.OneSixtyOneThing.COLUMN_NAME_DESCRIPTION,
        OneSixtyOneContract.OneSixtyOneThing.COLUMN_NAME_STATUS
    };
    return new CursorLoader(this, OneSixtyOneProvider.CONTENT_URI, projection, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    adapter.swapCursor(data);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    // data is not available any more, delete reference
    adapter.swapCursor(null);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
