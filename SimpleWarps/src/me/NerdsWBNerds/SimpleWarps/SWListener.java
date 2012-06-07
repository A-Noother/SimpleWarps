package me.NerdsWBNerds.SimpleWarps;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SWListener implements Listener {
	@SuppressWarnings("unused")
	private SimpleWarps plugin;
	public SWListener(SimpleWarps s){
		plugin = s;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN){
				Sign sign = (Sign) e.getClickedBlock().getState();
				
				if(ChatColor.stripColor(sign.getLine(0)).equalsIgnoreCase("[WARP]")){
					String warp = ChatColor.stripColor(sign.getLine(1));
					
					if(!SimpleWarps.isWarp(warp)){
						sign.setLine(0, ChatColor.DARK_RED + "!ERROR!");
						sign.setLine(1, "warp doesn't");
						sign.setLine(2, "exist.");
						sign.setLine(3, "");
						return;
					}
					
					if(!e.getPlayer().hasPermission("simplewarps.usesign") && !SimpleWarps.useOP){
						e.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to do this.");
						return;
					}


					if(!e.getPlayer().hasPermission("simplewarps.warps." + warp) && !SimpleWarps.useOP){
						e.getPlayer().sendMessage(RED + "[SimpleWarps] You don't have permission to do this.");
						return;
					}
					
					e.getPlayer().teleport(SimpleWarps.getWarp(warp));
					e.getPlayer().sendMessage(GOLD + "[SimpleWarps] " + GREEN + "You have been teleported to the " + AQUA + warp + GREEN + " warp.");
				}
			}
		}
	}
	
	@EventHandler
	public void onSignCreate(SignChangeEvent e){
		Player player = e.getPlayer();
		
		if(e.getLine(0).equalsIgnoreCase("[WARP]")){
			if(!SimpleWarps.hasPerm(player, "simplewarps.createsign")){
				e.setLine(0, ChatColor.DARK_RED + "!ERROR!");
				e.setLine(1, "don't have");
				e.setLine(2, "permission.");
				e.setLine(3, "");
				
				return;
			}

			if(!SimpleWarps.isWarp(e.getLine(1))){
				e.setLine(0, ChatColor.DARK_RED + "!ERROR!");
				e.setLine(1, "warp doesn't");
				e.setLine(2, "exist.");
				e.setLine(3, "");
			}else{
				e.setLine(0, ChatColor.WHITE + "[WARP]");
				e.setLine(1, ChatColor.BOLD + e.getLine(1));
				e.setLine(2, player.getName());
				e.setLine(3, ChatColor.DARK_GRAY + e.getLine(3));
			}
		}
	}
}
