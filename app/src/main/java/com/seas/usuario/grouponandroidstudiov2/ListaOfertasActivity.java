package com.seas.usuario.grouponandroidstudiov2;

import java.util.ArrayList;


import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.seas.usuario.grouponandroidstudiov2.adaptadores.AdaptadorLocales;
import com.seas.usuario.grouponandroidstudiov2.beans.Local;
import com.seas.usuario.grouponandroidstudiov2.datos.GrouponData;
import com.seas.usuario.grouponandroidstudiov2.threads.ServiciosLista;

public class ListaOfertasActivity extends Activity {
	private ArrayList<Local> m_locals = null;
	public ArrayList<Local> getM_locals() {
		return m_locals;
	}

	public void setM_locals(ArrayList<Local> m_locals) {
		this.m_locals = m_locals;
	}

	private AdaptadorLocales adaptadorLocales;
	private ServiciosLista serviciosLista = new ServiciosLista();
	
	  private static ListaOfertasActivity listaOfertasActivity= null;
	    public static ListaOfertasActivity getInstance(){
	    	return listaOfertasActivity;
	    }
	    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ofertas);
        listaOfertasActivity = this;
        
        ListView lv = (ListView)findViewById(R.id.listView);
        
        m_locals = new ArrayList<>();
        actualizarListaLocales();
        serviciosLista.miThread("Todos");

        // Almaceno el ArrayList para que sea accesible 
        // desde cualquier punto de la aplicación
        	GrouponData.setLstLocales(m_locals);
        											 // getBaseContext()
		adaptadorLocales = new AdaptadorLocales(this, R.layout.list_item, m_locals);
		lv.setAdapter(this.adaptadorLocales);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Local local = (Local) parent.getItemAtPosition(position);
		        
		    	// Almaceno el Local seleccionado para que sea accesible 
		        // desde cualquier punto de la aplicación
		    		GrouponData.setLocalSeleccionado(local);
		    		
				Intent myIntent = new Intent(getBaseContext(), MapaActivity.class);
				startActivity(myIntent);
				
		    	//Bundle bundle = new Bundle();
				//bundle.putString(myKey, local.getLocalName());
				//myIntent.putExtras(bundle);
				
			}
			
		});
		
    }
    
   public void actualizarListaLocales(){
		// Debería venir desde el Servidor Web PHP
    	try{
//			m_locals = new ArrayList<Local>();
//			Local o1 = new Local();
//			o1.setLocalName("Bancos");
//			o1.setLocalImage(R.drawable.bancos);
//			Local o2 = new Local();
//			o2.setLocalName("Gasolineras");
//			o2.setLocalImage(R.drawable.gasolineras);
//			Local o3 = new Local();
//			o3.setLocalName("Hospitales");
//			o3.setLocalImage(R.drawable.hospitales);
//			Local o4 = new Local();
//			o4.setLocalName("Restaurantes");
//			o4.setLocalImage(R.drawable.restaurantes);
//			Local o5 = new Local();
//			o5.setLocalName("Tiendas");
//			o5.setLocalImage(R.drawable.tiendas);
//			Local o6 = new Local();
//			o6.setLocalName("Seas");
//			o6.setLocalImage(R.drawable.seas);
//			Local o7 = new Local();
//			o7.setLocalName("Todos");
//			o7.setLocalImage(R.drawable.ok);
//			// a�adimos los sitios al array
//			m_locals.add(o1);
//			m_locals.add(o2);
//			m_locals.add(o3);
//			m_locals.add(o4);
//			m_locals.add(o5);
//			m_locals.add(o6);
//			m_locals.add(o7);
		}catch(Exception e){
			Log.e("BACKGROUND_PROC", e.getMessage());
		}
		
		if(m_locals != null && m_locals.size() > 0){
//			for(int i = 0; i < m_locals.size(); i++){
//				adaptadorLocales.add(m_locals.get(i));
//			}
			adaptadorLocales.notifyDataSetChanged();
		}
	}
}
