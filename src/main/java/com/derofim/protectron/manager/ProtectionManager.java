package com.derofim.protectron.manager;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.plugin.Plugin;

import com.derofim.protectron.ProtectronPlugin;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

/**
 * Represents the region protection management system
 *
 * @author Derofim
 */
public class ProtectionManager {
	private ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private static ProtectionManager instance = new ProtectionManager();

	public static final ProtectionManager getInstance() {
		return instance;
	}

	private ProtectionManager() {
		if (getWorldGuard() != null)
			plugin.getLogger().info("Protection found WorldGuard");
		else
			plugin.getLogger().warning("Protection cant load WorldGuard!");
	}

	// Returns true if regions has different owners
	public boolean isDifferentRegionOwners(ApplicableRegionSet setFrom, ApplicableRegionSet setTo) {
		if (setTo == null || setFrom == null)
			return false;
		Set<String> fromOwners = ProtectionManager.getInstance().getUserSet(setFrom);
		Set<String> toOwners = ProtectionManager.getInstance().getUserSet(setTo);
		if (setTo.size() > 0 && (fromOwners.isEmpty() || !toOwners.containsAll(fromOwners))) {
			return true;
		}
		return false;
	}

	public Set<String> getUserSet(ApplicableRegionSet rs) {
		Set<String> set = new HashSet<String>();
		if (rs != null) {
			for (ProtectedRegion region : rs) {
				DefaultDomain dom = region.getOwners();
				for (String p : dom.getPlayers()) {
					set.add(p.toLowerCase());
				}
				for (String p : dom.getGroups()) {
					set.add("g:" + p.toLowerCase());
				}
				dom = region.getMembers();
				for (String p : dom.getPlayers()) {
					set.add(p.toLowerCase());
				}
				for (String p : dom.getGroups()) {
					set.add("g:" + p.toLowerCase());
				}
			}
		}
		return set;
	}

	// Gets region manager plugin, for more info visit
	// http://docs.enginehub.org/manual/worldguard/latest/developer/dependency/
	public WorldGuardPlugin getWorldGuard() {
		Plugin pluginWG = plugin.getServer().getPluginManager().getPlugin("WorldGuard");
		if (pluginWG == null || !(pluginWG instanceof WorldGuardPlugin)) {
			plugin.getLogger().warning("Protection cant load WorldGuard!");
			plugin.getServer().getPluginManager().disablePlugin(plugin);
			return null; // Maybe you want throw an exception instead
		}
		return (WorldGuardPlugin) pluginWG;
	}

	public WorldEditPlugin getWorldEdit() {
		Plugin pluginWE = plugin.getServer().getPluginManager().getPlugin("WorldEdit");
		if (pluginWE == null || !(pluginWE instanceof WorldEditPlugin)) {
			plugin.getLogger().warning("Protection cant load WorldEdit!");
			plugin.getServer().getPluginManager().disablePlugin(plugin);
			return null; // Maybe you want throw an exception instead
		}
		return (WorldEditPlugin) pluginWE;
	}
}
