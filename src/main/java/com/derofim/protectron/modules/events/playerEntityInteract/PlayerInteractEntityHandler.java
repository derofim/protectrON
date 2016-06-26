package com.derofim.protectron.modules.events.playerEntityInteract;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;

public class PlayerInteractEntityHandler extends AbstractEvent {
	private PlayerInteractEntityController playerEntityInteractController = null;

	public PlayerInteractEntityHandler() {
		playerEntityInteractController = new PlayerInteractEntityController();
	}
	
	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_PLAYER_INTERACT_ENITY;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	// Represents an event that is called when a player right clicks an entity.
	// May be used to stop talking with npc
	@EventHandler(priority = EventPriority.LOWEST)
	public void PlayerInteractEntity(PlayerInteractEntityEvent e) {
		if (handlePlayerInteractEntity(e))
			e.setCancelled(true);
	}

	// Проверки при событии
	public boolean handlePlayerInteractEntity(PlayerInteractEntityEvent e) {
		if (playerEntityInteractController.checkPlayerInteractEntity(e)) {
			return true;
		}
		return false;
	}
}
