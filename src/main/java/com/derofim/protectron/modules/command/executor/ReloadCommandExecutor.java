package com.derofim.protectron.modules.command.executor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.command.AbstractExecutor;
import com.derofim.protectron.modules.config.ConfigManager;
import com.derofim.protectron.modules.messages.MessagesConfig;
import com.derofim.protectron.util.Vars;

public class ReloadCommandExecutor extends AbstractExecutor {
	private ProtectronPlugin plugin = ProtectronPlugin.getInstance();
	private MessagesConfig msg = MessagesConfig.getInstance();
	private ConfigManager moduleConf = ConfigManager.getInstance();

	private static ReloadCommandExecutor instance = new ReloadCommandExecutor();

	public static final ReloadCommandExecutor getInstance() {
		return instance;
	}
	
	private ReloadCommandExecutor() {
	}

	private boolean canPlayerUseCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;
		if (!p.hasPermission(Vars.PERM_RLD)) {
			p.sendMessage(msg.getStr(MessagesConfig.MSG_NO_PERMISSION));
			return false;
		}
		return false;
	}

	private boolean handleSelfReload(CommandSender sender, Command command, String label, String[] args) {
		plugin.getLogger().info("Reloading config");
		moduleConf.reload();
		plugin.getLogger().info("Reloaded config");
		if (sender instanceof Player) {
			Player p = (Player) sender;
			p.sendMessage("Reloaded config");
		}
		return true;
	}

	private boolean handleOtherPluginReload(CommandSender sender, Command command, String label, String[] args) {
		if (!Bukkit.getPluginManager().isPluginEnabled(args[0])) {
			plugin.getLogger().info("Plugin not enabled!");
			return false;
		}
		Bukkit.getPluginManager().getPlugin(args[0]).reloadConfig();
		String reloadedMsg = "Reloaded config.yml config file in "
				+ Bukkit.getPluginManager().getPlugin(args[0]).getDataFolder() + " for " + args[0];
		plugin.getLogger().info(reloadedMsg);
		if (sender instanceof Player) {
			Player p = (Player) sender;
			p.sendMessage(reloadedMsg);
		}
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			if (!canPlayerUseCommand(sender, command, label, args))
				return false;
		}
		if (args.length == 0) {
			if (handleSelfReload(sender, command, label, args))
				return true;
		} else if (args.length == 1) {
			if (handleOtherPluginReload(sender, command, label, args))
				return true;
		}
		return false;
	}

	@Override
	public String getConfigName() {
		return "reload";
	}
	
	@Override
	public String getCommandName() {
		return Vars.CMD_RELOAD;
	}
}
