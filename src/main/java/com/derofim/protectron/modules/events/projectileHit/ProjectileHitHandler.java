package com.derofim.protectron.modules.events.projectileHit;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.events.AbstractEvent;

public class ProjectileHitHandler extends AbstractEvent {
	private ProjectileHitController projectileHitController = null;

	public ProjectileHitHandler() {
		projectileHitController = new ProjectileHitController();
	}

	@Override
	public String getConfigName() {
		return ModulesConfig.MODULE_PROJECTILE_HIT;
	}

	@Override
	public boolean getDefaultState() {
		return true;
	}

	// Called when a projectile hits an object
	@EventHandler(priority = EventPriority.LOWEST)
	public void ProjectileHitEventHandler(ProjectileHitEvent e) {
		handleProjectileHit(e);
	}

	// Проверки при событии
	// deletes projectile in region
	public void handleProjectileHit(ProjectileHitEvent e) {
		if (projectileHitController.checkProjectileHit(e)) {
			Entity ent = e.getEntity();
			ent.remove();
		}
	}
}
