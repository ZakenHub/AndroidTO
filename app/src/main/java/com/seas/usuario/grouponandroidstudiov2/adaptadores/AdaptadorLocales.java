package com.seas.usuario.grouponandroidstudiov2.adaptadores;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seas.usuario.grouponandroidstudiov2.R;
import com.seas.usuario.grouponandroidstudiov2.beans.Local;

public class AdaptadorLocales extends ArrayAdapter<Local>{
		private ArrayList<Local> items;
		private Context context;
	
		public AdaptadorLocales(Context context, 
								int disenyoPorFila,
							    ArrayList<Local> locales) {
			super(context, disenyoPorFila, locales);
			this.items = locales;
			this.context = context;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if(v == null){
				LayoutInflater vi = (LayoutInflater) 
						context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.list_item, null);
			}
			Local o = items.get(position);
			if(o != null){
				TextView tt = (TextView) v.findViewById(R.id.row_toptext);
				ImageView im = (ImageView) v.findViewById(R.id.icon);
				if(im != null){
					im.setImageResource(o.getLocalImage());
				}
				if(tt != null){
					tt.setText(o.getLocalName());
				}
			}
			return v;
		}
	}
