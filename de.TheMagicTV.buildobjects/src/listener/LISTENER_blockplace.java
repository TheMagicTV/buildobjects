package listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import datenbank.DatenbankDatentransfer;
import main.Main;

public class LISTENER_blockplace implements Listener{
	
	private Main plugin;
	private DatenbankDatentransfer datenbankDatentransfer;
	private String prefix;
	HashMap<Player,String> workerOnProject;
	
	
	public LISTENER_blockplace(Main main, DatenbankDatentransfer datenbankDatentransfer) {
		this.plugin = main;
		this.datenbankDatentransfer = datenbankDatentransfer;
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
		this.prefix = plugin.getPrefix();
		this.workerOnProject = plugin.getWorkerOnProject();
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		
		//System.out.println("BLOCK PLACE EVENT!");
		
		for(Map.Entry<Player, String> entry : workerOnProject.entrySet()) {
			if(p.getName().equals(entry.getKey().getName())) {
				System.out.println(prefix + "§3Der Block: " + e.getBlockPlaced() + " wurde gespeichert!");
				
				
				Location blockLoc = e.getBlock().getLocation();
				String blockName = e.getBlockPlaced().getType().name();
				String weltname = blockLoc.getWorld().getName();
				int x = blockLoc.getBlockX();
				int y = blockLoc.getBlockY();
				int z = blockLoc.getBlockZ();
				String building = entry.getValue();
				if(datenbankDatentransfer.existBlockInProject(weltname, x, y, z, building)) {
					datenbankDatentransfer.changeBlock(blockName, weltname, x, y, z, building);
					p.sendMessage(prefix + "BLOCK wurde getauscht!");
				} else {
					datenbankDatentransfer.addBlock(blockName, weltname, x, y, z, building);
					p.sendMessage(prefix + "BLock wurde hinzugefügt!");
				}
			}
		}
	}
	
	
	
}
