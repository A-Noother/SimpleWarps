package me.NerdsWBNerds.SimpleWarps;

import static org.bukkit.ChatColor.*;

import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class SWListener implements Listener{
	public SimpleWarps plugin;
	public SWListener(SimpleWarps p){
		plugin = p;
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e){
		Player player = e.getPlayer();
		String[] args = e.getMessage().split(" ");
		

		if(args[0].equalsIgnoreCase("/warp")){
			if(args.length==1){
				boolean first = true;
				e.setCancelled(true);
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
			}
			
			if(args.length==2){
				e.setCancelled(true);
				
				if(!SimpleWarps.warps.containsKey(args[1])){
					tell(player, RED + "[SimpleWarps] Warp not found.");
					return;
				}

				player.teleport(parseLocation(SimpleWarps.warps.get(args[1])));
				tell(player, GOLD + "[SimpleWarps] " + GREEN + "You have been teleported to the " + AQUA + args[1] + GREEN + " warp.");
			}
			
			if(args.length==3){
				e.setCancelled(true);
				
				if(!player.isOp()){
					tell(player, RED + "You don't have permission to do this!");
					return;
				}
				
				Player target = plugin.server.getPlayer(args[1]);
				if(target==null || !target.isOnline()){
					tell(player, RED + "[SimpleWarps] Player not found.");
					return;
				}
				
				if(!SimpleWarps.warps.containsKey(args[1])){
					tell(player, RED + "[SimpleWarps] Warp not found.");
					return;
				}
				
				target.teleport(parseLocation(SimpleWarps.warps.get(args[1])));
				tell(player, GOLD + "[SimpleWarps] " + GREEN + "You have teleported " + AQUA + target.getName() + GREEN + " to the " + AQUA + args[1] + GREEN + " warp.");
				tell(target, GOLD + "[SimpleWarps] " + GREEN + "You have been teleported to the " + AQUA + args[1] + GREEN + " warp.");
			}
		}
		
		if(!player.isOp()){
			tell(player, RED + "You don't have permission to do this!");
			return;
		}
		
		if((args[0].equalsIgnoreCase("/addwarp")  || args[0].equalsIgnoreCase("/setwarp")) && args.length==2){
			e.setCancelled(true);
			
			SimpleWarps.warps.put(args[1], intoString(player.getLocation()));
			tell(player, GOLD + "[SimpleWarps] " + GREEN + "The warp " + AQUA + args[1] + GREEN + " has been created.");
		}
		if((args[0].equalsIgnoreCase("/delwarp") || args[0].equalsIgnoreCase("/removewarp") ) && args.length==2){
			e.setCancelled(true);
			
			if(!SimpleWarps.warps.containsKey(args[1])){
				tell(player, RED + "[SimpleWarps] Warp not found.");
				return;
			}
			
			SimpleWarps.warps.remove(args[1]);
			tell(player, GOLD + "[SimpleWarps] " + GREEN + "The warp " + AQUA + args[1] + GREEN + " has been removed.");
		}
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
