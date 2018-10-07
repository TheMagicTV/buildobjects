package commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import datenbank.DatenbankDatentransfer;
import main.Main;


public class COMMAND_buildObject implements CommandExecutor{

	private String prefix;
	private Main plugin;
	private DatenbankDatentransfer datenbankDatentransfer;
	
	public COMMAND_buildObject(Main main, DatenbankDatentransfer datenbankDatentransfer) {
		this.plugin = main;
		this.datenbankDatentransfer = datenbankDatentransfer;
		
		this.prefix = plugin.getPrefix();
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(prefix + "§aDu musst ingame sein!");
			return true;
		}
		
		Player p = (Player) sender;
		
		if(p.isOp()) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("Save")) {
					String projektname = plugin.getWorkerOnProject().get(p);
					Location loc = p.getLocation();
					System.out.println(projektname);
					if(datenbankDatentransfer.updateAllLocationsOfBlocksInProject(projektname, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())) {
						p.sendMessage(prefix + "§3Alle Änderungen wurden gespeichert!");
						return true;
					}
				}
			} if(args.length == 2) {
				if(args[0].equalsIgnoreCase("Join")) {
					if(!plugin.getWorkerOnProject().containsKey(p)) {
						String projectName = args[1];
						plugin.addWorker(p, projectName);
						if(!datenbankDatentransfer.existProject(projectName)) {
							datenbankDatentransfer.addProject(projectName);
							p.sendMessage(prefix + "§3Das Project §6" + projectName + " §3 wurde erfolgreich erstellt!");
						} else {
							p.sendMessage(prefix + "§3Du arbeitest nun wieder an dem Projekt§6 " + projectName + " §3!");
						}
					} else {
						p.sendMessage(prefix + "§4Du bist noch in einem Projekt!!");
					}
				} else if(args[0].equalsIgnoreCase("Leave")) {
					if(plugin.getWorkerOnProject().containsKey(p)) {
						plugin.removeWorker(p);
						p.sendMessage(prefix + "§3Du hast erfolgreich das Projekt verlassen!");
					} else {
						p.sendMessage(prefix + "§4Du bist in keinem Projekt!");
					}
				}
			} else {
				p.sendMessage(prefix + "§4/buildobjects [Join/Leave] [Projektname]");
			}
		}
		
		return true;
	}

}
