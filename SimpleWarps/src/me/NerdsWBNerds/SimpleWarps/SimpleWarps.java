package me.NerdsWBNerds.SimpleWarps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleWarps extends JavaPlugin {
	public SWListener Listener = new SWListener(this);
	public Server server;
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
	    }
	}
	
	public void onDisable(){
		save();
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
