package com.derofim.protectron.modules.config;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.util.Vars;

/**
 * Represents abstract configuration
 *
 * @author Derofim
 */
public abstract class AbstractConfig implements ConfigInterface {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private final String configLanguageVersion = "0.0.3";
	private final static File defaultFile = new File(plugin.getDataFolder(), "cofig.yml");
	private static ConfigUtils configUtils = ConfigUtils.getInstance();
	private FileConfiguration fc = null;

	protected abstract void setCustomDefaults(FileConfiguration fconf);

	public File getFile() {
		return defaultFile;
	}

	public String getLanguageVersion() {
		return configLanguageVersion;
	}

	public void save() {
		configUtils.saveConfig(getFile());
		fc = configUtils.loadConfig(getFile());
	}

	public void setDefaults() {
		FileConfiguration fconf = new YamlConfiguration();
		setCustomDefaults(fconf); // fill file
		fconf.addDefault(Vars.CONF_VER, getLanguageVersion());
		configUtils.setDefaultsConfig(getFile(), fconf, this);
	}

	public FileConfiguration loadConfig() {
		if (!getFile().exists()) {
			setDefaults();
			save();
		} else {
			updateConfig();
		}
		return fc;
	}
	
	public FileConfiguration reloadConfig() {
		if (!getFile().exists()) {
			loadConfig();
		}
		return fc = configUtils.loadConfig(getFile());
	}

	public String getConfigVersion() {
		return configUtils.getConfigVersion(getConfig());
	}

	public FileConfiguration getConfig() {
		if (fc == null)
			loadConfig();
		return fc;
	}

	public void updateConfig() {
		if (fc == null)
			fc = configUtils.loadConfig(getFile());
		if (configUtils.checkNewVersion(getConfig(), getLanguageVersion())) {
			plugin.getLogger().info("Found new configuration language version");
			configUtils.updateConfigVersion(fc, getFile(), this);
			fc = configUtils.loadConfig(getFile());
		}
	}
	
	public int getInt(String par) {
		return getConfig().getInt(par);
	}

	public boolean getBool(String par) {
		return getConfig().getBoolean(par);
	}

	public String getStr(String par) {
		return getConfig().getString(par);
	}

	public List<String> getStrList(String par) {
		return getConfig().getStringList(par);
	}

	public String getHeader() {
		return "";
	}
}
