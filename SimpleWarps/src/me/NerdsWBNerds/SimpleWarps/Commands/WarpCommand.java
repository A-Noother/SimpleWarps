package me.NerdsWBNerds.SimpleWarps.Commands;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;

import java.util.Map.Entry;

import org.bukkit.Location;
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
					boolean first = true;
					tell(player, GOLD + "----- WARP LIST -----");
					String list = "";
					for (Entry<String, String> warp : SimpleWarps.warps.entrySet()) {
						if(first){
							list+= warp.getKey();
							first = false;
						}else{
							list+=", " + warp.getKey();
						}
					}
					
					tell(player, GREEN + list);
					return true;
				}
				
				if(args.length==1){
					if(!SimpleWarps.warps.containsKey(args[0])){
						tell(player, RED + "[SimpleWarps] Warp not found.");
						return true;
					}

					player.teleport(parseLocation(SimpleWarps.warps.get(args[0])));
					tell(player, GOLD + "[SimpleWarps] " + GREEN + "You have been teleported to the " + AQUA + args[0] + GREEN + " warp.");
					return true;
				}
				
				if(args.length==2){
					if(!player.isOp()){
						tell(player, RED + "[SimpleWarps] You don't have permission to do this!");
						return true;
					}
					
					Player target = plugin.server.getPlayer(args[0]);
					if(target==null || !target.isOnline()){
						tell(player, RED + "[SimpleWarps] Player not found.");
						return true;
					}
					
					if(!SimpleWarps.warps.containsKey(args[1])){
						tell(player, RED + "[SimpleWarps] Warp not found.");
						return true;
					}
					
					target.teleport(parseLocation(SimpleWarps.warps.get(args[1])));
					tell(player, GOLD + "[SimpleWarps] " + GREEN + "You have teleported " + AQUA + target.getName() + GREEN + " to the " + AQUA + args[1] + GREEN + " warp.");
					tell(target, GOLD + "[SimpleWarps] " + GREEN + "You have been teleported to the " + AQUA + args[1] + GREEN + " warp.");
				}
			}
		}else{
			//////////////// -------------------------------------------- /////////////////
			//////////////// ------------ CONSOLE COMMANDS -------------- /////////////////
			//////////////// -------------------------------------------- /////////////////

			if(cmd.getName().equalsIgnoreCase("warp")){
				if(args.length==2){
					Player target = plugin.server.getPlayer(args[0]);
					if(target==null || !target.isOnline()){
						System.out.println("[SimpleWarps] Player not found.");
						return true;
					}
					
					if(!SimpleWarps.warps.containsKey(args[1])){
						System.out.println("[SimpleWarps] Warp not found.");
						return true;
					}
					
					target.teleport(parseLocation(SimpleWarps.warps.get(args[1])));
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
