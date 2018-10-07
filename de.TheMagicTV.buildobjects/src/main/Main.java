package main;

/*
 * Der Spieler geht in ein Projekt
 * Der Spieler setzt blöcke
 * Nachdem der Spieler alles gebaut hat stellt er sich an einen Punkt und schreibt /buildproject save
 * Dann werden alle Blöcke, die gesetzt wurden, relativ zu der Spieler-Location gespeichert
 * Später kann dann der Spieler /buildproject paste [projektname] machen
 */



import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import commands.COMMAND_buildObject;
import datenbank.DatenbankDatentransfer;
import datenbank.DatenbankInfos;
import datenbank.DatenbankVerbindung;
import listener.LISTENER_blockplace;

public class Main extends JavaPlugin{

	private String prefix = "§7[§6BuildObjects§7] ";
	private DatenbankDatentransfer datenbankDatentransfer;
	
	private HashMap<Player,String> workerOnProject = new HashMap<>();
	
	
	
	@Override
	public void onEnable() {
		
		DatenbankInfos file = new DatenbankInfos();
		file.setStandard();
		file.readData();
		
		DatenbankVerbindung.connect();		
		datenbankDatentransfer = new DatenbankDatentransfer();
		
		registerCommands();
		registerListener();
		
		System.out.println("[BuildObjects] BuildObjects Plugin loaded!");
	}
	
	@Override
	public void onDisable() {
		System.out.println("[BuildObjects] BuildObjects Plugin disabled!");
	}
	
	private void registerCommands() {
		//COMMAND_coins cCommand_coins = new COMMAND_coins(this, datenbankDatentransfer);
		//getCommand("coins").setExecutor(cCommand_coins);
		COMMAND_buildObject cCommand_buildObject = new COMMAND_buildObject(this, datenbankDatentransfer);
		getCommand("buildobject").setExecutor(cCommand_buildObject);
	}
	
	private void registerListener() {
		new LISTENER_blockplace(this, datenbankDatentransfer);
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public HashMap<Player,String> getWorkerOnProject() {
		return workerOnProject;
	}
	
	
	public void addWorker(Player worker, String projectName) {
		if(!workerOnProject.containsKey(worker))
			workerOnProject.put(worker, projectName);
	}
	
	public void removeWorker(Player worker) {
		if(workerOnProject.containsKey(worker))
			workerOnProject.remove(worker);
	}
}
