package com.derofim.protectron.modules.events;

import org.bukkit.event.Listener;

public abstract class AbstractEvent implements Listener  {
	public abstract String getConfigName();
	public abstract boolean getDefaultState();
}
