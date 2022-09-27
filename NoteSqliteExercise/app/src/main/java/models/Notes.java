package models;

import android.database.Cursor;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Notes {
	private int id;
	private String header;
	private String content;
	private boolean isImportant;
	private Date createdDate;

	public Notes() {
	}

	public Notes(int id, String header, String content, boolean isImportant, Date createdDate) {
		this.id = id;
		this.header = header;
		this.content = content;
		this.isImportant = isImportant;
		this.createdDate = createdDate;
	}

	public Notes(String header, String content, boolean isImportant, Date createdDate) {
		this.header = header;
		this.content = content;
		this.isImportant = isImportant;
		this.createdDate = createdDate;
	}

	public void setData(Cursor cursor) {
		this.id = cursor.getInt(cursor.getColumnIndex("_id"));
		this.header = cursor.getString(cursor.getColumnIndex("header"));
		this.content = cursor.getString(cursor.getColumnIndex("content"));
		this.isImportant = cursor.getInt(cursor.getColumnIndex("important")) == 1?true:false;
		String ntao = cursor.getString(cursor.getColumnIndex("createddate"));
		try {
			this.createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ntao);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isImportant() {
		return isImportant;
	}

	public void setImportant(boolean important) {
		isImportant = important;
	}

	public String getCreatedDate() {
		Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(createdDate);
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
