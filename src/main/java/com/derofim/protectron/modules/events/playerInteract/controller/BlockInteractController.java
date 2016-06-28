package com.derofim.protectron.modules.events.playerInteract.controller;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.manager.ProtectionManager;
import com.derofim.protectron.modules.events.playerInteract.PlayerInteractConfig;
import com.derofim.protectron.modules.blockGroup.BlocksConfig;
import com.derofim.protectron.modules.blockGroup.BlocksUtils;
import com.derofim.protectron.modules.debug.DebugConfig;
import com.derofim.protectron.modules.events.playerInteract.PackManager;
import com.derofim.protectron.util.Vars;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class BlockInteractController {
	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	private WorldGuardPlugin wg = ProtectionManager.getInstance().getWorldGuard();
	private PackManager packEvent = PackManager.getInstance();
	private DebugConfig dcf = DebugConfig.getInstance();
	private BlocksConfig blc = BlocksConfig.getInstance();
	private PlayerInteractConfig acf = PlayerInteractConfig.getInstance();

	public BlockInteractController() {
	}

	private boolean checkEventByPack(PlayerInteractEvent e, String Pack) {
		Player p = (Player) e.getPlayer();

		// Возвращает true если название действия и пакета совпадают
		if (!packEvent.checkPackRegion(e, Pack))
			return false;

		if (dcf.getBool(DebugConfig.PARAM_DALL) && p.hasPermission(Vars.PERM_DBGPLG))
			lg.info("+++ " + Pack);

		// Перебирает наборы в пакете действий
		for (String it : acf.getStrList(Pack)) {
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

	// Проверка нажатия правой кнопки мыши
	// Возвращает true если действие нужно запретить
	public boolean checkBlockRC(PlayerInteractEvent e) {
		if (checkEventByPack(e, PlayerInteractConfig.RC_BG_EW)) {
			return true;
		} else if (checkEventByPack(e, PlayerInteractConfig.RC_BG_WG)) {
			return true;
		} else if (checkEventByPack(e, PlayerInteractConfig.RC_BG_MY)) {
			return true;
		}
		return false;
	}

	// Проверка нажатия левой кнопки мыши
	// Возвращает true если действие нужно запретить
	public boolean checkBlockLC(PlayerInteractEvent e) {
		if (checkEventByPack(e, PlayerInteractConfig.LC_BG_EW)) {
			return true;
		} else if (checkEventByPack(e, PlayerInteractConfig.LC_BG_WG)) {
			return true;
		} else if (checkEventByPack(e, PlayerInteractConfig.RC_BG_MY)) {
			return true;
		}
		return false;
	}

	// Проверка взаимодействия с блоком
	public boolean checkBlock(PlayerInteractEvent e) {
		Player p = (Player) e.getPlayer();
		if (p.hasPermission(Vars.PERM_ALL_BLOCKS_INTERACT))
			return false;
		Block b = e.getClickedBlock();
		String blockName = BlocksUtils.getBlockTypeFull(b);
		String blockIdName = BlocksUtils.getBlockIdFull(b);
		boolean canBuild = wg.canBuild(p, b.getLocation());
		if (dcf.getBool(DebugConfig.PARAM_DCLICKB) && p.hasPermission(Vars.PERM_DBGCLKB)) {
			lg.info("Clicked " + blockName + " (" + blockIdName + ") at " + b.getLocation() + ". Can interact: "
					+ canBuild);
		}
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && checkBlockRC(e)) {
			return true;
		}
		if (e.getAction().equals(Action.LEFT_CLICK_BLOCK) && checkBlockLC(e)) {
			return true;
		}
		return false;
	}
}
