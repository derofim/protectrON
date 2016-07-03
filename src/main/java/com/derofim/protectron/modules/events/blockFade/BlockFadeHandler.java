package com.derofim.protectron.modules.events.blockFade;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockFadeEvent;

import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;
import com.derofim.protectron.modules.limiter.BlockLimitHandler;

public class BlockFadeHandler extends AbstractEvent {
	public BlockFadeHandler() {
	}

	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_BLOCKS_FADE;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void blockFadeEventMonitor(BlockFadeEvent e) {
		if (BlockLimitHandler.handleBlockProtection(e.getBlock()))
			e.setCancelled(true);
	}
}
