package com.derofim.protectron.modules.events.blockPhysics;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPhysicsEvent;

import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;

public class BlockPhysicsHandler extends AbstractEvent {
	private BlockPhysicsController blockPhysics = BlockPhysicsController.getInstance();

	public BlockPhysicsHandler() {
	}
	
	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_BLOCKS_PHYSICS;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	// Thrown when a block physics check is called
	@EventHandler(priority = EventPriority.LOWEST)
	public void BlockPhysicsEventHandler(BlockPhysicsEvent e) {
		if (handleBlockPhysics(e))
			e.setCancelled(true);
	}

	// Проверки при событии
	public boolean handleBlockPhysics(BlockPhysicsEvent e) {
		if (blockPhysics.checkBlockPhysics(e)) {
			return true;
		}
		return false;
	}
}
