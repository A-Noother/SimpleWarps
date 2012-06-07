package me.NerdsWBNerds.SimpleWarps.Commands;

import static org.bukkit.ChatColor.RED;
import me.NerdsWBNerds.SimpleWarps.SimpleWarps;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OtherCommand implements CommandExecutor{
	private SimpleWarps plugin;
	public OtherCommand(SimpleWarps s){
		plugin = s;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(sender instanceof Player){
			Player player = (Player) sender;		

			//////////////// -------------------------------------------- /////////////////
			//////////////// ------------ PLAYER COMMANDS --------------- /////////////////
			//////////////// -------------------------------------------- /////////////////
			
			if(cmd.getName().equalsIgnoreCase("sw")){
				if(args.length==2){
					if(args[0].equalsIgnoreCase("useop")){
						if(!SimpleWarps.hasPerm(player, "simplewarps.changeuseop")){
							player.sendMessage(RED + "[SimpleWarps] You don't have permission to do this!");
							return true;
						}				
						
						if((args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("true")) && SimpleWarps.useOP == false){
							SimpleWarps.useOP = true;
							plugin.saveConf();
							
							player.sendMessage(ChatColor.GOLD + "[SimpleWarps] " + ChatColor.GREEN + "Now using OP permissions.");
							return true;
						}
						
						if((args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("false")) && SimpleWarps.useOP == true){
							SimpleWarps.useOP = false;
							plugin.saveConf();
							
							player.sendMessage(ChatColor.GOLD + "[SimpleWarps] " + ChatColor.GREEN + "Now using permissions, not OP.");
							return true;
						}
					}
				}
				
				if(args.length==1){
					if(args[0].equalsIgnoreCase("reload")){
						if(!SimpleWarps.hasPerm(player, "simplewarps.reload")){
							player.sendMessage(RED + "[SimpleWarps] You don't have permission to do this!");
							return true;
						}		
						
						plugin.loadConf();

						player.sendMessage(ChatColor.GOLD + "[SimpleWarps] " + ChatColor.GREEN + "Plugin reloaded.");
						return true;
					}
				}
				
				return true;
			}
		}else{

			//////////////// -------------------------------------------- /////////////////
			//////////////// ------------ CONSOLE COMMANDS -------------- /////////////////
			//////////////// -------------------------------------------- /////////////////
			
			if(cmd.getName().equalsIgnoreCase("sw")){				
				if(args.length==2){
					if(args[0].equalsIgnoreCase("useop")){
						if((args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("true")) && SimpleWarps.useOP == false){
							SimpleWarps.useOP = true;
							plugin.saveConf();
							
							System.out.println("[SimpleWarps] Now using OP permissions.");
							return true;
						}
						
						if((args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("false")) && SimpleWarps.useOP == true){
							SimpleWarps.useOP = false;
							plugin.saveConf();
							
							System.out.println("[SimpleWarps] Now using permissions, not OP.");
							return true;
						}
					}
				}
			
				if(args.length==1){
					if(args[0].equalsIgnoreCase("reload")){
						plugin.loadConf();

						System.out.println("[SimpleWarps] Plugin reloaded.");
						return true;
					}
				}

				return true;
			}
		}
		
		return false;
	}
}
