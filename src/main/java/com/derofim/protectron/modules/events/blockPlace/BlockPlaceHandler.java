package com.derofim.protectron.modules.events.blockPlace;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;
import com.derofim.protectron.modules.limiter.BlockLimitConfig;

public class BlockPlaceHandler extends AbstractEvent {
	private BlockPlaceController blockPlace = BlockPlaceController.getInstance();

	public BlockPlaceHandler() {
	}

	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_BLOCKS_PLACE;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	// Thrown when a block physics check is called
	@EventHandler(priority = EventPriority.LOWEST)
	public void BlockPlaceEventHandler(BlockPlaceEvent e) {
		if (handleBlockPlace(e))
			e.setCancelled(true);
	}

	// Called after successfull event
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void BlockPlaceEventMonitor(BlockPlaceEvent e) {
		if (!ModulesConfig.getInstance().getBool(ModulesConfig.MODULE_BLOCKS_LIMITER))
			return;
		int configLimit = BlockLimitConfig.getPlayerConfigLimit(e.getPlayer(), e.getBlock());
		if (configLimit < 0) {
			return;
		}
		if (blockPlace.storeBlockPlace(e))
			e.setCancelled(true);
	}

	// Checks event
	public boolean handleBlockPlace(BlockPlaceEvent e) {
		if (blockPlace.checkBlockPlace(e)) {
			return true;
		}
		return false;
	}
}
