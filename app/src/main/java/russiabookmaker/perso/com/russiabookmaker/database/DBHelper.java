package russiabookmaker.perso.com.russiabookmaker.database;

/**
 * Created by versusmind on 27/09/2016.
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import russiabookmaker.perso.com.russiabookmaker.model.Match;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "russiabookmaker.db";
    public static final String SCHEDULE_TABLE_NAME = "schedule";
    public static final String SCHEDULE_COLUMN_ID = "id";
    public static final String SCHEDULE_COLUMN_TEAM1 = "team1";
    public static final String SCHEDULE_COLUMN_TEAM2 = "team2";
    public static final String SCHEDULE_COLUMN_TEAM1ID = "team1id";
    public static final String SCHEDULE_COLUMN_TEAM2ID = "team2id";
    public static final String SCHEDULE_COLUMN_FLAG1 = "flag1";
    public static final String SCHEDULE_COLUMN_FLAG2 = "flag2";
    public static final String SCHEDULE_COLUMN_RESULTTEAM1 = "resultteam1";
    public static final String SCHEDULE_COLUMN_RESULTTEAM2 = "resultteam2";
    public static final String SCHEDULE_COLUMN_MATCHTIME = "matchtime";
    public static final String SCHEDULE_COLUMN_OVERTIME = "overtime";
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + SCHEDULE_TABLE_NAME +
                        "(" + SCHEDULE_COLUMN_ID + " integer primary key, " + SCHEDULE_COLUMN_TEAM1 +
                        " text, " + SCHEDULE_COLUMN_TEAM2 + " text, " + SCHEDULE_COLUMN_FLAG1 +
                        " text, " + SCHEDULE_COLUMN_FLAG2 + " text, " + SCHEDULE_COLUMN_MATCHTIME +
                        " text, " + SCHEDULE_COLUMN_RESULTTEAM1 + " integer, " + SCHEDULE_COLUMN_RESULTTEAM2 +
                        " integer, " + SCHEDULE_COLUMN_OVERTIME + " boolean, " + SCHEDULE_COLUMN_TEAM1ID + " integer, " +
                        SCHEDULE_COLUMN_TEAM2ID + " integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertMatch  (String team1, String team2, String flag1, String flag2 , String matchtime, int resultteam1, int resultteam2,
                                 boolean overtime, int match_id, int team1Id, int team2Id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHEDULE_COLUMN_TEAM1, team1);
        contentValues.put(SCHEDULE_COLUMN_TEAM2, team2);
        contentValues.put(SCHEDULE_COLUMN_TEAM1ID, team1Id);
        contentValues.put(SCHEDULE_COLUMN_TEAM2ID, team2Id);
        contentValues.put(SCHEDULE_COLUMN_FLAG1, flag1);
        contentValues.put(SCHEDULE_COLUMN_FLAG2, flag2);
        contentValues.put(SCHEDULE_COLUMN_MATCHTIME, matchtime);
        contentValues.put(SCHEDULE_COLUMN_RESULTTEAM1, resultteam1);
        contentValues.put(SCHEDULE_COLUMN_RESULTTEAM2, resultteam2);
        contentValues.put(SCHEDULE_COLUMN_OVERTIME, overtime);
        //contentValues.put(SCHEDULE_COLUMN_ID, match_id);
        db.insert(SCHEDULE_TABLE_NAME, null, contentValues);
        return true;
    }

    public Match getMatch(int id){
        int newid = id + 1;
        System.out.println("get id " + id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + SCHEDULE_TABLE_NAME +" where " + SCHEDULE_COLUMN_ID + " = " + newid, null );
        res.moveToFirst();
        if (res.getCount() > 0) {
            Match match = new Match();
            //String dateYear = res.getString(res.getColumnIndex(SCHEDULE_COLUMN_MATCHTIME)).concat(" 2018");
            String dateYear = res.getString(res.getColumnIndex(SCHEDULE_COLUMN_MATCHTIME));
            System.out.println("dateYear : " + dateYear);
            SimpleDateFormat parseFormat = new SimpleDateFormat("EEEE dd MMMM HH:mm yyyy", Locale.FRENCH);
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                //Date date = parseFormat.parse(dateYear);
                Date date = dateFormat.parse(dateYear);
                System.out.println("date : " + date);
                match.setMatchTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            match.setTeam1(res.getString(res.getColumnIndex(SCHEDULE_COLUMN_TEAM1)));
            match.setTeam2(res.getString(res.getColumnIndex(SCHEDULE_COLUMN_TEAM2)));
            match.setFlag1(res.getString(res.getColumnIndex(SCHEDULE_COLUMN_FLAG1)));
            match.setFlag1(res.getString(res.getColumnIndex(SCHEDULE_COLUMN_FLAG2)));
            match.setId(res.getInt(res.getColumnIndex(SCHEDULE_COLUMN_ID)));
            match.setTeam2Id(res.getInt(res.getColumnIndex(SCHEDULE_COLUMN_TEAM2ID)));
            match.setTeam1Id(res.getInt(res.getColumnIndex(SCHEDULE_COLUMN_TEAM1ID)));
            //match.setMatchTime(res.getString(res.getColumnIndex(SCHEDULE_COLUMN_MATCHTIME)));
            System.out.println("get team 1 " + match.getTeam1());
            if (!res.isClosed()) {
                res.close();
            }
            return match;
        }
        return new Match();
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SCHEDULE_TABLE_NAME);
        return numRows;
    }

    public boolean updateMatch (Integer id, String team1, String team2, String matchtime, String flag1, String flag2, int resultteam1,
                                int resultteam2, boolean overtime)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHEDULE_COLUMN_TEAM1, team1);
        contentValues.put(SCHEDULE_COLUMN_TEAM2, team2);
        contentValues.put(SCHEDULE_COLUMN_FLAG1, flag1);
        contentValues.put(SCHEDULE_COLUMN_FLAG2, flag2);
        contentValues.put(SCHEDULE_COLUMN_OVERTIME, overtime);
        contentValues.put(SCHEDULE_COLUMN_RESULTTEAM1, resultteam1);
        contentValues.put(SCHEDULE_COLUMN_RESULTTEAM2, resultteam2);
        db.update(SCHEDULE_TABLE_NAME, contentValues, SCHEDULE_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteMatch (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SCHEDULE_TABLE_NAME,
                SCHEDULE_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<Match> getAllMatchs()
    {
        ArrayList<Match> array_list = new ArrayList<Match>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + SCHEDULE_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Match match = new Match();
            match.setFlag1(res.getString(res.getColumnIndex(SCHEDULE_COLUMN_FLAG1)));
            match.setFlag2(res.getString(res.getColumnIndex(SCHEDULE_COLUMN_FLAG2)));
            match.setResultTeam1(res.getInt(res.getColumnIndex(SCHEDULE_COLUMN_RESULTTEAM1)));
            match.setResultTeam2(res.getInt(res.getColumnIndex(SCHEDULE_COLUMN_RESULTTEAM2)));
            array_list.add(match);
            res.moveToNext();
        }
        return array_list;
    }
}
