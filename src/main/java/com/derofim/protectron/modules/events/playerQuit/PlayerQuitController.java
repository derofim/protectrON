package com.derofim.protectron.modules.events.playerQuit;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.manager.ProtectionManager;
import com.derofim.protectron.manager.data.DataManager;
import com.derofim.protectron.modules.ModulesConfig;

public class PlayerQuitController {
	private static final boolean debugVerbose = false;

	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	ProtectionManager protMgr = ProtectionManager.getInstance();

	public PlayerQuitController() {
	}

	// Returns true if action allowed
	public boolean checkPlayerQuit(PlayerQuitEvent e) {
		if (!ModulesConfig.getInstance().getBool(ModulesConfig.MODULE_BLOCKS_LIMITER))
			return false;
		Player p = e.getPlayer();
		// ToDo track as stAnalytics
		if(debugVerbose){
			lg.info(p.getName()+" Quited");
		}
		DataManager.insertUsersTable(p);
		return false;
	}
}
