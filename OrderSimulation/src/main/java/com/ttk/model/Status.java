package com.ttk.model;

public enum Status {
	IN_STOCK("IN_STOCK"), OUT_OF_STOCK("OUT_OF_STOCK");

	private String code;

	Status(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static Status fromCode(String code) {
		for (Status status : Status.values()) {
			if (status.getCode().equals(code)) {
				return status;
			}
		}
		throw new UnsupportedOperationException("The code " + code + " is not supported!");
	}
}
