package com.derofim.protectron.modules.command.executor;

import java.util.TreeMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.manager.data.DataManager;
import com.derofim.protectron.modules.command.AbstractExecutor;
import com.derofim.protectron.modules.messages.MessagesConfig;
import com.derofim.protectron.util.Vars;

public class OopsLimit extends AbstractExecutor {
	private ProtectronPlugin plugin = ProtectronPlugin.getInstance();
	private MessagesConfig msg = MessagesConfig.getInstance();

	private static OopsLimit instance = new OopsLimit();

	public static final OopsLimit getInstance() {
		return instance;
	}

	private OopsLimit() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission(Vars.PERM_OOPS_LIMIT)) {
				p.sendMessage(msg.getStr(MessagesConfig.MSG_NO_PERMISSION));
				return false;
			}
			int pId = DataManager.findUsersIdTable(p);
			int deletedCount = DataManager.clearBlocksIdTable(pId);
			TreeMap<String, String> matches = new TreeMap<String, String>();
			matches.put("#count#", ""+deletedCount);
			matches.put("#player#", p.getName());
			p.sendMessage(msg.prepareStr(MessagesConfig.MSG_LIMITS_CLEARED, matches));
		} else {
			plugin.getLogger().info(msg.getStr(MessagesConfig.MSG_ONLY_PLAYERS));
		}
		return true;
	}

	@Override
	public String getConfigName() {
		return "oops_limit";
	}

	@Override
	public String getCommandName() {
		return Vars.CMD_OOPS_LIMIT;
	}
}
