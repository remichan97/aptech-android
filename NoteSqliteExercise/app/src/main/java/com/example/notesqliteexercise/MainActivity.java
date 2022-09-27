package com.example.notesqliteexercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Date;

import adapter.NotesAdapter;
import entity.NotesEntity;
import models.Notes;

public class MainActivity extends AppCompatActivity {
	private ListView listNotes;
	private Cursor currCursor;
	private NotesAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listNotes = findViewById(R.id.listNotes);
		currCursor = NotesEntity.getCursor(this.getApplicationContext());
		adapter = new NotesAdapter(this, currCursor);
		listNotes.setAdapter(adapter);

		registerForContextMenu(listNotes);

		listNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				currCursor.moveToPosition(i);

				Notes note = new Notes();

				note.setData(currCursor);

				showDetailDialogue(note);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		showNoteDialogue(null);
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.popup_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(@NonNull MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		currCursor.moveToPosition(info.position);
		Notes currentNote = new Notes();
		currentNote.setData(currCursor);

		switch (item.getItemId())
		{
			case R.id.editNote:
				showNoteDialogue(currentNote);
				return true;
			case R.id.deleteNote:
				showDeleteConfirmation(currentNote.getId());
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	private void showDeleteConfirmation(int id)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Confirmation");
		builder.setMessage("Would you like to delete the selected note?\nThis action cannot be undone!");

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.cancel();
			}
		});

		builder.setPositiveButton("Yes, Delete", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				NotesEntity.delete(id, getApplicationContext());
				Toast.makeText(getApplicationContext(), "Note Deleted!", Toast.LENGTH_SHORT).show();
				updateCursor();
			}
		});

		builder.show();
	}

	private void showNoteDialogue(Notes note)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		View v = getLayoutInflater().inflate(R.layout.note_dialogue, null);

		EditText headerText = v.findViewById(R.id.editTextNoteHeader);
		EditText contentText = v.findViewById(R.id.editTextNoteContent);
		SwitchMaterial importantSwitch = v.findViewById(R.id.switchImportant);

		if (note != null)
		{
			builder.setTitle("Edit Note");
			headerText.setText(note.getHeader());
			contentText.setText(note.getContent());
			importantSwitch.setChecked(note.isImportant());
		}
		else
		{
			builder.setTitle("Add new Note");
		}

		builder.setView(v);

		builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				if (headerText.getText().toString().isEmpty() && contentText.getText().toString().isEmpty())
				{
					Toast.makeText(MainActivity.this, "Empty field, Note creation/updating aborted!", Toast.LENGTH_SHORT).show();
					return;
				}
				String header = headerText.getText().toString();
				String content = contentText.getText().toString();
				boolean important = importantSwitch.isChecked();

				if (note != null)
				{
					note.setHeader(header);
					note.setContent(content);
					note.setImportant(important);

					NotesEntity.update(note, getApplicationContext());
				}
				else
				{
					Notes notes = new Notes(header, content, important, new Date());
					NotesEntity.insert(notes, getApplicationContext());
				}
				Toast.makeText(MainActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
				updateCursor();
			}
		});

		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.dismiss();
			}
		});

		builder.show();
	}

	private void showDetailDialogue(Notes note)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Note details");

		StringBuilder sb = new StringBuilder();

		sb.append("Note name: " + note.getHeader());
		sb.append("\n");
		sb.append("Created on: " + note.getCreatedDate());
		sb.append("\n");
		sb.append(note.getContent());

		String details = sb.toString();

		builder.setMessage(details);

		builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.cancel();
			}
		});

		builder.show();
	}

	private void updateCursor()
	{
		currCursor = NotesEntity.getCursor(this.getApplicationContext());
		adapter.changeCursor(currCursor);
		adapter.notifyDataSetChanged();
	}
}