package com.derofim.protectron.modules.events.pistonExtend;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPistonExtendEvent;

import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;

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

	// Проверки при событии
	public boolean handlePistonExtend(BlockPistonExtendEvent e) {
		if (pistonExtendController.checkPistonExtend(e)) {
			return true;
		}
		return false;
	}
}
