package com.derofim.protectron.modules.messages;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.config.AbstractConfig;
import com.derofim.protectron.modules.debug.DebugConfig;
import com.derofim.protectron.util.Vars;

public class MessagesConfig extends AbstractConfig {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private final String configLanguageVersion = "0.0.6";
	private final static File defaultFile = new File(plugin.getDataFolder(),
			Vars.FOLDER_MODULES + Vars.FOLDER_SEPARATOR + "messages" + Vars.FOLDER_SEPARATOR + "messages.yml");

	// Localization messages
	public static final String MSG_NOT_ALLOWED = "msg_not_allowed";
	public static final String MSG_REG_PROTECTED = "msg_region_protected";
	public static final String MSG_NO_PERMISSION = "msg_not_enough_perm";
	public static final String MSG_ONLY_PLAYERS = "msg_only_players";
	public static final String MSG_WG_ERR_MAX_HEIGHT = "msg_wg_err_max_height";
	public static final String MSG_WG_ERR_MAX_WIDTH = "msg_wg_err_max_width";
	public static final String MSG_WG_ERR_MAX_TOTAL = "msg_wg_err_max_total";
	public static final String MSG_WG_ERR_MAX_LENGTH = "msg_wg_err_max_length";
	public static final String MSG_WG_ERR_ALREADY_CLAIMED = "msg_wg_err_already_claimed";
	public static final String MSG_WG_ERR_WRONG = "msg_wg_error_wrong";
	public static final String MSG_WG_CANT_DO = "msg_wg_cant_do";
	public static final String MSG_WG_CREATED = "msg_wg_created";
	public static final String MSG_WG_ERR_CANT_AUTO_FLAG = "msg_wg_cant_auto_flag";
	public static final String MSG_WG_ERR_NO_REGION = "msg_wg_err_no_region";

	public static final String CONFIG_MESSAGES_HEADER = "~~ Messages ~~\r\n" + "* " + MSG_NOT_ALLOWED + "\r\n"
			+ "Message like 'It is forbidden to interact with' that will be sent to the player when he is trying to do prohibited action.\r\n"
			+ "Configuration must be enabled " + DebugConfig.PARAM_DINFORM + ": true \r\n"
			+ "Player must have permission " + Vars.PERM_INFORM + "\r\n\r\n" + "* " + MSG_REG_PROTECTED + "\r\n"
			+ "Message like 'Area under protection' that will be sent to the player when he is trying to do prohibited action.\r\n"
			+ "Configuration must be enabled " + DebugConfig.PARAM_DINFORM + ": true \r\n"
			+ "Player must have permission " + Vars.PERM_INFORM + "\r\n";

	private static MessagesConfig instance = new MessagesConfig();

	public static final MessagesConfig getInstance() {
		return instance;
	}

	private MessagesConfig() {
	}

	@Override
	protected void setCustomDefaults(FileConfiguration fconf) {
		fconf.addDefault(MSG_NOT_ALLOWED, ChatColor.GOLD + "Not allowed " + ChatColor.WHITE);
		fconf.addDefault(MSG_REG_PROTECTED, ChatColor.GOLD + "Region protected " + ChatColor.WHITE);
		fconf.addDefault(MSG_NO_PERMISSION, ChatColor.GOLD + "Not enough permissions " + ChatColor.WHITE);
		fconf.addDefault(MSG_WG_ERR_MAX_HEIGHT, ChatColor.RED + "Error. Max region height is: ");
		fconf.addDefault(MSG_WG_ERR_MAX_WIDTH, ChatColor.RED + "Error. Max region width is: ");
		fconf.addDefault(MSG_WG_ERR_MAX_LENGTH, ChatColor.RED + "Error. Max region length is: ");
		fconf.addDefault(MSG_WG_ERR_MAX_TOTAL, ChatColor.RED + "Error. Max region size is: ");
		fconf.addDefault(MSG_WG_ERR_ALREADY_CLAIMED, ChatColor.RED + "Error. Region already claimed! ");
		fconf.addDefault(MSG_WG_ERR_WRONG, ChatColor.RED + "Error. Something went wrong. ");
		fconf.addDefault(MSG_WG_CANT_DO, ChatColor.RED + "You can't do that. ");
		fconf.addDefault(MSG_WG_CREATED, ChatColor.GREEN + "Region created. ");
		fconf.addDefault(MSG_WG_ERR_CANT_AUTO_FLAG, ChatColor.RED + "Error. Cant automatically set flags! ");
		fconf.addDefault(MSG_WG_ERR_NO_REGION, ChatColor.RED + "Error. Cant find region! ");
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
		return CONFIG_MESSAGES_HEADER;
	}
}
