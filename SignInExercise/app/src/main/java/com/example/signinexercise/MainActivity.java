package com.example.signinexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	EditText username, password;
	Button signIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		username = findViewById(R.id.editTextUserName);
		password = findViewById(R.id.editTextPassword);

		signIn = findViewById(R.id.buttonSignIn);

		signIn.setOnClickListener(new View.OnClickListener(

		) {
			@Override
			public void onClick(View view) {
				String txtUsername = username.getText().toString();
				String txtPassword = password.getText().toString();
				if (txtUsername.equals("aptech")  && txtPassword.equals("12345")) {
					Toast.makeText(getApplicationContext(), "Hi, you have signed in!", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(), "Please check your credentials!", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}