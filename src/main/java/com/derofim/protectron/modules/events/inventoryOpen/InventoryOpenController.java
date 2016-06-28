package com.derofim.protectron.modules.events.inventoryOpen;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.manager.ProtectionManager;
import com.derofim.protectron.modules.messages.MessagesConfig;
import com.derofim.protectron.util.Utils;
import com.derofim.protectron.util.Vars;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class InventoryOpenController {
	private ProtectronPlugin main = ProtectronPlugin.getInstance();
	private Logger lg = main.getLogger();
	private WorldGuardPlugin wg = ProtectionManager.getInstance().getWorldGuard();
	private MessagesConfig msg = MessagesConfig.getInstance();
	inventoryOpenConfig icf = inventoryOpenConfig.getInstance();
	private final boolean debugVerbose = false;

	public InventoryOpenController() {
	}

	// Returns true if action not allowed with invName in region regionConfigName
	private boolean checkListsByConfigRegion(String worldName, String invName, String regionConfigName) {
		if (!icf.getConfig().contains(inventoryOpenConfig.formRegionConfigName(worldName, regionConfigName)))
			return false;
		if (!icf.getBool(inventoryOpenConfig.formRegionConfigName(worldName, regionConfigName) + ".enabled")) {
			if (debugVerbose) {
				lg.info(inventoryOpenConfig.formRegionConfigName(worldName, regionConfigName) + ".enabled = false");
			}
			return false;
		}
		String moduleMode = icf.getStr(inventoryOpenConfig.formWorldConfigName(worldName) + ".Module.Mode");
		List<String> whitelist = icf.getStrList(
				inventoryOpenConfig.formRegionConfigName(worldName, regionConfigName) + "." + Vars.WHITELIST);
		List<String> blacklist = icf.getStrList(
				inventoryOpenConfig.formRegionConfigName(worldName, regionConfigName) + "." + Vars.BLACKLIST);
		if (moduleMode.equalsIgnoreCase(Vars.WHITELIST)) {
			for (String s : whitelist) {
				List<String> whitelistInv = icf.getStrList(s);
				if (Utils.isInList(whitelistInv, invName))
					return false;
			}
		} else {
			for (String s : blacklist) {
				List<String> blacklistInv = icf.getStrList(s);
				if (!Utils.isInList(blacklistInv, invName))
					return false;
			}
		}
		return true;
	}

	// Checks if inventory can be opened in region
	// Returns true if inventory prohibited
	private boolean checkOpenedRegion(InventoryOpenEvent e, Location location) {
		if (!(e.getPlayer() instanceof Player))
			return false;
		Player p = (Player) e.getPlayer();
		Inventory inv = e.getInventory();
		String worldName = p.getWorld().getName().toLowerCase();
		String invName = Utils.getInventoryFullName(inv);

		if (p.hasPermission(Vars.PERM_ALL_INV_ACCESS))
			return false;

		if (checkListsByConfigRegion(worldName, invName, inventoryOpenConfig.GLOBAL_REGION)) {
			p.sendMessage(msg.getStr(MessagesConfig.MSG_NOT_ALLOWED) + " " + inv.getType().toString());
			return true;
		}

		if (!wg.canBuild(p, location)) {
			if (!checkListsByConfigRegion(worldName, invName, inventoryOpenConfig.FOREIGN_REGION))
				return false;
			p.sendMessage(msg.getStr(MessagesConfig.MSG_NOT_ALLOWED) + " " + inv.getType().toString() + ". "
					+ msg.getStr(MessagesConfig.MSG_REG_PROTECTED));
			return true;
		}
		return false;
	}

	// Returns true if action not allowed
	public boolean checkInventoryOpen(InventoryOpenEvent e) {
		Inventory inv = e.getInventory();
		InventoryHolder holder = inv.getHolder();
		Location location = Utils.getIvnentoryLocation(holder);
		Player p = (Player) e.getPlayer();
		String invName = Utils.getInventoryFullName(inv);
		if (p.hasPermission(Vars.PERM_VIEW_INV_NAME)) {
			p.sendMessage(ChatColor.GOLD + "Inventory name: " + ChatColor.WHITE + invName);
			lg.info(ChatColor.GOLD + "Inventory name: " + ChatColor.WHITE + invName);
		}
		String worldName = p.getWorld().getName().toLowerCase();
		if (icf.getConfig().contains(inventoryOpenConfig.formWorldConfigName(worldName))) {
			// check by region
			if (location != null && checkOpenedRegion(e, location)) {
				return true;
			}
		} else if (debugVerbose)
			lg.info("Cant find " + inventoryOpenConfig.formWorldConfigName(worldName));
		return false;
	}
}
