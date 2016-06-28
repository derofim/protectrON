package com.derofim.protectron.util;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import com.derofim.protectron.modules.messages.MessagesConfig;

public class Utils {

	private static Utils instance = new Utils();

	public static final Utils getInstance() {
		return instance;
	}

	private Utils() {
	}

	// Returns Location by InventoryHolder
	public static Location getIvnentoryLocation(InventoryHolder holder) {
		Location location = null;
		if (holder instanceof BlockState) {
			location = ((BlockState) holder).getLocation();
		} else if (holder instanceof Entity) {
			location = ((Entity) holder).getLocation();
		} else if (holder instanceof DoubleChest) {
			location = ((DoubleChest) holder).getLocation();
		} else if (holder instanceof Chest) {
			location = ((Chest) holder).getLocation();
		}
		return location;
	}

	// Sends message to closest player
	public static void sendMessageToClosestPlayer(Location origin, List<Player> plst) {
		if (plst.isEmpty())
			return;
		double minDist = plst.get(0).getLocation().distanceSquared(origin);
		double curDist = 0;
		Player closest = null;
		for (Player itPlr : origin.getWorld().getPlayers()) {
			curDist = itPlr.getLocation().distanceSquared(origin);
			if (curDist <= minDist) {
				minDist = curDist;
				closest = itPlr;
			}
		}
		if (closest != null) {
			closest.sendMessage(MessagesConfig.getInstance().getStr(MessagesConfig.MSG_NOT_ALLOWED));
		}
	}

	// Returns true if item is deprecated (is in blacklist or not in whitelist)
	// Param: configModuleValue may be "whitelist" or someting else (blacklist)
	public static boolean checkLists(String item, String configModuleValue, List<String> whitelist,
			List<String> blacklist) {
		if (configModuleValue.equalsIgnoreCase("whitelist")) {
			if (Utils.isInList(whitelist, item))
				return false;
		} else {
			if (!Utils.isInList(blacklist, item))
				return false;
		}
		return true;
	}

	public static String getInventoryFullName(Inventory inv) {
		return inv.getTitle() + "_" + inv.getSize();
	}

	public static boolean isInList(List<String> list, String entry) {
		return list.contains(entry);
	}
}
