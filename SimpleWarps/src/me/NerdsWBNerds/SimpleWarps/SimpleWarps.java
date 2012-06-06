package me.NerdsWBNerds.SimpleWarps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import me.NerdsWBNerds.SimpleWarps.Commands.DelWarpCommand;
import me.NerdsWBNerds.SimpleWarps.Commands.SetWarpCommand;
import me.NerdsWBNerds.SimpleWarps.Commands.WarpCommand;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleWarps extends JavaPlugin {
	public SWListener Listener = new SWListener(this);
	public static Server server;
	public Logger log;

	public static String Path = "plugins/SimpleWarps" + File.separator + "Warps.dat";
	public static HashMap<String, String> warps = new HashMap<String, String>();
	
	public void onEnable(){
		server = this.getServer();
		log = this.getLogger();

		server.getPluginManager().registerEvents(Listener, this);
		
		File file = new File(Path);
		new File("plugins/SimpleWarps").mkdir();
	    
		if(file.exists()){
			warps = load();
			
			if(warps==null || warps.isEmpty()){
				warps = new HashMap<String, String>();
			}
	    }

		this.getCommand("warp").setExecutor(new WarpCommand(this));
		this.getCommand("setwarp").setExecutor(new SetWarpCommand(this));
		this.getCommand("addwarp").setExecutor(new SetWarpCommand(this));
		this.getCommand("delwarp").setExecutor(new DelWarpCommand(this));
		this.getCommand("removewarp").setExecutor(new DelWarpCommand(this));
	}
	
	public void onDisable(){
		save();
	}
	
	public static boolean isWarp(String warp){
		for(Entry<String, String> w: warps.entrySet()){
			if(w.getKey().toLowerCase().equalsIgnoreCase(warp))
				return true;
		}
		
		return false;
	}
	
	public static void addWarp(String w, Location to){
		warps.put(w, intoString(to));
	}
	
	public static void removeWarp(String w){
		warps.remove(w);
	}
	
	public static Location getWarp(String wa){
		for(Entry<String, String> w: warps.entrySet()){
			if(w.getKey().toLowerCase().equalsIgnoreCase(wa))
				return parseLocation(w.getValue());
		}
		
		return null;
	}
	
	public static Location parseLocation(String loc){
		String i[] = loc.split(",");
		Location to = new Location(server.getWorld(i[0]), toDouble(i[1]), toDouble(i[2]), toDouble(i[3]), toFloat(i[4]), toFloat(i[5]));
		
		return to;
	}
	
	public static String intoString(Location l){
		return l.getWorld().getName() + "," + l.getX() + ","  + l.getY() + ","  + l.getZ() + ","  + l.getYaw() + ","  + l.getPitch();
	}
	
	public static double toDouble(String i){
		return Double.parseDouble(i);
	}
	
	public static Float toFloat(String i){
		return Float.parseFloat(i);
	}
	
	public static void save(){
		File file = new File(Path);
		new File("plugins/SimpleWarps").mkdir();
	    if(!file.exists()){
	    	try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
		try{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Path));
			oos.writeObject(warps);
			oos.flush();
			oos.close();
			//Handle I/O exceptions
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> load(){
		try{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Path));
			Object result = ois.readObject();
			if(result != null)
				return (HashMap<String,String>)result;
			else
				return new HashMap<String, String>();
		}catch(Exception e){
			return new HashMap<String, String>();
		}
	}
}
