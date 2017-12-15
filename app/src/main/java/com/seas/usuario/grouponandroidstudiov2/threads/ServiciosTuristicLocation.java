package com.seas.usuario.grouponandroidstudiov2.threads;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.seas.usuario.grouponandroidstudiov2.MapaActivity;
import com.seas.usuario.grouponandroidstudiov2.tools.Post;
import com.seas.usuario.grouponandroidstudiov2.tools.Utils;


public class ServiciosTuristicLocation {
	final Handler handleTouristic = new Handler();
	private JSONArray datos; 
	
//	private OverlayItem getOverlayItem( String nombre, String descripcion, String latitud, String longitud, Drawable drawable){
//		 	String coordinates[] = {latitud,longitud};
//	   		double lat = Double.parseDouble(coordinates[0]);
//	   		double lng = Double.parseDouble(coordinates[1]);
//	   		GeoPoint point = new GeoPoint((int) (lng * 1E6),(int) (lat * 1E6));
//	   		OverlayItem overlayitem = new OverlayItem(point, nombre, descripcion);
//	   		overlayitem.setMarker(MapaActivity.getInstance().getItemizedoverlay().boundCenterBottomAux(drawable));
//	   		return overlayitem;
//	 }
	
	public void miThread(){
			Thread t = new Thread(){
				public void run(){
					Post post = null;
					 try{
					 	ArrayList<String> parametros = new ArrayList<String>();
						parametros.add("tipo");
						parametros.add("Todos");
						//Llamada a Servidor Web PHP
						post = new Post();
						datos = post.getServerData(parametros,"http://zakendevs.x10host.com/groupon/ofertas.php");
					} catch (Exception e) {
						Toast.makeText(
								MapaActivity.getInstance().getBaseContext(),
								e.getMessage(), Toast.LENGTH_SHORT).show();
					}
					 handleTouristic.post(procesoTouristic);
				}
			};
			t.start();
	}
	 
		final Runnable procesoTouristic = new Runnable(){
			public void run(){
				try {
					ImageView imagenView = new ImageView(MapaActivity.getInstance().getBaseContext());
				if(datos!=null && datos.length()>0){
					for(int i = 0 ; i <datos.length() ; i++){
						JSONObject json_data = datos.getJSONObject(i);
						int idSitio = json_data.getInt("ID");
						String nombre = json_data.getString("NOMBRE");
						String descripcion = json_data.getString("DESCRIPCION");
						String latitud = json_data.getString("LONGITUD");
						String longitud = json_data.getString("LATITUD");
						String img = json_data.getString("IMAGEN");
						String urlImagen = "http://zakendevs.x10host.com/groupon/images/" + img;

						Utils utils = new Utils(urlImagen);
						utils.start();
						while(utils.isEjecucion()){
							// Me quedo a la espera del 2º hilo.
						}
						imagenView.setImageBitmap(utils.getLoadedImage());
						//OverlayItem overlay = getOverlayItem(nombre,descripcion,latitud,longitud,imagenView.getDrawable());
						//MapaActivity.getInstance().anadePuntoAlMapaConItemizedOverlayEImagen(overlay );
						MapaActivity.getInstance().anadirMarca(nombre,descripcion,latitud,longitud,utils.getLoadedImage());
					}
				}
			}catch (Exception e) {}
		}};
		
		public Bitmap downloadFile(String imageHttpAddress) {
	        URL imageUrl = null;
	        Bitmap loadedImage = null;
	        HttpURLConnection conn = null; 
	        try {
	            imageUrl = new URL(imageHttpAddress);
	            conn = (HttpURLConnection) imageUrl.openConnection();
	            conn.connect();
	            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
	        } catch (IOException e) {
	        }finally{
	        	if(conn!=null){conn.disconnect();}
	        }
	        return loadedImage;
	    }
//	 public void getSitiosTuristicos(){
//		 Post post = null;
//		 try{
//		 	ImageView imagenView = new ImageView(MapaActivity.getInstance().getBaseContext());
//	    	//INICIO Llamada a Servidor Web PHP
//			ArrayList<String> parametros = new ArrayList<String>();
//			parametros.add("tipo");
//			parametros.add("Hospitales");
//			//Llamada a Servidor Web PHP
//			post = new Post();
//			JSONArray datos = post.getServerData(parametros,"http://192.168.1.3/login.php");
//			if(datos!=null && datos.length()>0){
//				for(int i = 0 ; i <datos.length() ; i++){
//						JSONObject json_data = datos.getJSONObject(i);
//						int idSitio = json_data.getInt("ID");
//						String nombre = json_data.getString("NOMBRE");
//						String descripcion = json_data.getString("DESCRIPCION");
//						String longitud = json_data.getString("LONGITUD");
//						String latitud = json_data.getString("LATITUD");
//						String img = json_data.getString("IMAGEN");
//						String urlImagen = "http://192.168.1.3/Imagenes/Imagenes/" + img;
//						Bitmap loadedImage = Utils.downloadFile(urlImagen);
//						imagenView.setImageBitmap(loadedImage);
//						OverlayItem overlay = getOverlayItem(nombre,descripcion,latitud,longitud,imagenView.getDrawable());
//						MapaActivity.getInstance().anadePuntoAlMapaConItemizedOverlayEImagen(overlay );
//				}
//			}
//		}catch(Exception e){
//			  Toast.makeText(MapaActivity.getInstance().getBaseContext(), 
//		                e.getMessage(), 
//		                Toast.LENGTH_SHORT).show();
//		}
//		}	//FIN Llamada a Servidor Web PHP
}

