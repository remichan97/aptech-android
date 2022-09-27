package entity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import models.Notes;
import utils.DbHelper;

public class NotesEntity {
	public final static String _tableName = "note";
	public final static String _createTableQuery = "create table note (\n" +
			"\t_id integer primary key autoincrement,\n" +
			"\theader text,\n" +
			"\tcontent text,\n" +
			"\timportant integer check (important between 0 and 1),\n" +
			"\tcreateddate datetime\n" +
			")";

	public static void insert(Notes notes, Context context)
	{
		SQLiteDatabase db = DbHelper.getInstance(context).getWritableDatabase();

		ContentValues content = new ContentValues();

		content.put("header", notes.getHeader());
		content.put("content", notes.getContent());
		content.put("important", notes.isImportant() ? 1 : 0);
		content.put("createddate", notes.getCreatedDate());

		db.insert(_tableName, null, content);
	}

	public static void update(Notes notes, Context context)
	{
		SQLiteDatabase db = DbHelper.getInstance(context).getWritableDatabase();

		ContentValues content = new ContentValues();

		content.put("header", notes.getHeader());
		content.put("content", notes.getContent());
		content.put("important", notes.isImportant() ? 1 : 0);
		content.put("createddate", notes.getCreatedDate());

		db.update(_tableName, content, "_id = " + notes.getId(), null);
	}

	public static void delete(int id, Context context) {
		SQLiteDatabase db = DbHelper.getInstance(context).getWritableDatabase();

		db.delete(_tableName, "_id = " + id, null);
	}

	public static Cursor getCursor(Context context) {
		SQLiteDatabase db = DbHelper.getInstance(context).getReadableDatabase();

		String sql = "select * from " + _tableName;
		Cursor cursor = db.rawQuery(sql, null);

		return cursor;
	}
}
