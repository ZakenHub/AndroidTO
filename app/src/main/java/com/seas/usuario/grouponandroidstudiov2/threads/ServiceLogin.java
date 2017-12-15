package com.seas.usuario.grouponandroidstudiov2.threads;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.EditText;
import android.widget.Toast;

import com.seas.usuario.grouponandroidstudiov2.ListaOfertasActivity;
import com.seas.usuario.grouponandroidstudiov2.LoginActivity;
import com.seas.usuario.grouponandroidstudiov2.beans.Cliente;
import com.seas.usuario.grouponandroidstudiov2.datos.GrouponData;
import com.seas.usuario.grouponandroidstudiov2.tools.Post;

public class ServiceLogin{
	private final static Handler manejador = new Handler();
	private static String messageUser; 
	private static JSONArray datos;
	private static Thread threadLogin;
	
	public static void accionLogin(final String user,final String pass){
		threadLogin = new Thread(){
			public void run(){
				ArrayList<String> parametros = new ArrayList<>();
				parametros.add("Usuario");
				parametros.add(user);
				parametros.add("Contrasena");
				parametros.add(pass);
				// Llamada a Servidor Web PHP
				try {
					Post post = new Post();
					datos = post.getServerData(parametros,"http://zakendevs.x10host.com/groupon/loginGroupon.php");
				} catch (Exception e) {
					messageUser = "Error al conectar con el servidor. ";
				}
				manejador.post(proceso);
			}
		};
		threadLogin.start();
	}
	
	private final static Runnable proceso = new Runnable(){
		public void run() {
			try{
				if (datos != null && datos.length() > 0) {
					JSONObject json_data = datos.getJSONObject(0);
					int numRegistrados = json_data.getInt("ID_USUARIO");
					if (numRegistrados > 0) {
						Cliente cliente = new Cliente();
						cliente.setIdUsuario(numRegistrados);
						cliente.setEmail(json_data.getString("EMAIL"));
						cliente.setPass(json_data.getString("PASS"));
						GrouponData.setCliente(cliente);
						Toast.makeText(LoginActivity.getInstance().getBaseContext(),"" +
								"Usuario correcto. ", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(LoginActivity.getInstance().getBaseContext(), ListaOfertasActivity.class);
					    // Aquí se le puede pasar información al intent como el id del elemento o la posición con
					    // los métodos putExtra
						LoginActivity.getInstance().startActivity(intent);
						//LoginActivity.getInstance().finish();
					}
				} else {
						Toast.makeText(LoginActivity.getInstance().getBaseContext(),"" +
								"Usuario incorrecto. ", Toast.LENGTH_SHORT).show();
						Toast.makeText(LoginActivity.getInstance().getBaseContext(),
								messageUser, Toast.LENGTH_SHORT).show();
				}
			}catch(Exception e){}
		}
	};
}
/*
 * ArrayList<String> parametros = new ArrayList<String>();
				parametros.add("Usuario");
				parametros.add("PEPE");
				parametros.add("Contrasena");
				parametros.add("PEPE");
				// Llamada a Servidor Web PHP
				try {
					Post post = new Post();
					JSONArray datos = post.getServerData(parametros,"http://www.javijava.260mb.org/index.php");
					//String datoss = "[{\"ID_USUARIO\":\"1\",\"USER\":\"javi@javi.\",\"PASS\":\"1234\"}]";
					//JSONArray datos = new JSONArray(datoss);
					
					// No se puede poner localhost, carga la consola de Windows
					// y escribe ipconfig/all para ver tu IP
					if (datos != null && datos.length() > 0) {
						JSONObject json_data = datos.getJSONObject(0);
						int numRegistrados = json_data.getInt("ID_USUARIO");
						if (numRegistrados > 0) {
							Toast.makeText(getBaseContext(),"Usuario correcto. ", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getBaseContext(),"Usuario incorrecto. ", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					Toast.makeText(getBaseContext(),"Error al conectar con el servidor. ",Toast.LENGTH_SHORT).show();
				}*/