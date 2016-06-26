package com.derofim.protectron.modules.events.playerInteract;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.config.AbstractConfig;
import com.derofim.protectron.util.CommonVars;

public class PlayerInteractConfig extends AbstractConfig {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private final String configLanguageVersion = "0.0.4";
	private final static File defaultFile = new File(plugin.getDataFolder(),
			"player_interact" + CommonVars.FOLDER_SEPARATOR + "player_interact.yml");

	private static PlayerInteractConfig instance = new PlayerInteractConfig();

	public static final PlayerInteractConfig getInstance() {
		return instance;
	}

	private PlayerInteractConfig() {
	}

	@Override
	protected void setCustomDefaults(FileConfiguration fconf) {
		List<String> RC_WG_GroupsList = Arrays.asList(CommonVars.VANILLA, CommonVars.IC2);
		List<String> RC_MY_GroupsList = Arrays.asList(CommonVars.TEST);
		List<String> RC_EW_GroupsList = Arrays.asList(CommonVars.TEST);
		//
		List<String> LC_WG_GroupsList = Arrays.asList(CommonVars.VANILLA);
		List<String> LC_MY_GroupsList = Arrays.asList(CommonVars.TEST);
		List<String> LC_EW_GroupsList = Arrays.asList(CommonVars.TEST);
		//
		List<String> ITM_RC_WG_GroupsList = Arrays.asList(CommonVars.VANILLA_ITEMS);
		List<String> ITM_RC_MY_GroupsList = Arrays.asList(CommonVars.TEST_ITEMS);
		List<String> ITM_RC_EW_GroupsList = Arrays.asList(CommonVars.TEST_ITEMS);
		//
		List<String> ITM_LC_WG_GroupsList = Arrays.asList(CommonVars.VANILLA_ITEMS);
		List<String> ITM_LC_MY_GroupsList = Arrays.asList(CommonVars.TEST_ITEMS);
		List<String> ITM_LC_EW_GroupsList = Arrays.asList(CommonVars.TEST_ITEMS);
		//
		List<String> PHYSICAL_WG_GroupsList = Arrays.asList(CommonVars.VANILLA);
		List<String> PHYSICAL_MY_GroupsList = Arrays.asList(CommonVars.TEST);
		List<String> PHYSICAL_EW_GroupsList = Arrays.asList(CommonVars.TEST);
		//
		fconf.addDefault(CommonVars.RC_BG_WG, RC_WG_GroupsList);
		fconf.addDefault(CommonVars.RC_BG_MY, RC_MY_GroupsList);
		fconf.addDefault(CommonVars.RC_BG_EW, RC_EW_GroupsList);
		//
		fconf.addDefault(CommonVars.LC_BG_MY, LC_WG_GroupsList);
		fconf.addDefault(CommonVars.LC_BG_WG, LC_MY_GroupsList);
		fconf.addDefault(CommonVars.LC_BG_EW, LC_EW_GroupsList);
		//
		fconf.addDefault(CommonVars.ITEM_RC_WG, ITM_RC_WG_GroupsList);
		fconf.addDefault(CommonVars.ITEM_RC_MY, ITM_RC_MY_GroupsList);
		fconf.addDefault(CommonVars.ITEM_RC_EW, ITM_RC_EW_GroupsList);
		//
		fconf.addDefault(CommonVars.ITEM_LC_WG, ITM_LC_WG_GroupsList);
		fconf.addDefault(CommonVars.ITEM_LC_MY, ITM_LC_MY_GroupsList);
		fconf.addDefault(CommonVars.ITEM_LC_EW, ITM_LC_EW_GroupsList);
		//
		fconf.addDefault(CommonVars.PHYSICAL_WG, PHYSICAL_WG_GroupsList);
		fconf.addDefault(CommonVars.PHYSICAL_MY, PHYSICAL_MY_GroupsList);
		fconf.addDefault(CommonVars.PHYSICAL_EW, PHYSICAL_EW_GroupsList);
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
		return CommonVars.CONFIG_ACTIONS_HEADER;
	}
}
