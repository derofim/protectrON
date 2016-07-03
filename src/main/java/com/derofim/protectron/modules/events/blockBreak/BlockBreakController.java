package com.derofim.protectron.modules.events.blockBreak;

import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.manager.PermissionManager;
import com.derofim.protectron.manager.data.DataManager;
import com.derofim.protectron.modules.blockGroup.BlocksConfig;
import com.derofim.protectron.modules.blockGroup.BlocksUtils;
import com.derofim.protectron.modules.limiter.BlockLimitConfig;
import com.derofim.protectron.modules.messages.MessagesConfig;
import com.derofim.protectron.util.Vars;

public class BlockBreakController {
	private static final boolean debugVerbose = false;
	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private static BlockBreakController instance = new BlockBreakController();
	private Logger lg = m.getLogger();
	private BlockBreakConfig bbc = BlockBreakConfig.getInstance();
	private MessagesConfig msg = MessagesConfig.getInstance();

	public static final BlockBreakController getInstance() {
		return instance;
	}

	private BlockBreakController() {
	}

	private boolean handleWorldGroup(BlockBreakEvent e, String worldName, String pGroup) {
		Block block = e.getBlock();
		List<String> blockSets = bbc.getStrList(BlockBreakConfig.getWorldGroup(worldName, pGroup));
		String blockTypeName = BlocksUtils.getBlockTypeName(block);
		String blockTypeFull = BlocksUtils.getBlockTypeFull(block);
		String blockIdName = BlocksUtils.getBlockIdName(block);
		String blockIdFull = BlocksUtils.getBlockIdFull(block);
		for (String setName : blockSets) {
			boolean isBlockInSet = BlocksConfig.containsBlockSet(setName, blockTypeFull)
					|| BlocksConfig.containsBlockSet(setName, blockTypeName)
					|| BlocksConfig.containsBlockSet(setName, blockIdFull)
					|| BlocksConfig.containsBlockSet(setName, blockIdName);
			String mode = bbc.getStr(BlockBreakConfig.getWorldMode(worldName));
			if (mode.equalsIgnoreCase("whitelist")) {
				if (!isBlockInSet)
					return true;
			} else {
				if (isBlockInSet)
					return true;
			}
		}
		return false;
	}

	// Returns true if action must be cancelled
	public boolean checkBlockBreak(BlockBreakEvent e) {
		String eventName = e.getEventName();
		Block block = e.getBlock();
		Location loc = block.getLocation();
		Player p = e.getPlayer();
		if (debugVerbose) {
			lg.info("eventName: " + eventName);
			lg.info("block: " + block.getType().name());
			lg.info("loc: " + loc.toString());
			lg.info("player: " + p.getDisplayName());
		}
		if (p.hasPermission("protectron.blocks.break.bypass")) {
			return false;
		}
		String worldName = block.getWorld().getName().toLowerCase();
		String pGroup = PermissionManager.getInstance().getPermission().getPrimaryGroup(p);
		if (bbc.getConfig().contains(BlockBreakConfig.getWorldGroup(worldName, pGroup))) {
			if (handleWorldGroup(e, worldName, pGroup)) {
				TreeMap<String, String> matches = new TreeMap<String, String>();
				matches.put("#blockname#", BlocksUtils.getLocalized(block));
				p.sendMessage(msg.prepareStr(MessagesConfig.MSG_NOT_ALLOWED_BREAK, matches));
				return true;
			}
		}
		return false;
	}
	
	public void storeBlockBreak(BlockBreakEvent e){
		if(e.getPlayer().hasPermission(Vars.PERM_BLOCKS_LIMITS)){
			return;
		}
		int configLimit = BlockLimitConfig.getPlayerConfigLimit(e.getPlayer(), e.getBlock());
		if (configLimit < 0) {
			return;
		}
		DataManager.deleteBlocksIdTable(e.getBlock());
	}
}
