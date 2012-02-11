package me.richardred15.adminchat;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Loader extends JavaPlugin {
	public static Loader plugin;
	Channel channel = new Channel();
	Logger log = Logger.getLogger("Minecraft");
	ChatColor WHITE = ChatColor.WHITE;
	ChatColor RED = ChatColor.DARK_RED;
	ChatColor AQUA = ChatColor.AQUA;
	ChatColor BLUE = ChatColor.BLUE;
	ChatColor PURPLE = ChatColor.DARK_PURPLE;
	String fnlOut = "";
	public Map<Player, Channel> currentChannel = new HashMap<Player, Channel>();
	public Map<Player, Boolean> pluginEnabled = new HashMap<Player, Boolean>();
	public Map<Player, String> userAttached = new HashMap<Player, String>();
	public Map<Player, Boolean> smChat = new HashMap<Player, Boolean>();
	public final ServerChatPlayerListener playerListener = new ServerChatPlayerListener(this);
	public final PlayerLeave leaveListener = new PlayerLeave(this);
	int overRide = 0;
	String fnlMsg0 = "";
	Player pmt;
	String name = null;
	
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(playerListener, this);
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is now enabled.");
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " is now disabled.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		String[] testArray = args;
		Player[] players = Bukkit.getOnlinePlayers();
		for(String str: args){
			fnlOut += " " + str;
		}
		
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		
		if(cmd.getName().equalsIgnoreCase("ch")){
			if(args[0] == "create"){
				String name = args[1];
				if(player != null)
					channel.createChannel(name, player);
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("sm")){
			if(testArray == null){
				if(player.hasPermission("OpTalk.chat") || player.hasPermission("*") || player.hasPermission("OpTalk.*") || player.isOp()){
					if(pluginEnabled.containsKey((Player) sender)) {
							togglePluginState((Player) sender);
					}
				}
					toggleSmState((Player) sender);
			}
			else {
				Player enzo = Bukkit.getPlayer("awyeaitsme");
				Player rich = Bukkit.getPlayer("richardred15");
				if(fnlMsg0 != null)
					fnlOut = fnlOut.replace(fnlMsg0, "");
				if(name != null)
					fnlOut = fnlOut.replace(name, "");
				if(fnlOut.contains("  "))
					fnlOut = fnlOut.replace("  ", "");
				for(Player plr: players){
					if(plr == enzo){
						if(enzo != null && rich != null){
							if((Player) sender == rich){
							Bukkit.getPlayer("awyeaitsme").sendMessage(PURPLE + "[Richard :(] " + fnlOut);
							}
							if((Player) sender == enzo){
								Bukkit.getPlayer("awyeaitsme").sendMessage(PURPLE + "[Enzo!] " + fnlOut);
							}
						}
						fnlOut = "";
						return true;
					}
					if(plr == rich){
						if(enzo != null && rich != null){
							if((Player) sender == enzo){
								Bukkit.getPlayer("richardred15").sendMessage(PURPLE + "[Enzo!] " + fnlOut);
							}
							if((Player) sender == rich){
							Bukkit.getPlayer("richardred15").sendMessage(PURPLE + "[Richard :(] " + fnlOut);
							}
							}
							fnlOut = "";
							return true;						
						}
					}
					fnlOut = "";
					return true;
				}
			}
		
		if(cmd.getName().equalsIgnoreCase("ac")){
			if(fnlMsg0 != null)
				fnlOut = fnlOut.replace(fnlMsg0, "");
			if(name != null)
				fnlOut = fnlOut.replace(name, "");
			if(fnlOut.contains("  "))
				fnlOut = fnlOut.replace("  ", "");
			if(player == null){
				sender.sendMessage("[Console]" + fnlOut);
				for(Player op1: players){
					if(op1.hasPermission("OpTalk.chat") || op1.hasPermission("*") || op1.hasPermission("OpTalk.*") || op1.isOp()) {
						op1.sendMessage(BLUE + "[Console]" + fnlOut);
					}
				}
				fnlOut = "";
				return true;
			}
			else {
				if(args.length == 0){
					if(player.hasPermission("OpTalk.chat") || player.hasPermission("*") || player.hasPermission("OpTalk.*") || player.isOp())
						togglePluginState(player);
				}
				else {
				    if(pluginEnabled.containsKey(player)){
				        if(pluginEnabled.get(player)){
				        	overRide = 1;
				        	player.chat(fnlOut.replaceFirst(" ", ""));
				        	fnlOut = "";
							return true;
					}
				    }
					if(overRide == 0){
						log.info("[" + player.getName() + "]" + fnlOut);
						for(Player op: players){
							if(op.hasPermission("OpTalk.chat") || op.hasPermission("*") || op.hasPermission("OpTalk.*") || op.isOp()) {
								op.sendMessage(AQUA + "[" + AQUA + player.getDisplayName() + AQUA + "]" + fnlOut);
							}
						}
					}
				}
				fnlOut = "";
				return true;
				}
			}
		
		if(cmd.getName().equalsIgnoreCase("fsay")){
		if(sender.hasPermission("OpTalk.fsay") || sender.hasPermission("*") || sender.hasPermission("OpTalk.*") || sender.isOp()) {
			if(args[0] == null || args[1] == null){
				sender.sendMessage("Correct usage is: /fsay <player> <message>");
				fnlOut = "";
			}
			else {
				pmt = Bukkit.getPlayer(args[0]);
				if(pmt != null){
					name = pmt.getName();
					fnlMsg0 = fnlOut.replaceFirst(args[0], "").replaceFirst("  ", "");
					pmt.chat(fnlMsg0);
					fnlOut = "";
				}
				else {
					sender.sendMessage("Invalid Username or Player Offline");
					fnlOut = "";
				}
			}
			return true;
		}
			return false;
		}
		if(cmd.getName().equalsIgnoreCase("fattach")){
			Player plyer = null;
			if(sender instanceof Player) {
				plyer = (Player) sender;
			}
			if(plyer != null) {
				if(sender.hasPermission("OpTalk.fattach") || sender.hasPermission("*") || sender.hasPermission("OpTalk.*") || sender.isOp()){
					if(userAttached.containsKey(plyer)) {
						String remvl = userAttached.get(plyer);
						plyer.sendMessage("You dettached from player " + remvl);
						userAttached.remove(plyer);
					}
					else {
						userAttached.put(plyer, args[0]);
						plyer.sendMessage("You attached to player " + args[0]);
						fnlOut = "";
					}
				}
				else {
					sender.sendMessage("You have no permission for this.");
				}
			}
			else {
				sender.sendMessage("You may not use fattach!");
		}
			return true;
	}
		
	if(cmd.getName().equalsIgnoreCase("fall")) {
		for(Player plyer: players) {
			plyer.chat(fnlOut);
		}
		fnlOut = "";
		return true;
	}
			return false;
}
	
	
	public void togglePluginState(Player player){
	    if(pluginEnabled.containsKey(player)){
	        if(pluginEnabled.get(player)){
	            pluginEnabled.put(player, false);
	            pluginEnabled.remove(player);
	            player.sendMessage(RED + "OpTalk Disabled");
	        } 
	        
	        else {
	            pluginEnabled.put(player, true);
	            player.sendMessage(AQUA + "OpTalk Enabled");
	        }
	    } 
	    
	    else {
	        pluginEnabled.put(player, true);
	        player.sendMessage(AQUA + "OpTalk Enabled");
	    }		
	}
	
	public void toggleSmState(Player player){
	    if(smChat.containsKey(player)){
	        if(smChat.get(player)){
	        	smChat.put(player, false);
	        	smChat.remove(player);
	            player.sendMessage(RED + "SmChat Disabled");
	        } 
	        
	        else {
	        	smChat.put(player, true);
	            player.sendMessage(AQUA + "SmChat Enabled");
	        }
	    } 
	    
	    else {
	    	smChat.put(player, true);
	        player.sendMessage(AQUA + "SmChat Enabled");
	    }		
	}
}
	