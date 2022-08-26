package com.example.listexercise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	Button btnAdd;
	ListView osList;
	ArrayAdapter arrayAdapter;
	private List<String> dataList = new ArrayList<String>() {
		{
			add("Windows");
			add("macOS");
			add("Linux");
			add("Windows Phone");
			add("Android");
			add("watchOS");
			add("iOS/iPadOS");
			add("BlackBerryOS");
			add("WebOS");
		}
	};
	private String _text = "";

	private void showAllList() {
		arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.item_subject, R.id.is_os_name, dataList);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnAdd = findViewById(R.id.btnAdd);
		osList = findViewById(R.id.listOsList);

		showAllList();

		osList.setAdapter(arrayAdapter);

		btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

				builder.setTitle("Add new OS");

				final EditText input = new EditText(MainActivity.this);

				input.setInputType(InputType.TYPE_CLASS_TEXT);

				builder.setView(input);

				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.cancel();
					}
				});

				builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						_text = input.getText().toString();

						if (_text.isEmpty()) {
							dialogInterface.cancel();
						} else {
							if (dataList.contains(_text)) {
								Toast.makeText(MainActivity.this, "The OS already existed", Toast.LENGTH_SHORT).show();
							} else {
								dataList.add(_text);
								arrayAdapter.notifyDataSetChanged();
								showAllList();
								Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_LONG).show();
							}
						}
					}
				});

				builder.show();
			}
		});

		// Tap an item once to reveal the Edit dialog box
		osList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				int pos = adapterView.getPositionForView(view);

				_text = dataList.get(pos);

				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

				builder.setTitle("Edit");

				final EditText input = new EditText(MainActivity.this);

				input.setInputType(InputType.TYPE_CLASS_TEXT);

				input.setText(_text);

				builder.setView(input);

				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.cancel();
					}
				});

				builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						_text = input.getText().toString();

						if (_text.isEmpty()) {
							dialogInterface.cancel();
						} else {
							if (dataList.contains(_text)) {
								dialogInterface.cancel();
							} else {
								dataList.set(pos, _text);
								_text = "";
								arrayAdapter.notifyDataSetChanged();
								showAllList();
								Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_LONG).show();
							}
						}
					}
				});

				builder.show();
			}
		});

		// Hold an item to delete it from the list
		osList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
				int pos = adapterView.getPositionForView(view);

				String data = dataList.get(pos);

				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

				builder.setTitle("Confirmation");

				builder.setMessage("Would you like to delete " + data + "?\n" + "This action is irrevesible!");

				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.cancel();
					}
				});

				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dataList.remove(pos);
						_text = "";
						arrayAdapter.notifyDataSetChanged();
						showAllList();
						Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
					}
				});

				builder.show();

				return true;
			}
		});
	}
}