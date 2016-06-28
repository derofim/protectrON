package com.derofim.protectron.modules.events.playerCommandPreprocess;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;

public class PlayerCommandPreprocessHandler extends AbstractEvent {
	private PlayerCommandPreprocessController playerCommandPreprocess = PlayerCommandPreprocessController.getInstance();

	public PlayerCommandPreprocessHandler() {
	}
	
	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_CMD_PREPROC_ENABLED;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	// This event is called whenever a player runs a command, see
	// https://jd.bukkit.org/org/bukkit/event/player/PlayerCommandPreprocessEvent.html
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		if (handlePlayerCommandPreprocess(e))
			e.setCancelled(true);
	}

	// Checks event
	public boolean handlePlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		if (playerCommandPreprocess.checkPlayerCommandPreprocess(e)) {
			return true;
		}
		return false;
	}
}
