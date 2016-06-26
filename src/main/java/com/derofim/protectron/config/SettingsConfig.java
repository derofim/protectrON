package com.derofim.protectron.config;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.config.AbstractConfig;
import com.derofim.protectron.util.CommonVars;

public class SettingsConfig extends AbstractConfig {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private final String configLanguageVersion = "0.0.4";
	private final static File defaultFile = new File(plugin.getDataFolder(), "settings.yml");

	private static SettingsConfig instance = new SettingsConfig();

	public static final SettingsConfig getInstance() {
		return instance;
	}

	private SettingsConfig() {
	}

	@Override
	protected void setCustomDefaults(FileConfiguration fconf) {
		fconf.addDefault(CommonVars.PARAM_OPEN_INVENTORY_CHECK_FOREIGN_REGION, true);
		fconf.addDefault(CommonVars.PARAM_DENY_LIQUID_FLOW_IN_ALL_REGIONS, true);
		fconf.addDefault(CommonVars.PARAM_DENY_ENTITY_EXPOSION_EVERYWHERE, true);
		fconf.addDefault(CommonVars.PARAM_DENY_ENTITY_INTERACT_IN_ANY_REGION, true);
		fconf.addDefault(CommonVars.PARAM_DENY_PLAYER_INTERACT_WITH_ENTITY_IN_FOREIGN_REGION, true);
		fconf.addDefault(CommonVars.PARAM_DENY_PROJECTILE_IN_FOREGN_REGION, true);
		fconf.addDefault(CommonVars.PARAM_DENY_DAMAGE_IN_FOREGN_REGION, true);
		List<String> blacklist = Arrays.asList("SNOW");
		fconf.addDefault(CommonVars.PARAM_BLOCK_PHYSICS_EVENT_BLACKLIST, blacklist);
		//
		fconf.addDefault(CommonVars.PARAM_OPEN_INVENTORY_MODULE_MODE, "whitelist");
		List<String> whitelistinv = Arrays.asList("tile.thermalexpansion.machine.smelter.name_6",
				"jds.tileentityfancyworkbench_9");
		fconf.addDefault(CommonVars.PARAM_OPEN_INVENTORY_MODULE_WHITELIST, whitelistinv);
		List<String> blacklistinv = Arrays.asList("Chest_27");
		fconf.addDefault(CommonVars.PARAM_OPEN_INVENTORY_MODULE_BLACKLIST, blacklistinv);
		//
		HashMap<String, Integer> claimsWidth = new HashMap<String, Integer>();
		claimsWidth.put("default", 200);
		claimsWidth.put("vip", 250);
		claimsWidth.put("super", 300);
		claimsWidth.put("premium", 400);
		claimsWidth.put("golden", 500);
		claimsWidth.put("Moderator", 1000000);
		claimsWidth.put("Admin", 1000000);
		for (String keyStr : claimsWidth.keySet()) {
			fconf.addDefault(CommonVars.PARAM_GROUP_CLAIMS_WIDTH + "." + keyStr, claimsWidth.get(keyStr));
		}
		//
		HashMap<String, Integer> claimsHeight = new HashMap<String, Integer>();
		claimsHeight.put("default", 200);
		claimsHeight.put("vip", 250);
		claimsHeight.put("super", 300);
		claimsHeight.put("premium", 400);
		claimsHeight.put("golden", 500);
		claimsHeight.put("Moderator", 1000000);
		claimsHeight.put("Admin", 1000000);
		for (String keyStr : claimsHeight.keySet()) {
			fconf.addDefault(CommonVars.PARAM_GROUP_CLAIMS_HEIGHT + "." + keyStr, claimsHeight.get(keyStr));
		}
		//
		HashMap<String, Integer> claimsLen = new HashMap<String, Integer>();
		claimsLen.put("default", 200);
		claimsLen.put("vip", 250);
		claimsLen.put("super", 300);
		claimsLen.put("premium", 400);
		claimsLen.put("golden", 500);
		claimsLen.put("Moderator", 1000000);
		claimsLen.put("Admin", 1000000);
		for (String keyStr : claimsLen.keySet()) {
			fconf.addDefault(CommonVars.PARAM_GROUP_CLAIMS_LENGTH + "." + keyStr, claimsLen.get(keyStr));
		}
		//
		HashMap<String, Integer> claimsTotal = new HashMap<String, Integer>();
		claimsTotal.put("default", 30000);
		claimsTotal.put("vip", 40000);
		claimsTotal.put("super", 50000);
		claimsTotal.put("premium", 60000);
		claimsTotal.put("golden", 70000);
		claimsTotal.put("Moderator", 1000000);
		claimsTotal.put("Admin", 1000000);
		for (String keyStr : claimsTotal.keySet()) {
			fconf.addDefault(CommonVars.PARAM_GROUP_CLAIMS_TOTAL + "." + keyStr, claimsTotal.get(keyStr));
		}
		//
		List<String> DefaultGroupList = Arrays.asList("DEFAULT_FLAGS");
		List<String> VipGroupList = Arrays.asList("DEFAULT_FLAGS", "VIP_FLAGS");
		fconf.addDefault(CommonVars.PARAM_AUTOFLAGS_FLAGS+".default", DefaultGroupList);
		fconf.addDefault(CommonVars.PARAM_AUTOFLAGS_FLAGS+".vip", VipGroupList);
		fconf.addDefault("DEFAULT_FLAGS.item-drop", "deny");
		fconf.addDefault("DEFAULT_FLAGS.greeting", "hello guest");
		fconf.addDefault("VIP_FLAGS.entry", "deny");
		fconf.addDefault("VIP_FLAGS.item-drop", "allow");
		fconf.addDefault("VIP_FLAGS.greeting", ChatColor.GOLD+"hello :)");
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
		return CommonVars.CONFIG_SETTINGS_HEADER;
	}
}
