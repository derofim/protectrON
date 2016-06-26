package com.derofim.protectron.modules;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.command.AbstractExecutor;
import com.derofim.protectron.modules.command.executor.BlockNameCommandExecutor;
import com.derofim.protectron.modules.command.executor.ItemNameCommandExecutor;
import com.derofim.protectron.modules.command.executor.ReloadCommandExecutor;
import com.derofim.protectron.modules.config.AbstractConfig;
import com.derofim.protectron.util.CommonVars;

public class ModulesConfig extends AbstractConfig {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();

	private final String configLanguageVersion = "0.0.4";
	private final static File defaultFile = new File(plugin.getDataFolder(), "modules.yml");

	// Modules
	public static final String module_section = "modules_enabled";
	public static final String MODULE_BLOCKS_PHYSICS = module_section + ".blocks_physics";
	public static final String MODULE_BLOCKS_MOVEMENT = module_section + ".blocks_movement";

	public static final String MODULE_COMMANDS = module_section + ".commands.enabled";

	public static final String MODULE_DEBUG = module_section + ".debug";
	public static final String MODULE_ENTITY_INTERACT = module_section + ".entity_interact";
	public static final String MODULE_ENTITY_DAMAGE = module_section + ".entity_damage";
	public static final String MODULE_ENTITY_EXPLODE = module_section + ".entity_explode";
	public static final String MODULE_INVENTORY_OPEN = module_section + ".inventory_open";
	public static final String MODULE_PLAYER_INTERACT = module_section + ".player_interact.enabled";
	public static final String MODULE_PLAYER_INTERACT_BLOCK = module_section + ".player_interact.block";
	public static final String MODULE_PLAYER_INTERACT_ITEM = module_section + ".player_interact.item";
	public static final String MODULE_PLAYER_INTERACT_PHYSICAL = module_section + ".player_interact.physical";
	public static final String MODULE_PLAYER_INTERACT_ENITY = module_section + ".player_interact_entity";
	public static final String MODULE_PROJECTILE_HIT = module_section + ".projectile_hit";
	public static final String MODULE_PISTON_EXTEND = module_section + ".piston_extend";
	public static final String MODULE_CMD_PREPROC_ENABLED = module_section + "command_preprocess.enabled";
	public static final String MODULE_CMD_PREPROC_WORLDGUARD_AUTOFLAGS = module_section + "command_preprocess.worldguard.autoflags";

	private static ModulesConfig instance = new ModulesConfig();

	public static final ModulesConfig getInstance() {
		return instance;
	}

	private ModulesConfig() {
	}

	@Override
	protected void setCustomDefaults(FileConfiguration fconf) {
		fconf.addDefault(MODULE_BLOCKS_PHYSICS, true);
		fconf.addDefault(MODULE_BLOCKS_MOVEMENT, true);
		//
		fconf.addDefault(MODULE_COMMANDS, true);
		List<AbstractExecutor> commands = new ArrayList<AbstractExecutor>();
		commands.add(ReloadCommandExecutor.getInstance());
		commands.add(ItemNameCommandExecutor.getInstance());
		commands.add(BlockNameCommandExecutor.getInstance());
		for (AbstractExecutor element : commands) {
			fconf.addDefault(module_section + ".commands." + element.getConfigName(), true);
		}
		//
		fconf.addDefault(MODULE_DEBUG, true);
		fconf.addDefault(MODULE_ENTITY_INTERACT, true);
		fconf.addDefault(MODULE_ENTITY_DAMAGE, true);
		fconf.addDefault(MODULE_ENTITY_EXPLODE, true);
		fconf.addDefault(MODULE_INVENTORY_OPEN, true);
		fconf.addDefault(MODULE_PLAYER_INTERACT, true);
		fconf.addDefault(MODULE_PLAYER_INTERACT_BLOCK, true);
		fconf.addDefault(MODULE_PLAYER_INTERACT_ITEM, true);
		fconf.addDefault(MODULE_PLAYER_INTERACT_PHYSICAL, true);
		fconf.addDefault(MODULE_PLAYER_INTERACT_ENITY, true);
		fconf.addDefault(MODULE_PROJECTILE_HIT, true);
		fconf.addDefault(MODULE_PISTON_EXTEND, true);
		fconf.addDefault(MODULE_CMD_PREPROC_ENABLED, true);
		fconf.addDefault(MODULE_CMD_PREPROC_WORLDGUARD_AUTOFLAGS, true);
	}

	@Override
	public File getFile() {
		return defaultFile;
	}

	@Override
	public String getLanguageVersion() {
		return configLanguageVersion;
	}

	@Override
	public String getHeader() {
		return CommonVars.CONFIG_MODULES_HEADER;
	}
}
