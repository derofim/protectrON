package com.derofim.protectron.modules.events.blockPlace;

import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.manager.PermissionManager;
import com.derofim.protectron.manager.data.DataManager;
import com.derofim.protectron.modules.blockGroup.BlocksConfig;
import com.derofim.protectron.modules.blockGroup.BlocksUtils;
import com.derofim.protectron.modules.limiter.BlockLimitHandler;
import com.derofim.protectron.modules.messages.MessagesConfig;
import com.derofim.protectron.util.Vars;

public class BlockPlaceController {
	private static final boolean debugVerbose = false;
	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private static BlockPlaceController instance = new BlockPlaceController();
	private Logger lg = m.getLogger();
	private BlockPlaceConfig bpc = BlockPlaceConfig.getInstance();
	private MessagesConfig msg = MessagesConfig.getInstance();;

	public static final BlockPlaceController getInstance() {
		return instance;
	}

	private BlockPlaceController() {
	}

	private boolean handleWorldGroup(BlockPlaceEvent e, String worldName, String pGroup) {
		Block block = e.getBlock();
		List<String> blockSets = bpc.getStrList(BlockPlaceConfig.getWorldGroup(worldName, pGroup));
		String blockTypeName = BlocksUtils.getBlockTypeName(block);
		String blockTypeFull = BlocksUtils.getBlockTypeFull(block);
		String blockIdName = BlocksUtils.getBlockIdName(block);
		String blockIdFull = BlocksUtils.getBlockIdFull(block);
		for (String setName : blockSets) {
			boolean isBlockInSet = BlocksConfig.containsBlockSet(setName, blockTypeFull)
					|| BlocksConfig.containsBlockSet(setName, blockTypeName)
					|| BlocksConfig.containsBlockSet(setName, blockIdFull)
					|| BlocksConfig.containsBlockSet(setName, blockIdName);
			String mode = bpc.getStr(BlockPlaceConfig.getWorldMode(worldName));
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
	public boolean checkBlockPlace(BlockPlaceEvent e) {
		boolean canBuild = e.canBuild();
		Block blockAgainst = e.getBlockAgainst();
		Block blockPlaced = e.getBlockPlaced();
		Location locReplaced = e.getBlockReplacedState().getLocation();
		ItemStack item = e.getItemInHand();
		Player p = e.getPlayer();
		if (debugVerbose) {
			lg.info("canBuild: " + canBuild);
			lg.info("blockAgainst: " + blockAgainst.getType().name());
			lg.info("blockPlaced: " + blockPlaced.getType().name());
			lg.info("locReplaced: " + locReplaced.toString());
			lg.info("item: " + item.getType().toString());
			lg.info("player: " + p.getDisplayName());
		}
		if (p.hasPermission("protectron.blocks.place.bypass")) {
			return false;
		}
		String worldName = blockPlaced.getWorld().getName().toLowerCase();
		String pGroup = PermissionManager.getInstance().getPermission().getPrimaryGroup(p);
		if (bpc.getConfig().contains(BlockPlaceConfig.getWorldGroup(worldName, pGroup))) {
			if (handleWorldGroup(e, worldName, pGroup)) {
				TreeMap<String, String> matches = new TreeMap<String, String>();
				matches.put("#blockname#", BlocksUtils.getLocalized(blockPlaced));
				p.sendMessage(msg.prepareStr(MessagesConfig.MSG_NOT_ALLOWED_PLACE, matches));
				return true;
			}
		}
		return false;
	}

	public boolean storeBlockPlace(BlockPlaceEvent e) {
		if (e.getPlayer().hasPermission(Vars.PERM_BLOCKS_LIMITS) || !e.canBuild()) {
			return false;
		}
		if (!BlockLimitHandler.checkBlockLimit(e.getBlock(), e.getPlayer()))
			DataManager.insertBlocksTable(e.getBlock(), e.getPlayer());
		else
			return true;
		return false;
	}
}
