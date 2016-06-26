package com.derofim.protectron.modules.blockGroup;

import org.bukkit.block.Block;

public class BlocksUtils {
	private static BlocksUtils instance = new BlocksUtils();

	public static final BlocksUtils getInstance() {
		return instance;
	}

	private BlocksUtils() {
	}

	// Возвращает номер блока
	@SuppressWarnings("deprecation")
	public static String getBlockIdName(Block b) {
		return "" + b.getType().getId();
	}

	// Возвращает дополнительные данные блока
	@SuppressWarnings("deprecation")
	public static String getBlockDataName(Block b) {
		return "" + b.getData();
	}

	// Возвращает название блока
	public static String getBlockTypeName(Block b) {
		return b.getType().toString();
	}

	// Возвращает название блока и данные блока
	public static String getBlockTypeFull(Block b) {
		return getBlockTypeName(b) + ":" + getBlockDataName(b);
	}

	// Возвращает номер блока и данные блока
	public static String getBlockIdFull(Block b) {
		return getBlockIdName(b) + ":" + getBlockDataName(b);
	}

	// Возвращает true если равны название блока в настройках и в игре
	public static boolean checkEqualBlock(Block b, String Alias) {
		if (getBlockTypeFull(b).equals(Alias)) {
			return true; // BLOCK_NAME:1 with metadata check
		} else if (getBlockTypeName(b).equals(Alias)) {
			return true; // BLOCK_NAME with any metadata
		} else if (getBlockIdFull(b).equals(Alias)) {
			return true; // 1234:1 with metadata check
		} else if (getBlockIdName(b).equals(Alias)) {
			return true; // 1234 with any metadata
		}
		return false;
	}
}
