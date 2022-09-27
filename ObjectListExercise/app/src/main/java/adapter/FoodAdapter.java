package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.objectlistexercise.R;

import java.util.List;

import models.Food;

public class FoodAdapter extends BaseAdapter {

	List<Food> foodList;
	Activity activity;

	public FoodAdapter(List<Food> foodList, Activity activity) {
		this.foodList = foodList;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return foodList.size();
	}

	@Override
	public Object getItem(int i) {
		return foodList.get(i);
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		view = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.food_list, viewGroup, false);

		Food food = foodList.get(i);

		TextView textFoodName, textFoodRating, textFoodEta;

		textFoodName = view.findViewById(R.id.textFoodName);
		textFoodRating = view.findViewById(R.id.textFoodRating);
		textFoodEta = view.findViewById(R.id.textFoodEta);

		textFoodName.setText(food.getName());
		textFoodRating.setText("Rating: " + food.getRating());
		textFoodEta.setText("Estimated arrival: " + food.getEta() + " minutes");

		return view;
	}
}
