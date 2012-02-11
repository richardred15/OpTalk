package me.richardred15.adminchat;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Channel {
	public Map<String, Player> channelList = new HashMap<String, Player>();
	public Map<String, Player> channelPlayers = new HashMap<String, Player>();
	
	public void createChannel(String name, Player player){
			channelList.put(name, player);
			player.sendMessage("Channel " + ChatColor.GREEN + name + " created!");
	}
}
