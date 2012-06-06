package me.NerdsWBNerds.SimpleWarps.Commands;

import static org.bukkit.ChatColor.*;
import static me.NerdsWBNerds.SimpleWarps.SimpleWarps.*;
import me.NerdsWBNerds.SimpleWarps.SimpleWarps;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelWarpCommand implements CommandExecutor{
	@SuppressWarnings("unused")
	private SimpleWarps plugin;
	public DelWarpCommand(SimpleWarps s){
		plugin = s;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(sender instanceof Player){
			Player player = (Player) sender;		

			//////////////// -------------------------------------------- /////////////////
			//////////////// ------------ PLAYER COMMANDS --------------- /////////////////
			//////////////// -------------------------------------------- /////////////////
			
			if(!player.isOp()){
				tell(player, RED + "[SimpleWarps] You don't have permission to do this!");
				return true;
			}
			
			if(cmd.getName().equalsIgnoreCase("delwarp") || cmd.getName().equalsIgnoreCase("removewarp")){
				if(args.length!=1){
					return false;
				}

				if(!isWarp(args[0])){
					tell(player, RED + "[SimpleWarps] Warp not found.");
					return true;
				}
				
				removeWarp(args[0]);
				tell(player, GOLD + "[SimpleWarps] " + GREEN + "The warp " + AQUA + args[0] + GREEN + " has been removed.");
			}
		}else{
			//////////////// -------------------------------------------- /////////////////
			//////////////// ------------ CONSOLE COMMANDS -------------- /////////////////
			//////////////// -------------------------------------------- /////////////////

			if(cmd.getName().equalsIgnoreCase("delwarp") || cmd.getName().equalsIgnoreCase("removewarp")){
				if(args.length!=1){
					return false;
				}

				if(!isWarp(args[0])){
					System.out.println("[SimpleWarps] Warp not found.");
					return true;
				}
				
				removeWarp(args[0]);
				System.out.println("[SimpleWarps] The warp " + args[0] + " has been removed.");
			}
		}
		
		return false;
	}
	
	public void tell(Player player, String m){
		player.sendMessage(m);
	}
}
