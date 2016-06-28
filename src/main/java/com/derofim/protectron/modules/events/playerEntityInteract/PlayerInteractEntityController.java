package com.derofim.protectron.modules.events.playerEntityInteract;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.config.SettingsConfig;
import com.derofim.protectron.manager.ProtectionManager;
import com.derofim.protectron.modules.messages.MessagesConfig;
import com.derofim.protectron.util.Vars;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class PlayerInteractEntityController {
	private static final boolean debugVerbose = false;

	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	private WorldGuardPlugin wg = ProtectionManager.getInstance().getWorldGuard();
	private SettingsConfig st = SettingsConfig.getInstance();

	public PlayerInteractEntityController() {
	}

	private boolean checkRegion(Player p, Location loc) {
		return st.getBool(Vars.PARAM_DENY_PLAYER_INTERACT_WITH_ENTITY_IN_FOREIGN_REGION) && !wg.canBuild(p, loc);
	}

	// Возвращает true если действие запрещено
	@SuppressWarnings("deprecation")
	public boolean checkPlayerInteractEntity(PlayerInteractEntityEvent e) {
		Entity ent = e.getRightClicked();
		Player p = e.getPlayer();
		if (p.hasPermission(Vars.PERM_INTERACT_ENTITY_EVERYWHERE))
			return false;
		if (checkRegion(p, ent.getLocation())) {
			if (debugVerbose) {
				lg.info("PlayerInteractEntity e.getPlayer()  " + e.getPlayer());
				lg.info("PlayerInteractEntity id  " + ent.getEntityId());
				lg.info("PlayerInteractEntity location " + ent.getLocation());
				lg.info("PlayerInteractEntity type  " + ent.getType().toString());
				lg.info("PlayerInteractEntity name  " + ent.getType().getName());
			}
			p.sendMessage(MessagesConfig.getInstance().getStr(MessagesConfig.MSG_NOT_ALLOWED));
			return true;
		}
		return false;
	}
}
