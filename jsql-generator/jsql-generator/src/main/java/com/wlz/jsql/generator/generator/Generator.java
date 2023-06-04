package com.wlz.jsql.generator.generator;

public class Generator {

	private boolean uppercase = true;
	public String convertStringCase(String str) {
		if(uppercase) {
			return str.toUpperCase();
		}else {
			return str.toLowerCase();
		}
	}
	
	public boolean isUppercase() {
		return uppercase;
	}

	public void setUppercase(boolean uppercase) {
		this.uppercase = uppercase;
	}
	
	public String generateCode() {
		return null;
	}
	
	public String getFileName() {
		return null;
	}
}
