package com.profiler.main;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.profiler.managers.ClientManager;
import com.profiler.managers.ConfigManager;
import com.profiler.managers.EventManager;
import com.profiler.managers.MysqlManager;

public class Profiler extends JavaPlugin{
	
	private static Profiler instance;
	public static Profiler getInstance() { return instance; }
	
	private ConfigManager configManager;
	public ConfigManager getConfigManager() { return this.configManager; }
	
	private MysqlManager mysqlManager;
	public MysqlManager getMysqlManager() { return this.mysqlManager; }
	
	private ClientManager clientManager;
	public ClientManager getClientManager() { return this.clientManager; }
	
	public boolean useMysqlStorage()
	{
		return (boolean) this.configManager.getStorage().getValue("Mysql");
	}
	
	public boolean useLocalStorage()
	{
		return (boolean) this.configManager.getStorage().getValue("Local");

	}
	
	@Override
	public void onEnable()
	{
		Profiler.instance = this;
		
		
		
		this.configManager = new ConfigManager();
		
		
		if(this.useMysqlStorage())
		{
			this.mysqlManager = new MysqlManager(this.configManager.getMysql().getValue("Username").toString(), this.configManager.getMysql().getValue("Password").toString(), this.configManager.getMysql().getValue("Host").toString(), this.configManager.getMysql().getValue("Port").toString(), this.configManager.getMysql().getValue("Database").toString());
		}
		
		this.clientManager = new ClientManager();
		
		this.getServer().getPluginManager().registerEvents(new EventManager(), this);

		for(Player p : this.getServer().getOnlinePlayers())
		{
			this.getClientManager().addClient(p);
		}
		
	}
	
	@Override
	public void onDisable()
	{}

}
