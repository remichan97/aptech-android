package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import entity.NotesEntity;

public class DbHelper extends SQLiteOpenHelper {

	private final static String __dbName = "NoteTable";
	private final static int _version = 1;
	private static DbHelper instance = null;

	private DbHelper(Context context)
	{
		super(context, __dbName, null, _version);
	}

	public synchronized static DbHelper getInstance(Context context)
	{
		if (instance == null) instance = new DbHelper(context);
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL(NotesEntity._createTableQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

	}
}
