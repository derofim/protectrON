package com.derofim.protectron.modules.events.playerInteract.controller;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.manager.ProtectionManager;
import com.derofim.protectron.modules.events.playerInteract.PlayerInteractConfig;
import com.derofim.protectron.modules.itemGroup.ItemsConfig;
import com.derofim.protectron.modules.itemGroup.itemsUtils;
import com.derofim.protectron.modules.blockGroup.BlocksUtils;
import com.derofim.protectron.modules.debug.DebugConfig;
import com.derofim.protectron.modules.events.playerInteract.PackManager;
import com.derofim.protectron.util.CommonVars;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class ItemInteractController {
	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	private WorldGuardPlugin wg = ProtectionManager.getInstance().getWorldGuard();
	private PackManager packEvent = PackManager.getInstance();
	private ItemBlockInteractController itemBlockInteract = ItemBlockInteractController.getInstance();
	private DebugConfig dcf = DebugConfig.getInstance();
	private ItemsConfig icf = ItemsConfig.getInstance();
	private PlayerInteractConfig acf = PlayerInteractConfig.getInstance();

	public ItemInteractController() {
	}

	private boolean checkEventByPack(PlayerInteractEvent e, String Pack) {
		Player p = (Player) e.getPlayer();

		// Возвращает true если название действия и пакета совпадают
		if (!packEvent.checkPackRegion(e, Pack))
			return false;

		if (dcf.getBool(CommonVars.PARAM_DALL) && p.hasPermission(CommonVars.PERM_DBGPLG))
			lg.info("+++ " + Pack);

		// Проверка по региону можно ли использовать предмет на блоках
		ItemStack itm = e.getItem();
		if (itemBlockInteract.ItemWithBlockInteractCheck(e, itm, Pack)) {
			return true;
		}

		// Перебирает наборы в пакете действий
		for (String it : acf.getStrList(Pack)) {
			if (dcf.getBool(CommonVars.PARAM_DALL) && p.hasPermission(CommonVars.PERM_DBGPLG))
				lg.info("++ " + it);
			if (!icf.getConfig().contains(it) || !icf.getConfig().isList(it)) {
				lg.warning("Need fix config for " + Pack + " : " + it);
				continue;
			}
			List<String> contentInGroup = icf.getStrList(it);
			if (!contentInGroup.isEmpty()) {
				if (packEvent.checkGriefListByItem(e, contentInGroup, Pack))
					return true;
			} else {
				lg.warning("Need fix config for : " + it);
			}
		}
		return false;
	}

	// Проверка нажатия правой кнопки мыши по блоку с предметом в руке
	// Возвращает true если действие нужно запретить
	public boolean checkItemRC(PlayerInteractEvent e) {
		if (checkEventByPack(e, CommonVars.ITEM_RC_EW)) {
			return true;
		} else if (checkEventByPack(e, CommonVars.ITEM_RC_WG)) {
			return true;
		} else if (checkEventByPack(e, CommonVars.ITEM_RC_MY)) {
			return true;
		}
		return false;
	}

	// Проверка нажатия левой кнопки мыши по блоку с предметом в руке
	// Возвращает true если действие нужно запретить
	public boolean checkItemLC(PlayerInteractEvent e) {
		if (checkEventByPack(e, CommonVars.ITEM_LC_EW)) {
			return true;
		} else if (checkEventByPack(e, CommonVars.ITEM_LC_WG)) {
			return true;
		} else if (checkEventByPack(e, CommonVars.ITEM_LC_MY)) {
			return true;
		}
		return false;
	}

	// Возвращает true если действие запрещено
	public boolean checkItem(PlayerInteractEvent e) {
		Player p = (Player) e.getPlayer();
		if (p.hasPermission(CommonVars.PERM_ALL_ITEMS_INTERACT))
			return false;
		ItemStack itm = e.getItem();
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			Block b = e.getClickedBlock();
			String itemName = itemsUtils.getItemTypeFull(itm);
			String blockName = BlocksUtils.getBlockTypeFull(b);
			String blockIdName = BlocksUtils.getBlockIdFull(b);
			boolean canBuild = wg.canBuild(p, b.getLocation());
			if (dcf.getBool(CommonVars.PARAM_DCLICKI) && p.hasPermission(CommonVars.PERM_DBGCLKI)) {
				lg.info("Clicked " + blockName + " (" + blockIdName + ") at " + b.getLocation() + " with item "
						+ itemName + ". Can interact: " + canBuild);
			}
			if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && checkItemRC(e)) {
				return true;
			}
			if (e.getAction().equals(Action.LEFT_CLICK_BLOCK) && checkItemLC(e)) {
				return true;
			}
		}
		return false;
	}
}
