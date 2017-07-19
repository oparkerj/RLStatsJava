package com.ssplugins.rlstats.entities;

/**
 * Represents a Rocket League platform.
 */
public enum Platform {
	
	STEAM(1, "Steam"),
	PS4(2, "PS4"),
	XBOX(3, "XboxOne");
	
	private int id;
	private String name;
	
	Platform(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Get the ID for this platform.
	 * @return Platform ID.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get the name of this platform.
	 * @return Platform name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get a {@link Platform} from it's ID.
	 * @param id ID of platform to get.
	 * @return Platform or null if not found.
	 */
	public static Platform fromId(int id) {
		switch (id) {
			case 1:
				return STEAM;
			case 2:
				return PS4;
			case 3:
				return XBOX;
		}
		return null;
	}
	
	/**
	 * Get a {@link Platform} by name.
	 * @param name Name of platform to get.
	 * @return Platform or null if not found.
	 */
	public static Platform fromName(String name) {
		switch (name.toLowerCase()) {
			case "steam":
			case "pc":
				return STEAM;
			case "ps4":
				return PS4;
			case "xbox":
			case "xboxone":
				return XBOX;
		}
		return null;
	}
	
}
