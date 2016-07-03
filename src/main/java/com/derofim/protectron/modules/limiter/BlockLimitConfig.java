package com.derofim.protectron.modules.limiter;

import java.io.File;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.manager.PermissionManager;
import com.derofim.protectron.modules.blockGroup.BlocksConfig;
import com.derofim.protectron.modules.blockGroup.BlocksUtils;
import com.derofim.protectron.modules.config.AbstractConfig;
import com.derofim.protectron.util.Vars;
import com.google.common.collect.TreeBasedTable;

public class BlockLimitConfig extends AbstractConfig {
	private static ProtectronPlugin m = ProtectronPlugin.getInstance();
	private final String configLanguageVersion = "0.0.8";
	private final static File defaultFile = new File(m.getDataFolder(),
			Vars.FOLDER_MODULES + Vars.FOLDER_SEPARATOR + "limits" + Vars.FOLDER_SEPARATOR + "block_limits.yml");

	public static final String CONFIG_HEADER = "";
	public static final String CONFIG_SECTION = "limits";

	private static TreeBasedTable<String, String, TreeMap<String, Integer>> tableSorted = TreeBasedTable.create();
	private static Set<String> protectBlocks = new TreeSet<String>();
	
	private static BlockLimitConfig instance = new BlockLimitConfig();

	public static final BlockLimitConfig getInstance() {
		return instance;
	}

	private BlockLimitConfig() {
	}
	
	public static boolean isLimitInMapWithMeta(TreeMap<String, Integer> mapBlocks, Block b){
		if (mapBlocks.containsKey(BlocksUtils.getBlockTypeFull(b)))
			return true;
		if (mapBlocks.containsKey(BlocksUtils.getBlockIdFull(b)))
			return true;
		if (mapBlocks.containsKey(BlocksUtils.getBlockIdName(b)))
			return false;
		if (mapBlocks.containsKey(BlocksUtils.getBlockTypeName(b)))
			return false;
		return false;
	}
	
	private static int getLimitInMap(TreeMap<String, Integer> mapBlocks, Block b) {
		int limit = -1;
		if (mapBlocks.containsKey(BlocksUtils.getBlockIdName(b)))
			limit = mapBlocks.get(BlocksUtils.getBlockIdName(b));
		else if (mapBlocks.containsKey(BlocksUtils.getBlockIdFull(b)))
			limit = mapBlocks.get(BlocksUtils.getBlockIdFull(b));
		else if (mapBlocks.containsKey(BlocksUtils.getBlockTypeName(b)))
			limit = mapBlocks.get(BlocksUtils.getBlockTypeName(b));
		else if (mapBlocks.containsKey(BlocksUtils.getBlockTypeFull(b)))
			limit = mapBlocks.get(BlocksUtils.getBlockTypeFull(b));
		return limit;
	}

	public static boolean isBlockProtected(Block b) {
		if (protectBlocks.contains(BlocksUtils.getBlockIdName(b))
				|| protectBlocks.contains(BlocksUtils.getBlockIdFull(b))
				|| protectBlocks.contains(BlocksUtils.getBlockTypeName(b))
				|| protectBlocks.contains(BlocksUtils.getBlockTypeFull(b)))
			return true;
		return false;
	}
	
	public static boolean isPlayerConfigLimitWithMeta(Player p, Block b) {
		String pGroup = PermissionManager.getInstance().getPermission().getPrimaryGroup(p);
		String worldName = b.getWorld().getName().toLowerCase();
		if (!tableSorted.contains(worldName, pGroup))
			return false;
		TreeMap<String, Integer> mapBlocks = tableSorted.get(worldName, pGroup);
		if(isLimitInMapWithMeta(mapBlocks, b)){
			return true;
		}
		return false;
	}

	public static int getPlayerConfigLimit(Player p, Block b) {
		String pGroup = PermissionManager.getInstance().getPermission().getPrimaryGroup(p);
		String worldName = b.getWorld().getName().toLowerCase();
		int limit = -1;
		if (!tableSorted.contains(worldName, pGroup))
			return limit;
		TreeMap<String, Integer> mapBlocks = tableSorted.get(worldName, pGroup);
		limit = getLimitInMap(mapBlocks, b);
		return limit;
	}

	private void reloadMapSorted() {
		tableSorted.clear();
		protectBlocks.clear();
		String section = CONFIG_SECTION;
		// get world
		for (String worldName : getSectionKeys(section)) {
			String sectionWorld = section + "." + worldName;
			// get player_group
			for (String player_group : getSectionKeys(sectionWorld)) {
				String sectionGroup = sectionWorld + "." + player_group;
				TreeMap<String, Integer> blocksSets = new TreeMap<String, Integer>();
				// get max_count
				for (String max_count : getSectionKeys(sectionGroup)) {
					String sectionCount = sectionGroup + "." + max_count;
					// get block_set
					int countValue = Integer.parseInt(max_count);
					for (String block_set : getConfig().getStringList(sectionCount)) {
						TreeSet<String> blocks = BlocksConfig.getBlockSet(block_set);
						for (String block_name : blocks) {
							blocksSets.put(block_name, countValue);
							protectBlocks.add(block_name);
						}
					}
				}
				tableSorted.put(worldName, player_group, blocksSets);
			}
		}
	}

	@Override
	public boolean onPostLoad() {
		reloadMapSorted();
		return true;
	}

	@Override
	public boolean onPostReload() {
		reloadMapSorted();
		return true;
	}

	@Override
	protected void setCustomDefaults(FileConfiguration fconf) {
		fconf.addDefault(CONFIG_SECTION + ".world.default.4", Arrays.asList("VANILLA_BLOCKS", "PHYSICS_BLOCKS"));
		fconf.addDefault(CONFIG_SECTION + ".world.default.3", Arrays.asList(Vars.LIMIT_BLOCKS));
		fconf.addDefault(CONFIG_SECTION + ".world.vip.20", Arrays.asList("PHYSICS_BLOCKS"));
		fconf.addDefault(CONFIG_SECTION + ".dim56.default.5", Arrays.asList("VANILLA_BLOCKS"));
	}

	@Override
	public File getFile() {
		return defaultFile;
	}

	@Override
	public String getLanguageVersion() {
		return configLanguageVersion;
	}

	@Override
	public String getHeader() {
		return CONFIG_HEADER;
	}
}
