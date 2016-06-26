package com.derofim.protectron.modules.itemGroup;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.config.AbstractConfig;
import com.derofim.protectron.util.CommonVars;

public class ItemsConfig extends AbstractConfig {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private final String configLanguageVersion = "0.0.4";
	private final static File defaultFile = new File(plugin.getDataFolder(),
			"sets" + CommonVars.FOLDER_SEPARATOR + "items.yml");

	private static ItemsConfig instance = new ItemsConfig();

	public static final ItemsConfig getInstance() {
		return instance;
	}

	private ItemsConfig() {
	}

	@Override
	protected void setCustomDefaults(FileConfiguration fconf) {
		List<String> exampleBlocksList = Arrays.asList("DISPENSER", "NOTE_BLOCK", "STONE");
		List<String> testItemsList = Arrays.asList("NOPE", "NOPE:0", "0", "0:0");
		List<String> vanillaItemsList = Arrays.asList("IRON_PICKAXE");
		fconf.addDefault(CommonVars.VANILLA_ITEMS, vanillaItemsList);
		fconf.addDefault(CommonVars.TEST_ITEMS, testItemsList);

		fconf.addDefault(CommonVars.PrefixClickMouseRight + ".IRON_PICKAXE." + CommonVars.SuffixOnBlocksEverywhere,
				exampleBlocksList);
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
		return CommonVars.CONFIG_ITEMS_HEADER;
	}
}
