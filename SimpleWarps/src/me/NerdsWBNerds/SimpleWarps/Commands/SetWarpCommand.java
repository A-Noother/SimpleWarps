package me.NerdsWBNerds.SimpleWarps.Commands;

import static org.bukkit.ChatColor.*;
import static me.NerdsWBNerds.SimpleWarps.SimpleWarps.*;
import me.NerdsWBNerds.SimpleWarps.SimpleWarps;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor{
	@SuppressWarnings("unused")
	private SimpleWarps plugin;
	public SetWarpCommand(SimpleWarps s){
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
			
			if(cmd.getName().equalsIgnoreCase("setwarp") || cmd.getName().equalsIgnoreCase("addwarp")){
				addWarp(args[0], player.getLocation());
				tell(player, GOLD + "[SimpleWarps] " + GREEN + "The warp " + AQUA + args[0] + GREEN + " has been created.");
				return true;
			}
		}
		
		return false;
	}
	
	public void tell(Player player, String m){
		player.sendMessage(m);
	}
}
