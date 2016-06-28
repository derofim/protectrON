package com.derofim.protectron.modules.events.inventoryOpen;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryOpenEvent;

import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;

public class InventoryOpenHandler extends AbstractEvent {
	private InventoryOpenController inventoryOpenController = null;

	public InventoryOpenHandler() {
		inventoryOpenController = new InventoryOpenController();
	}
	
	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_INVENTORY_OPEN;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void InventoryOpenEventHandler(InventoryOpenEvent e) {
		if (handleInventoryOpen(e))
			e.setCancelled(true);
	}

	// Checks event
	public boolean handleInventoryOpen(InventoryOpenEvent e) {
		if (inventoryOpenController.checkInventoryOpen(e)) {
			return true;
		}
		return false;
	}
}
