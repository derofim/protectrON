package com.derofim.protectron.modules.config;

import org.bukkit.configuration.file.FileConfiguration;

public interface ConfigInterface {
	public void setDefaults();
	public FileConfiguration loadConfig();
	public String getConfigVersion();
	public FileConfiguration getConfig();
	public void updateConfig();
	public String getHeader();
}
