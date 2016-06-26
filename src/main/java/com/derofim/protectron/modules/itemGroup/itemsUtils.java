package com.derofim.protectron.modules.itemGroup;

import org.bukkit.inventory.ItemStack;

public class itemsUtils {
	private static itemsUtils instance = new itemsUtils();

	public static final itemsUtils getInstance() {
		return instance;
	}

	private itemsUtils() {
	}
	

	// Возвращает номер предмета
	@SuppressWarnings("deprecation")
	public static String getItemIdName(ItemStack b) {
		return "" + b.getType().getId();
	}

	// Возвращает дополнительные данные предмета
	public static String getItemDataName(ItemStack b) {
		return "" + b.getDurability();
	}

	// Возвращает название предмета
	public static String getItemTypeName(ItemStack b) {
		return b.getType().toString();
	}

	// Возвращает название блока и данные предмета
	public static String getItemTypeFull(ItemStack b) {
		return getItemTypeName(b) + ":" + getItemDataName(b);
	}

	// Возвращает номер блока и данные предмета
	public static String getItemIdFull(ItemStack b) {
		return getItemIdName(b) + ":" + getItemDataName(b);
	}

	// Возвращает true если равны название предмета в настройках и в игре
	public static boolean checkEqualItem(ItemStack b, String Alias) {
		if (getItemTypeFull(b).equals(Alias)) {
			return true; // checks ITEM_NAME:1 with metadata check
		} else if (getItemTypeName(b).equals(Alias)) {
			return true; // checks ITEM_NAME with any metadata
		} else if (getItemIdFull(b).equals(Alias)) {
			return true; // checks 1234:1 with metadata check
		} else if (getItemIdName(b).equals(Alias)) {
			return true; // checks 1234 with any metadata
		}
		return false;
	}
}
