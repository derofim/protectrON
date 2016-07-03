package com.derofim.protectron.modules.events.blockBreak;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;
import com.derofim.protectron.modules.limiter.BlockLimitConfig;

public class BlockBreakHandler extends AbstractEvent {
	private BlockBreakController blockBreak = BlockBreakController.getInstance();

	public BlockBreakHandler() {
	}

	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_BLOCKS_BREAK;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	// Thrown when a block physics check is called
	@EventHandler(priority = EventPriority.LOWEST)
	public void BlockBreakEventHandler(BlockBreakEvent e) {
		if (handleBlockBreak(e))
			e.setCancelled(true);
	}

	// Called after successfull event
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void BlockBreakEventMonitor(BlockBreakEvent e) {
		if (!ModulesConfig.getInstance().getBool(ModulesConfig.MODULE_BLOCKS_LIMITER))
			return;
		if (BlockLimitConfig.isBlockProtected(e.getBlock())) {
			blockBreak.storeBlockBreak(e);
		}
	}

	// Checks event
	public boolean handleBlockBreak(BlockBreakEvent e) {
		if (e.getBlock().getType().equals(Material.AIR))
			return false;
		if (blockBreak.checkBlockBreak(e)) {
			return true;
		}
		return false;
	}
}
