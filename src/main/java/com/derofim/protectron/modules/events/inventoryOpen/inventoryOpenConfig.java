package com.derofim.protectron.modules.events.inventoryOpen;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.config.AbstractConfig;
import com.derofim.protectron.util.Vars;

public class InventoryOpenConfig extends AbstractConfig {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private final String configLanguageVersion = "0.0.8";
	private final static File defaultFile = new File(plugin.getDataFolder(), Vars.FOLDER_MODULES + Vars.FOLDER_SEPARATOR
			+ "events" + Vars.FOLDER_SEPARATOR + "inventory_open.yml");

	private static InventoryOpenConfig instance = new InventoryOpenConfig();

	public static final InventoryOpenConfig getInstance() {
		return instance;
	}

	public static final String INV = "OpenInventory";
	public static final String FOREIGN_REGION = "foreign_region";
	public static final String GLOBAL_REGION = "global_region";
	public static final String PARAM_WORLD_OPEN_INVENTORY_FOREIGN_MODULE_MODE = INV + ".world." + FOREIGN_REGION + ".Module.Mode";
	public static final String PARAM_WORLD_INV_FOREIGN_REGION = INV + ".world." + FOREIGN_REGION + ".enabled";
	public static final String PARAM_WORLD_INV_WHITELIST = INV + ".world." + FOREIGN_REGION + "." + Vars.WHITELIST;
	public static final String PARAM_WORLD_INV_BLACKLIST = INV + ".world." + FOREIGN_REGION + "." + Vars.BLACKLIST;
	public static final String PARAM_DIM1_OPEN_INVENTORY_FOREIGN_MODULE_MODE = INV + ".dim-1." + FOREIGN_REGION + ".Module.Mode";
	public static final String PARAM_DIM1_OPEN_INVENTORY_GLOBAL_MODULE_MODE = INV + ".dim-1." + GLOBAL_REGION + ".Module.Mode";
	public static final String PARAM_DIM1_INV_FOREIGN_REGION = INV + ".dim-1." + FOREIGN_REGION + ".enabled";
	public static final String PARAM_DIM1_INV_FOREIGN_WHITELIST = INV + ".dim-1." + FOREIGN_REGION + "."
			+ Vars.WHITELIST;
	public static final String PARAM_DIM1_INV_FOREIGN_BLACKLIST = INV + ".dim-1." + FOREIGN_REGION + "."
			+ Vars.BLACKLIST;

	public static final String PARAM_DIM1_INV_GLOBAL_REGION = INV + ".dim-1." + GLOBAL_REGION + ".enabled";
	public static final String PARAM_DIM1_INV_GLOBAL_WHITELIST = INV + ".dim-1." + GLOBAL_REGION + "." + Vars.WHITELIST;
	public static final String PARAM_DIM1_INV_GLOBAL_BLACKLIST = INV + ".dim-1." + GLOBAL_REGION + "." + Vars.BLACKLIST;

	public static final String CONFIG_HEADER = "~~ Settings ~~\r\n" + PARAM_WORLD_INV_FOREIGN_REGION
			+ ":\r\n * Should we disallow opening any inventory (Block, Chest, Entity, DoubleChest) in region owned by other player. Players with permission "
			+ Vars.PERM_ALL_INV_ACCESS + " can open without any checks.";

	private InventoryOpenConfig() {
	}

	public static String formWorldConfigName(String worldName) {
		return InventoryOpenConfig.INV + "." + worldName;
	}

	public static String formRegionConfigName(String worldName, String regionConfigName) {
		return formWorldConfigName(worldName) + "." + regionConfigName;
	}

	@Override
	protected void setCustomDefaults(FileConfiguration fconf) {
		fconf.addDefault(PARAM_WORLD_INV_FOREIGN_REGION, true);
		fconf.addDefault(PARAM_DIM1_INV_GLOBAL_REGION, false);
		//
		fconf.addDefault(PARAM_WORLD_OPEN_INVENTORY_FOREIGN_MODULE_MODE, "whitelist");
		fconf.addDefault(PARAM_DIM1_OPEN_INVENTORY_FOREIGN_MODULE_MODE, "blacklist");
		fconf.addDefault(PARAM_DIM1_OPEN_INVENTORY_GLOBAL_MODULE_MODE, "blacklist");
		List<String> whitelistinv = Arrays.asList("IC2_INV", "BC2_INV");
		List<String> blacklistinv = Arrays.asList("VANILLA_INV");
		fconf.addDefault(PARAM_WORLD_INV_WHITELIST, whitelistinv);
		fconf.addDefault(PARAM_WORLD_INV_BLACKLIST, blacklistinv);
		List<String> blacklistDiminv = Arrays.asList("TE_INV");
		fconf.addDefault(PARAM_DIM1_INV_FOREIGN_WHITELIST, Arrays.asList("IC2_INV", "TE_INV"));
		fconf.addDefault(PARAM_DIM1_INV_FOREIGN_BLACKLIST, blacklistDiminv);
		fconf.addDefault(PARAM_DIM1_INV_GLOBAL_WHITELIST, Arrays.asList("VANILLA_INV", "BC2_INV"));
		fconf.addDefault(PARAM_DIM1_INV_GLOBAL_BLACKLIST, Arrays.asList("AE2_INV"));
		fconf.addDefault("VANILLA_INV", Arrays.asList("container.crafting_10"));
		fconf.addDefault("IC2_INV",
				Arrays.asList("Kinetic Generator_1", "Steam Kinetic Generator_2", "Electric Kinetic Generator_11",
						"Wind Kinetic Generator_1", "Water Kinetic Generator_1", "StirlingKineticGenerator_7",
						"SolidHeatGenerator_2", "FluidHeatGenerator_2", "RTHeatGenerator_6"));
		fconf.addDefault("BC2_INV", Arrays.asList("Engine_1", "Filler_27", "Builder_28", "Template_2"));
		fconf.addDefault("TE_INV", Arrays.asList("tile.thermalexpansion.machine.precipitator.name_2",
				"tile.thermalexpansion.machine.furnace.name_3", "tile.thermalexpansion.machine.pulverizer.name_5",
				"tile.thermalexpansion.machine.smelter.name_6", "tile.thermalexpansion.machine.sawmill.name_5",
				"tile.thermalexpansion.machine.crucible.name_2", "tile.thermalexpansion.machine.transposer.name_4",
				"tile.thermalexpansion.machine.precipitator.name_2", "tile.thermalexpansion.machine.extruder.name_1",
				"tile.thermalexpansion.machine.accumulator.name_0"));
		fconf.addDefault("AE2_INV", Arrays.asList("TileInterface_8", "TileSkyChest_36", "TileGrinder_7",
				"TileInscriber_4", "TileWireless_1", "Chest_27"));
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
		return CONFIG_HEADER;
	}
}
