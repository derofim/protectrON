package com.derofim.protectron.config;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.manager.data.DataManager;
import com.derofim.protectron.modules.config.AbstractConfig;
import com.derofim.protectron.util.Vars;

public class SettingsConfig extends AbstractConfig {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private final String configLanguageVersion = "0.0.8";
	private final static File defaultFile = new File(plugin.getDataFolder(), "settings.yml");

	private static SettingsConfig instance = new SettingsConfig();

	public static final String CONFIG_SETTINGS_HEADER = "~~ Settings ~~\r\n";

	public static final SettingsConfig getInstance() {
		return instance;
	}

	
	public static final String PARAM_EXPAND_MAX_ENABLED = "autoexpand.auto_max_claim_size.enabled";
	public static final String PARAM_EXPAND_MAX_X_ENABLED = "autoexpand.auto_max_claim_size.x.enabled";
	public static final String PARAM_EXPAND_MAX_Y_ENABLED = "autoexpand.auto_max_claim_size.y.enabled";
	public static final String PARAM_EXPAND_MAX_Z_ENABLED = "autoexpand.auto_max_claim_size.z.enabled";
	public static final String PARAM_EXPAND_ADD_ENABLED = "autoexpand.add.enabled";
	public static final String PARAM_EXPAND_ADD_X = "autoexpand.add.x";
	public static final String PARAM_EXPAND_ADD_Y = "autoexpand.add.y";
	public static final String PARAM_EXPAND_ADD_Z = "autoexpand.add.z";
	//
	public static final String PARAM_MYSQL_USER = "db.mysql.user";
	public static final String PARAM_MYSQL_PASSWORD = "db.mysql.pasword";
	public static final String PARAM_MYSQL_PREFIX = "db.mysql.prefix";
	public static final String PARAM_MYSQL_DB = "db.mysql.database";
	public static final String PARAM_MYSQL_SERVER = "db.mysql.server";
	public static final String PARAM_MYSQL_PORT = "db.mysql.port";
	public static final String PARAM_MYSQL_JDBC = "db.mysql.jdbc";
	
	
	private SettingsConfig() {
	}

	@Override
	protected void setCustomDefaults(FileConfiguration fconf) {
		fconf.addDefault(Vars.PARAM_DENY_LIQUID_FLOW_EVERYWHERE, true);
		fconf.addDefault(Vars.PARAM_DENY_LIQUID_FLOW_IN_ALL_REGIONS, true);
		fconf.addDefault(Vars.PARAM_DENY_LIQUID_FLOW_IN_DIFFERENT_REGIONS, true);
		fconf.addDefault(Vars.PARAM_DENY_ENTITY_EXPOSION_EVERYWHERE, true);
		fconf.addDefault(Vars.PARAM_DENY_ENTITY_INTERACT_IN_ANY_REGION, true);
		fconf.addDefault(Vars.PARAM_DENY_PLAYER_INTERACT_WITH_ENTITY_IN_FOREIGN_REGION, true);
		fconf.addDefault(Vars.PARAM_DENY_PROJECTILE_IN_FOREGN_REGION, true);
		fconf.addDefault(Vars.PARAM_DENY_DAMAGE_IN_FOREGN_REGION, true);
		List<String> blacklist = Arrays.asList(Vars.PHYSICS_BLOCKS);
		fconf.addDefault(Vars.PARAM_BLOCK_PHYSICS_EVENT_BLACKLIST, blacklist);
		
		
		//
		HashMap<String, Integer> claimsWidth = new HashMap<String, Integer>();
		claimsWidth.put("default", 35);
		claimsWidth.put("vip", 40);
		claimsWidth.put("super", 50);
		claimsWidth.put("premium", 50);
		claimsWidth.put("golden", 50);
		claimsWidth.put("Moderator", 50);
		claimsWidth.put("Admin", 50);
		for (String keyStr : claimsWidth.keySet()) {
			fconf.addDefault(Vars.PARAM_GROUP_CLAIMS_WIDTH + "." + keyStr, claimsWidth.get(keyStr));
		}
		//
		HashMap<String, Integer> claimsHeight = new HashMap<String, Integer>();
		claimsHeight.put("default", 256);
		claimsHeight.put("vip", 256);
		claimsHeight.put("super", 256);
		claimsHeight.put("premium", 256);
		claimsHeight.put("golden", 256);
		claimsHeight.put("Moderator", 256);
		claimsHeight.put("Admin", 256);
		for (String keyStr : claimsHeight.keySet()) {
			fconf.addDefault(Vars.PARAM_GROUP_CLAIMS_HEIGHT + "." + keyStr, claimsHeight.get(keyStr));
		}
		//
		HashMap<String, Integer> claimsLen = new HashMap<String, Integer>();
		claimsLen.put("default", 35);
		claimsLen.put("vip", 40);
		claimsLen.put("super", 50);
		claimsLen.put("premium", 50);
		claimsLen.put("golden", 50);
		claimsLen.put("Moderator", 50);
		claimsLen.put("Admin", 50);
		for (String keyStr : claimsLen.keySet()) {
			fconf.addDefault(Vars.PARAM_GROUP_CLAIMS_LENGTH + "." + keyStr, claimsLen.get(keyStr));
		}
		//
		HashMap<String, Integer> claimsTotal = new HashMap<String, Integer>();
		claimsTotal.put("default", 1000000);
		claimsTotal.put("vip", 1000000);
		claimsTotal.put("super", 27000000);
		claimsTotal.put("premium", 64000000);
		claimsTotal.put("golden", 125000000);
		claimsTotal.put("Moderator", 1000000000);
		claimsTotal.put("Admin", 1000000000);
		for (String keyStr : claimsTotal.keySet()) {
			fconf.addDefault(Vars.PARAM_GROUP_CLAIMS_TOTAL + "." + keyStr, claimsTotal.get(keyStr));
		}
		//
		List<String> DefaultGroupList = Arrays.asList("DEFAULT_FLAGS");
		List<String> VipGroupList = Arrays.asList("DEFAULT_FLAGS", "VIP_FLAGS");
		fconf.addDefault(Vars.PARAM_AUTOFLAGS_FLAGS + ".default", DefaultGroupList);
		fconf.addDefault(Vars.PARAM_AUTOFLAGS_FLAGS + ".vip", VipGroupList);
		fconf.addDefault("DEFAULT_FLAGS.item-drop", "deny");
		fconf.addDefault("DEFAULT_FLAGS.greeting", "hello");
		fconf.addDefault("VIP_FLAGS.entry", "deny");
		fconf.addDefault("VIP_FLAGS.item-drop", "allow");
		fconf.addDefault("VIP_FLAGS.greeting", ChatColor.GOLD + "hello :)");
		fconf.addDefault(PARAM_EXPAND_MAX_ENABLED, true);
		fconf.addDefault(PARAM_EXPAND_MAX_X_ENABLED, true);
		fconf.addDefault(PARAM_EXPAND_MAX_Y_ENABLED, true);
		fconf.addDefault(PARAM_EXPAND_MAX_Z_ENABLED, true);
		fconf.addDefault(PARAM_EXPAND_ADD_ENABLED, false);
		fconf.addDefault(PARAM_EXPAND_ADD_X, 10);
		fconf.addDefault(PARAM_EXPAND_ADD_Y, 10);
		fconf.addDefault(PARAM_EXPAND_ADD_Z, 10);
		
		fconf.addDefault(PARAM_MYSQL_USER, "root");
		fconf.addDefault(PARAM_MYSQL_PASSWORD, "root");
		fconf.addDefault(PARAM_MYSQL_PREFIX, "protectron_");
		fconf.addDefault(PARAM_MYSQL_DB, "protectron");
		fconf.addDefault(PARAM_MYSQL_SERVER, "127.0.0.1");
		fconf.addDefault(PARAM_MYSQL_PORT, "3306");
		fconf.addDefault(PARAM_MYSQL_JDBC, "com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
	}

	@Override
	public File getFile() {
		return defaultFile;
	}

	@Override
	public boolean onPostReload() {
		DataManager.closeConnection();
		DataManager.setupConnection();
		return true;
	}
	
	@Override
	public String getLanguageVersion() {
		return configLanguageVersion;
	}

	@Override
	public String getHeader() {
		return CONFIG_SETTINGS_HEADER;
	}
}
