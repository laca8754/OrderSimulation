package com.ttk.model;

public enum ResponseStatus {
	OK, ERROR;

	public String toString() {
		switch (this) {
		case OK:
			return "OK";
		case ERROR:
			return "ERROR";
		}
		return null;
	}

}