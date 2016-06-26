package com.derofim.protectron.modules.debug;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.config.AbstractConfig;
import com.derofim.protectron.util.CommonVars;

public class DebugConfig extends AbstractConfig {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private final String configLanguageVersion = "0.0.4";
	private final static File defaultFile = new File(plugin.getDataFolder(),
			"debug" + CommonVars.FOLDER_SEPARATOR + "debug.yml");

	private static DebugConfig instance = new DebugConfig();

	public static final DebugConfig getInstance() {
		return instance;
	}

	private DebugConfig() {
	}

	@Override
	protected void setCustomDefaults(FileConfiguration fconf) {
		fconf.addDefault(CommonVars.PARAM_DINFORM, true);
		fconf.addDefault(CommonVars.PARAM_DGRIEFB, false);
		fconf.addDefault(CommonVars.PARAM_DGRIEFI, false);
		fconf.addDefault(CommonVars.PARAM_DGRIEFP, false);
		fconf.addDefault(CommonVars.PARAM_DCLICKB, false);
		fconf.addDefault(CommonVars.PARAM_DCLICKI, false);
		fconf.addDefault(CommonVars.PARAM_DCLICKP, false);
		fconf.addDefault(CommonVars.PARAM_DALL, false);
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
		return CommonVars.CONFIG_DEBUG_HEADER;
	}
}
