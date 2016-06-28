package com.derofim.protectron.modules.itemGroup;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.config.AbstractConfig;
import com.derofim.protectron.util.Vars;

public class ItemsConfig extends AbstractConfig {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private final String configLanguageVersion = "0.0.6";
	private final static File defaultFile = new File(plugin.getDataFolder(),
			"sets" + Vars.FOLDER_SEPARATOR + "items.yml");

	public static final String CONFIG_ITEMS_HEADER = "~~ Items sets ~~\r\n"
			+ "Description: Items set is any group (by title, modification, etc.) of items.\r\n"
			+ "~~ Deny item interaction with specific block ~~\r\n"
			+ "Just create in config parameter item-name with suffix (as below), inside that parameter list all blocks not allowed to interact with item \r\n"
			+ "Example of denying right clicking dispenser in foreign region holding iron pickaxe:\r\n"
			+ Vars.PrefixClickMouseRight + ".IRON_PICKAXE." + Vars.SuffixOnBlocksEverywhere + ":\r\n" + "- "
			+ Vars.BLOCK_DISPENSER + "\r\n" + "\r\n" + "~~ Possible suffixes after item name ~~\r\n"
			+ Vars.SuffixOnBlocksEverywhere + " - Use to deny in everywhere\r\n" + Vars.SuffixOnBlocksInForeignRegion
			+ " -Use to deny in foreign region\r\n" + Vars.SuffixOnBlocksInMyRegion
			+ " - Use to deny your region or outside regions\r\n";
	
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
		fconf.addDefault(Vars.VANILLA_ITEMS, vanillaItemsList);
		fconf.addDefault(Vars.TEST_ITEMS, testItemsList);

		fconf.addDefault(Vars.PrefixClickMouseRight + ".IRON_PICKAXE." + Vars.SuffixOnBlocksEverywhere,
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
		return CONFIG_ITEMS_HEADER;
	}
}
