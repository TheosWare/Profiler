package com.profiler.bases;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.profiler.main.Profiler;

public class Client {
	
	
	private UUID owner;
	public UUID getOwnerUUID() { return this.owner; }
	
	private List<Profile> profiles = new ArrayList<Profile>();
	public Profile getProfile(String name) { for(Profile p : profiles) { if(p.getName().equalsIgnoreCase(name)) { return p; } } return null; }
	public Profile getProfile(Plugin pl) { return this.getProfile(pl.getName()); }
	public List<Profile> getProfiles() { return this.profiles; }
	
	public Profile addProfile(String name) { Profile p = new Profile(name, this.owner); this.profiles.add(p); return p; }
	public Profile addProfile(Plugin pl) { return this.addProfile(pl.getName()); }
	public void addProfile(Profile p) {this.profiles.add(p);}
	
	
	public Client(Player p)
	{
		this(p.getUniqueId());
	}
	
	public Client(UUID owner)
	{
		this.owner = owner;
		if(!this.load())
		{
			this.create();
		}
	}
	
	private boolean load()
	{
		if(Profiler.getInstance().useMysqlStorage())
		{
			try {
				Statement statement = Profiler.getInstance().getMysqlManager().getConnection().createStatement();
				ResultSet res = statement.executeQuery("SELECT * FROM clients WHERE uuid = '" + this.owner.toString() + "';");
				if(res.next())
				{
					Gson g = new Gson();
					
					JsonElement jelem = g.fromJson(res.getString("data"), JsonElement.class);
					JsonObject jobj = jelem.getAsJsonObject();
					
				
					
					
					jobj.get("Profiles").getAsJsonObject().entrySet().forEach(entry -> {
						
						Profile p = new Profile(entry.getKey(), this.owner);
						JsonObject jobj2 = entry.getValue().getAsJsonObject();
						
						jobj2.entrySet().forEach(entry2 -> {
							
							
							
							p.setValue(entry2.getKey(), g.fromJson(entry2.getValue(), HashMap.class));
						});
						
						this.profiles.add(p);
					});
					return true;
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}else {
				File f = new File(Profiler.getInstance().getDataFolder(), "/Clients/" +this.owner.toString() + ".yml");
				if(!f.exists())
				{
					try {
						f.createNewFile();
					}catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
				if(f.exists())
				{
					FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
					
					for(String key : fc.getKeys(false))
					{
						Profile p = new Profile(key, this.owner);
						
						for(String key2 : fc.getConfigurationSection(key).getKeys(false))
						{
							Object value = fc.get(key + "." + key2);
							
							
							if(value instanceof MemorySection)
							{
								MemorySection ms = (MemorySection) value;
								p.setValue(key2, ms.getValues(true));
							}else {
								p.setValue(key2, value);
							}
							
							
						}
						this.profiles.add(p);
					}
					return true;
				}
			
		}
		
		return false;
	}
	
	private void create()
	{
		if(Profiler.getInstance().useMysqlStorage())
		{
			try {
				
				
				HashMap<Object, Object> data = new HashMap<Object, Object>();
				HashMap<Object, Object> pros = new HashMap<Object, Object>();

				for(Profile p : profiles)
				{
					pros.put(p.getName(), p.getProperties());
				}
				
				data.put("profiles", pros);		
				
				
				Gson g = new Gson();
				String json = g.toJson(data);
				
				PreparedStatement s = Profiler.getInstance().getMysqlManager().getConnection().prepareStatement("INSERT INTO clients (id, uuid, data) VALUES(NULL, ?, ?)");
				s.setString(1, this.owner.toString());
				s.setString(2, json);
				s.executeUpdate();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}else {
			this.save();
		}
	}
	
	public void save()
	{
		if(Profiler.getInstance().useMysqlStorage())
		{
			try {
				HashMap<Object, Object> data = new HashMap<Object, Object>();
				
				
				HashMap<Object, Object> pros = new HashMap<Object, Object>();

				for(Profile p : profiles)
				{
					pros.put(p.getName(), p.getProperties());
				}
				
				
				data.put("Profiles", pros);
				
				Gson g = new Gson();
				String json = g.toJson(data);
				
				PreparedStatement s = Profiler.getInstance().getMysqlManager().getConnection().prepareStatement("UPDATE clients SET data=? WHERE uuid=?");
				s.setString(1, json);
				s.setString(2, this.owner.toString());
				s.executeUpdate();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}else {

			File f = new File(Profiler.getInstance().getDataFolder(), "/Clients/" +this.owner.toString() + ".yml");
			if(!f.exists())
			{
				try {
					f.createNewFile();
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
			if(f.exists())
			{
				FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
				
				for(Profile p : profiles)
				{
					fc.set(p.getName(), p.getProperties());
				}
				
				try {
					fc.save(f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
