package com.derofim.protectron.modules.events.blockPhysics;

import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPhysicsEvent;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.config.SettingsConfig;
import com.derofim.protectron.modules.blockGroup.BlocksConfig;
import com.derofim.protectron.util.Utils;
import com.derofim.protectron.util.Vars;

public class BlockPhysicsController {
	private static final boolean debugVerbose = false;
	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private SettingsConfig sc = SettingsConfig.getInstance();
	private static BlockPhysicsController instance = new BlockPhysicsController();
	private BlocksConfig blc = BlocksConfig.getInstance();

	public static final BlockPhysicsController getInstance() {
		return instance;
	}

	private BlockPhysicsController() {
	}

	// returns true if block is in blacklist
	@SuppressWarnings("deprecation")
	private boolean checkBlacklist(List<String> contentInGroup, BlockPhysicsEvent e) {
		if (!contentInGroup.isEmpty()) {
			Block b = e.getBlock();
			if (Utils.isInList(contentInGroup, e.getChangedType().name())
					|| Utils.isInList(contentInGroup, b.getType().name())) {
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
		}
		return false;
	}

	// Возвращает true если действие запрещено
	public boolean checkBlockPhysics(BlockPhysicsEvent e) {
		List<String> blacklistBlockPhysicsEvent = sc.getStrList(Vars.PARAM_BLOCK_PHYSICS_EVENT_BLACKLIST);
		for (String it : blacklistBlockPhysicsEvent) {
			List<String> contentInGroup = blc.getStrList(it);
			if (checkBlacklist(contentInGroup, e))
				return true;
		}
		return false;
	}
}