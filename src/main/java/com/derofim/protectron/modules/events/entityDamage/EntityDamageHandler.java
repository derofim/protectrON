package com.derofim.protectron.modules.events.entityDamage;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;

public class EntityDamageHandler extends AbstractEvent {
	private EntityDamageController entityDamageController = null;

	public EntityDamageHandler() {
		entityDamageController = new EntityDamageController();
	}

	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_ENTITY_DAMAGE;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	// Stores data for damage events:
	// * EntityDamageByBlockEvent - Called when entity is damaged by block
	// * EntityDamageByEntityEvent - Called when entity is damaged by entity
	@EventHandler(priority = EventPriority.LOWEST)
	public void EntityDamageEventHandler(EntityDamageEvent e) {
		if (handleEntityDamage(e))
			e.setCancelled(true);
	}

	// Checks event
	public boolean handleEntityDamage(EntityDamageEvent e) {
		if (entityDamageController.checkEntityDamage(e)) {
			return true;
		}
		return false;
	}
}
