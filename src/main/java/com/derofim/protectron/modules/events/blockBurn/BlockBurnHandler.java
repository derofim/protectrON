package com.derofim.protectron.modules.events.blockBurn;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBurnEvent;
import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;
import com.derofim.protectron.modules.limiter.BlockLimitHandler;

public class BlockBurnHandler extends AbstractEvent {
	public BlockBurnHandler() {
	}

	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_BLOCKS_BURN;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void blockBurnEventMonitor(BlockBurnEvent e) {
		if (BlockLimitHandler.handleBlockProtection(e.getBlock()))
			e.setCancelled(true);
	}
}
