package me.iamajiu.OddCurrency;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	public OddConfig ocfg; 
	
	@Override 
	public void onEnable() {
		getLogger().info("Loading OddCurrency data files...");
		
		if (!getDataFolder().exists()) {
	    	   getLogger().info("Files not found. Creating new configuration files...");
	    	   getDataFolder().mkdirs(); 
	    }
		
		Bukkit.getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new OddEvents(), this);
		
		this.ocfg = new OddConfig(this); 
		
		this.getCommand("oc").setExecutor(new OddCommands());
	}
	
	@Override 
	public void onDisable() {
		getLogger().info("Saving files...");
		ocfg.save(); 
	}
}
