package com.derofim.protectron.modules.events.playerInteract.controller;

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
import com.derofim.protectron.modules.events.playerInteract.PlayerInteractConfig;
import com.derofim.protectron.modules.itemGroup.ItemsConfig;
import com.derofim.protectron.modules.itemGroup.itemsUtils;
import com.derofim.protectron.modules.messages.MessagesConfig;
import com.derofim.protectron.util.Vars;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class ItemBlockInteractController {

	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	private WorldGuardPlugin wg = ProtectionManager.getInstance().getWorldGuard();
	private MessagesConfig msg = MessagesConfig.getInstance();
	private DebugConfig dcf = DebugConfig.getInstance();
	private ItemsConfig icf = ItemsConfig.getInstance();
	private static final boolean debugVerbose = false;

	private ItemBlockInteractController() {
	}

	private static ItemBlockInteractController instance = new ItemBlockInteractController();

	public static final ItemBlockInteractController getInstance() {
		return instance;
	}

	// Проверка запрета взаимодействия предмета и блока
	// Возвращает true если действие нужно запретить
	public boolean checkBlockItemDeny(PlayerInteractEvent e, String ItemConfigName) {
		if (debugVerbose)
			lg.info("Checking checkBlockItemDeny for " + ItemConfigName);
		Player p = (Player) e.getPlayer();
		Block b = e.getClickedBlock();
		if (icf.getConfig().contains(ItemConfigName)) {
			List<String> listDenyItemRightClickOnBocks = icf.getStrList(ItemConfigName);
			for (String itBlock : listDenyItemRightClickOnBocks) {
				if (dcf.getBool(DebugConfig.PARAM_DALL) && p.hasPermission(Vars.PERM_DBGPLG))
					lg.info("# checking block " + itBlock + " for item " + ItemConfigName);
				if (BlocksUtils.checkEqualBlock(b, itBlock)) {
					if (dcf.getBool(DebugConfig.PARAM_DINFORM) && p.hasPermission(Vars.PERM_INFORM)) {
						String msgProt = "";
						String itmName = itemsUtils.getItemTypeFull(e.getItem());
						if (!wg.canBuild(p, e.getClickedBlock().getLocation()))
							msgProt = msg.getStr(MessagesConfig.MSG_REG_PROTECTED);
						p.sendMessage(msg.getStr(MessagesConfig.MSG_NOT_ALLOWED) + " " + itBlock + " & " + itmName + ". "
								+ msgProt);
					}
					if (dcf.getBool(DebugConfig.PARAM_DGRIEFB) && p.hasPermission(Vars.PERM_DPROT)) {
						lg.info("Grief attempt by " + p.getDisplayName() + " with " + itBlock + "&" + ItemConfigName
								+ "at" + p.getLocation());
					}
					return true;
				}
			}
		}
		return false;
	}

	// Проверка с учетом региона
	// Можно ли использовать предмет на определенных блоках
	// Возвращает true если действие нужно запретить
	public boolean ItemWithBlockInteractCheck(PlayerInteractEvent e, ItemStack itm, String Pack) {
		if (debugVerbose)
			lg.info("ItemWithBlockInteractCheck for" + Pack);
		String configName = ""; // ToDo: Item metadata
		String ConfigDenyItemRightClickOnBocks = Vars.PrefixClickMouseRight + "."
				+ itemsUtils.getItemTypeName(itm) + "." + Vars.SuffixOnBlocksEverywhere;
		String ConfigDenyItemLeftClickOnBocks = Vars.PrefixClickMouseLeft + "." + itemsUtils.getItemTypeName(itm)
				+ "." + Vars.SuffixOnBlocksEverywhere;
		String ConfigDenyItemRightClickOnBocksInForeignRegion = Vars.PrefixClickMouseRight + "."
				+ itemsUtils.getItemTypeName(itm) + "." + Vars.SuffixOnBlocksInForeignRegion;
		String ConfigDenyItemLeftClickOnBocksInForeignRegion = Vars.PrefixClickMouseLeft + "."
				+ itemsUtils.getItemTypeName(itm) + "." + Vars.SuffixOnBlocksInForeignRegion;
		String ConfigDenyItemRightClickOnBocksInMyRegion = Vars.PrefixClickMouseRight + "."
				+ itemsUtils.getItemTypeName(itm) + "." + Vars.SuffixOnBlocksInMyRegion;
		String ConfigDenyItemLeftClickOnBocksInMyRegion = Vars.PrefixClickMouseLeft + "."
				+ itemsUtils.getItemTypeName(itm) + "." + Vars.SuffixOnBlocksInMyRegion;

		Player p = (Player) e.getPlayer();
		if (!wg.canBuild(p, e.getClickedBlock().getLocation())) {
			if (Pack.equals(PlayerInteractConfig.ITEM_RC_WG)) {
				configName = ConfigDenyItemRightClickOnBocksInForeignRegion;
			} else if (Pack.equals(PlayerInteractConfig.ITEM_LC_WG)) {
				configName = ConfigDenyItemLeftClickOnBocksInForeignRegion;
			} else
				return false;
		} else {
			if (Pack.equals(PlayerInteractConfig.ITEM_RC_MY)) {
				configName = ConfigDenyItemRightClickOnBocksInMyRegion;
			} else if (Pack.equals(PlayerInteractConfig.ITEM_LC_MY)) {
				configName = ConfigDenyItemLeftClickOnBocksInMyRegion;
			} else if (Pack.equals(PlayerInteractConfig.ITEM_RC_EW)) {
				configName = ConfigDenyItemRightClickOnBocks;
			} else if (Pack.equals(PlayerInteractConfig.ITEM_LC_EW)) {
				configName = ConfigDenyItemLeftClickOnBocks;
			} else
				return false;
		}
		if (debugVerbose)
			lg.info("Checking item-block with " + configName);
		return checkBlockItemDeny(e, configName);
	}

}
