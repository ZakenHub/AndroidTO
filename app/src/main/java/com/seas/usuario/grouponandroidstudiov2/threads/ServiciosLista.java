package com.seas.usuario.grouponandroidstudiov2.threads;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.seas.usuario.grouponandroidstudiov2.ListaOfertasActivity;
import com.seas.usuario.grouponandroidstudiov2.MapaActivity;
import com.seas.usuario.grouponandroidstudiov2.R;
import com.seas.usuario.grouponandroidstudiov2.beans.Local;
import com.seas.usuario.grouponandroidstudiov2.tools.Post;
import com.seas.usuario.grouponandroidstudiov2.tools.Utils;


public class ServiciosLista {
	final Handler handle = new Handler();
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
	
	public void miThread(final String tipo){
			Thread t = new Thread(){
				public void run(){
					Post post = null;
					 try{
					 	ArrayList<String> parametros = new ArrayList<>();
						parametros.add("tipo");
						parametros.add(tipo);
						//Llamada a Servidor Web PHP
						post = new Post();
						datos = post.getServerData(parametros,"http://zakendevs.x10host.com/groupon/ofertas.php");
					} catch (Exception e) {
						Toast.makeText(
								MapaActivity.getInstance().getBaseContext(),
								e.getMessage(), Toast.LENGTH_SHORT).show();
					}
					handle.post(proceso);
				}
			};
			t.start();
	}
	 
		final Runnable proceso = new Runnable(){
			public void run(){
				try {
//					ImageView imagenView = new ImageView(MapaActivity.getInstance().getBaseContext());
				if(datos!=null && datos.length()>0){
					for(int i = 0 ; i <datos.length() ; i++){
						JSONObject json_data = datos.getJSONObject(i);
						//int idSitio = json_data.getInt("ID");
						String nombre = json_data.getString("NOMBRE");
//						String descripcion = json_data.getString("DESCRIPCION");
						String longitud = json_data.getString("LONGITUD");
						String latitud = json_data.getString("LATITUD");
						String img = json_data.getString("IMAGEN");
//						String urlImagen = "http://zakendevs.x10host.com/groupon/images/" + img;
//						Bitmap loadedImage = Utils.downloadFile(urlImagen);
//						imagenView.setImageBitmap(loadedImage);
//						OverlayItem overlay = getOverlayItem(nombre,descripcion,latitud,longitud,imagenView.getDrawable());
//						MapaActivity.getInstance().anadirMarca(nombre, descripcion, latitud, longitud, loadedImage);
						Local o1 = new Local();
						o1.setLocalName(nombre);
						o1.setLocalLat(latitud);
						o1.setLocalLong(longitud);

						switch (img) {
							case "Bancos.png":
								o1.setLocalImage(R.drawable.bancos);
								break;
							case "Gasolineras.png":
								o1.setLocalImage(R.drawable.gasolineras);
								break;
							case "Hospitales.png":
								o1.setLocalImage(R.drawable.hospitales);
								break;
							case "Restaurantes.png":
								o1.setLocalImage(R.drawable.restaurantes);
								break;
							case "Seas.png":
								o1.setLocalImage(R.drawable.seas);
								break;
							case "Tiendas.png":
								o1.setLocalImage(R.drawable.tiendas);
								break;
							default:
						}
						ListaOfertasActivity.getInstance().getM_locals().add(o1);
					}
				}
				ListaOfertasActivity.getInstance().actualizarListaLocales();
			}catch (Exception e) {}
		}};
}
