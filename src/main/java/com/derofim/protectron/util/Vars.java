package com.derofim.protectron.util;

// Class for storing constants
public class Vars {

	private Vars() {
	}

	// Global settings
	public static final String CONF_VER = "configurationLanguageVerion";
	public static final String FOLDER_SEPARATOR = "\\";
	public static final String FOLDER_MODULES = "modules";
	public static final String WHITELIST = "Whitelist";
	public static final String BLACKLIST = "Blacklist";

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
	public static final String PERM_DAMAGE_ENTITY_BY_PROJECTILE_EVERYWHERE = "protectron.damage.do.everywhere.projectile";
	public static final String PERM_DAMAGE_ENTITY_BY_WEAPON_EVERYWHERE = "protectron.damage.do.everywhere.weapon";
	public static final String PERM_GET_DAMAGED_BY_ENTITY_WEAPON_EVERYWHERE = "protectron.damage.get.everywhere.weapon";
	public static final String PERM_GET_DAMAGED_BY_ENTITY_PROJECTILE_EVERYWHERE = "protectron.damage.get.everywhere.projectile";
	public static final String PERM_INTERACT_ENTITY_EVERYWHERE = "protectron.interact.everywhere.entity";
	public static final String PERM_VIEW_INV_NAME = "protectron.view.inventory.name";

	// Local settings
	public static final String PARAM_GROUP_CLAIMS_WIDTH = "claim.limits.width";
	public static final String PARAM_GROUP_CLAIMS_HEIGHT = "claim.limits.height";
	public static final String PARAM_GROUP_CLAIMS_LENGTH = "claim.limits.length";
	public static final String PARAM_GROUP_CLAIMS_TOTAL = "claim.limits.total";
	public static final String PARAM_AUTOFLAGS_FLAGS = "autoflags.flags";
	public static final String PARAM_DENY_LIQUID_FLOW_EVERYWHERE = "Liquid.Deny.FlowEverywhere";
	public static final String PARAM_DENY_LIQUID_FLOW_IN_ALL_REGIONS = "Liquid.Deny.FlowInAllRegions";
	public static final String PARAM_DENY_LIQUID_FLOW_IN_DIFFERENT_REGIONS = "Liquid.Deny.FlowInDifferentRegions";
	public static final String PARAM_DENY_ENTITY_EXPOSION_EVERYWHERE = "Entity.Deny.ExplosionsEverywhere";
	public static final String PARAM_DENY_ENTITY_INTERACT_IN_ANY_REGION = "Entity.Deny.InteractInAnyRegion";
	public static final String PARAM_DENY_PLAYER_INTERACT_WITH_ENTITY_IN_FOREIGN_REGION = "Player.Deny.InteractWithEntityInForeignRegion";
	public static final String PARAM_DENY_PROJECTILE_IN_FOREGN_REGION = "Projectile.Deny.InForegnRegion";
	public static final String PARAM_DENY_DAMAGE_IN_FOREGN_REGION = "Damage.Deny.InForegnRegion";
	public static final String PARAM_BLOCK_PHYSICS_EVENT_BLACKLIST = "Physics.Blacklist.Blocks";

	// Запреты использования определенных предметов на блоках.
	// Являются суффиксами к названию предмета
	public static final String PrefixClickMouseRight = "CLICK.MOUSE.RIGHT.DENY.ITEM";
	public static final String PrefixClickMouseLeft = "CLICK.MOUSE.LEFT.DENY.ITEM";
	//
	public static final String SuffixOnBlocksEverywhere = "BLOCKS.EVERYWHERE";
	public static final String SuffixOnBlocksInForeignRegion = "BLOCKS.IN_FOREIGN_REGION";
	public static final String SuffixOnBlocksInMyRegion = "BLOCKS.IN_MY_REGION";

	// Block sets
	public static final String VANILLA = "VANILLA_BLOCKS";
	public static final String IC2 = "INDUSTRIALCRAFT_BLOCKS";
	public static final String BC2 = "BUILDCRAFT_BLOCKS";
	public static final String AE2 = "APPLIEDENERGETICS_BLOCKS";
	public static final String TEST = "CUSTOM_BLOCKS";
	public static final String PHYSICS_BLOCKS = "PHYSICS_BLOCKS";

	// Item sets
	public static final String VANILLA_ITEMS = "VANILLA_ITEMS";
	public static final String TEST_ITEMS = "CUSTOM_ITEMS";

	// Commands (also in plugin.yml) with permissions!
	public static final String CMD_RELOAD = "preload";
	public static final String CMD_ITEM_NAME = "pitem";
	public static final String CMD_BLOCK_NAME = "pblock";

	// Common
	public static final String BLOCK_DISPENSER = "DISPENSER";
}
