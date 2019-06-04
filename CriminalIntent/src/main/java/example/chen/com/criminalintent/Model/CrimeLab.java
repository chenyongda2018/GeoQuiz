package example.chen.com.criminalintent.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import example.chen.com.criminalintent.database.CrimeDbSchema.CrimeBaseHelper;
import example.chen.com.criminalintent.database.CrimeDbSchema.CrimeCursorWrapper;
import example.chen.com.criminalintent.database.CrimeDbSchema.CrimeDbSchema;

import static example.chen.com.criminalintent.database.CrimeDbSchema.CrimeDbSchema.*;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mSQLiteDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }

    /**
     * 新插入一个Crime记录
     *
     * @param crime
     */
    public void addCrime(Crime crime) {
        ContentValues values = getContentValues(crime);
        mSQLiteDatabase.insert(CrimeTable.NAME, null, values);
    }

    /**
     * 获得所有Crime记录
     *
     * @return
     */
    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursorWrapper = queryCrimes(null, null);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                crimes.add(cursorWrapper.getCrime());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return crimes;

    }

    /**
     * 获得单个Crime记录
     *
     * @param id
     * @return
     */
    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor = queryCrimes(CrimeTable.Cols.UUID + " = ? ", new String[]{id.toString()});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Crime crime) {
        File fileDir = mContext.getFilesDir();
        return new File(fileDir, crime.getPhotoFilename());
    }

    /**
     * 返回Crime在数据表中的位置
     */
    public int getCrimeOrder(UUID id) {
        CrimeCursorWrapper cursor = queryCrimes(null, null);
        try {
            int order = 0;
            while (cursor.moveToNext()) {
                Crime crime = cursor.getCrime();
                if (crime.getId().toString().equals(id.toString())) {
                    return order;
                }
                order++;
            }
        } finally {
            cursor.close();
        }
        return -1;
    }

    /**
     * 更新Crime记录
     *
     * @param crime
     */
    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mSQLiteDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    /**
     * 获得封装的含有所有Crime记录的CursorWrapper
     *
     * @param whereClause
     * @param whereArgs
     * @return
     */
    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mSQLiteDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new CrimeCursorWrapper(cursor);
    }

    /**
     * Crime表中的一行，相当与元组
     *
     * @param crime
     * @return
     */
    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());
        return values;
    }
}
