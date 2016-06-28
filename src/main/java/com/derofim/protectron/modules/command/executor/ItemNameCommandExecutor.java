package com.derofim.protectron.modules.command.executor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.command.AbstractExecutor;
import com.derofim.protectron.modules.itemGroup.itemsUtils;
import com.derofim.protectron.modules.messages.MessagesConfig;
import com.derofim.protectron.util.Vars;

public class ItemNameCommandExecutor extends AbstractExecutor {
	private ProtectronPlugin plugin = ProtectronPlugin.getInstance();
	private MessagesConfig msg = MessagesConfig.getInstance();

	private static ItemNameCommandExecutor instance = new ItemNameCommandExecutor();

	public static final ItemNameCommandExecutor getInstance() {
		return instance;
	}

	private ItemNameCommandExecutor() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission(Vars.PERM_ITEM_NAME)) {
				p.sendMessage(msg.getStr(MessagesConfig.MSG_NO_PERMISSION));
				return false;
			}
			ItemStack found = p.getItemInHand();
			p.sendMessage(ChatColor.GOLD + "Item id: " + ChatColor.WHITE + itemsUtils.getItemIdFull(found));
			p.sendMessage(ChatColor.GOLD + "Item name: " + ChatColor.WHITE + itemsUtils.getItemTypeFull(found));
		} else {
			plugin.getLogger().info(msg.getStr(MessagesConfig.MSG_ONLY_PLAYERS));
		}
		return true;
	}

	@Override
	public String getConfigName() {
		return "item_name";
	}

	@Override
	public String getCommandName() {
		return Vars.CMD_ITEM_NAME;
	}
}
