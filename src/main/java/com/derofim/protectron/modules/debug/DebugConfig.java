package com.derofim.protectron.modules.debug;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.config.AbstractConfig;
import com.derofim.protectron.util.Vars;

public class DebugConfig extends AbstractConfig {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private final String configLanguageVersion = "0.0.8";
	private final static File defaultFile = new File(plugin.getDataFolder(),
			Vars.FOLDER_MODULES + Vars.FOLDER_SEPARATOR + "debug" + Vars.FOLDER_SEPARATOR + "debug.yml");

	// Configuration settings
	public static final String PARAM_DINFORM = "player.chat.notify.ProhibitedAction";
	public static final String PARAM_DGRIEFB = "console.debug.notify.ProtectedBlockAttempt";
	public static final String PARAM_DGRIEFI = "console.debug.notify.ProtectedItemAttempt";
	public static final String PARAM_DGRIEFP = "console.debug.notify.ProtectedPhysicalAttempt";
	public static final String PARAM_DCLICKB = "console.debug.BlockInteract";
	public static final String PARAM_DCLICKI = "console.debug.ItemInteract";
	public static final String PARAM_DCLICKP = "console.debug.PhysicalInteract";
	public static final String PARAM_DALL = "console.debug.VerbosePluginActions";
	public static final String CONFIG_DEBUG_HEADER = "~~ Debug settings ~~\r\n" + PARAM_DINFORM
			+ ":\r\n * Should we tell player about protection? Player must have permission " + Vars.PERM_INFORM
			+ "\r\n\r\n" + PARAM_DCLICKP
			+ ":\r\n * Should we log in console name of any block with which the player interacts phisically? Stepping onto or into a block (Ass-pressure)"
			+ "\r\nExamples include jumping on soil, standing on pressure plate, triggering redstone ore, triggering tripwire. "
			+ "\r\nPlayer must have permission " + Vars.PERM_DBGCLKP + "\r\n\r\n" + "" + PARAM_DGRIEFB
			+ ":\r\n * Should we log in console name of any block with which the player interacts by clicking? Player must have permission "
			+ Vars.PERM_DBGCLKB + "\r\n\r\n" + "" + PARAM_DGRIEFB
			+ ":\r\n * Should we log in console name of any protected block with which the player interacts? Player must have permission "
			+ Vars.PERM_DPROT + "\r\n\r\n" + "" + PARAM_DALL
			+ ":\r\n * Should we log in console any actions made by plugin when player interacts with something? Player must have permission "
			+ Vars.PERM_DBGPLG;

	private static DebugConfig instance = new DebugConfig();

	public static final DebugConfig getInstance() {
		return instance;
	}

	private DebugConfig() {
	}

	@Override
	protected void setCustomDefaults(FileConfiguration fconf) {
		fconf.addDefault(PARAM_DINFORM, true);
		fconf.addDefault(PARAM_DGRIEFB, false);
		fconf.addDefault(PARAM_DGRIEFI, false);
		fconf.addDefault(PARAM_DGRIEFP, false);
		fconf.addDefault(PARAM_DCLICKB, false);
		fconf.addDefault(PARAM_DCLICKI, false);
		fconf.addDefault(PARAM_DCLICKP, false);
		fconf.addDefault(PARAM_DALL, false);
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
		return CONFIG_DEBUG_HEADER;
	}
}
