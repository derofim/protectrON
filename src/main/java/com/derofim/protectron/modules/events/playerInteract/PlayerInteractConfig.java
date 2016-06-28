package com.derofim.protectron.modules.events.playerInteract;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.config.AbstractConfig;
import com.derofim.protectron.util.Vars;

public class PlayerInteractConfig extends AbstractConfig {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private final String configLanguageVersion = "0.0.6";
	private final static File defaultFile = new File(plugin.getDataFolder(), Vars.FOLDER_MODULES + Vars.FOLDER_SEPARATOR
			+ "player_interact" + Vars.FOLDER_SEPARATOR + "player_interact.yml");

	// Action packs
	public static final String RC_BG_WG = "PACKS.CLICK.RIGHT_MOUSE.DENY.BLOCK.IN_FOREIGN_PRIVATE";
	public static final String RC_BG_MY = "PACKS.CLICK.RIGHT_MOUSE.DENY.BLOCK.IN_OWN_PRIVATE_AND_WORLD";
	public static final String RC_BG_EW = "PACKS.CLICK.RIGHT_MOUSE.DENY.BLOCK.EVERYWHERE";
	// Запреты нажатия по блоку: Левая кнопка мыши
	public static final String LC_BG_MY = "PACKS.CLICK.LEFT_MOUSE.DENY.BLOCK.IN_OWN_PRIVATE_AND_WORLD";
	public static final String LC_BG_WG = "PACKS.CLICK.LEFT_MOUSE.DENY.BLOCK.IN_FOREIGN_PRIVATE";
	public static final String LC_BG_EW = "PACKS.CLICK.LEFT_MOUSE.DENY.BLOCK.BLOCK_EVERYWHERE";
	// Запреты физического воздействия на блоки
	public static final String PHYSICAL_WG = "PACKS.PHYSICAL.DENY.BLOCK.IN_FOREIGN_PRIVATE";
	public static final String PHYSICAL_MY = "PACKS.PHYSICAL.DENY.BLOCK.IN_OWN_PRIVATE_AND_WORLD";
	public static final String PHYSICAL_EW = "PACKS.PHYSICAL.DENY.BLOCK.EVERYWHERE";
	// Запреты использования определенных предметов: Правая кнопка мыши
	public static final String ITEM_RC_WG = "PACKS.CLICK.RIGHT_MOUSE.DENY.ITEM.IN_FOREIGN_PRIVATE";
	public static final String ITEM_RC_MY = "PACKS.CLICK.RIGHT_MOUSE.DENY.ITEM.OWN_PRIVATE_AND_WORLD";
	public static final String ITEM_RC_EW = "PACKS.CLICK.RIGHT_MOUSE.DENY.ITEM.EVERYWHERE";
	// Запреты использования определенных предметов: Левая кнопка мыши
	public static final String ITEM_LC_WG = "PACKS.CLICK.LEFT_MOUSE.DENY.ITEM.IN_FOREIGN_PRIVATE";
	public static final String ITEM_LC_MY = "PACKS.CLICK.LEFT_MOUSE.DENY.ITEM.IN_OWN_PRIVATE_AND_WORLD";
	public static final String ITEM_LC_EW = "PACKS.CLICK.LEFT_MOUSE.DENY.ITEM.EVERYWHERE";
	public static final String CONFIG_ACTIONS_HEADER = "\r\n" + "~~ Action Packs ~~\r\n"
			+ "Description: Action Packs made to handle actions with block sets\r\n" + "Example for " + RC_BG_WG
			+ ":\r\n" + "" + RC_BG_WG + ":\r\n" + "- " + Vars.VANILLA + "\r\n" + "- " + Vars.TEST + "\r\n" + "\r\n" + ""
			+ "\r\n~~ Block click actions ~~\r\n" + RC_BG_WG + ":\r\n"
			+ " Disallows right click in WorldGuard region owned by foreign player for specific block groups. Action: RIGHT_CLICK_BLOCK\r\n"
			+ "\r\n" + LC_BG_WG + ":\r\n"
			+ " Disallows left click in WorldGuard region (owned by foreign player) for specific block groups. Action: LEFT_CLICK_BLOCK\r\n"
			+ "\r\n" + RC_BG_MY + ":\r\n"
			+ " Disallows right click in WorldGuard region (owned by player or outside any regions) for specific block groups. Action: RIGHT_CLICK_BLOCK\r\n"
			+ "\r\n" + LC_BG_MY + ":\r\n"
			+ " Disallows left click in WorldGuard region (owned by player or outside any regions) for specific block groups.  Action: LEFT_CLICK_BLOCK\r\n"
			+ "\r\n" + RC_BG_EW + ":\r\n"
			+ " Disallows right click (globally) for specific block groups. Action: RIGHT_CLICK_BLOCK\r\n" + "\r\n" + ""
			+ LC_BG_EW + ":\r\n"
			+ " Disallows left click (globally) for specific block groups. Action: LEFT_CLICK_BLOCK\r\n" + "\r\n"
			+ "\r\n~~ Block physical actions (Stepping onto or into a block) ~~\r\n" + PHYSICAL_EW + ":\r\n"
			+ " Disallows physical interact (globally) for specific block groups. Action: PHYSICAL\r\n" + "\r\n"
			+ "\r\n" + PHYSICAL_MY + ":\r\n"
			+ " Disallows physical interact in WorldGuard region (owned by player) for specific block groups. Action: PHYSICAL\r\n"
			+ "\r\n" + "" + PHYSICAL_WG + ":\r\n"
			+ " Disallows physical interact in WorldGuard region owned by foreign player for specific block groups. Action: PHYSICAL\r\n"
			+ "\r\n" + "~~ Item actions (without clicked block name check) ~~\r\n" + ITEM_RC_EW + ":\r\n"
			+ " Disallows right click on all blocks (globally) for specific item groups. Action: RIGHT_CLICK_BLOCK\r\n"
			+ "\r\n" + ITEM_RC_MY + ":\r\n"
			+ " Disallows right click clickicks on all blocks in WorldGuard region (owned by player) for specific item groups. Action: RIGHT_CLICK_BLOCK\r\n"
			+ "\r\n" + ITEM_RC_WG + ":\r\n"
			+ " Disallows right click on all blocks in WorldGuard region owned by foreign player for specific item groups. Action: RIGHT_CLICK_BLOCK\r\n"
			+ "\r\n" + ITEM_LC_EW + ":\r\n"
			+ " Disallows left click on all blocks (globally) for specific item groups. Action: LEFT_CLICK_BLOCK\r\n"
			+ "\r\n" + ITEM_LC_MY + ":\r\n"
			+ " Disallows left click clickicks on all blocks in WorldGuard region (owned by player) for specific item groups. Action: LEFT_CLICK_BLOCK\r\n"
			+ "\r\n" + ITEM_LC_WG + ":\r\n"
			+ " Disallows left click on all blocks in WorldGuard region owned by foreign player for specific item groups. Action: LEFT_CLICK_BLOCK\r\n"
			+ "\r\n";

	private static PlayerInteractConfig instance = new PlayerInteractConfig();

	public static final PlayerInteractConfig getInstance() {
		return instance;
	}

	private PlayerInteractConfig() {
	}

	@Override
	protected void setCustomDefaults(FileConfiguration fconf) {
		List<String> RC_WG_GroupsList = Arrays.asList(Vars.VANILLA, Vars.IC2);
		List<String> RC_MY_GroupsList = Arrays.asList(Vars.TEST);
		List<String> RC_EW_GroupsList = Arrays.asList(Vars.TEST);
		//
		List<String> LC_WG_GroupsList = Arrays.asList(Vars.VANILLA);
		List<String> LC_MY_GroupsList = Arrays.asList(Vars.TEST);
		List<String> LC_EW_GroupsList = Arrays.asList(Vars.TEST);
		//
		List<String> ITM_RC_WG_GroupsList = Arrays.asList(Vars.VANILLA_ITEMS);
		List<String> ITM_RC_MY_GroupsList = Arrays.asList(Vars.TEST_ITEMS);
		List<String> ITM_RC_EW_GroupsList = Arrays.asList(Vars.TEST_ITEMS);
		//
		List<String> ITM_LC_WG_GroupsList = Arrays.asList(Vars.VANILLA_ITEMS);
		List<String> ITM_LC_MY_GroupsList = Arrays.asList(Vars.TEST_ITEMS);
		List<String> ITM_LC_EW_GroupsList = Arrays.asList(Vars.TEST_ITEMS);
		//
		List<String> PHYSICAL_WG_GroupsList = Arrays.asList(Vars.VANILLA);
		List<String> PHYSICAL_MY_GroupsList = Arrays.asList(Vars.TEST);
		List<String> PHYSICAL_EW_GroupsList = Arrays.asList(Vars.TEST);
		//
		fconf.addDefault(RC_BG_WG, RC_WG_GroupsList);
		fconf.addDefault(RC_BG_MY, RC_MY_GroupsList);
		fconf.addDefault(RC_BG_EW, RC_EW_GroupsList);
		//
		fconf.addDefault(LC_BG_MY, LC_WG_GroupsList);
		fconf.addDefault(LC_BG_WG, LC_MY_GroupsList);
		fconf.addDefault(LC_BG_EW, LC_EW_GroupsList);
		//
		fconf.addDefault(ITEM_RC_WG, ITM_RC_WG_GroupsList);
		fconf.addDefault(ITEM_RC_MY, ITM_RC_MY_GroupsList);
		fconf.addDefault(ITEM_RC_EW, ITM_RC_EW_GroupsList);
		//
		fconf.addDefault(ITEM_LC_WG, ITM_LC_WG_GroupsList);
		fconf.addDefault(ITEM_LC_MY, ITM_LC_MY_GroupsList);
		fconf.addDefault(ITEM_LC_EW, ITM_LC_EW_GroupsList);
		//
		fconf.addDefault(PHYSICAL_WG, PHYSICAL_WG_GroupsList);
		fconf.addDefault(PHYSICAL_MY, PHYSICAL_MY_GroupsList);
		fconf.addDefault(PHYSICAL_EW, PHYSICAL_EW_GroupsList);
	}

	@Override
	public File getFile() {
		return defaultFile;
	}

	@Override
	public String getLanguageVersion() {
		return configLanguageVersion;
	}

	@Override
	public String getHeader() {
		return CONFIG_ACTIONS_HEADER;
	}
}
