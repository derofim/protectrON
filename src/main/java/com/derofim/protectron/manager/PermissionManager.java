package com.derofim.protectron.manager;

import org.bukkit.plugin.RegisteredServiceProvider;

import com.derofim.protectron.ProtectronPlugin;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class PermissionManager {
	private static PermissionManager instance = new PermissionManager();
	private ProtectronPlugin plugin = ProtectronPlugin.getInstance();
	private Economy econ = null;
	private Permission perms = null;
	private Chat chat = null;

	public static final PermissionManager getInstance() {
		return instance;
	}

	protected Economy getEconomy() {
		return econ;
	}

	public Permission getPermission() {
		return perms;
	}

	public Chat getChat() {
		return chat;
	}

	private PermissionManager() {
		// May be added setupEconomy()
		setupPermissions();
		setupChat();
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> rsp = plugin.getServer().getServicesManager().getRegistration(Chat.class);
		chat = rsp.getProvider();
		return chat != null;
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager()
				.getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}
}
