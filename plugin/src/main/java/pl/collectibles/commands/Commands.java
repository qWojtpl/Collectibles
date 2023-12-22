package pl.collectibles.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.collectibles.Collectibles;
import pl.collectibles.gui.list.CollectionsList;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("reload")) {
                if(sender.hasPermission("collectibles.reload")) {
                    if(args.length > 1) {
                        if(args[1].equalsIgnoreCase("--skipSave")) {
                            sender.sendMessage("§cSkipped save!");
                        } else {
                            Collectibles.getInstance().getDataHandler().saveData();
                        }
                    } else {
                        Collectibles.getInstance().getDataHandler().saveData();
                    }
                    Collectibles.getInstance().getDataHandler().loadAll();
                    sender.sendMessage("§aReloaded!");
                    return true;
                }
            }
        }
        if(sender instanceof Player) {
            new CollectionsList((Player) sender);
        } else {
            sender.sendMessage("§cYou have to be player to open GUI!");
        }
        return true;
    }

}
