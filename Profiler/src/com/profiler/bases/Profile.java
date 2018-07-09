package com.profiler.bases;

import java.util.HashMap;
import java.util.UUID;

public class Profile {
	
	private String name;
	public String getName() { return this.name; }
	
	private UUID owner;
	public UUID getOwner() { return this.owner; }
	
	private HashMap<String, Object> properties = new HashMap<String, Object>();
	public HashMap<String, Object> getProperties() { return this.properties; }
	public Object getValue(String key){ return this.properties.get(key); }
	public void setValue(String key, Object value) { this.properties.put(key, value); }
	
	public Profile(String name, UUID owner){ this.name = name; this.owner = owner; }
}
