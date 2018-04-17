package ke.co.taara.taara.Database;

import android.provider.BaseColumns;

public class TaaraDbContract {

    public TaaraDbContract(){}

    public static abstract  class TaaraUsers implements BaseColumns{
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_FIRST_NAME = "FirstName";
        public static final String COLUMN_NAME_SECOND_NAME = "SecondName";
        public static final String COLUMN_NAME_PHONE = "Phone";
        public static final String COLUMN_NAME_EMAIL = "Email";
        public static final String COLUMN_NAME_CREDITS = "Credits";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_USERS =
            "CREATE TABLE " + TaaraUsers.TABLE_NAME + " (" +
                    TaaraUsers._ID + " INTEGER PRIMARY KEY," +
                    TaaraUsers.COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                    TaaraUsers.COLUMN_NAME_SECOND_NAME + TEXT_TYPE + COMMA_SEP +
                    TaaraUsers.COLUMN_NAME_PHONE + TEXT_TYPE + COMMA_SEP +
                    TaaraUsers.COLUMN_NAME_CREDITS + TEXT_TYPE + COMMA_SEP +
                    TaaraUsers.COLUMN_NAME_EMAIL + TEXT_TYPE +

            " )";

    private static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS " + TaaraUsers.TABLE_NAME;




}
