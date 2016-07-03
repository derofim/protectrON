package com.derofim.protectron.modules.command;

import java.util.ArrayList;
import java.util.List;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.command.executor.BlockNameCommandExecutor;
import com.derofim.protectron.modules.command.executor.ItemNameCommandExecutor;
import com.derofim.protectron.modules.command.executor.OopsLimit;
import com.derofim.protectron.modules.command.executor.ReloadCommandExecutor;

public class CommandsManager {
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();
	private static final boolean debugVerbose = false;
	public List<AbstractExecutor> commands = new ArrayList<AbstractExecutor>();

	private static CommandsManager instance = new CommandsManager();
	private ModulesConfig mc = ModulesConfig.getInstance();

	public static final CommandsManager getInstance() {
		return instance;
	}

	private CommandsManager() {
		// Also in ModulesConfig and plugin.yml
		commands.add(ReloadCommandExecutor.getInstance());
		commands.add(ItemNameCommandExecutor.getInstance());
		commands.add(BlockNameCommandExecutor.getInstance());
		commands.add(OopsLimit.getInstance());
	}

	// Registers commands, for more info visit
	// https://jd.bukkit.org/org/bukkit/command/CommandExecutor.html
	public void setCommands() {
		if (!mc.getBool(ModulesConfig.MODULE_COMMANDS))
			return;
		// Commands in plugin.yml
		for (AbstractExecutor element : commands) {
			if (mc.getBool(ModulesConfig.module_section + ".commands." + element.getConfigName()))
				plugin.getCommand(element.getCommandName()).setExecutor(element);
			else if (debugVerbose)
				plugin.getLogger().info("Command " + element.getConfigName() + " disabled");
		}
		if (debugVerbose)
			plugin.getLogger().info("Commands loaded");
	}
}
