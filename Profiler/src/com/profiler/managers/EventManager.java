package com.profiler.managers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.profiler.main.Profiler;

public class EventManager implements Listener{
	
	
	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent e)
	{
		Profiler.getInstance().getClientManager().addClient(e.getPlayer());
	}

	@EventHandler
	public void playerExitEvent(PlayerQuitEvent e)
	{
		Profiler.getInstance().getClientManager().removeClient(e.getPlayer());
	}
	
	@EventHandler
	public void playerKickEvent(PlayerKickEvent e)
	{
		Profiler.getInstance().getClientManager().removeClient(e.getPlayer());
	}
	
}
