package com.example.objectlistexercise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import adapter.FoodAdapter;
import models.Food;

public class MainActivity extends AppCompatActivity {

	ListView listView;

	List<Food> foodList = new ArrayList<Food>() {
		{
			add(new Food("Pho Bo", 4.5d, 25));
			add(new Food("Pho Ga", 4.5d, 25));
			add(new Food("Mien Luon", 4.5d, 25));
			add(new Food("Banh Da Cua", 4.5d, 25));
		}
	};
	FoodAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = findViewById(R.id.listFoodData);

		adapter = new FoodAdapter(foodList, MainActivity.this);

		listView.setAdapter(adapter);

		registerForContextMenu(listView);
	}

	public void editDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

		builder.setTitle("Add new Food");

		EditText textFoodName = new EditText(MainActivity.this);
		EditText textFoodRating = new EditText(MainActivity.this);
		EditText textFoodEta = new EditText(MainActivity.this);

		textFoodName.setHint("Enter Food name");
		textFoodRating.setHint("Enter Food rating");
		textFoodEta.setHint("Enter Food ETA");

		textFoodName.setInputType(InputType.TYPE_CLASS_TEXT);
		textFoodRating.setInputType(InputType.TYPE_CLASS_NUMBER);
		textFoodEta.setInputType(InputType.TYPE_CLASS_NUMBER);

		LinearLayout lila = new LinearLayout(MainActivity.this);
		lila.setOrientation(LinearLayout.VERTICAL);

		lila.addView(textFoodName);
		lila.addView(textFoodRating);
		lila.addView(textFoodEta);

		builder.setView(lila);

		builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());

		builder.setPositiveButton("Save", (dialogInterface, i) -> {
			if (textFoodEta.getText().toString().isEmpty() || textFoodName.getText().toString().isEmpty() || textFoodRating.getText().toString().isEmpty()) {
				dialogInterface.cancel();
			} else {
				String name = textFoodName.getText().toString();
				double rating = Double.parseDouble(textFoodRating.getText().toString());
				int eta = Integer.parseInt(textFoodEta.getText().toString());

				Food food = new Food(name, rating, eta);

				if (foodList.contains(food)) {
					Toast.makeText(this, "The item already existed!", Toast.LENGTH_SHORT).show();
					dialogInterface.cancel();
				} else {
					foodList.add(food);
					adapter.notifyDataSetChanged();
					Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
				}
			}
		});

		builder.show();
	}

	public void editDialog(int pos) {

		Food food = foodList.get(pos);

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

		builder.setTitle("Edit");

		EditText textFoodName = new EditText(MainActivity.this);
		EditText textFoodRating = new EditText(MainActivity.this);
		EditText textFoodEta = new EditText(MainActivity.this);

		textFoodName.setHint("Enter Food name");
		textFoodRating.setHint("Enter Food rating");
		textFoodEta.setHint("Enter Food ETA");

		textFoodName.setInputType(InputType.TYPE_CLASS_TEXT);
		textFoodRating.setInputType(InputType.TYPE_CLASS_NUMBER);
		textFoodEta.setInputType(InputType.TYPE_CLASS_NUMBER);

		textFoodName.setText(food.getName());
		textFoodRating.setText(String.valueOf(food.getRating()));
		textFoodEta.setText(String.valueOf(food.getEta()));

		LinearLayout lila = new LinearLayout(MainActivity.this);
		lila.setOrientation(LinearLayout.VERTICAL);

		lila.addView(textFoodName);
		lila.addView(textFoodRating);
		lila.addView(textFoodEta);

		builder.setView(lila);

		builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());

		builder.setPositiveButton("Save", (dialogInterface, i) -> {
			if (textFoodEta.getText().toString().isEmpty() || textFoodName.getText().toString().isEmpty() || textFoodRating.getText().toString().isEmpty()) {
				dialogInterface.cancel();
			} else {
				String name = textFoodName.getText().toString();
				double rating = Double.parseDouble(textFoodRating.getText().toString());
				int eta = Integer.parseInt(textFoodEta.getText().toString());

				Food foodEdit = new Food(name, rating, eta);

				if (foodList.contains(foodEdit)) {
					dialogInterface.cancel();
				} else {
					foodList.set(pos, foodEdit);
					adapter.notifyDataSetChanged();
					Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
				}
			}
		});

		builder.show();
	}

	public void deleteDialog(int pos) {
		Food food = foodList.get(pos);

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

		builder.setTitle("Confirmation");

		builder.setMessage("Would you like to delete the selected food?");

		builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel());

		builder.setPositiveButton("Yes, Delete", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				foodList.remove(pos);
				adapter.notifyDataSetChanged();
				Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
			}
		});

		builder.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.addFood) {
			editDialog();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		// you can set menu header with title icon etc
		menu.setHeaderTitle("Choose an action");

		getMenuInflater().inflate(R.menu.popup_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

		int pos = info.position;

		switch (item.getItemId()) {
			case R.id.editFood:
				editDialog(pos);
				break;
			case R.id.deleteFood:
				deleteDialog(pos);
				break;
		}

		return false;
	}
}