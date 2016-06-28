package com.derofim.protectron.modules.events.entityDamage;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.config.SettingsConfig;
import com.derofim.protectron.manager.ProtectionManager;
import com.derofim.protectron.modules.messages.MessagesConfig;
import com.derofim.protectron.util.Vars;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class EntityDamageController {
	private static final boolean debugVerbose = false;
	// overNotify may cause huge debug spam
	private static final boolean overNotify = false;

	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	private WorldGuardPlugin wg = ProtectionManager.getInstance().getWorldGuard();
	private SettingsConfig st = SettingsConfig.getInstance();
	private MessagesConfig mcfg = MessagesConfig.getInstance();

	public EntityDamageController() {
	}

	private boolean isRegionDamageProhibited(Player p, Location loc) {
		if (st.getBool(Vars.PARAM_DENY_DAMAGE_IN_FOREGN_REGION) && !wg.canBuild(p, loc))
			return true;
		return false;
	}

	// Игрок выстрелил по мобу
	private boolean handleEntityDamageByPlayerProjectile(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();
		Entity victim = e.getEntity();
		Projectile ple = (Projectile) damager;
		Player shooter = (Player) ple.getShooter();
		if (shooter != null) {
			if (isRegionDamageProhibited(shooter, victim.getLocation())) {
				if (debugVerbose)
					lg.info("EntityDamageEventHandler shooter name " + shooter.getName());
				return true;
			}
		}
		return false;
	}

	// По игроку выстрелил моб
	private boolean handlePlayerDamageByEntityProjectile(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();
		Entity victim = e.getEntity();
		Projectile ple = (Projectile) damager;
		Player plr = (Player) victim;
		LivingEntity lenty = (LivingEntity) ple.getShooter();
		if (isRegionDamageProhibited(plr, lenty.getLocation())) {
			if (debugVerbose)
				lg.info("EntityDamageEventHandler plr name " + plr.getName());
			return true;
		}
		return false;
	}

	private boolean handleEntityDamageByProjectile(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();
		Entity victim = e.getEntity();
		Projectile ple = (Projectile) damager;
		if (debugVerbose)
			lg.info("EntityDamageEventHandler Projectile " + ple.getType().toString());
		if (ple.getShooter() instanceof Player) {
			Player p = (Player) ple.getShooter();
			if (p.hasPermission(Vars.PERM_DAMAGE_ENTITY_BY_PROJECTILE_EVERYWHERE))
				return false;
			if (handleEntityDamageByPlayerProjectile(e)) {
				p.sendMessage(mcfg.getStr(MessagesConfig.MSG_NO_PERMISSION));
				return true;
			}
		} else if (ple.getShooter() instanceof LivingEntity) {
			if (victim instanceof Player) {
				Player p = (Player) victim;
				if (p.hasPermission(Vars.PERM_GET_DAMAGED_BY_ENTITY_PROJECTILE_EVERYWHERE))
					return false;
				if (handlePlayerDamageByEntityProjectile(e)) {
					if (overNotify)
						p.sendMessage(mcfg.getStr(MessagesConfig.MSG_NO_PERMISSION));
					return true;
				}
			}
		}
		return false;
	}

	private boolean handleEntityDamageByPlayerWeapon(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();
		Entity victim = e.getEntity();
		Player plr = (Player) damager;
		if (plr.hasPermission(Vars.PERM_DAMAGE_ENTITY_BY_WEAPON_EVERYWHERE))
			return false;
		if (isRegionDamageProhibited(plr, victim.getLocation())) {
			if (debugVerbose)
				lg.info("EntityDamageEventHandler plr getName" + plr.getName());
			plr.sendMessage(mcfg.getStr(MessagesConfig.MSG_NO_PERMISSION));
			return true;
		}
		return false;
	}

	private boolean handlePlayerDamageByEntityWeapon(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();
		Entity victim = e.getEntity();
		LivingEntity lenty = (LivingEntity) damager;
		if (victim instanceof Player) {
			Player p = (Player) victim;
			if (p.hasPermission(Vars.PERM_GET_DAMAGED_BY_ENTITY_WEAPON_EVERYWHERE))
				return false;
			Player plrVictim = (Player) victim;
			if (isRegionDamageProhibited(plrVictim, lenty.getLocation())) {
				if (debugVerbose)
					lg.info("EntityDamageEventHandler plr name " + plrVictim.getName());
				if (overNotify)
					p.sendMessage(mcfg.getStr(MessagesConfig.MSG_NO_PERMISSION));
				return true;
			}
		}
		if (debugVerbose)
			lg.info("EntityDamageEventHandler LivingEntity " + lenty.getType().toString());
		return false;
	}

	private boolean handleEntityDamageByEntity(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();
		if (damager instanceof Projectile) {
			if (handleEntityDamageByProjectile(e)) // кто то выстрелил и попал
				return true;
		} else if (damager instanceof Player) {
			if (handleEntityDamageByPlayerWeapon(e)) // игрок ударил вблизи
				return true;
		} else if (damager instanceof LivingEntity) {
			if (handlePlayerDamageByEntityWeapon(e)) // моб ударил вблизи
				return true;
		}
		return false;
	}

	// Возвращает true если действие запрещено
	public boolean checkEntityDamage(EntityDamageEvent e) {
		if (e instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent evt = (EntityDamageByEntityEvent) e;
			if (handleEntityDamageByEntity(evt))
				return true;
		}
		return false;
	}
}
