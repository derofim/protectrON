package com.derofim.protectron.modules.command;

import org.bukkit.command.CommandExecutor;

public abstract class AbstractExecutor implements CommandExecutor {
	public abstract String getConfigName();
	public abstract String getCommandName();
}
