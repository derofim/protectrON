package com.derofim.protectron.modules.events.pistonExtend;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPistonExtendEvent;
import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.manager.ProtectionManager;
import com.derofim.protectron.util.CommonUtils;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class PistonExtendController {
	private static final boolean debugVerbose = false;

	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	private WorldGuardPlugin wg = ProtectionManager.getInstance().getWorldGuard();

	public PistonExtendController() {
	}

	private boolean checkAffectedBlocks(BlockPistonExtendEvent e) {
		Block b = e.getBlock();
		List<Block> affectedBlocks = e.getBlocks();
		RegionManager regionManager = wg.getRegionManager(b.getWorld());
		ApplicableRegionSet set = regionManager.getApplicableRegions(b.getLocation());
		Set<String> pistonOwners = ProtectionManager.getInstance().getUserSet(set);
		if (debugVerbose) {
			for (String sUsr : pistonOwners) {
				lg.info("BlockPistonExtendEventHandler pistonOwners at " + b.getLocation() + ": user " + sUsr);
			}
			lg.info("BlockPistonExtendEventHandler affectedBlocks size " + affectedBlocks.size());
		}
		for (Block block : affectedBlocks) {
			set = regionManager.getApplicableRegions(block.getLocation());
			if (debugVerbose) {
				for (String sUsr : ProtectionManager.getInstance().getUserSet(set)) {
					lg.info("BlockPistonExtendEventHandler getUserSet at " + block.getLocation() + ": user " + sUsr);
				}
			}
			if (set != null && set.size() > 0 && (pistonOwners.isEmpty()
					|| !ProtectionManager.getInstance().getUserSet(set).containsAll(pistonOwners))) {
				if (debugVerbose) {
					lg.info("BlockPistonExtendEventHandler block " + b.getType().toString());
					lg.info("BlockPistonExtendEventHandler evtName " + e.getEventName());
				}
				CommonUtils.sendMessageToClosestPlayer(b.getLocation(), b.getLocation().getWorld().getPlayers());
				return true;
			}
		}

		return false;
	}

	// Возвращает true если действие запрещено
	public boolean checkPistonExtend(BlockPistonExtendEvent e) {
		List<Block> affectedBlocks = e.getBlocks();
		// Проверка владельца региона с поршннем и владельцев сдвигаемых блоков
		if ((affectedBlocks != null) && (affectedBlocks.size() > 0)) {
			if (checkAffectedBlocks(e)) {
				return true;
			}
		}
		return false;
	}
}