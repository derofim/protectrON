package com.derofim.protectron.modules.config;

import java.util.ArrayList;
import java.util.List;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.config.SettingsConfig;
import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.blockGroup.BlocksConfig;
import com.derofim.protectron.modules.debug.DebugConfig;
import com.derofim.protectron.modules.events.blockBreak.BlockBreakConfig;
import com.derofim.protectron.modules.events.blockPlace.BlockPlaceConfig;
import com.derofim.protectron.modules.events.inventoryOpen.InventoryOpenConfig;
import com.derofim.protectron.modules.events.playerInteract.PlayerInteractConfig;
import com.derofim.protectron.modules.itemGroup.ItemsConfig;
import com.derofim.protectron.modules.limiter.BlockLimitConfig;
import com.derofim.protectron.modules.messages.MessagesConfig;

/**
 * Represents the global configuration
 *
 * @author Derofim
 */
public class ConfigManager {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();
	private static final boolean debugVerbose = false;

	private static ConfigManager instance = new ConfigManager();
	public List<AbstractConfig> configurations = new ArrayList<AbstractConfig>();

	public static final ConfigManager getInstance() {
		return instance;
	}

	private ConfigManager() {
		configurations.add(DebugConfig.getInstance());
		configurations.add(PlayerInteractConfig.getInstance());
		configurations.add(BlocksConfig.getInstance());
		configurations.add(ItemsConfig.getInstance());
		configurations.add(MessagesConfig.getInstance());
		configurations.add(ModulesConfig.getInstance());
		configurations.add(SettingsConfig.getInstance());
		configurations.add(InventoryOpenConfig.getInstance());
		configurations.add(BlockBreakConfig.getInstance());
		configurations.add(BlockPlaceConfig.getInstance());
		configurations.add(BlockLimitConfig.getInstance());
	}

	public void load() {
		if (debugVerbose)
			plugin.getLogger().info("Configuration loaded");
		for (AbstractConfig element : configurations) {
			element.loadConfig();
		}
	}

	public void reload() {
		for (AbstractConfig element : configurations) {
			element.reloadConfig();
		}
		if (debugVerbose)
			plugin.getLogger().info("Configuration reloaded");
	}
}
