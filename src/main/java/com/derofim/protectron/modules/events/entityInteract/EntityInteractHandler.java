package com.derofim.protectron.modules.events.entityInteract;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityInteractEvent;

import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;

public class EntityInteractHandler extends AbstractEvent {
	private EntityInteractController entityInteractController = null;

	public EntityInteractHandler() {
		entityInteractController = new EntityInteractController();
	}
	
	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_ENTITY_INTERACT;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	// Called when an entity interacts with an object.
	// For example, presesing wooden plate.
	@EventHandler(priority = EventPriority.LOWEST)
	public void EntityInteractEventHandler(EntityInteractEvent e) {
		if (handleEntityInteract(e))
			e.setCancelled(true);
	}

	// Checks event
	public boolean handleEntityInteract(EntityInteractEvent e) {
		if (entityInteractController.checkEntityInteract(e)) {
			return true;
		}
		return false;
	}
}
