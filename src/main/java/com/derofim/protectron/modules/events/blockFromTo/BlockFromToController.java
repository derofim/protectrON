package com.derofim.protectron.modules.events.blockFromTo;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockFromToEvent;
import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.config.SettingsConfig;
import com.derofim.protectron.manager.ProtectionManager;
import com.derofim.protectron.util.Vars;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class BlockFromToController {
	private static final boolean debugVerbose = false;
	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	private WorldGuardPlugin wg = ProtectionManager.getInstance().getWorldGuard();
	private SettingsConfig st = SettingsConfig.getInstance();
	ProtectionManager protMgr = ProtectionManager.getInstance();

	private static BlockFromToController instance = new BlockFromToController();

	public static final BlockFromToController getInstance() {
		return instance;
	}

	private BlockFromToController() {
	}

	// Возвращает true если действие запрещено
	public boolean checkBlockFromTo(BlockFromToEvent e) {
		if (st.getBool(Vars.PARAM_DENY_LIQUID_FLOW_EVERYWHERE)) {
			return true;
		}
		Block b = e.getBlock();
		Block bTo = e.getToBlock();
		Location loc = bTo.getLocation();
		Location locTo = bTo.getLocation();
		String evtName = e.getEventName();
		RegionManager regionManager = wg.getRegionManager(bTo.getWorld());
		ApplicableRegionSet set = regionManager.getApplicableRegions(locTo);
		RegionManager regionManagerFrom = wg.getRegionManager(b.getWorld());
		ApplicableRegionSet setFrom = regionManagerFrom.getApplicableRegions(loc);
		if (st.getBool(Vars.PARAM_DENY_LIQUID_FLOW_IN_ALL_REGIONS)) {
			if ((setFrom.size() != 0 || set.size() != 0)) {
				if (debugVerbose) {
					lg.info("BlockFromToEventHandler block " + b.getType().toString());
					lg.info("BlockFromToEventHandler evtName " + evtName);
				}
				return true;
			}
		}
		if (st.getBool(Vars.PARAM_DENY_LIQUID_FLOW_IN_DIFFERENT_REGIONS)) {
			if (protMgr.isDifferentRegionOwners(setFrom, set)) {
				return true;
			}
		}
		return false;
	}
}
