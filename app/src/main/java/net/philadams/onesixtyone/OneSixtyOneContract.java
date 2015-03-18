package net.philadams.onesixtyone;

import android.provider.BaseColumns;

/**
 * Contract (one inner class per table) for OneSixtyOne data store.
 */
public final class OneSixtyOneContract {

  public static enum THING_STATES {NOT_DONE, DONE, WILL_NOT_DO}

  // prevent accidentally instantiating the contract class
  public OneSixtyOneContract() {
  }

  public static abstract class OneSixtyOneThing implements BaseColumns {
    public static final String TABLE_NAME = "one_sixty_one_thing";
    public static final String COLUMN_NAME_THING_ID = "thing_id";
    public static final String COLUMN_NAME_DESCRIPTION = "description";
    public static final String COLUMN_NAME_STATUS = "status";
  }
}
