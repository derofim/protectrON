package com.derofim.protectron.modules.events.playerInteract;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.manager.ProtectionManager;
import com.derofim.protectron.modules.blockGroup.BlocksUtils;
import com.derofim.protectron.modules.debug.DebugConfig;
import com.derofim.protectron.modules.itemGroup.itemsUtils;
import com.derofim.protectron.modules.messages.MessagesConfig;
import com.derofim.protectron.util.CommonVars;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

/* 
 * Управление пакетами действий
 * 
 * Пакет (pack) действия (нажатие кнопки и т.п.) хранит названия наборов вещей которые будут проверяться.
 * Наборы (set) это любая группа (по названию мода и т.п.) предметов или блоков.
 * Например, можно указать для каких модов будет проверяться нажатие правой кнопки мыши.
 * Для этого в пакете "нажатие правой кнопки мыши" указываются наборы вида "INDUSTRIALCRAFT, BUILDCRAFT" и т.д.
 * В самих наборах указываются названия предметов. Можно создавать собственные наборы с любым названием и вещами.
 * 
 * @author Derofim
 */
public class PackManager {
	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	private WorldGuardPlugin wg = ProtectionManager.getInstance().getWorldGuard();
	private MessagesConfig msg = MessagesConfig.getInstance();
	private DebugConfig dcf = DebugConfig.getInstance();

	private static PackManager instance = new PackManager();

	public static final PackManager getInstance() {
		return instance;
	}

	private PackManager() {
	}

	// Перебирает предметы в наборе
	// Возвращает true если действие нужно запретить
	public boolean checkGriefListByItem(PlayerInteractEvent e, List<String> namesList, String Pack) {
		if (!e.hasItem())
			return false;

		Player p = (Player) e.getPlayer();
		ItemStack b = e.getItem();

		// Проверка по региону можно ли использовать предмет на любых блоках
		for (String itStack : namesList) {
			if (dcf.getBool(CommonVars.PARAM_DALL) && p.hasPermission(CommonVars.PERM_DBGPLG))
				lg.info("+ " + itStack);
			if (itemsUtils.checkEqualItem(b, itStack)) {
				String msgProt = "";
				if (!wg.canBuild(p, e.getClickedBlock().getLocation()))
					msgProt = msg.getStr(CommonVars.MSG_REG_PROTECTED);

				if (dcf.getBool(CommonVars.PARAM_DINFORM) && p.hasPermission(CommonVars.PERM_INFORM)) {
					p.sendMessage(msg.getStr(CommonVars.MSG_NOT_ALLOWED) + " " + itStack + ". " + msgProt);
				}
				if (dcf.getBool(CommonVars.PARAM_DGRIEFB) && p.hasPermission(CommonVars.PERM_DPROT)) {
					lg.info("Grief attempt by " + p.getDisplayName() + " with item " + itStack + "at"
							+ p.getLocation());
				}
				return true;
			}
		}
		return false;
	}

	// Перебирает блоки в наборе
	// Возвращает true если действие нужно запретить
	public boolean checkGriefListByClickedBlock(PlayerInteractEvent e, List<String> griefList, String Pack) {
		Player p = (Player) e.getPlayer();
		Block b = e.getClickedBlock();
		for (String itBlock : griefList) {
			if (dcf.getBool(CommonVars.PARAM_DALL) && p.hasPermission(CommonVars.PERM_DBGPLG))
				lg.info("+ " + itBlock);
			if (BlocksUtils.checkEqualBlock(b, itBlock)) {
				if (dcf.getBool(CommonVars.PARAM_DINFORM) && p.hasPermission(CommonVars.PERM_INFORM)) {
					String msgProt = "";
					if (!wg.canBuild(p, e.getClickedBlock().getLocation()))
						msgProt = msg.getStr(CommonVars.MSG_REG_PROTECTED);
					p.sendMessage(msg.getStr(CommonVars.MSG_NOT_ALLOWED) + " " + itBlock + ". " + msgProt);
				}
				if (dcf.getBool(CommonVars.PARAM_DGRIEFB) && p.hasPermission(CommonVars.PERM_DPROT)) {
					lg.info("Grief attempt by " + p.getDisplayName() + " with block " + itBlock + "at"
							+ p.getLocation());
				}
				return true;
			}
		}
		return false;
	}

	// Проверка региона
	// Возвращает true если название действия и пакета совпадают
	public boolean checkPackRegion(PlayerInteractEvent e, String Pack) {
		Player p = (Player) e.getPlayer();
		if (Pack.equals(CommonVars.RC_BG_WG) || Pack.equals(CommonVars.LC_BG_WG) || Pack.equals(CommonVars.PHYSICAL_WG)
				|| Pack.equals(CommonVars.ITEM_RC_WG) || Pack.equals(CommonVars.ITEM_LC_WG)) {
			if (!wg.canBuild(p, e.getClickedBlock().getLocation())) {
				return true;
			}
		} else if (Pack.equals(CommonVars.RC_BG_MY) || Pack.equals(CommonVars.LC_BG_MY)
				|| Pack.equals(CommonVars.PHYSICAL_MY) || Pack.equals(CommonVars.ITEM_RC_MY)
				|| Pack.equals(CommonVars.ITEM_LC_MY)) {
			if (wg.canBuild(p, e.getClickedBlock().getLocation())) {
				return true;
			}
		} else if (Pack.equals(CommonVars.RC_BG_EW) || Pack.equals(CommonVars.LC_BG_EW)
				|| Pack.equals(CommonVars.PHYSICAL_EW) || Pack.equals(CommonVars.ITEM_RC_EW)
				|| Pack.equals(CommonVars.ITEM_LC_EW)) {
			return true;
		}
		return false;
	}
}
