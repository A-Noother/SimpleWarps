package me.NerdsWBNerds.SimpleWarps.Commands;

import static org.bukkit.ChatColor.*;
import static me.NerdsWBNerds.SimpleWarps.SimpleWarps.*;

import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.NerdsWBNerds.SimpleWarps.SimpleWarps;

public class WarpCommand implements CommandExecutor{
	private SimpleWarps plugin;
	public WarpCommand(SimpleWarps s){
		plugin = s;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(sender instanceof Player){
			Player player = (Player) sender;		

			//////////////// -------------------------------------------- /////////////////
			//////////////// ------------ PLAYER COMMANDS --------------- /////////////////
			//////////////// -------------------------------------------- /////////////////
			
			if(cmd.getName().equalsIgnoreCase("warp")){
				if(args.length==0){
					tell(player, GOLD + "----- WARP LIST -----");
					String list = "";
					for (Entry<String, String> warp : SimpleWarps.warps.entrySet()) {
							list+= " *" + warp.getKey();
					}
					
					tell(player, GREEN + list);
					return true;
				}
				
				if(args.length==1){
					if(!isWarp(args[0])){
						tell(player, RED + "[SimpleWarps] Warp not found.");
						return true;
					}

					player.teleport(getWarp(args[0]));
					tell(player, GOLD + "[SimpleWarps] " + GREEN + "You have been teleported to the " + AQUA + args[0] + GREEN + " warp.");
					return true;
				}
				
				if(args.length==2){
					if(!player.isOp()){
						tell(player, RED + "[SimpleWarps] You don't have permission to do this!");
						return true;
					}
					
					Player target = plugin.getServer().getPlayer(args[0]);
					if(target==null || !target.isOnline()){
						tell(player, RED + "[SimpleWarps] Player not found.");
						return true;
					}
					
					if(!isWarp(args[1])){
						tell(player, RED + "[SimpleWarps] Warp not found.");
						return true;
					}
					
					target.teleport(getWarp(args[1]));
					tell(player, GOLD + "[SimpleWarps] " + AQUA + target.getName() + GREEN + " teleported to " + AQUA + args[1] + GREEN + " warp.");
					tell(target, GOLD + "[SimpleWarps] " + GREEN + "You have been teleported to the " + AQUA + args[1] + GREEN + " warp.");
					
					return true;
				}
			}
		}else{
			//////////////// -------------------------------------------- /////////////////
			//////////////// ------------ CONSOLE COMMANDS -------------- /////////////////
			//////////////// -------------------------------------------- /////////////////

			if(cmd.getName().equalsIgnoreCase("warp")){
				if(args.length==2){
					Player target = SimpleWarps.server.getPlayer(args[0]);
					if(target==null || !target.isOnline()){
						System.out.println("[SimpleWarps] Player not found.");
						return true;
					}
					
					if(!isWarp(args[1])){
						System.out.println("[SimpleWarps] Warp not found.");
						return true;
					}
					
					target.teleport(getWarp(args[1]));
					System.out.println("[SimpleWarps] You have teleported " + target.getName() + " to the " + args[1] + " warp.");
					tell(target, GOLD + "[SimpleWarps] " + GREEN + "You have been teleported to the " + AQUA + args[1] + GREEN + " warp.");
				}
			}
		}
		
		return false;
	}

	public void tell(Player player, String m){
		player.sendMessage(m);
	}
}
