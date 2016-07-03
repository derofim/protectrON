package com.derofim.protectron.modules.events.playerJoin;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.manager.ProtectionManager;
import com.derofim.protectron.manager.data.DataManager;
import com.derofim.protectron.modules.ModulesConfig;

public class PlayerJoinController {
	private static final boolean debugVerbose = false;

	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	ProtectionManager protMgr = ProtectionManager.getInstance();

	public PlayerJoinController() {
	}

	// Returns true if action allowed
	public boolean checkPlayerJoin(PlayerJoinEvent e) {
		if (!ModulesConfig.getInstance().getBool(ModulesConfig.MODULE_BLOCKS_LIMITER))
			return false;
		Player p = e.getPlayer();
		// ToDo track as stAnalytics p.hasPlayedBefore()
		if(debugVerbose){
			lg.info(p.getName()+" joined");
		}
		DataManager.insertUsersTable(p);
		return false;
	}
}
