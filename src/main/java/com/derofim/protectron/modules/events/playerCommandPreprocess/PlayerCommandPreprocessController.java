package com.derofim.protectron.modules.events.playerCommandPreprocess;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.config.SettingsConfig;
import com.derofim.protectron.manager.PermissionManager;
import com.derofim.protectron.manager.ProtectionManager;
import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.messages.MessagesConfig;
import com.derofim.protectron.util.Vars;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.bukkit.commands.RegionCommands;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class PlayerCommandPreprocessController {
	private static final boolean debugVerbose = false;

	private ProtectronPlugin m = ProtectronPlugin.getInstance();
	private Logger lg = m.getLogger();
	private WorldGuardPlugin wg = ProtectionManager.getInstance().getWorldGuard();
	private WorldEditPlugin wed = ProtectionManager.getInstance().getWorldEdit();
	private MessagesConfig mcfg = MessagesConfig.getInstance();
	private SettingsConfig stfg = SettingsConfig.getInstance();

	private static PlayerCommandPreprocessController instance = new PlayerCommandPreprocessController();

	public static final PlayerCommandPreprocessController getInstance() {
		return instance;
	}

	private PlayerCommandPreprocessController() {
	}

	// Region per-group autoflags
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean handleAutoFlags(PlayerCommandPreprocessEvent e, String rgname) {
		Player player = e.getPlayer();
		if(player.hasPermission("protectron.claim.autoflags.bypass")) return false;
		RegionManager regionManager = wg.getRegionManager(player.getWorld());
		ProtectedRegion pr = regionManager.getRegion(rgname);
		if (pr == null) {
			player.sendMessage(mcfg.getStr(MessagesConfig.MSG_WG_ERR_NO_REGION));
			return false;
		}
		String pGroup = PermissionManager.getInstance().getPermission().getPrimaryGroup(player);
		List<String> userGroupAutoflags = stfg.getStrList(Vars.PARAM_AUTOFLAGS_FLAGS + "." + pGroup);
		if (userGroupAutoflags == null || userGroupAutoflags.isEmpty())
			return false;
		if (debugVerbose) {
			lg.info("userGroupAutoflags for " + pGroup);
		}
		for (String strIt : userGroupAutoflags) {
			if (debugVerbose) {
				lg.info("userGroupAutoflags: " + strIt);
			}
			ConfigurationSection flagKeys = stfg.getConfig().getConfigurationSection(strIt);
			if (flagKeys == null) {
				lg.warning("Section " + strIt + " dont exist!");
				return false;
			}
			for (String fKey : flagKeys.getKeys(false)) {
				if (debugVerbose) {
					lg.info("key: " + fKey);
					lg.info("value: " + flagKeys.getString(fKey));
				}
				Flag<?> flag = DefaultFlag.fuzzyMatchFlag(fKey);
				if (flag == null) {
					lg.warning("Flag with name " + fKey + " dont exist!");
					return false;
				}
				try {
					pr.setFlag((Flag) flag, flag.parseInput(wg, player, flagKeys.getString(fKey)));
				} catch (InvalidFlagFormat e1) {
					e1.printStackTrace();
					return false;
				}

			}
		}
		return true;
	}

	// Creates new region without limits check
	// returns true if region created successfully
	private boolean createNewRegion(PlayerCommandPreprocessEvent e, Selection selection) {
		Player player = e.getPlayer();
		if(player.hasPermission("protectron.claim.all.bypass")) return false;
		String msg = e.getMessage();
		String rgname = msg.split(" ")[2];
		RegionManager regionManager = wg.getRegionManager(player.getWorld());
		// see http://wiki.sk89q.com/wiki/WorldGuard/Regions/API
		Location locMin = selection.getMinimumPoint();
		Location locMax = selection.getMaximumPoint();
		ApplicableRegionSet regions = wg.getRegionManager(locMin.getWorld()).getApplicableRegions(locMin);
		ApplicableRegionSet regions2 = wg.getRegionManager(locMax.getWorld()).getApplicableRegions(locMax);
		if (regions.size() > 0 || regions2.size() > 0) {
			player.sendMessage(mcfg.getStr(MessagesConfig.MSG_WG_ERR_ALREADY_CLAIMED));
			return false;
		}
		if (debugVerbose) {
			lg.info("Saving region");
		}
		// plot was inside regions owned by player
		RegionCommands r = new RegionCommands(wg);
		try {
			CommandContext args = new CommandContext(new String[] { "claim", rgname });
			r.claim(args, player);
			if (debugVerbose) {
				lg.info("Claimed region");
			}
			player.sendMessage(mcfg.getStr(MessagesConfig.MSG_WG_CREATED));
			if (ModulesConfig.getInstance().getBool(ModulesConfig.MODULE_CMD_PREPROC_WORLDGUARD_AUTOFLAGS)) {
				if (!handleAutoFlags(e, rgname)) {
					player.sendMessage(mcfg.getStr(MessagesConfig.MSG_WG_ERR_CANT_AUTO_FLAG));
				}
			}
		} catch (CommandException e1) {
			player.sendMessage(e1.getMessage());
			e1.printStackTrace();
			return false;
		}
		// make sure that the region creation was a success before charging
		ProtectedRegion newRg = regionManager.getRegion(rgname);
		if (newRg == null) {
			player.sendMessage(mcfg.getStr(MessagesConfig.MSG_WG_ERR_WRONG));
			return false;
		}
		if (debugVerbose) {
			lg.info("Region claim success");
		}
		return true;
	}

	private boolean isRegionLimitsExceed(Player player, Selection selection) {
		if(player.hasPermission("protectron.claim.limits.bypass")) return false;
		String pGroup = PermissionManager.getInstance().getPermission().getPrimaryGroup(player);
		Integer maxTotalSize = getClaimTotalLimitByGroup(pGroup);
		Integer maxWidthSize = getClaimWidthLimitByGroup(pGroup);
		Integer maxHeightSize = getClaimHeightLimitByGroup(pGroup);
		Integer maxLengthSize = getClaimLengthLimitByGroup(pGroup);
		if (debugVerbose) {
			lg.info("maxTotalSize: " + maxTotalSize);
			lg.info("maxWidthSize: " + maxWidthSize);
			lg.info("maxHeightSize: " + maxHeightSize);
		}
		// Get Y-size.
		if (selection.getHeight() > maxHeightSize) {
			player.sendMessage(mcfg.getStr(MessagesConfig.MSG_WG_ERR_MAX_HEIGHT) + maxHeightSize);
			return true;
		}
		// Get X-size.
		if (selection.getWidth() > maxWidthSize) {
			player.sendMessage(mcfg.getStr(MessagesConfig.MSG_WG_ERR_MAX_WIDTH) + maxWidthSize);
			return true;
		}
		// Get Z-size.
		if (selection.getLength() > maxLengthSize) {
			player.sendMessage(mcfg.getStr(MessagesConfig.MSG_WG_ERR_MAX_LENGTH) + maxLengthSize);
			return true;
		}
		// Get the number of blocks in the region.
		if (selection.getArea() > maxTotalSize) {
			player.sendMessage(mcfg.getStr(MessagesConfig.MSG_WG_ERR_MAX_TOTAL) + maxTotalSize);
			return true;
		}
		return false;
	}

	private boolean handleCreationOfNewRegion(PlayerCommandPreprocessEvent e, Selection selection) {
		Player player = e.getPlayer();
		if (debugVerbose) {
			lg.info("Selection width: " + selection.getWidth());
			lg.info("Selection height: " + selection.getHeight());
		}
		if (isRegionLimitsExceed(player, selection))
			return true;

		if (!createNewRegion(e, selection))
			return true;
		return true;
	}

	private int getClaimWidthLimitByGroup(String group) {
		if (SettingsConfig.getInstance().getConfig().contains(Vars.PARAM_GROUP_CLAIMS_WIDTH + "." + group)) {
			return SettingsConfig.getInstance().getInt(Vars.PARAM_GROUP_CLAIMS_WIDTH + "." + group);
		}
		return 40000;
	}

	private int getClaimHeightLimitByGroup(String group) {
		if (SettingsConfig.getInstance().getConfig().contains(Vars.PARAM_GROUP_CLAIMS_HEIGHT + "." + group)) {
			return SettingsConfig.getInstance().getInt(Vars.PARAM_GROUP_CLAIMS_HEIGHT + "." + group);
		}
		return 40000;
	}

	private int getClaimLengthLimitByGroup(String group) {
		if (SettingsConfig.getInstance().getConfig().contains(Vars.PARAM_GROUP_CLAIMS_LENGTH + "." + group)) {
			return SettingsConfig.getInstance().getInt(Vars.PARAM_GROUP_CLAIMS_LENGTH + "." + group);
		}
		return 40000;
	}

	private int getClaimTotalLimitByGroup(String group) {
		if (SettingsConfig.getInstance().getConfig().contains(Vars.PARAM_GROUP_CLAIMS_TOTAL + "." + group)) {
			return SettingsConfig.getInstance().getInt(Vars.PARAM_GROUP_CLAIMS_TOTAL + "." + group);
		}
		return 40000;
	}

	private boolean handleWorldGuardClaim(PlayerCommandPreprocessEvent e) {
		String msg = e.getMessage();
		String[] split = msg.toLowerCase().trim().split(" ");
		if (debugVerbose)
			lg.info("COMMAND: " + msg.toLowerCase().trim());
		if (!split[0].equalsIgnoreCase("/rg") && !split[0].equalsIgnoreCase("/region"))
			return false;
		if (debugVerbose)
			lg.info("WG COMMAND: rg");
		if (!msg.startsWith("/region claim ") && !msg.startsWith("/rg claim "))
			return false;
		Player player = e.getPlayer();
		if (player instanceof Player) {
			player = (Player) player;
		} else {
			player.sendMessage(mcfg.getStr(MessagesConfig.MSG_WG_CANT_DO));
			return true;
		}
		if (split.length < 3) {
			if (ModulesConfig.getInstance().getBool(ModulesConfig.MODULE_CMD_PREPROC_WORLDGUARD_AUTONAME)) {
				msg += " zone" + wg.getRegionManager(player.getWorld()).getRegionCountOfPlayer(wg.wrapPlayer(player));
			} else {
				lg.info("need more args");
				return false;
			}
		}
		String pGroup = PermissionManager.getInstance().getPermission().getPrimaryGroup(player);
		if (debugVerbose)
			lg.info("WG pGroup: " + pGroup);
		if (!player.hasPermission("worldguard.region.claim")) {
			return true;
		}
		if (debugVerbose)
			lg.info("WG COMMAND: worldguard.region.claim");
		// cancel the normal region claim event
		e.setCancelled(true);
		Selection selection = wed.getSelection(player);
		Selection tmpSelection = selection;
		if (ModulesConfig.getInstance().getBool(ModulesConfig.MODULE_CMD_PREPROC_WORLDGUARD_AUTOEXPAND) && !player.hasPermission("protectron.claim.autoexpand.bypass")) {
			if (debugVerbose)
				lg.info("WG autoexpanding");
			// m.getServer().dispatchCommand(player, "//expand vert");
			if (stfg.getBool(SettingsConfig.PARAM_EXPAND_ADD_ENABLED)) {
				int autoExpandX = stfg.getInt(SettingsConfig.PARAM_EXPAND_ADD_X);
				int autoExpandY = stfg.getInt(SettingsConfig.PARAM_EXPAND_ADD_Y);
				int autoExpandZ = stfg.getInt(SettingsConfig.PARAM_EXPAND_ADD_Z);
				selection = new CuboidSelection(player.getWorld(),
						selection.getNativeMinimumPoint().add(-autoExpandX, -autoExpandY, -autoExpandZ),
						selection.getNativeMaximumPoint().add(autoExpandX, autoExpandY, autoExpandZ));
				wed.setSelection(player, selection);
			}
			if (stfg.getBool(SettingsConfig.PARAM_EXPAND_MAX_ENABLED)) {
				int autoMaxX = getClaimWidthLimitByGroup(pGroup);
				int autoMaxY = getClaimHeightLimitByGroup(pGroup);
				int autoMaxZ = getClaimLengthLimitByGroup(pGroup);
				int deltaAutoMaxX = autoMaxX - selection.getWidth();
				int deltaAutoMaxY = autoMaxY - selection.getHeight();
				int deltaAutoMaxZ = autoMaxZ - selection.getLength();
				selection = new CuboidSelection(player.getWorld(),
						selection.getNativeMinimumPoint().add(-deltaAutoMaxX / 2, -deltaAutoMaxY / 2,
								-deltaAutoMaxZ / 2),
						selection.getNativeMaximumPoint().add(deltaAutoMaxX / 2, deltaAutoMaxY / 2, deltaAutoMaxZ / 2));
				wed.setSelection(player, selection);
			}
			if (isRegionLimitsExceed(player, selection)) {
				wed.setSelection(player, tmpSelection);
				selection = tmpSelection;
				lg.warning("Region autoexpand size too big!");
			}
		}
		RegionManager regionManager = wg.getRegionManager(player.getWorld());
		String rgname = msg.split(" ")[2];
		if (selection != null) {
			if (debugVerbose)
				lg.info("WG COMMAND: selection != null");
			ProtectedRegion existingRg = regionManager.getRegion(rgname);
			if (existingRg == null) {
				if (handleCreationOfNewRegion(e, selection))
					return true;
			} else {
				if (debugVerbose)
					lg.info("WG COMMAND: worldguard region exists!");
				player.sendMessage(mcfg.getStr(MessagesConfig.MSG_WG_ERR_ALREADY_CLAIMED));
			}
		}
		return true;
	}

	public boolean checkPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		if (handleWorldGuardClaim(e))
			return true; // May cancel event!
		return false;
	}
}
