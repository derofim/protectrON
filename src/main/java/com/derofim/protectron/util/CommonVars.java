package com.derofim.protectron.util;

import com.derofim.protectron.modules.ModulesConfig;

// That file must be formatted manually! 
// Dont use automatic formatting here!
public class CommonVars {
	
	private static CommonVars instance = new CommonVars();

	public static final CommonVars getInstance() {
		return instance;
	}

	private CommonVars() {
	}
	
	public static final String CONF_VER = "configurationLanguageVerion";
	public static final String FOLDER_SEPARATOR = "\\";
	// Permission nodes
	public static final String PERM_INFORM = "protectron.chat.notify.prohibited";
	public static final String PERM_DBGCLKB = "protectron.debug.console.block.interact";
	public static final String PERM_DBGCLKI = "protectron.debug.console.item.interact";
	public static final String PERM_DBGCLKP = "protectron.debug.console.physical.interact";
	public static final String PERM_DBGPLG = "protectron.debug.console.plugin.actions";
	public static final String PERM_DPROT = "protectron.debug.console.notify.protected";
	public static final String PERM_ITEM_NAME = "protectron.command.item.name";
	public static final String PERM_BLOCK_NAME = "protectron.command.block.name";
	public static final String PERM_RLD = "protectron.command.plugin.reload";
	public static final String PERM_ALL_INV_ACCESS = "protectron.access.everywhere.inventory";
	public static final String PERM_ALL_BLOCKS_INTERACT = "protectron.access.everywhere.blocks";
	public static final String PERM_ALL_ITEMS_INTERACT = "protectron.access.everywhere.items";
	public static final String PERM_ALL_PHYSICAL_INTERACT = "protectron.access.everywhere.physical";
	public static final String PERM_DAMAGE_ENTITY_BY_PROJECTILE_EVERYWHERE  = "protectron.damage.do.everywhere.projectile";
	public static final String PERM_DAMAGE_ENTITY_BY_WEAPON_EVERYWHERE  = "protectron.damage.do.everywhere.weapon";
	public static final String PERM_GET_DAMAGED_BY_ENTITY_WEAPON_EVERYWHERE  = "protectron.damage.get.everywhere.weapon";
	public static final String PERM_GET_DAMAGED_BY_ENTITY_PROJECTILE_EVERYWHERE  = "protectron.damage.get.everywhere.projectile";
	public static final String PERM_INTERACT_ENTITY_EVERYWHERE = "protectron.interact.everywhere.entity";
	public static final String PERM_VIEW_INV_NAME = "protectron.view.inventory.name";
	public static final String PARAM_GROUP_CLAIMS_WIDTH  = "claim.limits.width";
	public static final String PARAM_GROUP_CLAIMS_HEIGHT  = "claim.limits.height";
	public static final String PARAM_GROUP_CLAIMS_LENGTH = "claim.limits.length";
	public static final String PARAM_GROUP_CLAIMS_TOTAL = "claim.limits.total";
	public static final String PARAM_AUTOFLAGS_FLAGS = "autoflags.flags";
	// Configuration settings
	public static final String PARAM_DINFORM = "player.chat.notify.ProhibitedAction";
	//
	public static final String PARAM_DGRIEFB = "console.debug.notify.ProtectedBlockAttempt";
	public static final String PARAM_DGRIEFI = "console.debug.notify.ProtectedItemAttempt";
	public static final String PARAM_DGRIEFP = "console.debug.notify.ProtectedPhysicalAttempt";
	//
	public static final String PARAM_DCLICKB = "console.debug.BlockInteract";
	public static final String PARAM_DCLICKI = "console.debug.ItemInteract";
	public static final String PARAM_DCLICKP = "console.debug.PhysicalInteract";
	public static final String PARAM_DALL = "console.debug.VerbosePluginActions";
	//
	//public static final String PARAM_DENY_OPEN_INV_IN_FOREIGN_REGION = "DenyOpeningInventoryInForeignRegion";
	public static final String PARAM_DENY_LIQUID_FLOW_IN_ALL_REGIONS = "Liquid.Deny.FlowInAllRegions"; 
	public static final String PARAM_DENY_ENTITY_EXPOSION_EVERYWHERE = "Entity.Deny.ExplosionsEverywhere";
	//public static final String PARAM_DENY_BLOCK_EXPOSION_EVERYWHERE = "DenyBlockExplosionsEverywhere";
	public static final String PARAM_DENY_ENTITY_INTERACT_IN_ANY_REGION = "Entity.Deny.InteractInAnyRegion";
	public static final String PARAM_DENY_PLAYER_INTERACT_WITH_ENTITY_IN_FOREIGN_REGION = "Player.Deny.InteractWithEntityInForeignRegion"; 
	public static final String PARAM_DENY_PROJECTILE_IN_FOREGN_REGION = "Projectile.Deny.InForegnRegion";
	public static final String PARAM_DENY_DAMAGE_IN_FOREGN_REGION = "Damage.Deny.InForegnRegion";
	public static final String PARAM_BLOCK_PHYSICS_EVENT_BLACKLIST = "Physics.Blacklist.Blocks";
	public static final String PARAM_OPEN_INVENTORY_MODULE_MODE = "OpenInventory.Module.Mode";
	public static final String PARAM_OPEN_INVENTORY_CHECK_FOREIGN_REGION = "OpenInventory.Check.foreign_region";
	public static final String PARAM_OPEN_INVENTORY_MODULE_WHITELIST = "OpenInventory.Module.Whitelist";
	public static final String PARAM_OPEN_INVENTORY_MODULE_BLACKLIST = "OpenInventory.Module.Blacklist";
	// Action packs
	// 
	public static final String RC_BG_WG = "PACKS.CLICK.RIGHT_MOUSE.DENY.ON_BLOCK.IN_FOREIGN_PRIVATE";
	public static final String RC_BG_MY = "PACKS.CLICK.RIGHT_MOUSE.DENY.ON_BLOCK.IN_OWN_PRIVATE_AND_WORLD";
	public static final String RC_BG_EW = "PACKS.CLICK.RIGHT_MOUSE.DENY.ON_BLOCK.EVERYWHERE";
	// Запреты нажатия по блоку: Левая кнопка мыши
	public static final String LC_BG_MY = "PACKS.CLICK.LEFT_MOUSE.DENY.ON_BLOCK.IN_OWN_PRIVATE_AND_WORLD";
	public static final String LC_BG_WG = "PACKS.CLICK.LEFT_MOUSE.DENY.ON_BLOCK.IN_FOREIGN_PRIVATE";
	public static final String LC_BG_EW = "PACKS.CLICK.LEFT_MOUSE.DENY.ON_BLOCK.BLOCK_EVERYWHERE";
	// Запреты физического воздействия на блоки
	public static final String PHYSICAL_WG = "PACKS.PHYSICAL.DENY.ON_BLOCK.IN_FOREIGN_PRIVATE";
	public static final String PHYSICAL_MY = "PACKS.PHYSICAL.DENY.ON_BLOCK.IN_OWN_PRIVATE_AND_WORLD";
	public static final String PHYSICAL_EW = "PACKS.PHYSICAL.DENY.ON_BLOCK.EVERYWHERE";
	// Запреты использования определенных предметов: Правая кнопка мыши
	public static final String ITEM_RC_WG = "PACKS.CLICK.RIGHT_MOUSE.DENY.WITH_ITEM.IN_FOREIGN_PRIVATE";
	public static final String ITEM_RC_MY = "PACKS.CLICK.RIGHT_MOUSE.DENY.WITH_ITEM.OWN_PRIVATE_AND_WORLD";
	public static final String ITEM_RC_EW = "PACKS.CLICK.RIGHT_MOUSE.DENY.WITH_ITEM.EVERYWHERE";
	// Запреты использования определенных предметов: Левая кнопка мыши
	public static final String ITEM_LC_WG = "PACKS.CLICK.LEFT_MOUSE.DENY.WITH_ITEM.IN_FOREIGN_PRIVATE";
	public static final String ITEM_LC_MY = "PACKS.CLICK.LEFT_MOUSE.DENY.WITH_ITEM.IN_OWN_PRIVATE_AND_WORLD";
	public static final String ITEM_LC_EW = "PACKS.CLICK.LEFT_MOUSE.DENY.WITH_ITEM.EVERYWHERE";
	// Запреты использования определенных предметов на блоках. 
	// Являются суффиксами к названию предмета
	public static final String PrefixClickMouseRight = "CLICK.MOUSE.RIGHT.DENY.WITH_ITEM";
	public static final String PrefixClickMouseLeft  = "CLICK.MOUSE.LEFT.DENY.WITH_ITEM";
	//
	public static final String SuffixOnBlocksEverywhere = "ON_BLOCKS.EVERYWHERE";
	public static final String SuffixOnBlocksInForeignRegion = "ON_BLOCKS.IN_FOREIGN_REGION";
	public static final String SuffixOnBlocksInMyRegion  = "ON_BLOCKS.IN_MY_REGION";

	// Block sets
	public static final String VANILLA = "VANILLA_BLOCKS";
	public static final String IC2 = "INDUSTRIALCRAFT_BLOCKS";
	public static final String BC2 = "BUILDCRAFT_BLOCKS";
	public static final String AE2 = "APPLIEDENERGETICS_BLOCKS";
	public static final String TEST = "CUSTOM_BLOCKS";
	
	// Item sets
	public static final String VANILLA_ITEMS = "VANILLA_ITEMS";
	public static final String TEST_ITEMS = "CUSTOM_ITEMS";

	// Localisation messages
	public static final String MSG_NOT_ALLOWED = "msg_not_allowed";
	public static final String MSG_REG_PROTECTED = "msg_region_protected";
	public static final String MSG_NO_PERMISSION = "msg_not_enough_perm";
	public static final String MSG_ONLY_PLAYERS = "msg_only_players";
	public static final String MSG_WG_ERR_MAX_HEIGHT = "msg_wg_err_max_height";
	public static final String MSG_WG_ERR_MAX_WIDTH = "msg_wg_err_max_width";
	public static final String MSG_WG_ERR_MAX_TOTAL = "msg_wg_err_max_total";
	public static final String MSG_WG_ERR_MAX_LENGTH = "msg_wg_err_max_length";
	public static final String MSG_WG_ERR_ALREADY_CLAIMED = "msg_wg_err_already_claimed";
	public static final String MSG_WG_ERROR_WRONG = "msg_wg_error_wrong";
	public static final String MSG_WG_CANT_DO = "msg_wg_cant_do";
	public static final String MSG_WG_CREATED = "msg_wg_created";
	public static final String  MSG_WG_ERR_CANT_AUTO_FLAG  = "msg_wg_cant_auto_flag";
	public static final String MSG_WG_ERR_NO_REGION = "msg_wg_err_no_region";
	// Commands (also in plugin.yml) with permissions!
	public static final String CMD_RELOAD = "preload";
	public static final String CMD_ITEM_NAME = "pitem";
	public static final String CMD_BLOCK_NAME = "pblock";
	
	// Example
	public static final String BLOCK_DISPENSER = "DISPENSER";

	public static final String CONFIG_MODULES_HEADER = 
			"Main configuration file\r\n" 
			+ "\r\n" 
			+ "~~ Modules ~~\r\n"
			+ "Modules checks player actions (when enabled). Most modules correspond to in-game events.\r\n"
			+ "Set false after module name if you want to disable it.\r\n" 
			+ "For example, "+ModulesConfig.MODULE_PLAYER_INTERACT+": false will disable checking player interactions (blok/item clicking and pressing buttons).\r\n" 
			+ "\r\n" + 
			"~~ Commands ~~\r\n"
			+ "Reload configuration command: /" 
			+ CMD_RELOAD + ". Permission for command: " + PERM_RLD + "\r\n"
			+ "Reload any plugin configuration command: /" 
			+ CMD_RELOAD + " [PluginName]. Permission for command: " + PERM_RLD + "\r\n" 
			+ "Get name of item in hand command: /" + CMD_ITEM_NAME + ". Permission for command: " + PERM_ITEM_NAME + "\r\n" 
			+ "Get block at eye location : /" + CMD_BLOCK_NAME + ". Permission for command: " + PERM_BLOCK_NAME + "\r\n" 
			;
	public static final String CONFIG_ACTIONS_HEADER = 
            "\r\n" + "~~ Action Packs ~~\r\n"
        	+ "Description: Action Packs made to handle actions with block sets\r\n" 
        	+ "Example for " + RC_BG_WG + ":\r\n"  + "" + RC_BG_WG + ":\r\n" + "- " + VANILLA + "\r\n" + "- " + TEST + "\r\n" + "\r\n" + ""
        	+ "\r\n~~ Block click actions ~~\r\n" 
        	+ RC_BG_WG + ":\r\n"
        	+ " Disallows right click in WorldGuard region owned by foreign player for specific block groups. Action: RIGHT_CLICK_BLOCK\r\n"
        	+ "\r\n"
        	+ LC_BG_WG + ":\r\n"
        	+ " Disallows left click in WorldGuard region (owned by foreign player) for specific block groups. Action: LEFT_CLICK_BLOCK\r\n"
        	+ "\r\n"
        	+ RC_BG_MY + ":\r\n"
        	+ " Disallows right click in WorldGuard region (owned by player or outside any regions) for specific block groups. Action: RIGHT_CLICK_BLOCK\r\n"
        	+ "\r\n" 
        	+ LC_BG_MY + ":\r\n"
        	+ " Disallows left click in WorldGuard region (owned by player or outside any regions) for specific block groups.  Action: LEFT_CLICK_BLOCK\r\n"
        	+ "\r\n" 
        	+ RC_BG_EW + ":\r\n"
        	+ " Disallows right click (globally) for specific block groups. Action: RIGHT_CLICK_BLOCK\r\n" 
        	+ "\r\n" + ""
        	+ LC_BG_EW + ":\r\n"
        	+ " Disallows left click (globally) for specific block groups. Action: LEFT_CLICK_BLOCK\r\n" 
        	+ "\r\n"
        	+ "\r\n~~ Block physical actions (Stepping onto or into a block) ~~\r\n" 
        	+ PHYSICAL_EW + ":\r\n"
        	+ " Disallows physical interact (globally) for specific block groups. Action: PHYSICAL\r\n" 
        	+ "\r\n" + "\r\n" 
        	+ PHYSICAL_MY + ":\r\n"
        	+ " Disallows physical interact in WorldGuard region (owned by player) for specific block groups. Action: PHYSICAL\r\n"
        	+ "\r\n" + "" 
        	+ PHYSICAL_WG + ":\r\n"
        	+ " Disallows physical interact in WorldGuard region owned by foreign player for specific block groups. Action: PHYSICAL\r\n"
        	+ "\r\n" 
        	+ "~~ Item actions (without clicked block name check) ~~\r\n"
        	+ ITEM_RC_EW + ":\r\n"
        	+ " Disallows right click on all blocks (globally) for specific item groups. Action: RIGHT_CLICK_BLOCK\r\n"
        	+ "\r\n"
        	+ ITEM_RC_MY + ":\r\n"
        	+ " Disallows right click clickicks on all blocks in WorldGuard region (owned by player) for specific item groups. Action: RIGHT_CLICK_BLOCK\r\n"
        	+ "\r\n"
        	+ ITEM_RC_WG + ":\r\n"
        	+ " Disallows right click on all blocks in WorldGuard region owned by foreign player for specific item groups. Action: RIGHT_CLICK_BLOCK\r\n"
        	+ "\r\n"
        	+ ITEM_LC_EW + ":\r\n"
        	+ " Disallows left click on all blocks (globally) for specific item groups. Action: LEFT_CLICK_BLOCK\r\n"
        	+ "\r\n"
        	+ ITEM_LC_MY + ":\r\n"
        	+ " Disallows left click clickicks on all blocks in WorldGuard region (owned by player) for specific item groups. Action: LEFT_CLICK_BLOCK\r\n"
        	+ "\r\n"
        	+ ITEM_LC_WG + ":\r\n"
        	+ " Disallows left click on all blocks in WorldGuard region owned by foreign player for specific item groups. Action: LEFT_CLICK_BLOCK\r\n"
        	+ "\r\n";
	
	public static final String CONFIG_SETTINGS_HEADER = 
        	"~~ Settings ~~\r\n"
        	+ PARAM_OPEN_INVENTORY_CHECK_FOREIGN_REGION
        	+ ":\r\n * Should we disallow opening any inventory (Block, Chest, Entity, DoubleChest) in region owned by other player. Players with permission " + PERM_ALL_INV_ACCESS + " can open without any checks."
        	;
	
	public static final String CONFIG_DEBUG_HEADER = 
        	"~~ Debug settings ~~\r\n" 
        	+ PARAM_DINFORM
        	+ ":\r\n * Should we tell player about protection? Player must have permission " + PERM_INFORM + "\r\n\r\n"
        	+ PARAM_DCLICKP
        	+ ":\r\n * Should we log in console name of any block with which the player interacts phisically? Stepping onto or into a block (Ass-pressure)"
        	+ "\r\nExamples include jumping on soil, standing on pressure plate, triggering redstone ore, triggering tripwire. "
        	+ "\r\nPlayer must have permission " + PERM_DBGCLKP 
        	+ "\r\n\r\n" + "" + PARAM_DGRIEFB
        	+ ":\r\n * Should we log in console name of any block with which the player interacts by clicking? Player must have permission " + PERM_DBGCLKB 
        	+ "\r\n\r\n" + "" + PARAM_DGRIEFB
        	+ ":\r\n * Should we log in console name of any protected block with which the player interacts? Player must have permission " + PERM_DPROT 
        	+ "\r\n\r\n" + "" + PARAM_DALL
        	+ ":\r\n * Should we log in console any actions made by plugin when player interacts with something? Player must have permission " + PERM_DBGPLG ;
	
	public static final String CONFIG_BLOCKS_HEADER = 
        	"~~ Block sets ~~\r\n"
        	+ "Description: Block set is any group (by title, modification, etc.) of blocks.\r\n"
        	+ "Usage example: You can specify (for any modification) what must be checked when right mouse button clicked.\r\n"
        	+ "Example: For action pack 'right-clicking' (" + RC_BG_EW
        	+ ") specify block sets with names 'INDUSTRIALCRAFT, BUILDCRAFT', etc.\r\n"
        	+ "         In block sets indicate list of block names. You can create your own sets with any name and content.\r\n"
        	+ "Example of VANILLA block set:\r\n" + "VANILLA:\r\n" + "- DISPENSER\r\n" + "- NOTE_BLOCK\r\n" + "\r\n"
        	+ "~~ You can get block by id/name with/without metadata ~~\r\n" 
        	+ "Example 1: CHEST - Checks by block name with any metadata\r\n"
        	+ "Example 2: 1 - Checks by block id with any metadata\r\n"
        	+ "Example 3: CHEST:1 - Checks by block name with metadata 1\r\n"
        	+ "Example 4: 1:1 - Checks by block id with metadata 1\r\n";
	
	public static final String CONFIG_ITEMS_HEADER = 
        	"~~ Items sets ~~\r\n"
        	+ "Description: Items set is any group (by title, modification, etc.) of items.\r\n"
        	+ "~~ Deny item interaction with specific block ~~\r\n"
        	+ "Just create in config parameter item-name with suffix (as below), inside that parameter list all blocks not allowed to interact with item \r\n"
        	+ "Example of denying right clicking dispenser in foreign region holding iron pickaxe:\r\n"
        	+ CommonVars.PrefixClickMouseRight + ".IRON_PICKAXE." + CommonVars.SuffixOnBlocksEverywhere+":\r\n"
        	+ "- " + BLOCK_DISPENSER + "\r\n"
        	+ "\r\n"
        	+ "~~ Possible suffixes after item name ~~\r\n"
        	+ SuffixOnBlocksEverywhere+" - Use to deny in everywhere\r\n"
        	+ SuffixOnBlocksInForeignRegion+" -Use to deny in foreign region\r\n"
        	+ SuffixOnBlocksInMyRegion+" - Use to deny your region or outside regions\r\n";
	
	public static final String CONFIG_MESSAGES_HEADER = 
        	"~~ Messages ~~\r\n" + "* " + MSG_NOT_ALLOWED + "\r\n"
        	+ "Message like 'It is forbidden to interact with' that will be sent to the player when he is trying to do prohibited action.\r\n"
        	+ "Configuration must be enabled " + PARAM_DINFORM + ": true \r\n" + "Player must have permission " + PERM_INFORM 
        	+ "\r\n\r\n" + "* " + MSG_REG_PROTECTED + "\r\n"
        	+ "Message like 'Area under protection' that will be sent to the player when he is trying to do prohibited action.\r\n"
        	+ "Configuration must be enabled " + PARAM_DINFORM + ": true \r\n" + "Player must have permission " + PERM_INFORM + "\r\n";
        	
}
