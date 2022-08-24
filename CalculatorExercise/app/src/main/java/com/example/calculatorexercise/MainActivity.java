package com.example.calculatorexercise;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	EditText num1, num2, ans;
	double answer;
	String err;
	Button add, minus, multi, divide, eval;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		num1 = findViewById(R.id.editTextNumber);
		num2 = findViewById(R.id.editTextNumber2);
		ans = findViewById(R.id.editAns);

		add = findViewById(R.id.btnAdd);
		minus = findViewById(R.id.btnMinus);
		multi = findViewById(R.id.btnMultiply);
		divide = findViewById(R.id.btnDivide);
		eval = findViewById(R.id.btnEval);

		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (num1.getText().toString().isEmpty() || num2.getText().toString().isEmpty()) {
					Toast.makeText(getApplicationContext(), "Enter Numbers", Toast.LENGTH_SHORT).show();
				} else {
					double a = Double.parseDouble(num1.getText().toString());
					double b = Double.parseDouble(num2.getText().toString());
					answer = a + b;
				}
			}
		});

		minus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (num1.getText().toString().isEmpty() || num2.getText().toString().isEmpty()) {
					Toast.makeText(getApplicationContext(), "Enter Numbers", Toast.LENGTH_SHORT).show();
				} else {
					double a = Double.parseDouble(num1.getText().toString());
					double b = Double.parseDouble(num2.getText().toString());
					answer = a - b;
				}
			}
		});

		multi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (num1.getText().toString().isEmpty() || num2.getText().toString().isEmpty()) {
					Toast.makeText(getApplicationContext(), "Enter Numbers", Toast.LENGTH_SHORT).show();
				} else {
					double a = Double.parseDouble(num1.getText().toString());
					double b = Double.parseDouble(num2.getText().toString());
					answer = a * b;
				}


			}
		});

		divide.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (num1.getText().toString().isEmpty() || num2.getText().toString().isEmpty()) {
					Toast.makeText(getApplicationContext(), "Enter Numbers", Toast.LENGTH_SHORT).show();
					return;
				}

				if (num2.getText().toString().equals("0"))
				{
					Toast.makeText(getApplicationContext(), "Cannot divide by zero", Toast.LENGTH_SHORT).show();
					return;
				}

				double a = Double.parseDouble(num1.getText().toString());
				double b = Double.parseDouble(num2.getText().toString());
				answer = a / b;
			}
		});

		eval.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String ans1 = String.valueOf(answer);
				ans.setText(ans1);
				answer = 0;
			}
		});
	}
}