package com.seas.usuario.grouponandroidstudiov2;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.seas.usuario.grouponandroidstudiov2.threads.ServiceLogin;

public class LoginActivity extends Activity {
	private EditText edtEmail;
	private EditText edtPass;
	private Button btnLogin;

	private static LoginActivity loginActivity;
	public static LoginActivity getInstance(){
		return loginActivity;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		loginActivity = this;
		
		edtEmail = (EditText) findViewById(R.id.edtEmail);
		edtPass = (EditText) findViewById(R.id.edtPass);
		
        edtEmail.setText("a@svalero.com");
		edtPass.setText("1234");
        btnLogin = (Button) findViewById(R.id.btnEnviar);
	        
	        btnLogin.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					ServiceLogin.accionLogin(edtEmail.getText().toString(), edtPass.getText().toString());
					
				}
			});
	}



}
