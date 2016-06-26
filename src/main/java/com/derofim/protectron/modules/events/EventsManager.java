package com.derofim.protectron.modules.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.blockFromTo.BlockFromToHandler;
import com.derofim.protectron.modules.events.blockPhysics.BlockPhysicsHandler;
import com.derofim.protectron.modules.events.entityDamage.EntityDamageHandler;
import com.derofim.protectron.modules.events.entityExplode.EntityExplodeHandler;
import com.derofim.protectron.modules.events.entityInteract.EntityInteractHandler;
import com.derofim.protectron.modules.events.inventoryOpen.InventoryOpenHandler;
import com.derofim.protectron.modules.events.pistonExtend.PistonExtendHandler;
import com.derofim.protectron.modules.events.playerCommandPreprocess.PlayerCommandPreprocessHandler;
import com.derofim.protectron.modules.events.playerEntityInteract.PlayerInteractEntityHandler;
import com.derofim.protectron.modules.events.playerInteract.PlayerInteractHandler;
import com.derofim.protectron.modules.events.projectileHit.ProjectileHitHandler;

public class EventsManager {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();
	private static final boolean debugVerbose = false;
	public List<AbstractEvent> events = new ArrayList<AbstractEvent>();
	private static EventsManager instance = new EventsManager();

	public static final EventsManager getInstance() {
		return instance;
	}

	private EventsManager() {
		events.add(new BlockFromToHandler());
		events.add(new BlockPhysicsHandler());
		events.add(new EntityDamageHandler());
		events.add(new EntityExplodeHandler());
		events.add(new EntityInteractHandler());
		events.add(new InventoryOpenHandler());
		events.add(new PistonExtendHandler());
		events.add(new PlayerCommandPreprocessHandler());
		events.add(new PlayerInteractEntityHandler());
		events.add(new PlayerInteractHandler());
		events.add(new ProjectileHitHandler());
	}

	public void setEvents() {
		if (debugVerbose)
			plugin.getLogger().info("Registering events");
		for (AbstractEvent element : events) {
			if (ModulesConfig.getInstance().getBool(element.getConfigName()))
				Bukkit.getPluginManager().registerEvents(element, plugin);
		}
		if (debugVerbose)
			plugin.getLogger().info("Events registered");
	}
}
