package com.derofim.protectron.modules.events.inventoryOpen;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.config.SettingsConfig;
import com.derofim.protectron.manager.ProtectionManager;
import com.derofim.protectron.modules.messages.MessagesConfig;
import com.derofim.protectron.util.CommonUtils;
import com.derofim.protectron.util.CommonVars;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class InventoryOpenController {
	private static final boolean debugVerbose = false;

	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	private WorldGuardPlugin wg = ProtectionManager.getInstance().getWorldGuard();
	private MessagesConfig msg = MessagesConfig.getInstance();
	private SettingsConfig st = SettingsConfig.getInstance();

	public InventoryOpenController() {
	}

	private boolean checkGlobalOpenInv(InventoryOpenEvent e, Location location) {
		Player p = (Player) e.getPlayer();
		Inventory inv = e.getInventory();
		if (!wg.canBuild(p, location) && !p.hasPermission(CommonVars.PERM_ALL_INV_ACCESS)
				&& st.getBool(CommonVars.PARAM_OPEN_INVENTORY_CHECK_FOREIGN_REGION)) {
			p.sendMessage(msg.getStr(CommonVars.MSG_NOT_ALLOWED) + " " + inv.getType().toString() + ". "
					+ msg.getStr(CommonVars.MSG_REG_PROTECTED));
			return true;
		}
		return false;
	}

	// ���������� true ���� �������� ���������
	public boolean checkInventoryOpen(InventoryOpenEvent e) {
		Inventory inv = e.getInventory();
		// String evtName = e.getEventName();
		InventoryHolder holder = inv.getHolder();
		Location location = null;
		if (holder instanceof BlockState) {
			location = ((BlockState) holder).getLocation();
			if (debugVerbose)
				lg.info("InventoryOpenEventHandler BlockState" + location);
		} else if (holder instanceof Entity) {
			location = ((Entity) holder).getLocation();
			if (debugVerbose)
				lg.info("InventoryOpenEventHandler Entity" + location);
		} else if (holder instanceof DoubleChest) {
			location = ((DoubleChest) holder).getLocation();
			if (debugVerbose)
				lg.info("InventoryOpenEventHandler DoubleChest" + location);
		} else if (holder instanceof Chest) {
			location = ((Chest) holder).getLocation();
		} else {
			if (debugVerbose)
				lg.info("InventoryOpenEventHandler unknown holder" + inv.getTitle());
		}
		Player p = (Player) e.getPlayer();
		if (p.hasPermission(CommonVars.PERM_VIEW_INV_NAME)) {
			p.sendMessage(
					ChatColor.GOLD + "Inventory name: " + ChatColor.WHITE + CommonUtils.getInventoryFullName(inv));
			lg.info(ChatColor.GOLD + "Inventory name: " + ChatColor.WHITE + CommonUtils.getInventoryFullName(inv));
		}
		if (SettingsConfig.getInstance().getStr(CommonVars.PARAM_OPEN_INVENTORY_MODULE_MODE)
				.equalsIgnoreCase("whitelist")) {
			List<String> whitelist = SettingsConfig.getInstance()
					.getStrList(CommonVars.PARAM_OPEN_INVENTORY_MODULE_WHITELIST);
			for (String itBlock : whitelist) {
				if (CommonUtils.getInventoryFullName(inv).equals(itBlock))
					return false;
			}
		} else {
			List<String> blacklist = SettingsConfig.getInstance()
					.getStrList(CommonVars.PARAM_OPEN_INVENTORY_MODULE_BLACKLIST);
			if (!blacklist.contains(CommonUtils.getInventoryFullName(inv)))
				return false;
		}
		if (location != null) {
			if (checkGlobalOpenInv(e, location)) {
				return true;
			}
		}
		return false;
	}
}
