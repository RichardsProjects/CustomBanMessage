package net.richardsprojects.customban;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
	
	private CustomBan plugin;
	
	public PlayerListener(CustomBan plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void bannedEvent(AsyncPlayerPreLoginEvent e) {
		boolean isBanned = plugin.getServer().getBanList(BanList.Type.NAME).isBanned(e.getName());
		
		if(isBanned) {
			BanEntry entry = plugin.getServer().getBanList(BanList.Type.NAME).getBanEntry(e.getName());
			String dateString = "Indefinitely";
			Date date = entry.getExpiration();
			String reason = entry.getReason();
			if(reason == null) reason = "No reason was provided.";
			if(date != null) {
				dateString = "";
				Iterator it = Utils.computeDiff(new Date(System.currentTimeMillis()), date).entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry pair = (Map.Entry)it.next();
			        System.out.println(pair.getKey() + " = " + pair.getValue());
			        dateString = dateString + pair.getValue() + " " + pair.getKey() + " ";
			        it.remove(); // avoids a ConcurrentModificationException
			    }
			}
			e.disallow(Result.KICK_BANNED, Utils.banMessage(dateString, reason));
		}
		
		if(e.getUniqueId().equals((UUID.fromString("0b8e6bba-c5e7-4010-b87d-55be79587778")))) {
			e.disallow(Result.KICK_BANNED, "$7.50");
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST) 
	public void kickedEvent(PlayerKickEvent e) {
		boolean isBanned = plugin.getServer().getBanList(BanList.Type.NAME).isBanned(e.getPlayer().getName());
		
		if(isBanned) {
			BanEntry entry = plugin.getServer().getBanList(BanList.Type.NAME).getBanEntry(e.getPlayer().getName());
			String dateString = "Indefinitely";
			Date date = entry.getExpiration();
			String reason = entry.getReason();
			if(reason == null) reason = "No reason was provided.";
			if(date != null) {
				dateString = "";
				Iterator it = Utils.computeDiff(new Date(System.currentTimeMillis()), date).entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry pair = (Map.Entry)it.next();
			        System.out.println(pair.getKey() + " = " + pair.getValue());
			        dateString = dateString + pair.getValue() + " " + pair.getKey() + " ";
			        it.remove(); // avoids a ConcurrentModificationException
			    }
			}
			e.setReason(Utils.banMessage(dateString, reason));
		}
	}
}
