package russiabookmaker.perso.com.russiabookmaker.database;

/**
 * Created by versusmind on 27/09/2016.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import russiabookmaker.perso.com.russiabookmaker.bet.Match;
import russiabookmaker.perso.com.russiabookmaker.teams.Team;

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
    public static final String SCHEDULE_COLUMN_RESULTBET = "resultbet";
    public static final String TEAM_COLUMN_NAME = "name";
    public static final String TEAM_COLUMN_FLAG = "flag";
    public static final String TEAM_COLUMN_ID = "id";
    public static final String TEAM_TABLE_NAME = "team";

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
                        SCHEDULE_COLUMN_TEAM2ID + " integer, " + SCHEDULE_COLUMN_RESULTBET + " text)"
        );

        db.execSQL(
                "create table " + TEAM_TABLE_NAME +
                        "(" + TEAM_COLUMN_ID + " integer primary key, " + TEAM_COLUMN_NAME +
                        " text, " + TEAM_COLUMN_FLAG + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertMatch  (String team1, String team2, String flag1, String flag2 , String matchtime, int resultteam1, int resultteam2,
                                 boolean overtime, int match_id, int team1Id, int team2Id, String resultbet)
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
        contentValues.put(SCHEDULE_COLUMN_RESULTBET, resultbet);
        contentValues.put(SCHEDULE_COLUMN_ID, match_id);
        db.insert(SCHEDULE_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public boolean insertTeam(String name, String flag, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEAM_COLUMN_FLAG, flag);
        contentValues.put(TEAM_COLUMN_NAME, name);
        contentValues.put(TEAM_COLUMN_ID, id);
        db.insert(TEAM_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public ArrayList<Match> getAllMatchs(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Match> matchs = new ArrayList<Match>();
        Cursor cursor =  db.rawQuery( "select " + SCHEDULE_COLUMN_ID + " from " + SCHEDULE_TABLE_NAME, null );
        if (cursor .moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                Integer id = cursor.getInt(cursor.getColumnIndex(SCHEDULE_COLUMN_ID));

                matchs.add(getMatch(id - 1));
                cursor.moveToNext();
            }
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return matchs;

    }

    public boolean hasTeam(String name)
    {
        boolean result = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + TEAM_COLUMN_ID +
                " from " + TEAM_TABLE_NAME + " where " + TEAM_COLUMN_NAME + " like '" + name + "'", null);
        if (cursor.moveToFirst())
            result = true;
        if (!cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return result;
    }

    public Team getTeam(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TEAM_TABLE_NAME + " where " + TEAM_COLUMN_ID + " = " + id, null);
        if (cursor.moveToFirst())
        {
            Team team = new Team();
            team.setId(cursor.getInt(cursor.getColumnIndex(TEAM_COLUMN_ID)));
            team.setFlag(cursor.getString(cursor.getColumnIndex(TEAM_COLUMN_FLAG)));
            team.setName(cursor.getString(cursor.getColumnIndex(TEAM_COLUMN_NAME)));
            cursor.close();
            db.close();
            return team;
        }
        else
        {
            cursor.close();
            db.close();
            return null;
        }
    }

    public ArrayList<Team> getAllTeams()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Team> teams = new ArrayList<Team>();
        Cursor cursor = db.rawQuery("select " + TEAM_COLUMN_ID + ", " + TEAM_COLUMN_FLAG + ",  " + TEAM_COLUMN_NAME +
                " from " + TEAM_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false)
            {
                Team team = new Team();
                team.setFlag(cursor.getString(cursor.getColumnIndex(TEAM_COLUMN_FLAG)));
                team.setName(cursor.getString(cursor.getColumnIndex(TEAM_COLUMN_NAME)));
                teams.add(team);
                cursor.moveToNext();
            }
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        if (teams.size() > 0)
            return teams;
        else
            return null;
    }

    public Match getMatch(int id){
        int newid = id + 1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + SCHEDULE_TABLE_NAME +" where " + SCHEDULE_COLUMN_ID + " = " + newid, null );
        res.moveToFirst();
        if (res.getCount() > 0) {
            Match match = new Match();
            //String dateYear = res.getString(res.getColumnIndex(SCHEDULE_COLUMN_MATCHTIME)).concat(" 2018");
            String dateYear = res.getString(res.getColumnIndex(SCHEDULE_COLUMN_MATCHTIME));
            SimpleDateFormat parseFormat = new SimpleDateFormat("EEEE dd MMMM HH:mm yyyy", Locale.FRENCH);
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                //Date date = parseFormat.parse(dateYear);
                Date date = dateFormat.parse(dateYear);
                match.setMatchTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            match.setTeam1(res.getString(res.getColumnIndex(SCHEDULE_COLUMN_TEAM1)));
            match.setTeam2(res.getString(res.getColumnIndex(SCHEDULE_COLUMN_TEAM2)));
            match.setFlag1(res.getString(res.getColumnIndex(SCHEDULE_COLUMN_FLAG1)));
            match.setFlag2(res.getString(res.getColumnIndex(SCHEDULE_COLUMN_FLAG2)));
            match.setId(res.getInt(res.getColumnIndex(SCHEDULE_COLUMN_ID)));
            match.setTeam2Id(res.getInt(res.getColumnIndex(SCHEDULE_COLUMN_TEAM2ID)));
            match.setTeam1Id(res.getInt(res.getColumnIndex(SCHEDULE_COLUMN_TEAM1ID)));
            match.setResultTeam1(res.getInt(res.getColumnIndex(SCHEDULE_COLUMN_RESULTTEAM1)));
            match.setResultTeam2(res.getInt(res.getColumnIndex(SCHEDULE_COLUMN_RESULTTEAM2)));
            //match.setProlongations(res.get);
            //match.setDateServeur(response.body().get(i).getDateServeur());
            //match.setGroup(response.body().get(i).getGroup());
            match.setResultBet(res.getString(res.getColumnIndex(SCHEDULE_COLUMN_RESULTBET)));
            //match.setMatchTime(res.getString(res.getColumnIndex(SCHEDULE_COLUMN_MATCHTIME)));
            if (!res.isClosed()) {
                res.close();
            }
            db.close();
            return match;
        }
        else {
            db.close();
            return null;
        }
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
        db.close();
        return true;
    }

    public boolean updateTeam(Integer id, String name, String flag)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEAM_COLUMN_FLAG, flag);
        contentValues.put(TEAM_COLUMN_NAME, name);
        contentValues.put(TEAM_COLUMN_ID, id);
        db.update(TEAM_TABLE_NAME, contentValues, TEAM_COLUMN_ID + " = ? ", new String[] { Integer.toString(id)});
        db.close();
        return true;
    }

    public int updateResult (Integer id, String resultBet)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHEDULE_COLUMN_RESULTBET, resultBet);
        int result = db.update(SCHEDULE_TABLE_NAME, contentValues, SCHEDULE_COLUMN_ID + " = ? ", new String[] {String.valueOf(id)});
        db.close();
        return result;
    }

    public Integer deleteMatch (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SCHEDULE_TABLE_NAME,
                SCHEDULE_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

}
