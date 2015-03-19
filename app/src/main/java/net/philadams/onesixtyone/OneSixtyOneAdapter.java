package net.philadams.onesixtyone;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Adapter for the OneSixtyOne list view items
 */
public class OneSixtyOneAdapter extends CursorAdapter {

  private LayoutInflater layoutInflater;

  public OneSixtyOneAdapter(Context context, Cursor cursor, int flags) {
    super(context, cursor, flags);
    layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  public void bindView(View view, Context context, Cursor cursor) {
    TextView tvThingId = (TextView) view.findViewById(R.id.thing_id);
    TextView tvDescription = (TextView) view.findViewById(R.id.description);
    tvThingId.setText(cursor.getString(
        cursor.getColumnIndex(OneSixtyOneContract.OneSixtyOneThing.COLUMN_NAME_THING_ID)));
    tvDescription.setText(cursor.getString(
        cursor.getColumnIndex(OneSixtyOneContract.OneSixtyOneThing.COLUMN_NAME_DESCRIPTION)));
  }

  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return layoutInflater.inflate(R.layout.one_sixty_one_thing, parent, false);
  }
}
