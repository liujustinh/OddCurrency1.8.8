package me.iamajiu.OddCurrency;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class OddCommands implements CommandExecutor{
	
	public static boolean isNumeric(String str) {
		  NumberFormat formatter = NumberFormat.getInstance();
		  ParsePosition pos = new ParsePosition(0);
		  formatter.parse(str, pos);
		  return str.length() == pos.getIndex();
		}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0 || args.length > 2) {
			sender.sendMessage("Incorrect usage. Correct usage: /oc shop");
			return true; 
		}
		if (args[0].equalsIgnoreCase("shop") && sender.hasPermission("oc.player")) {
			if (sender instanceof Player) {
				Player player = (Player)sender; 
				openShop(player); 
				return true; 
			}
			else {
				sender.sendMessage("Only players can use this command!");
				return true; 
			}
		}
		else if (Bukkit.getServer().getPlayer(args[0]) != null && isNumeric(args[1]) && sender.hasPermission("oc.admin")) {
			Player player = Bukkit.getServer().getPlayer(args[0]); 
			if (!player.hasPlayedBefore() && !(player.isOnline())) {
				sender.sendMessage("Player is currently offline or has not played before!");
				return true; 
			}
			else {
				int amount = Integer.parseInt(args[1]); 
				giveCurrency(player, amount); 
				return true;
			}
		}
		else {
			if (!(args[0].equalsIgnoreCase("shop"))) {
				sender.sendMessage("You do not have permission.");
				return true; 
			}
			else {
				sender.sendMessage("Incorrect usage of /oc command. Correct usage: /oc shop");
				return true; 
			}
		}
	}
	
	public void openShop(Player sender) {
		int num_items = Main.getPlugin(Main.class).ocfg.getNumItems(); 
		ArrayList<ShopItem> shopitems = Main.getPlugin(Main.class).ocfg.getShopItemList();  
		Inventory inv = Bukkit.getServer().createInventory(null, 9, ChatColor.DARK_BLUE + "OddShop"); 
		for (int i = 0; i < num_items; ++i) {
			if (shopitems.get(i).validChecker() == true) {
				inv.setItem(i, shopitems.get(i).getItemStack());
			}
			else {
				continue; 
			}
		}
		sender.openInventory(inv); 
		return;
	}
	
	public void giveCurrency(Player player, int amount) {
		ItemStack item = new ItemStack(Material.SLIME_BALL, amount); 
		ItemMeta meta = item.getItemMeta(); 
		meta.setDisplayName(ChatColor.GREEN + " " + ChatColor.BOLD + "OddCurrency");
		ArrayList<String> lore = new ArrayList<String>(); 
		lore.add(ChatColor.DARK_PURPLE + "Maybe you could spend it on something useful!"); 
		meta.setLore(lore);
		item.setItemMeta(meta); 
		player.getInventory().addItem(item); 
	}
	

}