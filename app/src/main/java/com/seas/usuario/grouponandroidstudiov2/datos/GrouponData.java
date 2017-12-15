package com.seas.usuario.grouponandroidstudiov2.datos;

import com.seas.usuario.grouponandroidstudiov2.beans.Cliente;
import com.seas.usuario.grouponandroidstudiov2.beans.Local;

import java.util.ArrayList;


public class GrouponData {
	private static Cliente cliente;
	private static ArrayList<Local> lstLocales;
	private static Local localSeleccionado;
	
	public static ArrayList<Local> getLstLocales() {
		return lstLocales;
	}

	public static void setLstLocales(ArrayList<Local> lstLocales) {
		GrouponData.lstLocales = lstLocales;
	}

	public static Local getLocalSeleccionado() {
		return localSeleccionado;
	}

	public static void setLocalSeleccionado(Local localSeleccionado) {
		GrouponData.localSeleccionado = localSeleccionado;
	}

	public static Cliente getCliente() {
		return cliente;
	}

	public static void setCliente(Cliente cliente) {
		GrouponData.cliente = cliente;
	}
	
}
