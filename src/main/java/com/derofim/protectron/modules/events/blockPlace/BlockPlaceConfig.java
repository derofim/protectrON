package com.derofim.protectron.modules.events.blockPlace;

import java.io.File;
import java.util.Arrays;

import org.bukkit.configuration.file.FileConfiguration;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.config.AbstractConfig;
import com.derofim.protectron.util.Vars;

public class BlockPlaceConfig extends AbstractConfig {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private final String configLanguageVersion = "0.0.8";
	private final static File defaultFile = new File(plugin.getDataFolder(),
			Vars.FOLDER_MODULES + Vars.FOLDER_SEPARATOR + "events" + Vars.FOLDER_SEPARATOR + "block_place.yml");

	private static BlockPlaceConfig instance = new BlockPlaceConfig();

	public static final BlockPlaceConfig getInstance() {
		return instance;
	}

	public static final String CONFIG_ROOT = "BlockPlace";
	public static final String CONFIG_HEADER = "";
	public static final String MODE = "Mode";
	public static final String WORLD_MODE = CONFIG_ROOT + ".world." + MODE;

	private BlockPlaceConfig() {
	}

	public static String getWorldGroup(String world, String group) {
		return CONFIG_ROOT + "." + world + "." + group;
	}

	public static String getWorldMode(String world) {
		return CONFIG_ROOT + "." + world + "." + MODE;
	}

	@Override
	protected void setCustomDefaults(FileConfiguration fconf) {
		fconf.addDefault(WORLD_MODE, "blacklist");
		fconf.addDefault(getWorldGroup("world", "default"), Arrays.asList(Vars.PLACE_DENY));
		fconf.addDefault(getWorldGroup("world", "vip"), Arrays.asList(Vars.PLACE_DENY, Vars.BREAK_DENY));
		fconf.addDefault(getWorldGroup("world", "super"), Arrays.asList(Vars.PLACE_DENY));
		fconf.addDefault(getWorldGroup("world", "premium"), Arrays.asList(Vars.PLACE_DENY));
		fconf.addDefault(getWorldGroup("world", "golden"), Arrays.asList(Vars.PLACE_DENY));
		fconf.addDefault(getWorldGroup("world", "Moderator"), Arrays.asList(Vars.PLACE_DENY));
		fconf.addDefault(getWorldGroup("world", "Admin"), Arrays.asList(Vars.PLACE_DENY));
		//
		fconf.addDefault(getWorldGroup("arcana", "default"), Arrays.asList(Vars.PLACE_DENY));
		fconf.addDefault(getWorldGroup("arcana", "vip"), Arrays.asList(Vars.PLACE_DENY, Vars.BREAK_DENY));
		fconf.addDefault(getWorldGroup("arcana", "super"), Arrays.asList(Vars.PLACE_DENY));
		fconf.addDefault(getWorldGroup("arcana", "premium"), Arrays.asList(Vars.PLACE_DENY));
		fconf.addDefault(getWorldGroup("arcana", "golden"), Arrays.asList(Vars.PLACE_DENY));
		fconf.addDefault(getWorldGroup("arcana", "Moderator"), Arrays.asList(Vars.PLACE_DENY));
		fconf.addDefault(getWorldGroup("arcana", "Admin"), Arrays.asList(Vars.PLACE_DENY));
	}

	@Override
	public File getFile() {
		return defaultFile;
	}

	@Override
	public String getLanguageVersion() {
		return configLanguageVersion;
	}

	@Override
	public String getHeader() {
		return CONFIG_HEADER;
	}
}
