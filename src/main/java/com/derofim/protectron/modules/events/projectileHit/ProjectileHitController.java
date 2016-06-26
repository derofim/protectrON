package com.derofim.protectron.modules.events.projectileHit;

import java.util.logging.Logger;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.config.SettingsConfig;
import com.derofim.protectron.manager.ProtectionManager;
import com.derofim.protectron.util.CommonVars;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class ProjectileHitController {
	private static final boolean debugVerbose = false;

	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	private WorldGuardPlugin wg = ProtectionManager.getInstance().getWorldGuard();
	private SettingsConfig st = SettingsConfig.getInstance();

	public ProjectileHitController() {
	}

	@SuppressWarnings("deprecation")
	private boolean checkProjectile(ProjectileHitEvent e) {
		Entity ent = e.getEntity();
		Projectile proj = (Projectile) ent;
		if (proj.getShooter() instanceof Player) {
			Player shooter = (Player) proj.getShooter();
			if (shooter != null) {
				if (st.getBool(CommonVars.PARAM_DENY_PROJECTILE_IN_FOREGN_REGION)
						&& !wg.canBuild(shooter, proj.getLocation())) {
					if (debugVerbose) {
						lg.info("ProjectileHitEventHandler id  " + ent.getEntityId());
						lg.info("ProjectileHitEventHandler location " + ent.getLocation());
						lg.info("ProjectileHitEventHandler ent.toString  " + ent.toString());
						lg.info("ProjectileHitEventHandler type  " + ent.getType().toString());
						lg.info("ProjectileHitEventHandler name  " + ent.getType().getName());
						lg.info("ProjectileHitEventHandler shooter name  " + shooter.getName());
					}
					return true;
				}
			}
		}
		return false;
	}

	// Возвращает true если снаряд надо удалить
	public boolean checkProjectileHit(ProjectileHitEvent e) {
		if (e.getEntity() instanceof Projectile) {
			if (checkProjectile(e))
				return true;
		}
		return false;
	}
}
