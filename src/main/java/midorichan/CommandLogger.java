package midorichan;

import midorichan.commands.logAdminCommand;
import midorichan.listener.commandLog;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CommandLogger extends JavaPlugin {

    public static List<UUID> logging = new ArrayList<UUID>();

    private static FileConfiguration config = null;
    private static CommandLogger plugin = null;

    public static CommandLogger getInstance() {
        return plugin;
    }
    public static FileConfiguration getConf() {
        return config;
    }

    public static String getPrefix() {
        return config.getString("plugin-prefix", " &2>&a>&r ")
                .replace("&", "§");
    }

    public static boolean isLog(Player p) {
        return logging.contains(p.getUniqueId());
    }

    public static boolean toggleLog(Player p) {
        if (isLog(p)) {
            logging.add(p.getUniqueId());
            return true;
        } else {
            logging.remove(p.getUniqueId());
            return false;
        }
    }

    @Override
    public void onEnable() {
        this.plugin = this;

        //Config
        this.saveDefaultConfig();
        this.config = this.getConfig();

        //register commands
        Bukkit.getPluginCommand("togglelog").setExecutor(new logAdminCommand());

        //register listener
        Bukkit.getPluginManager().registerEvents(new commandLog(), this);

        this.getLogger().info(this.getPrefix() + "Enabled MidoriCommandLogger v1.0");
    }

    @Override
    public void onDisable() {
        this.getLogger().info(this.getPrefix() + "Disabled MidoriCommandLogger v1.0");
    }

}