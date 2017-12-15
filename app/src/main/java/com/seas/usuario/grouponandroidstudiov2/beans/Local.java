package com.seas.usuario.grouponandroidstudiov2.beans;

public class Local {
	
	private String localName, localLat, localLong;
	private int localImage;

	public String getLocalName() {
		return localName;
	}
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	public int getLocalImage() {
		return localImage;
	}
	public void setLocalImage(int localImage) {
		this.localImage = localImage;
	}
	public String getLocalLong(){
		return localLong;
	}
	public void setLocalLong(String localLong){
		this.localLong = localLong;
	}
	public String getLocalLat(){
		return localLat;
	}
	public void setLocalLat(String localLat){
		this.localLat = localLat;
	}
}
