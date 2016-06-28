package com.derofim.protectron.modules.events.playerInteract.controller;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.manager.ProtectionManager;
import com.derofim.protectron.modules.blockGroup.BlocksConfig;
import com.derofim.protectron.modules.blockGroup.BlocksUtils;
import com.derofim.protectron.modules.debug.DebugConfig;
import com.derofim.protectron.modules.events.playerInteract.PackManager;
import com.derofim.protectron.modules.events.playerInteract.PlayerInteractConfig;
import com.derofim.protectron.util.Vars;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

// »спользует BlockInteractController дл¤ проверки имени блока
public class PhysicalInteractController {
	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	private WorldGuardPlugin wg = ProtectionManager.getInstance().getWorldGuard();
	private PackManager packEvent = PackManager.getInstance();
	private DebugConfig dcf = DebugConfig.getInstance();
	private BlocksConfig blc = BlocksConfig.getInstance();

	public PhysicalInteractController() {
	}

	// ¬озвращает true если проверка запрещена т.е. есть полный доступ
	private boolean checkConfiguration(PlayerInteractEvent e) {
		Player p = (Player) e.getPlayer();
		if (p.hasPermission(Vars.PERM_ALL_PHYSICAL_INTERACT))
			return true;
		if (dcf.getBool(DebugConfig.PARAM_DCLICKP) && p.hasPermission(Vars.PERM_DBGCLKP)) {
			Block b = e.getClickedBlock();
			String blockName = BlocksUtils.getBlockTypeFull(b);
			boolean canBuild = wg.canBuild(p, b.getLocation());
			lg.info("onPlayerInteract:PHYSICAL " + blockName + " at " + b.getLocation() + ". Can interact: "
					+ canBuild);
		}

		return false;
	}

	private boolean checkEventByPack(PlayerInteractEvent e, String Pack) {
		Player p = (Player) e.getPlayer();

		// ¬озвращает true если название действи¤ и пакета совпадают
		if (!packEvent.checkPackRegion(e, Pack))
			return false;

		if (dcf.getBool(DebugConfig.PARAM_DALL) && p.hasPermission(Vars.PERM_DBGPLG))
			lg.info("+++ " + Pack);

		// ѕеребирает наборы в пакете действий
		for (String it : blc.getStrList(Pack)) {
			if (dcf.getBool(DebugConfig.PARAM_DALL) && p.hasPermission(Vars.PERM_DBGPLG))
				lg.info("++ " + it);
			if (!blc.getConfig().contains(it) || !blc.getConfig().isList(it)) {
				lg.warning("Need fix config for " + Pack + " : " + it);
				continue;
			}
			List<String> contentInGroup = blc.getStrList(it);
			if (!contentInGroup.isEmpty()) {
				if (packEvent.checkGriefListByClickedBlock(e, contentInGroup, Pack))
					return true;
			} else {
				lg.warning("Need fix config for : " + it);
			}
		}
		return false;
	}

	// ѕроверка физического воздействи¤ на блок
	// ¬озвращает true если действие нужно запретить
	public boolean checkPhysical(PlayerInteractEvent e) {
		if (checkConfiguration(e))
			return false;

		if (checkEventByPack(e, PlayerInteractConfig.PHYSICAL_EW)) {
			return true;
		} else if (checkEventByPack(e, PlayerInteractConfig.PHYSICAL_WG)) {
			return true;
		} else if (checkEventByPack(e, PlayerInteractConfig.PHYSICAL_MY)) {
			return true;
		}

		return false;
	}
}
