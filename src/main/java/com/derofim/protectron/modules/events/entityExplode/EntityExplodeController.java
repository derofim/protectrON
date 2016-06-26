package com.derofim.protectron.modules.events.entityExplode;

import java.util.logging.Logger;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.config.SettingsConfig;
import com.derofim.protectron.util.CommonVars;

public class EntityExplodeController {
	private static final boolean debugVerbose = false;

	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	private SettingsConfig st = SettingsConfig.getInstance();

	public EntityExplodeController() {
	}

	// Возвращает true если действие запрещено
	public boolean checkEntityExplode(EntityExplodeEvent e) {
		Entity ent = e.getEntity();
		if (st.getBool(CommonVars.PARAM_DENY_ENTITY_EXPOSION_EVERYWHERE)) {
			if (debugVerbose) {
				lg.info("EntityExplodeEventHandler ent.getType() " + ent.getType().toString());
				lg.info("EntityExplodeEventHandler evtName " + e.getEventName());
				lg.info("EntityExplodeEventHandler getLocation " + e.getLocation());
			}
			return true;
		}
		return false;
	}
}
