package datenbank;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class DatenbankInfos {

	public void setStandard() {
		FileConfiguration cfg = getFileConfiguration();
		
		cfg.options().copyDefaults(true);
		
		cfg.addDefault("host", "localhost");
		cfg.addDefault("port", "3306");
		cfg.addDefault("database", "buildobjects");
		cfg.addDefault("username", "localhost");
		cfg.addDefault("password", "localhost");
		
		try {
			cfg.save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private File getFile() {
		return new File("plugins/buildobjects", "DatenbankVerbindung.yml");
	}
	private FileConfiguration getFileConfiguration() {
		return YamlConfiguration.loadConfiguration(getFile());
	}
	
	public void readData() {
		FileConfiguration cfg = getFileConfiguration();
		
		DatenbankVerbindung.host = cfg.getString("host");
		DatenbankVerbindung.port = cfg.getString("port");
		DatenbankVerbindung.database = cfg.getString("database");
		DatenbankVerbindung.username = cfg.getString("username");
		DatenbankVerbindung.password = cfg.getString("password");
	}
	
}
