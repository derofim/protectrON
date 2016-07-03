package com.derofim.protectron.modules.events.blockFromTo;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockFromToEvent;

import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;

public class BlockFromToHandler extends AbstractEvent {
	private BlockFromToController blockFromToController = BlockFromToController.getInstance();
	public BlockFromToHandler() {
	}

	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_BLOCKS_MOVEMENT;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	// Represents events with a source block and a destination block.
	// Currently only applies to liquid and teleporting dragon eggs.
	// If a Block From To event is cancelled, the block will not move
	@EventHandler(priority = EventPriority.LOWEST)
	public void BlockFromToEventHandler(BlockFromToEvent e) {
		if (handleBlockFromTo(e))
			e.setCancelled(true);
	}

	// Checks event
	public boolean handleBlockFromTo(BlockFromToEvent e) {
		if (blockFromToController.checkBlockFromTo(e)) {
			return true;
		}
		return false;
	}
}
