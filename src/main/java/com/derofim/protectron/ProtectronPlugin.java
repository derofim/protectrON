/******************************************************************************
 * Written by Denis Trofimov <derofim@yandex.ru>, June 2016                   *
 ******************************************************************************/

package com.derofim.protectron;

import com.derofim.protectron.modules.command.CommandsManager;
import com.derofim.protectron.modules.config.ConfigManager;
import com.derofim.protectron.modules.events.EventsManager;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * The implementation of the Protectron.
 */
public final class ProtectronPlugin extends JavaPlugin {

	private static ProtectronPlugin instance = null;

	public static final ProtectronPlugin getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		getLogger().info("Enabling plugin. Written by Denis Trofimov. Protectron (c) the Dero Team 2016");
		instance = this;
		ConfigManager.getInstance().load();
		CommandsManager.getInstance().setCommands();
		EventsManager.getInstance().setEvents();
		getLogger().info("Plugin enabled");
	}

	@Override
	public void onDisable() {
		getLogger().info("Disabling plugin");
	}
}
