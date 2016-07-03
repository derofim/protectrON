package com.derofim.protectron.modules.events.playerQuit;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;
import com.derofim.protectron.modules.events.playerQuit.PlayerQuitController;

public class PlayerQuitHandler  extends AbstractEvent {
	private PlayerQuitController playerQuitController = null;
	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	private static final boolean debugVerbose = false;

	public PlayerQuitHandler() {
		playerQuitController = new PlayerQuitController();
	}

	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_PLAYER_QUIT;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	// This event is called when a player has successfully logged onto the server.
	// This is the event you use if you want to have a motd. Or just a welcome message.
	@EventHandler(priority = EventPriority.LOWEST)
	public void PlayerQuitEventHandler(PlayerQuitEvent e) {
		if (handlePlayerQuit(e))
			if (debugVerbose) {
				lg.info("PlayerQuitEventHandler: " + e.getPlayer().getName() + " Quited");
			}
	}

	// Checks event
	public boolean handlePlayerQuit(PlayerQuitEvent e) {
		if (playerQuitController.checkPlayerQuit(e)) {
			return true;
		}
		return false;
	}

}
