package me.NerdsWBNerds.SimpleWarps.Commands;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;
import me.NerdsWBNerds.SimpleWarps.SimpleWarps;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelWarpCommand implements CommandExecutor{
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

				if(!SimpleWarps.warps.containsKey(args[0])){
					tell(player, RED + "[SimpleWarps] Warp not found.");
					return true;
				}
				
				SimpleWarps.warps.remove(args[0]);
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

				if(!SimpleWarps.warps.containsKey(args[0])){
					System.out.println("[SimpleWarps] Warp not found.");
					return true;
				}
				
				SimpleWarps.warps.remove(args[0]);
				System.out.println("[SimpleWarps] The warp " + args[0] + " has been removed.");
			}
		}
		
		return false;
	}
	
	public void tell(Player player, String m){
		player.sendMessage(m);
	}
	
	public Location parseLocation(String loc){
		String i[] = loc.split(",");
		Location to = new Location(plugin.server.getWorld(i[0]), toDouble(i[1]), toDouble(i[2]), toDouble(i[3]), toFloat(i[4]), toFloat(i[5]));
		
		return to;
	}
	
	public String intoString(Location l){
		return l.getWorld().getName() + "," + l.getX() + ","  + l.getY() + ","  + l.getZ() + ","  + l.getYaw() + ","  + l.getPitch();
	}
	
	public double toDouble(String i){
		return Double.parseDouble(i);
	}
	
	public Float toFloat(String i){
		return Float.parseFloat(i);
	}
}
