package com.derofim.protectron.util;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import com.derofim.protectron.modules.messages.MessagesConfig;

public class CommonUtils {

	private static CommonUtils instance = new CommonUtils();

	public static final CommonUtils getInstance() {
		return instance;
	}

	private CommonUtils() {
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
			closest.sendMessage(MessagesConfig.getInstance().getStr(CommonVars.MSG_NOT_ALLOWED));
		}
	}

	public static String getInventoryFullName(Inventory inv) {
		return inv.getTitle() + "_" + inv.getSize();
	}
}
