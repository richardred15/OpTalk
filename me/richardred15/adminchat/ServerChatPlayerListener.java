package me.richardred15.adminchat;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ServerChatPlayerListener implements Listener {
	public static Loader plugin;
	Logger log = Logger.getLogger("Minecraft");
	
	public ServerChatPlayerListener(Loader instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent chat) {
		Player sender = chat.getPlayer();
		if(plugin.userAttached.containsKey(chat.getPlayer())){
			String to = plugin.userAttached.get(sender);
			String msg = "/fsay " + to + " " + chat.getMessage();
			sender.chat(msg);
			chat.setCancelled(true);
		}
		else {
		if(plugin.overRide == 1){
			plugin.overRide = 0;
		}
		else{
		Player p = chat.getPlayer();
		String message = chat.getMessage();
		if(plugin.pluginEnabled.containsKey(p)){
			Player[] players = Bukkit.getOnlinePlayers();
			log.info("[" + p.getName() + "]" + message);
			for(Player op: players){
				if(op.hasPermission("OpTalk.chat")) {
					op.sendMessage(plugin.AQUA + "[" + p.getDisplayName() + plugin.AQUA + "] " + plugin.GREEN + message);
				}
			}
		    chat.setCancelled(true);
		}
		}
	}
	}
}