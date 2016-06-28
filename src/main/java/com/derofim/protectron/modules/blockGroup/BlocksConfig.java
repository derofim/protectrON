package com.derofim.protectron.modules.blockGroup;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.config.AbstractConfig;
import com.derofim.protectron.modules.events.playerInteract.PlayerInteractConfig;
import com.derofim.protectron.util.Vars;

public class BlocksConfig extends AbstractConfig {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private final String configLanguageVersion = "0.0.6";
	private final static File defaultFile = new File(plugin.getDataFolder(),
			"sets" + Vars.FOLDER_SEPARATOR + "blocks.yml");

	public static final String CONFIG_BLOCKS_HEADER = "~~ Block sets ~~\r\n"
			+ "Description: Block set is any group (by title, modification, etc.) of blocks.\r\n"
			+ "Usage example: You can specify (for any modification) what must be checked when right mouse button clicked.\r\n"
			+ "Example: For action pack 'right-clicking' (" + PlayerInteractConfig.RC_BG_EW
			+ ") specify block sets with names 'INDUSTRIALCRAFT, BUILDCRAFT', etc.\r\n"
			+ "         In block sets indicate list of block names. You can create your own sets with any name and content.\r\n"
			+ "Example of VANILLA block set:\r\n" + "VANILLA:\r\n" + "- DISPENSER\r\n" + "- NOTE_BLOCK\r\n" + "\r\n"
			+ "~~ You can get block by id/name with/without metadata ~~\r\n"
			+ "Example 1: CHEST - Checks by block name with any metadata\r\n"
			+ "Example 2: 1 - Checks by block id with any metadata\r\n"
			+ "Example 3: CHEST:1 - Checks by block name with metadata 1\r\n"
			+ "Example 4: 1:1 - Checks by block id with metadata 1\r\n";

	private static BlocksConfig instance = new BlocksConfig();

	public static final BlocksConfig getInstance() {
		return instance;
	}

	private BlocksConfig() {
	}

	@Override
	protected void setCustomDefaults(FileConfiguration fconf) {
		List<String> vanillaBlocksList = Arrays.asList("WORKBENCH", "DISPENSER", "NOTE_BLOCK", "BED_BLOCK", "CHEST",
				"FURNACE", "BURNING_FURNACE", "WOODEN_DOOR", "LEVER", "STONE_BUTTON");
		List<String> icraftBlocksList = Arrays.asList("IC2_BLOCKMACHINE", "IC2_BLOCKMACHINE2", "IC2_BLOCKMACHINE3");
		List<String> bcraftBlocksList = Arrays.asList("BUILDCRAFTCORE_ENGINEBLOCK");
		List<String> appliedenergeticsBlocksList = Arrays.asList("APPLIEDENERGISTICS2_TILEBLOCKCHEST");
		List<String> testBlocksList = Arrays.asList("NOPE", "NOPE:0", "0", "0:0");
		List<String> physicsBlocksList = Arrays.asList("SNOW", "SAND", "YELLOW_FLOWER");
		// Config
		fconf.addDefault(Vars.VANILLA, vanillaBlocksList);
		fconf.addDefault(Vars.IC2, icraftBlocksList);
		fconf.addDefault(Vars.VANILLA, vanillaBlocksList);
		fconf.addDefault(Vars.BC2, bcraftBlocksList);
		fconf.addDefault(Vars.AE2, appliedenergeticsBlocksList);
		fconf.addDefault(Vars.TEST, testBlocksList);
		fconf.addDefault(Vars.PHYSICS_BLOCKS, physicsBlocksList);
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
		return CONFIG_BLOCKS_HEADER;
	}
}
