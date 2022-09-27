package adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.notesqliteexercise.R;

import models.Notes;

public class NotesAdapter extends CursorAdapter {

	Activity activity;

	public NotesAdapter(Activity activity, Cursor cursor)
	{
		super(activity, cursor);
		this.activity = activity;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		View v = activity.getLayoutInflater().inflate(R.layout.notes_layout, null);
		return v;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		View colourTag = view.findViewById(R.id.isImportant);
		TextView headerView = view.findViewById(R.id.textNoteHeader);
		TextView dateView = view.findViewById(R.id.textCreatedDate);

		Notes notes = new Notes();
		notes.setData(cursor);
		if (notes.isImportant()) {
			colourTag.setBackgroundColor(Color.RED);
		} else {
			colourTag.setBackgroundColor(Color.GREEN);
		}

		headerView.setText(notes.getHeader());
		dateView.setText(notes.getCreatedDate());
	}
}
