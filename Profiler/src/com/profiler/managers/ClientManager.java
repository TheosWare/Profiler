package com.profiler.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.profiler.bases.Client;

public class ClientManager {
	
	private List<Client> clients = new ArrayList<Client>();
	public List<Client> getClients() { return this.clients; }
	
	public Client getClient(Player p) { return this.getClient(p.getUniqueId()); }
	public Client getClient(UUID owner) { for(Client c : this.clients) { if(c.getOwnerUUID().equals(owner)) { return c; } } return null; }
	
	public void addClient(UUID uuid) { Client c = this.getClient(uuid); if(c == null) { this.clients.add(new Client(uuid)); } }
	public void addClient(Player p) { this.addClient(p.getUniqueId()); }
	
	public void removeClient(Player p) { Client c = this.getClient(p); if(c != null) { this.clients.remove(c); } }
	public void removeClient(Client c) { Client cl = this.getClient(c.getOwnerUUID()); if(cl != null) { this.clients.remove(cl); } }
	
	public void removeClient(UUID uuid) {  Client c = this.getClient(uuid); if(c != null) { this.clients.remove(c); } }
}
