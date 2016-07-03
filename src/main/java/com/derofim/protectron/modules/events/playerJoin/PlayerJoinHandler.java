package com.derofim.protectron.modules.events.playerJoin;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;

public class PlayerJoinHandler extends AbstractEvent {
	private PlayerJoinController playerJoinController = null;
	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	private static final boolean debugVerbose = false;

	public PlayerJoinHandler() {
		playerJoinController = new PlayerJoinController();
	}

	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_PLAYER_JOIN;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	// This event is called when a player has successfully logged onto the server.
	// This is the event you use if you want to have a motd. Or just a welcome message.
	@EventHandler(priority = EventPriority.LOWEST)
	public void PlayerJoinEventHandler(PlayerJoinEvent e) {
		if (handlePlayerJoin(e))
			if (debugVerbose) {
				lg.info("PlayerJoinEventHandler: " + e.getPlayer().getName() + " joined");
			}
	}

	// Checks event
	public boolean handlePlayerJoin(PlayerJoinEvent e) {
		if (playerJoinController.checkPlayerJoin(e)) {
			return true;
		}
		return false;
	}
}
