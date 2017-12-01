package me.iamajiu.OddCurrency;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OddEvents implements Listener {
	
	private Inventory getClickedInv(InventoryClickEvent event) {
		int rawSlot = event.getRawSlot();
		InventoryView view = event.getView();
		Inventory top = view.getTopInventory();
		if (rawSlot >= 0 && top != null && rawSlot < top.getSize())
		  return top;
		return rawSlot >= 0 ? view.getBottomInventory() : null;
	}
	
	public ShopItem shop_event(ItemStack clicked_item) {
		for (int i = 0; i < Main.getPlugin(Main.class).ocfg.getNumItems(); ++i) {
			if (Main.getPlugin(Main.class).ocfg.getShopItemList().get(i).getMeta().equals(clicked_item.getItemMeta())) {     //compare itemmetas to get the itemshop for execute()
				return Main.getPlugin(Main.class).ocfg.getShopItemList().get(i); 
			}
		}
		return null; 
	}
	
	public String parseCommand(Player player, ShopItem sitem) {
		String player_name = player.getName(); 
		String parse = sitem.getCommand();
		String replace = "player.sender"; 
		if (parse.contains(replace)) {
			String temp = parse.replaceAll(replace, player_name); 
			return temp; 
		}
		else {
			return parse; 
		}
	}
	
	private void checkFunds(Player player, ShopItem sitem) {
		if (!player.getInventory().contains(Material.SLIME_BALL)) {
			player.sendMessage(ChatColor.RED + "You don't even have anything, kid.");
			return;
		}
		Inventory player_inv = player.getInventory(); 
		int price = sitem.getPrice(); 
		ItemStack player_items[] = player_inv.getContents(); 
		for (int i = 0; i < player_inv.getSize(); ++i) {
			if (player_items[i] == null || !(player_items[i].hasItemMeta())) {
				player.sendMessage(ChatColor.RED + "Insufficient OddCurrency!");
				return;
			}
			if (player_items[i].getItemMeta().getDisplayName().equals(ChatColor.GREEN + " " + ChatColor.BOLD + "OddCurrency")) {
				if (price > player_items[i].getAmount()) {
					player.sendMessage(ChatColor.RED + "Insufficient OddCurrency!");
					return;
				}
				else {
					int total = player_items[i].getAmount() - price; 
					if (total == 0) {
						player_inv.remove(player_items[i]);
					}
					player_items[i].setAmount(total);
					player.sendMessage(ChatColor.BLUE + "You purchased " + sitem.getName() + "!");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), parseCommand(player, sitem));
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void InvenClick(InventoryClickEvent event) {
		if (getClickedInv(event) == null || event.getCurrentItem() == null) {
			return; 
		}
		Player player = (Player) event.getWhoClicked();
		ClickType click = event.getClick(); 
		Inventory clicked_inv = getClickedInv(event); 
		ItemStack clicked_item = event.getCurrentItem(); 
		if (clicked_inv.getName().equals(ChatColor.DARK_BLUE + "OddShop")) {
			event.setCancelled(true);
			if (clicked_item == null || !clicked_item.hasItemMeta()) {
				return; 
			}
			else if (Main.getPlugin(Main.class).ocfg.getItemMetaList().contains(clicked_item.getItemMeta())) {
				ShopItem temp = shop_event(clicked_item); 
				checkFunds(player, temp); 
				return; 
			}
		}
		else {
			return; 
		}
	}
}

