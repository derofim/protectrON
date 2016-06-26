package com.derofim.protectron.modules.command.executor;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.blockGroup.BlocksUtils;
import com.derofim.protectron.modules.command.AbstractExecutor;
import com.derofim.protectron.modules.messages.MessagesConfig;
import com.derofim.protectron.util.CommonVars;

@SuppressWarnings("deprecation")
public class BlockNameCommandExecutor extends AbstractExecutor  {
	private ProtectronPlugin plugin = ProtectronPlugin.getInstance();
	private MessagesConfig msg = MessagesConfig.getInstance();

	private static BlockNameCommandExecutor instance = new BlockNameCommandExecutor();

	public static final BlockNameCommandExecutor getInstance() {
		return instance;
	}

	private BlockNameCommandExecutor() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission(CommonVars.PERM_BLOCK_NAME)) {
				p.sendMessage(msg.getStr(CommonVars.MSG_NO_PERMISSION));
				return false;
			}
			Block found = null;
			for (Block b : p.getLastTwoTargetBlocks(null, 100)) {
				if (!b.getType().toString().equalsIgnoreCase("AIR")) {
					found = b;
				}
			}
			if (found != null) {
				p.sendMessage(ChatColor.GOLD + "Block id: " + ChatColor.WHITE + BlocksUtils.getBlockIdFull(found));
				p.sendMessage(ChatColor.GOLD + "Block name: " + ChatColor.WHITE + BlocksUtils.getBlockTypeFull(found));
			} else
				p.sendMessage(ChatColor.GOLD + "Look better!");
		} else {
			plugin.getLogger().info(msg.getStr(CommonVars.MSG_ONLY_PLAYERS));
		}
		return true;
	}

	@Override
	public String getConfigName() {
		return "block_name";
	}

	@Override
	public String getCommandName() {
		return CommonVars.CMD_BLOCK_NAME;
	}
}
