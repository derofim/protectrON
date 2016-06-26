package com.derofim.protectron.modules.blockGroup;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.config.AbstractConfig;
import com.derofim.protectron.util.CommonVars;

public class BlocksConfig extends AbstractConfig {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private final String configLanguageVersion = "0.0.4";
	private final static File defaultFile = new File(plugin.getDataFolder(),
			"sets" + CommonVars.FOLDER_SEPARATOR + "blocks.yml");

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
		// Config
		fconf.addDefault(CommonVars.VANILLA, vanillaBlocksList);
		fconf.addDefault(CommonVars.IC2, icraftBlocksList);
		fconf.addDefault(CommonVars.VANILLA, vanillaBlocksList);
		fconf.addDefault(CommonVars.BC2, bcraftBlocksList);
		fconf.addDefault(CommonVars.AE2, appliedenergeticsBlocksList);
		fconf.addDefault(CommonVars.TEST, testBlocksList);
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
		return CommonVars.CONFIG_BLOCKS_HEADER;
	}
}
