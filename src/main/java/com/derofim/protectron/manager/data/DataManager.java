package com.derofim.protectron.manager.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.derofim.protectron.ProtectronPlugin;
import com.derofim.protectron.config.SettingsConfig;
import com.derofim.protectron.modules.ModulesConfig;
import com.derofim.protectron.modules.blockGroup.BlocksUtils;
import com.zaxxer.hikari.HikariDataSource;

public class DataManager {
	private static final boolean debugVerbose = false;
	private static DataManager instance = null;
	private static ProtectronPlugin plugin = ProtectronPlugin.getInstance();
	private static Logger lg = plugin.getLogger();
	private static SettingsConfig stg = SettingsConfig.getInstance();

	// https://www.spigotmc.org/threads/tutorial-implement-mysql-in-your-plugin-with-pooling.61678/
	private static HikariDataSource hikari;

	private static String prefix = "pro_";
	private static String database = "pro";
	private static String jdbc = "com.mysql.jdbc.jdbc2.optional.MysqlDataSource";
	private static String serverName = "0.0.0.0";
	private static String serverPort = "0000";
	private static String userName = "name";
	private static String userPass = "pass";

	public static final DataManager getInstance() {
		if (instance == null)
			instance = new DataManager();
		return instance;
	}

	private DataManager() {
		reloadVars();
	}

	public static void reloadVars() {
		database = stg.getStr(SettingsConfig.PARAM_MYSQL_DB);
		jdbc = stg.getStr(SettingsConfig.PARAM_MYSQL_JDBC);
		userPass = stg.getStr(SettingsConfig.PARAM_MYSQL_PASSWORD);
		serverPort = stg.getStr(SettingsConfig.PARAM_MYSQL_PORT);
		prefix = stg.getStr(SettingsConfig.PARAM_MYSQL_PREFIX);
		serverName = stg.getStr(SettingsConfig.PARAM_MYSQL_SERVER);
		userName = stg.getStr(SettingsConfig.PARAM_MYSQL_USER);
	}

	public static HikariDataSource getHikari() {
		return hikari;
	}

	public static void setupConnection() {
		if (!ModulesConfig.getInstance().getBool(ModulesConfig.MODULE_MYSQL))
			return;
		reloadVars();
		if (debugVerbose) {
			lg.info("connecting to database " + database);
		}
		hikari = new HikariDataSource();
		hikari.setDataSourceClassName(jdbc);
		hikari.addDataSourceProperty("serverName", serverName);
		hikari.addDataSourceProperty("port", serverPort);
		hikari.addDataSourceProperty("databaseName", database);
		hikari.addDataSourceProperty("user", userName);
		hikari.addDataSourceProperty("password", userPass);
		createDatabase();
		createTables();
	}

	public static void createDatabase() {
		try (Connection connection = hikari.getConnection(); Statement statement = connection.createStatement();) {
			statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS `" + database + "` DEFAULT CHARACTER SET ucs2");
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void createTables() {
		createUsersTable();
		createBlocksTable();
	}

	private static void createUsersTable() {
		String tableName = "users";
		try (Connection connection = hikari.getConnection(); Statement statement = connection.createStatement();) {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + "`" + database + "`." + "`" + prefix + tableName
					+ "`(" + "  `ID` INT NOT NULL AUTO_INCREMENT," + "  `UUID` VARCHAR(36) NOT NULL,"
					+ "  `NAME` VARCHAR(20) NOT NULL," + "  UNIQUE INDEX `UUID_UNIQUE` (`UUID` ASC),"
					+ "  PRIMARY KEY (`ID`)" + ")");
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void createBlocksTable() {
		String tableName = "blocks";
		String referenceTableName = "users";
		try (Connection connection = hikari.getConnection(); Statement statement = connection.createStatement();) {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS `" + database + "`.`" + prefix + tableName + "` ( "
					+ "`ID` INT NOT NULL AUTO_INCREMENT, " + "`BLOCK_NAME` VARCHAR(50) NOT NULL, "
					+ "`BLOCK_ID` INT NOT NULL, " + "`BLOCK_META` INT NOT NULL, "
					+ "`BLOCK_WORLD` VARCHAR(25) NOT NULL, " + "`BLOCK_X` DOUBLE NOT NULL, "
					+ "`BLOCK_Y` DOUBLE NOT NULL, " + "`BLOCK_Z` DOUBLE NOT NULL, " + "`BLOCK_PITCH` FLOAT NOT NULL, "
					+ "`BLOCK_YAW` FLOAT NOT NULL, " + "`BLOCK_OWNER_ID` INT NOT NULL, "
					+ "`PLACED_TIME` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, " + "PRIMARY KEY (`ID`), "
					+ "CONSTRAINT `FOREIGN_BLOCKS_USER` FOREIGN KEY (`BLOCK_OWNER_ID`) " + "REFERENCES `" + database
					+ "`.`" + prefix + referenceTableName + "` (`ID`) " + "ON DELETE NO ACTION ON UPDATE NO ACTION)");
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Saves Player as owner of Block (with all Block data)
	public static void insertBlocksTable(Block b, Player p) {
		if (debugVerbose) {
			lg.info("inserting block " + b.getType().toString());
		}
		String tableName = "blocks";
		String INSERT = "INSERT INTO `" + database + "`.`" + prefix + tableName + "` "
				+ "(`BLOCK_NAME`,`BLOCK_ID`,`BLOCK_META`,`BLOCK_WORLD`,"
				+ "`BLOCK_X`,`BLOCK_Y`,`BLOCK_Z`,`BLOCK_PITCH`,`BLOCK_YAW`,`BLOCK_OWNER_ID`) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
		try (Connection connection = getHikari().getConnection();
				PreparedStatement insert = connection.prepareStatement(INSERT)) {
			int playerRecordID = findUsersIdTable(p);
			if (debugVerbose) {
				lg.info("playerRecordID" + playerRecordID);
			}
			if (playerRecordID <= 0) {
				lg.warning("Cant insert block: Not saved user " + p.getUniqueId().toString());
				return;
			}
			Location loc = b.getLocation();
			insert.setString(1, BlocksUtils.getBlockTypeName(b));
			insert.setInt(2, BlocksUtils.getBlockId(b));
			insert.setInt(3, BlocksUtils.getBlockData(b));
			insert.setString(4, loc.getWorld().getName().toLowerCase());
			insert.setDouble(5, loc.getX());
			insert.setDouble(6, loc.getY());
			insert.setDouble(7, loc.getZ());
			insert.setFloat(8, loc.getPitch());
			insert.setFloat(9, loc.getYaw());
			insert.setInt(10, playerRecordID);
			insert.execute();
			insert.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int findBlocksIdTable(int id, int meta, Location loc, boolean withMeta) {
		ResultSet result = null;
		int recID = -1;
		if (debugVerbose) {
			lg.info("Finding block with id " + id);
		}
		String tableName = "blocks";
		String SELECT;
		if(withMeta){
			SELECT = "SELECT * FROM " + "`" + database + "`.`" + prefix + tableName + "`"
				+ " WHERE BLOCK_ID=? AND BLOCK_META=? AND BLOCK_WORLD=? AND BLOCK_X=? AND BLOCK_Y=? AND BLOCK_Z=?";
		} else {
			SELECT = "SELECT * FROM " + "`" + database + "`.`" + prefix + tableName + "`"
				+ " WHERE BLOCK_ID=? AND BLOCK_WORLD=? AND BLOCK_X=? AND BLOCK_Y=? AND BLOCK_Z=?";
		}
		try (Connection connection = getHikari().getConnection();
				PreparedStatement select = connection.prepareStatement(SELECT)) {
			if(withMeta){
				select.setInt(1, id);
				select.setInt(2, meta);
				select.setString(3, loc.getWorld().getName().toLowerCase());
				select.setDouble(4, loc.getX());
				select.setDouble(5, loc.getY());
				select.setDouble(6, loc.getZ());
			} else {
				select.setInt(1, id);
				select.setString(2, loc.getWorld().getName().toLowerCase());
				select.setDouble(3, loc.getX());
				select.setDouble(4, loc.getY());
				select.setDouble(5, loc.getZ());
			}
			result = select.executeQuery();
			if (result.next())
				recID = result.getInt("ID");
			result.close();
			select.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recID;
	}

	// deletes not correct rows
	public static void updateBlocksIdTable(int pId, World pWorld) {
		ResultSet result = null;
		if (debugVerbose) {
			lg.info("Updating blocks for player " + pId);
		}
		String tableName = "blocks";
		String SELECT = "SELECT * FROM " + "`" + database + "`.`" + prefix + tableName + "`"
				+ " WHERE BLOCK_WORLD=? AND BLOCK_OWNER_ID=?";
		try (Connection connection = getHikari().getConnection();
				PreparedStatement select = connection.prepareStatement(SELECT, 
					    ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE)) {
			select.setString(1, pWorld.getName().toLowerCase());
			select.setInt(2, pId);
			result = select.executeQuery();
			while (result.next()) {
				int blockId = result.getInt("BLOCK_ID");
				//int blockMeta = result.getInt("BLOCK_META");
				int blockX = result.getInt("BLOCK_X");
				int blockY = result.getInt("BLOCK_Y");
				int blockZ = result.getInt("BLOCK_Z");
				Block blk = pWorld.getBlockAt(blockX, blockY, blockZ);
				if (BlocksUtils.getBlockId(blk) != blockId) {
					result.deleteRow();
				}
			}
			result.close();
			select.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// deletes all rows for player
	public static int clearBlocksIdTable(int pId) {
		ResultSet result = null;
		if (debugVerbose) {
			lg.info("Updating blocks for player " + pId);
		}
		int deletedCount = 0;
		String tableName = "blocks";
		String SELECT = "SELECT * FROM " + "`" + database + "`.`" + prefix + tableName + "`"
				+ " WHERE BLOCK_OWNER_ID=?";
		try (Connection connection = getHikari().getConnection();
				PreparedStatement select = connection.prepareStatement(SELECT, ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE)) {
			select.setInt(1, pId);
			result = select.executeQuery();
			while (result.next()) {
				int blockId = result.getInt("BLOCK_ID");
				//int blockMeta = result.getInt("BLOCK_META");
				String blockWorld = result.getString("BLOCK_WORLD");
				int blockX = result.getInt("BLOCK_X");
				int blockY = result.getInt("BLOCK_Y");
				int blockZ = result.getInt("BLOCK_Z");
				World wrd = plugin.getServer().getWorld(blockWorld);
				Block blk = wrd.getBlockAt(blockX, blockY, blockZ);
				if (BlocksUtils.getBlockId(blk) == blockId) {
					blk.setType(Material.AIR);
					result.deleteRow();
				}
				deletedCount++;
			}
			result.close();
			select.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return deletedCount;
	}

	public static void deleteBlocksIdTable(Block b) {
		deleteBlocksIdTable(BlocksUtils.getBlockId(b), BlocksUtils.getBlockData(b), b.getLocation());
	}

	public static void deleteBlocksIdTable(int id, int meta, Location loc) {
		if (debugVerbose) {
			lg.info("Deleting block with id " + id);
		}
		String tableName = "blocks";
		String SELECT = "DELETE FROM " + "`" + database + "`.`" + prefix + tableName + "`"
				+ " WHERE BLOCK_ID=? AND BLOCK_META=? AND BLOCK_WORLD=? AND BLOCK_X=? AND BLOCK_Y=? AND BLOCK_Z=?";
		try (Connection connection = getHikari().getConnection();
				PreparedStatement delete = connection.prepareStatement(SELECT)) {
			delete.setInt(1, id);
			delete.setInt(2, meta);
			delete.setString(3, loc.getWorld().getName().toLowerCase());
			delete.setDouble(4, loc.getX());
			delete.setDouble(5, loc.getY());
			delete.setDouble(6, loc.getZ());
			delete.execute();
			delete.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int countBlocksTable(int playerRecId, int id, Location loc) {
		ResultSet result = null;
		int recID = -1;
		if (debugVerbose) {
			lg.info("Counting block with id " + id + " and player " + playerRecId);
		}
		String tableName = "blocks";
		String SELECT = "SELECT COUNT(*) FROM " + "`" + database + "`.`" + prefix + tableName + "`"
				+ " WHERE BLOCK_ID=? AND BLOCK_WORLD=? AND BLOCK_OWNER_ID=?";
		try (Connection connection = getHikari().getConnection();
				PreparedStatement select = connection.prepareStatement(SELECT)) {
			select.setInt(1, id);
			select.setString(2, loc.getWorld().getName().toLowerCase());
			select.setDouble(3, playerRecId);
			result = select.executeQuery();
			if (result.next())
				recID = result.getInt(1);
			result.close();
			select.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recID;
	}
	
	public static int countBlocksTable(int playerRecId, int id, int meta, Location loc) {
		ResultSet result = null;
		int recID = -1;
		if (debugVerbose) {
			lg.info("Counting block with id " + id + " and meta " + meta + " and player " + playerRecId);
		}
		String tableName = "blocks";
		String SELECT = "SELECT COUNT(*) FROM " + "`" + database + "`.`" + prefix + tableName + "`"
				+ " WHERE BLOCK_ID=? AND BLOCK_META=? AND BLOCK_WORLD=? AND BLOCK_OWNER_ID=?";
		try (Connection connection = getHikari().getConnection();
				PreparedStatement select = connection.prepareStatement(SELECT)) {
			select.setInt(1, id);
			select.setInt(2, meta);
			select.setString(3, loc.getWorld().getName().toLowerCase());
			select.setDouble(4, playerRecId);
			result = select.executeQuery();
			if (result.next())
				recID = result.getInt(1);
			result.close();
			select.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recID;
	}

	public static int findUsersIdTable(Player p) {
		ResultSet result = null;
		int recID = -1;
		if (debugVerbose) {
			lg.info("finding player " + p.getName());
		}
		String tableName = "users";
		String SELECT = "SELECT * FROM " + "`" + database + "`.`" + prefix + tableName + "`" + " WHERE uuid=?";
		try (Connection connection = getHikari().getConnection();
				PreparedStatement select = connection.prepareStatement(SELECT)) {
			select.setString(1, p.getUniqueId().toString());
			result = select.executeQuery();
			if (result.next())
				recID = result.getInt("ID");
			result.close();
			select.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recID;
	}

	public static void insertUsersTable(Player p) {
		String playerName = p.getName();
		if (debugVerbose) {
			lg.info("inserting player " + playerName);
		}
		String tableName = "users";
		String INSERT = "INSERT INTO `" + database + "`.`" + prefix + tableName + "` "
				+ "(`UUID`,`NAME`) VALUES(?,?) ON DUPLICATE KEY UPDATE `NAME`=?";
		try (Connection connection = getHikari().getConnection();
				PreparedStatement insert = connection.prepareStatement(INSERT)) {
			insert.setString(1, p.getUniqueId().toString());
			insert.setString(2, playerName);
			insert.setString(3, playerName);
			insert.execute();
			insert.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnection() {
		if (!ModulesConfig.getInstance().getBool(ModulesConfig.MODULE_MYSQL))
			return;
		if (hikari != null)
			hikari.close();
	}
}
