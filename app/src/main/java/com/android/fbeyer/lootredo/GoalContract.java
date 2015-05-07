package com.android.fbeyer.lootredo;

/**
 * Created by fbeyer on 5/7/2015.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class GoalContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public GoalContract() {}

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GoalEntry.TABLE_NAME + " (" +
                    GoalEntry._ID + " INTEGER PRIMARY KEY," +
                    GoalEntry.COLUMN_NAME_GOAL_ID + TEXT_TYPE + COMMA_SEP +
                    GoalEntry.COLUMN_NAME_GOAL_NAME + TEXT_TYPE + COMMA_SEP +
                    GoalEntry.COLUMN_NAME_GOAL_COST + TEXT_TYPE + COMMA_SEP +
                    GoalEntry.COLUMN_NAME_GOAL_DATE + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GoalEntry.TABLE_NAME;

    /* Inner class that defines the table contents */
    public static abstract class GoalEntry implements BaseColumns {
        public static final String TABLE_NAME = "goal";
        public static final String COLUMN_NAME_GOAL_ID = "goalid";
        public static final String COLUMN_NAME_GOAL_NAME = "name";
        public static final String COLUMN_NAME_GOAL_COST = "cost";
        public static final String COLUMN_NAME_GOAL_DATE = "date";
    }

    public static class GoalContractDbHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "LootGoals.db";

        public GoalContractDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
