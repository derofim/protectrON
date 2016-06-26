package com.derofim.protectron.modules.messages;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.config.AbstractConfig;
import com.derofim.protectron.util.CommonVars;

public class MessagesConfig extends AbstractConfig {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private final String configLanguageVersion = "0.0.4";
	private final static File defaultFile = new File(plugin.getDataFolder(),
			"messages" + CommonVars.FOLDER_SEPARATOR + "messages.yml");

	private static MessagesConfig instance = new MessagesConfig();

	public static final MessagesConfig getInstance() {
		return instance;
	}

	private MessagesConfig() {
	}

	@Override
	protected void setCustomDefaults(FileConfiguration fconf) {
		fconf.addDefault(CommonVars.MSG_NOT_ALLOWED, ChatColor.GOLD + "Not allowed " + ChatColor.WHITE);
		fconf.addDefault(CommonVars.MSG_REG_PROTECTED, ChatColor.GOLD + "Region protected " + ChatColor.WHITE);
		fconf.addDefault(CommonVars.MSG_NO_PERMISSION, ChatColor.GOLD + "Not enough permissions " + ChatColor.WHITE);
		fconf.addDefault(CommonVars.MSG_WG_ERR_MAX_HEIGHT, ChatColor.RED + "Error. Max region height is: ");
		fconf.addDefault(CommonVars.MSG_WG_ERR_MAX_WIDTH, ChatColor.RED + "Error. Max region width is: ");
		fconf.addDefault(CommonVars.MSG_WG_ERR_MAX_LENGTH, ChatColor.RED + "Error. Max region length is: ");
		fconf.addDefault(CommonVars.MSG_WG_ERR_MAX_TOTAL, ChatColor.RED + "Error. Max region size is: ");
		fconf.addDefault(CommonVars.MSG_WG_ERR_ALREADY_CLAIMED, ChatColor.RED + "Error. Region already claimed! ");
		fconf.addDefault(CommonVars.MSG_WG_ERROR_WRONG, ChatColor.RED + "Error. Something went wrong. ");
		fconf.addDefault(CommonVars.MSG_WG_CANT_DO, ChatColor.RED + "You can't do that. ");
		fconf.addDefault(CommonVars.MSG_WG_CREATED, ChatColor.GREEN + "Region created. ");
		fconf.addDefault(CommonVars.MSG_WG_ERR_CANT_AUTO_FLAG, ChatColor.RED + "Error. Cant automatically set flags! ");
		fconf.addDefault(CommonVars.MSG_WG_ERR_NO_REGION, ChatColor.RED + "Error. Cant find region! ");
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
		return CommonVars.CONFIG_MESSAGES_HEADER;
	}
}
