package com.derofim.protectron.modules.events.pistonExtend;

import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPistonExtendEvent;
import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;
import com.derofim.protectron.modules.limiter.BlockLimitHandler;

public class PistonExtendHandler extends AbstractEvent {
	private PistonExtendController pistonExtendController = null;

	public PistonExtendHandler() {
		pistonExtendController = new PistonExtendController();
	}

	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_PISTON_EXTEND;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	// Called when a piston extends
	@EventHandler(priority = EventPriority.LOWEST)
	public void BlockPistonExtendEventHandler(BlockPistonExtendEvent e) {
		if (handlePistonExtend(e))
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void blockPistonExtendEventMonitor(BlockPistonExtendEvent e) {
		List<Block> affectedBlocks = e.getBlocks();
		// Проверка владельца региона с поршннем и владельцев сдвигаемых блоков
		if ((affectedBlocks != null) && (affectedBlocks.size() > 0)) {
			for (Block b : affectedBlocks) {
				if (BlockLimitHandler.handleBlockProtection(b))
					e.setCancelled(true);
			}
		}
	}

	// Checks event
	public boolean handlePistonExtend(BlockPistonExtendEvent e) {
		if (pistonExtendController.checkPistonExtend(e)) {
			return true;
		}
		return false;
	}
}
