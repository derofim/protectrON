package com.derofim.protectron.modules.events.BlockIgnite;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockIgniteEvent;

import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;
import com.derofim.protectron.modules.limiter.BlockLimitHandler;

public class BlockIgniteHandler extends AbstractEvent {

	public BlockIgniteHandler() {
	}

	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_BLOCKS_IGNITE;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void blockIgniteEventMonitor(BlockIgniteEvent e) {
		if (BlockLimitHandler.handleBlockProtection(e.getBlock()))
			e.setCancelled(true);
	}
}
