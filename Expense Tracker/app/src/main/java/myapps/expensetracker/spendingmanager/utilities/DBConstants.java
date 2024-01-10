package myapps.expensetracker.spendingmanager.utilities;

public class DBConstants {

    public static final String DB_LOCATION = "/data/data/myapps.expensetracker.spendingmanager/databases/" + DBConstants.DB_NAME;

    static final String DB_NAME = "app_database";
    static final int DB_VERSION = 2;

    static final String TABLE_INFO = "TABLE_INFO";
    static final String INFO_FIELD_TEXT = "INFO_FIELD_TEXT";

    static final String DATABASE_CREATE;
    static {
        DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_INFO
                + " (" + INFO_FIELD_TEXT + " VARCHAR);";
    }
}
