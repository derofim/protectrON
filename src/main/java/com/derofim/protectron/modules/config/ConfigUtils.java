package com.derofim.protectron.modules.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.util.CommonVars;

public class ConfigUtils {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private static ConfigUtils instance = new ConfigUtils();

	private static final boolean debugVerbose = false;

	public static final ConfigUtils getInstance() {
		return instance;
	}

	private ConfigUtils() {
	}
	
	private void saveFolders(File file){
		if (!file.getAbsoluteFile().exists()) {
			try {
				if (file.getAbsoluteFile().mkdirs()) {
					plugin.getLogger().info("Folder " + file.getAbsoluteFile().getName() + " created.");
				} else {
					plugin.getLogger().warning("Unable to create folder " + file.getAbsoluteFile().getName() + ".");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void saveFile(File file){
		if (!file.exists()) {
			try {
				file.createNewFile();
				plugin.getLogger().warning("Creating file " + file.getName() + ".");
			} catch (IOException e) {
				plugin.getLogger().warning("Unable to create file " + file.getName() + ".");
				e.printStackTrace();
			}
		}
	}

	public void saveConfig(File file) {
		saveFolders(file);
		saveFile(file);
	}
	
	private void saveFileConfiguration(File file, FileConfiguration fc){
		try {
			fc.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setDefaultsConfig(File file, FileConfiguration fc, ConfigInterface ci) {
		// Saves FileConfiguration
		fc.options().copyDefaults(true);
		if (!ci.getHeader().isEmpty())
			fc.options().header(ci.getHeader());
		saveFileConfiguration(file, fc);
	}

	public FileConfiguration loadConfig(File file) {
		return YamlConfiguration.loadConfiguration(file);
	}

	public String getConfigVersion(FileConfiguration fc) {
		if (fc.contains(CommonVars.CONF_VER)) {
			if (debugVerbose)
				plugin.getLogger()
						.info("Found " + fc.getCurrentPath() + " v." + fc.getString(CommonVars.CONF_VER));
			return fc.getString(CommonVars.CONF_VER);
		}
		return "";
	}

	public boolean checkNewVersion(FileConfiguration fc, String configLanguageVersion) {
		if (getConfigVersion(fc).equals("") || !fc.getString(CommonVars.CONF_VER).equals(configLanguageVersion))
			return true;
		return false;
	}
	
	public String getBackupFolderName(){
		return CommonVars.FOLDER_SEPARATOR+"backup"+CommonVars.FOLDER_SEPARATOR;
	}
	
	private void makeBackupFolder(){
		File dir = new File(plugin.getDataFolder() + getBackupFolderName());
		dir.mkdir();
	}

	public void updateConfigVersion(FileConfiguration fc, File file, ConfigInterface ci) {
		File oldFile = file;
		String oldRenamed = oldFile.getName() + "_v" + getConfigVersion(fc) + ".yml";
		File confToOldFile = new File(plugin.getDataFolder() + getBackupFolderName(), oldRenamed);
		makeBackupFolder();
		if (oldFile.exists() && !confToOldFile.exists()) {
			try {
				if (debugVerbose)
					plugin.getLogger().info("Moving " + oldFile.toPath() + " to " + confToOldFile.toPath());
				Files.move(oldFile.toPath(), confToOldFile.toPath());
				plugin.getLogger().info("Saved old config as " + plugin.getDataFolder() + getBackupFolderName() + oldRenamed);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ci.setDefaults();
			plugin.getLogger().info("Updated " + plugin.getDataFolder() + "\\" + oldFile.getName());
		}
	}
}
