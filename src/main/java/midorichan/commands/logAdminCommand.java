package midorichan.commands;

import midorichan.CommandLogger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class logAdminCommand implements CommandExecutor {

    private CommandLogger plugin = CommandLogger.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("togglelog")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(plugin.getPrefix() + "コンソールからは使用できません");
            } else {
                Player p = (Player) sender;

                if (p.hasPermission("midorilog.command")) {
                    if (args.length == 0) {
                        plugin.toggleLog(p);

                        if (plugin.isLog(p)) {
                            p.sendMessage(plugin.getPrefix() + "コマンドログを有効にしました");
                        } else {
                            p.sendMessage(plugin.getPrefix() + "コマンドログを無効にしました");
                        }
                    } else {
                        p.sendMessage(plugin.getPrefix() + "引数が不正です");
                    }
                } else {
                    p.sendMessage(plugin.getPrefix() + "権限がありません");
                }
            }
        }

        return true;
    }

}
