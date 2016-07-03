package com.derofim.protectron.modules.limiter;

import java.util.TreeMap;
import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.manager.data.DataManager;
import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.blockGroup.BlocksUtils;
import com.derofim.protectron.modules.messages.MessagesConfig;
import com.derofim.protectron.util.Utils;

public class BlockLimitHandler {
	private static final boolean debugVerbose = false;
	private static ProtectronPlugin m = ProtectronPlugin.getInstance();
	private static BlockLimitHandler instance = new BlockLimitHandler();
	private static Logger lg = m.getLogger();
	private static MessagesConfig msg = MessagesConfig.getInstance();
	private static ModulesConfig mcf = ModulesConfig.getInstance();

	public static final BlockLimitHandler getInstance() {
		return instance;
	}

	private BlockLimitHandler() {
	}

	// Returns true if block may be protected by limiter
	public static boolean handleBlockProtection(Block b) {
		if (!mcf.getBool(ModulesConfig.MODULE_BLOCKS_LIMITER))
			return false;
		if (BlockLimitConfig.isBlockProtected(b)) {
			TreeMap<String, String> matches = new TreeMap<String, String>();
			matches.put("#blockname#", BlocksUtils.getLocalized(b));
			matches.put("#location#", "(" + b.getX() + "," + b.getY() + "," + b.getZ() + ")");
			Utils.notifyClosestPlayer(b.getLocation(), b.getLocation().getWorld().getPlayers(),
					MessagesConfig.getInstance().prepareStr(MessagesConfig.MSG_BLOCK_PROTECTED, matches), 5);
			return true;
		}
		return false;
	}

	// Returns true if limit already reached
	public static boolean checkBlockLimit(Block b, Player p) {
		int configLimit = BlockLimitConfig.getPlayerConfigLimit(p, b);
		if (configLimit < 0) {
			if (debugVerbose) {
				lg.info("configLimit<0 for " + p.getName());
				return false;
			}
		}
		if (debugVerbose) {
			lg.info("configLimit for player " + p.getName() + " = " + configLimit);
		}
		int pId = DataManager.findUsersIdTable(p);
		DataManager.updateBlocksIdTable(pId, p.getWorld());
		int countBlocks = -99;
		if (BlockLimitConfig.isPlayerConfigLimitWithMeta(p, b)) {
			countBlocks = DataManager.countBlocksTable(pId, BlocksUtils.getBlockId(b), BlocksUtils.getBlockData(b),
					b.getLocation());
		} else {
			countBlocks = DataManager.countBlocksTable(pId, BlocksUtils.getBlockId(b), b.getLocation());
		}
		if (debugVerbose) {
			lg.info("countBlocks for getBlockId(" + BlocksUtils.getBlockId(b) + ") = " + countBlocks);
		}
		if (countBlocks < 0)
			return false;
		if (countBlocks >= configLimit) {
			TreeMap<String, String> matches = new TreeMap<String, String>();
			matches.put("#blockname#", BlocksUtils.getLocalized(b));
			p.sendMessage(msg.getStr(MessagesConfig.MSG_LIMIT_REACHED)
					+ msg.prepareStr(MessagesConfig.MSG_LIMIT_BLOCK, matches) + configLimit);
			return true;
		}
		return false;
	}
}
