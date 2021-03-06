package com.derofim.protectron.modules.events.entityExplode;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;
import com.derofim.protectron.modules.limiter.BlockLimitHandler;

public class EntityExplodeHandler extends AbstractEvent {
	private EntityExplodeController entityExplodeController = null;

	public EntityExplodeHandler() {
		entityExplodeController = new EntityExplodeController();
	}

	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_ENTITY_EXPLODE;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	// Called when an entity explodes
	@EventHandler(priority = EventPriority.LOWEST)
	public void EntityExplodeEventHandler(EntityExplodeEvent e) {
		if (handleEntityExplode(e))
			e.setCancelled(true);
	}

	// Checks event
	public boolean handleEntityExplode(EntityExplodeEvent e) {
		if (entityExplodeController.checkEntityExplode(e)) {
			return true;
		}
		return false;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void entityExplodeEventMonitor(EntityExplodeEvent e) {
		for (Block b : e.blockList()) {
			if (BlockLimitHandler.handleBlockProtection(b))
				e.setCancelled(true);
		}
	}
}
