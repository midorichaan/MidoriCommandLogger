package midorichan.listener;

import midorichan.CommandLogger;
import org.bukkit.Bukkit;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class commandLog implements Listener {

    private static final CommandLogger plugin = CommandLogger.getInstance();

    @EventHandler(priority= EventPriority.HIGHEST)
    public static void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        if (plugin.getConf().getBoolean("enable-player")) {
            String logmsg = plugin.getConf().getString(
                    "cmdlog-player",
                    String.format(" §a* §7%s : %s", e.getPlayer().getName(), e.getMessage())
            )
                    .replace("&", "§")
                    .replace("{PLAYER}", e.getPlayer().getName())
                    .replace("{DISPLAYNAME}", e.getPlayer().getDisplayName())
                    .replace("{UUID}", e.getPlayer().getUniqueId().toString())
                    .replace("{WORLD}", e.getPlayer().getWorld().getName())
                    .replace("{X}", Integer.toString(e.getPlayer().getLocation().getBlockX()))
                    .replace("{Y}", Integer.toString(e.getPlayer().getLocation().getBlockY()))
                    .replace("{Z}", Integer.toString(e.getPlayer().getLocation().getBlockZ()))
                    .replace("{COMMAND}", e.getMessage());

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("midorilog.message.player") || p.isOp()) {
                    if (plugin.isLog(p)) {
                        p.sendMessage(logmsg);
                    }
                }
            }
        }
    }

    @EventHandler(priority= EventPriority.HIGHEST)
    public static void onServerCommand(ServerCommandEvent e){
        if (e.getSender() instanceof BlockCommandSender) {
            if (plugin.getConf().getBoolean("enable-cmdblock")) {
                BlockCommandSender s = (BlockCommandSender) e.getSender();

                if (!(s.getBlock().getState() instanceof CommandBlock)) {
                    return;
                }

                CommandBlock cmdb = (CommandBlock) s.getBlock().getState();
                String cmd = cmdb.getCommand();

                String logmsg = plugin.getConf().getString(
                                "cmdlog-cmdblock",
                                String.format(
                                        " §a* §7CMDBLOCK : %s [%s - X: %s Y: %s Z: %s]",
                                        cmd,
                                        cmdb.getLocation().getWorld().getName(),
                                        cmdb.getLocation().getBlockX(),
                                        cmdb.getLocation().getBlockY(),
                                        cmdb.getLocation().getBlockZ()
                                )
                        )
                        .replace("{COMMAND}", cmd)
                        .replace("{WORLD}", cmdb.getLocation().getWorld().getName())
                        .replace("{X}", Integer.toString(cmdb.getLocation().getBlockX()))
                        .replace("{Y}", Integer.toString(cmdb.getLocation().getBlockY()))
                        .replace("{Z}", Integer.toString(cmdb.getLocation().getBlockZ()))
                        .replace("&", "§");

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission("midorilog.message.cmdblock") || p.isOp()) {
                        if (plugin.isLog(p)) {
                            p.sendMessage(logmsg);
                        }
                    }
                }
            }
        } else {
            if (plugin.getConf().getBoolean("enable-console")) {
                String logmsg = plugin.getConf().getString(
                        "cmdlog-console",
                        String.format(" §a* §7CONSOLE : %s", e.getCommand())
                )
                        .replace("{COMMAND}", e.getCommand())
                        .replace("&", "§");

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission("midorilog.message.console") || p.isOp()) {
                        if (plugin.isLog(p)) {
                            p.sendMessage(logmsg);
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public static void onRemoteServerCommand(RemoteServerCommandEvent e){
        if (plugin.getConf().getBoolean("enable-rcon")) {
            String logmsg = plugin.getConf().getString(
                    "cmdlog-rcon",
                    String.format(" §a* §7RCON : %s", e.getCommand())
            )
                    .replace("{COMMAND}", e.getCommand())
                    .replace("&", "§");

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("midorilog.message.rcon") || p.isOp()) {
                    if (plugin.isLog(p)) {
                        p.sendMessage(logmsg);
                    }
                }
            }
        }
    }

}
