package com.ssplugins.rlstats.util;

public abstract class NameIDPair {
	
	private String name;
	private int id;
	
	public NameIDPair(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	/**
	 * Get the name of this item.
	 * @return Name of the current item.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the ID of this item.
	 * @return ID of the current item.
	 */
	public int getId() {
		return id;
	}
	
}
