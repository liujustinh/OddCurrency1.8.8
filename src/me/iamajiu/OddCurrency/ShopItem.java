package me.iamajiu.OddCurrency;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopItem {
	private int price; 
	private String name; 
	private String cmd; 
	private String lore; 
	private String type; 
	private ItemStack item; 
	private ItemMeta item_meta; 
	
	public ShopItem(int price, String name, String cmd, String lore, String type) {
		this.price = price; 
		this.name = name; 
		this.cmd = cmd; 
		this.lore = lore; 
		this.type = type; 
		if (validChecker() == true) {
			this.item = makeItemStack(); 
			this.item_meta = this.item.getItemMeta(); 
		}
		else {
			this.item = null; 
			this.item_meta = null; 
		}
	}
	
	public boolean validChecker() {
		if (name.isEmpty() || lore.isEmpty() || type.isEmpty()) {
			System.out.println("Invalid OddShop item entry. Please fix config file!!"); 
			return false; 
		}
		else {
			return true; 
		}
	}
	
	private ItemStack makeItemStack() {
		Material material = Material.matchMaterial(type); 
		ItemStack tempitem = new ItemStack(material, 1);
		ItemMeta meta = tempitem.getItemMeta(); 
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		ArrayList<String> lores = new ArrayList<String>(); 
		lores.add(ChatColor.translateAlternateColorCodes('&', this.lore)); 
		lores.add(ChatColor.GOLD + "Price: " + String.valueOf(price));      //sets price
		meta.setLore(lores);
		tempitem.setItemMeta(meta); 
		return tempitem; 
	}
	
	public String getCommand() {
		return this.cmd; 
	}
	
	
	public String getName() {
		return this.name; 
	}
	
	public int getPrice() {
		return this.price; 
	}
	
	public ItemMeta getMeta() {
		return this.item_meta; 
	}
	
	public ItemStack getItemStack() {
		return this.item; 
	}
	
	public void execute() {
		//Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
		return;
	}
}
