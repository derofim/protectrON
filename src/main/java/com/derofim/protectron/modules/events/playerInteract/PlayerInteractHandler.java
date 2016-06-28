package com.derofim.protectron.modules.events.playerInteract;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;
import com.derofim.protectron.modules.events.playerInteract.controller.BlockInteractController;
import com.derofim.protectron.modules.events.playerInteract.controller.ItemInteractController;
import com.derofim.protectron.modules.events.playerInteract.controller.PhysicalInteractController;

public class PlayerInteractHandler extends AbstractEvent {

	private ItemInteractController itemInteractController = null;
	private BlockInteractController blockInteractController = null;
	private PhysicalInteractController physicalInteractController = null;

	public PlayerInteractHandler() {
		blockInteractController = new BlockInteractController();
		itemInteractController = new ItemInteractController();
		physicalInteractController = new PhysicalInteractController();
	}
	
	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_PLAYER_INTERACT;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	/**
	 * Called when a player interacts with an object or air. Action may be:
	 * LEFT_CLICK_AIR - Left-clicking the air LEFT_CLICK_BLOCK - Left-clicking a
	 * block PHYSICAL - Stepping onto or into a block, jump RIGHT_CLICK_AIR -
	 * Right-clicking the air RIGHT_CLICK_BLOCK - Right-clicking a block
	 **/
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (handlePlayerInteract(e))
			e.setCancelled(true);
	}

	// Проверка нажатия предметом
	// Возвращает true если действие нужно запретить
	private boolean detectItemClick(PlayerInteractEvent e) {
		if (!ModulesConfig.getInstance().getBool(ModulesConfig.MODULE_PLAYER_INTERACT_ITEM))
			return false;
		if (e.hasItem()) {
			if (itemInteractController.checkItem(e)) {
				return true;
			}
		}
		return false;
	}

	// Проверка нажатия мышью на блок
	// Возвращает true если действие нужно запретить
	private boolean detectBlockClick(PlayerInteractEvent e) {
		if (!ModulesConfig.getInstance().getBool(ModulesConfig.MODULE_PLAYER_INTERACT_BLOCK))
			return false;
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			if (blockInteractController.checkBlock(e)) {
				return true;
			}
		}
		return false;
	}

	// Проверка физического воздействия на блок
	// Возвращает true если действие нужно запретить
	private boolean detectPhysical(PlayerInteractEvent e) {
		if (!ModulesConfig.getInstance().getBool(ModulesConfig.MODULE_PLAYER_INTERACT_PHYSICAL))
			return false;
		if (e.getAction().equals(Action.PHYSICAL)) {
			if (physicalInteractController.checkPhysical(e)) {
				return true;
			}
		}
		return false;
	}

	// Checks event
	public boolean handlePlayerInteract(PlayerInteractEvent e) {
		if (detectItemClick(e))
			return true;
		else if (detectBlockClick(e))
			return true;
		else if (detectPhysical(e))
			return true;
		return false;
	}
}
