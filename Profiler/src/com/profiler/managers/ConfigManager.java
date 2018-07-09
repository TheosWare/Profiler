package com.profiler.managers;

import java.io.File;

import com.profiler.bases.Config;
import com.profiler.main.Profiler;



public class ConfigManager {
		
	private Config mysqlConfig;
	public Config getMysql() { return this.mysqlConfig; }
	
	
	public ConfigManager()
	{
		File temp = new File(Profiler.getInstance().getDataFolder(), "/Clients/");
		if(!temp.exists())
		{
			temp.mkdirs();
		}
		
		this.mysqlConfig = new Config("Mysql") {

			@Override
			public void defaultValues() {
				this.setDefault("Enabled", false);
				this.setDefault("Username", "root");
				this.setDefault("Password", "");
				this.setDefault("Host", "localhost");
				this.setDefault("Port", "3306");
				this.setDefault("Database", "Profiler");
			}
			
		};
		
		if(!mysqlConfig.exists())
		{
			mysqlConfig.create();
		}
		
		
		
		
	}

}
