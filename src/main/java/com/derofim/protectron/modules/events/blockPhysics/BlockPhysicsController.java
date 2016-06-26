package com.derofim.protectron.modules.events.blockPhysics;

import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPhysicsEvent;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.config.SettingsConfig;
import com.derofim.protectron.util.CommonVars;

public class BlockPhysicsController {
	private static final boolean debugVerbose = false;
	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private SettingsConfig sc = SettingsConfig.getInstance();
	private static BlockPhysicsController instance = new BlockPhysicsController();

	public static final BlockPhysicsController getInstance() {
		return instance;
	}

	private BlockPhysicsController() {
	}

	// Возвращает true если действие запрещено
	@SuppressWarnings("deprecation")
	public boolean checkBlockPhysics(BlockPhysicsEvent e) {
		List<String> blacklistBlockPhysicsEvent = sc.getStrList(CommonVars.PARAM_BLOCK_PHYSICS_EVENT_BLACKLIST);
		Block b = e.getBlock();
		if (blacklistBlockPhysicsEvent.contains(e.getChangedType())
				|| blacklistBlockPhysicsEvent.contains(b.getType().name())) {
			if (debugVerbose) {
				String evtName = e.getEventName();
				m.getLogger().info("====================================================== ");
				m.getLogger().info("BlockPhysicsEvent e.getChangedType " + e.getChangedType());
				m.getLogger().info("BlockPhysicsEvent getTypeId " + b.getTypeId());
				m.getLogger().info("BlockPhysicsEvent getType name " + b.getType().name());
				m.getLogger().info("BlockPhysicsEvent block " + b.getType().toString());
				m.getLogger().info("BlockPhysicsEvent evtName " + evtName);
				m.getLogger().info("BlockPhysicsEvent block loc " + b.getLocation());
				m.getLogger().info("BlockPhysicsEvent block toString " + b.toString());
			}
			return true;
		}
		return false;
	}
}
