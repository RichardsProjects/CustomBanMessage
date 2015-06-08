package net.richardsprojects.customban;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomBan extends JavaPlugin {

	public static Logger log;
	public FileConfiguration config;
	
	public static String bannedLine1 = "&4&lBanned:";
	public static String bannedLine2 = "&d&lReason: &b&l REASON";
	public static String bannedLine3 = "&d&lBan Length: &b&l BAN_TIME";
	public static String bannedLine4 = "&d&lAppeal Here: &b&l [Fill in Website Address]";
	
	@Override
	public void onEnable() {
		log = Logger.getLogger("Minecraft");
		loadConfig();
		
		this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
	}
	
	//TODO: Make it replace formatting codes with ChatColor counterpart when it sends them to client
	
	@Override
	public void onDisable() {
		
	}
	
	public void loadConfig() {
		this.reloadConfig();
		
		File file = new File(getDataFolder() + File.separator + "config.yml");
		if(!file.exists()) {
			config = this.getConfig();
			
			config.addDefault("BannedLine1", bannedLine1);
			config.addDefault("BannedLine2", bannedLine2);
			config.addDefault("BannedLine3", bannedLine3);
			config.addDefault("BannedLine4", bannedLine4);
			
			config.options().copyDefaults(true);
			saveConfig();
			
			log.info("[CustomBanMessage] Generated config.yml");
		} else {
			config = this.getConfig();
			
			bannedLine1 = config.getString("BannedLine1");
			bannedLine2 = config.getString("BannedLine2");
			bannedLine3 = config.getString("BannedLine3");
			bannedLine4 = config.getString("BannedLine4");
		}
	}


}
