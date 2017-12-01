package me.iamajiu.OddCurrency;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import me.iamajiu.OddCurrency.Main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OddConfig {
	
	protected Main main; 
	private File file; 
	protected FileConfiguration config; 
	private ArrayList<ShopItem> shop_items; 
	private ArrayList<ItemMeta> item_metas; 
	
	public OddConfig(Main main) {
		this.main = main; 
		this.file = new File(main.getDataFolder(), "config.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.config = YamlConfiguration.loadConfiguration(file); 
		newConfig(); 
		this.shop_items = parseItems(); 
		this.item_metas = parseItemMeta(); 
	}
	
	public void newConfig() {
		if (!config.contains("Number of items")) {
			config.set("Number of items", 0);
		}
		if (!config.contains("Items")) {
			config.set("Items.Slot1.Price", 0); 
			config.set("Items.Slot1.Name", " ");
			config.set("Items.Slot1.Command", " ");
			config.set("Items.Slot1.Lore", " ");
			config.set("Items.Slot1.Type", " ");
			config.set("Items.Slot2.Price", 0); 
			config.set("Items.Slot2.Name", " ");
			config.set("Items.Slot2.Command", " ");
			config.set("Items.Slot2.Lore", " ");
			config.set("Items.Slot2.Type", " ");
			config.set("Items.Slot3.Price", 0); 
			config.set("Items.Slot3.Name", " ");
			config.set("Items.Slot3.Command", " ");
			config.set("Items.Slot3.Lore", " ");
			config.set("Items.Slot3.Type", " ");
			config.set("Items.Slot4.Price", 0); 
			config.set("Items.Slot4.Name", " ");
			config.set("Items.Slot4.Command", " ");
			config.set("Items.Slot4.Lore", " ");
			config.set("Items.Slot4.Type", " ");
			config.set("Items.Slot5.Price", 0); 
			config.set("Items.Slot5.Name", " ");
			config.set("Items.Slot5.Command", " ");
			config.set("Items.Slot5.Lore", " ");
			config.set("Items.Slot5.Type", " ");
			config.set("Items.Slot6.Price", 0); 
			config.set("Items.Slot6.Name", " ");
			config.set("Items.Slot6.Command", " ");
			config.set("Items.Slot6.Lore", " ");
			config.set("Items.Slot6.Type", " ");
			config.set("Items.Slot7.Price", 0); 
			config.set("Items.Slot7.Name", " ");
			config.set("Items.Slot7.Command", " ");
			config.set("Items.Slot7.Lore", " ");
			config.set("Items.Slot7.Type", " ");
			config.set("Items.Slot8.Price", 0); 
			config.set("Items.Slot8.Name", " ");
			config.set("Items.Slot8.Command", " ");
			config.set("Items.Slot8.Lore", " ");
			config.set("Items.Slot8.Type", " ");
			config.set("Items.Slot9.Price", 0); 
			config.set("Items.Slot9.Name", " ");
			config.set("Items.Slot9.Command", " ");
			config.set("Items.Slot9.Lore", " ");
			config.set("Items.Slot9.Type", " ");
		}
		save(); 
		return; 
	}
	
	public int getNumItems() { 
		return config.getInt("Number of items"); 
	}
	
	
	private ArrayList<ShopItem> parseItems() {
		ArrayList<ShopItem> items = new ArrayList<ShopItem>(); 
		int num_items = getNumItems(); 
		String conc_items = "Items."; 
		String conc_slot = "Slot"; 
		String conc_price = ".Price"; 
		String conc_name = ".Name"; 
		String conc_command = ".Command"; 
		String conc_lore = ".Lore"; 
		String conc_type = ".Type"; 
		for (int i = 0; i < num_items; ++i) {
			String num = String.valueOf(i+1); 
			int price = config.getInt(conc_items + conc_slot + num + conc_price); 
			String name = config.getString(conc_items + conc_slot + num + conc_name);
			String command = config.getString(conc_items + conc_slot + num + conc_command);
			String lore = config.getString(conc_items + conc_slot + num + conc_lore);
			String type = config.getString(conc_items + conc_slot + num + conc_type);
			items.add(new ShopItem(price, name, command, lore, type)); 
		}
		return items; 
	}
	
	public ArrayList<ItemMeta> parseItemMeta() {
		ArrayList<ItemMeta> temp_metas = new ArrayList<ItemMeta>(); 
		for (int i = 0; i < getNumItems(); ++i) {
			temp_metas.add(this.shop_items.get(i).getMeta()); 
		}
		return temp_metas;
	}
	
	public ArrayList<ShopItem> getShopItemList() {
		return this.shop_items; 
	}
	
	public ArrayList<ItemMeta> getItemMetaList() {
		return this.item_metas; 
	}
	
	
	public void save() {
		try {
			config.save(file); 
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}