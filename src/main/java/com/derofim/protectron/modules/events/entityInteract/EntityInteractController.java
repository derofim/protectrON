package com.derofim.protectron.modules.events.entityInteract;

import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityInteractEvent;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.config.SettingsConfig;
import com.derofim.protectron.manager.ProtectionManager;
import com.derofim.protectron.util.CommonVars;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class EntityInteractController {
	private static final boolean debugVerbose = false;

	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	private WorldGuardPlugin wg = ProtectionManager.getInstance().getWorldGuard();
	private SettingsConfig st = SettingsConfig.getInstance();

	public EntityInteractController() {
	}

	// Возвращает true если действие запрещено
	@SuppressWarnings("deprecation")
	public boolean checkEntityInteract(EntityInteractEvent e) {
		Block b = e.getBlock();
		Entity ent = e.getEntity();
		RegionManager regionManager = wg.getRegionManager(b.getWorld());
		ApplicableRegionSet set = regionManager.getApplicableRegions(ent.getLocation());
		if (st.getBool(CommonVars.PARAM_DENY_ENTITY_INTERACT_IN_ANY_REGION) && set.size() != 0) {
			if (debugVerbose) {
				lg.info("EntityInteractEventHandler  block  " + b.getType().toString());
				lg.info("EntityInteractEventHandler id  " + ent.getEntityId());
				lg.info("EntityInteractEventHandler location " + ent.getLocation());
				lg.info("EntityInteractEventHandler type  " + ent.getType().toString());
				lg.info("EntityInteractEventHandler name  " + ent.getType().getName());
			}
			return true;
		}
		return false;
	}
}
