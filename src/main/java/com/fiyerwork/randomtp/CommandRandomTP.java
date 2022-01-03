package com.fiyerwork.randomtp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

import static org.bukkit.ChatColor.*;

public class CommandRandomTP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("randomtp")) {
            if(args.length == 0) { // /randomtp
                if (!(sender instanceof Player)) {
                    sender.sendMessage(RED + "Only players can execute that command.");
                } else {
                    Player player = (Player) sender;
                    if (player.hasPermission("randomtp.command.self") || player.hasPermission("randomtp.command.other")) {
                        player.sendMessage(RandomTP.getPluginConfig().getTeleportSelfMsg());
                        randomTeleport(player, player.getWorld());
                    } else {
                        player.sendMessage(RandomTP.getPluginConfig().getPermissionErrorMsg());
                    }
                }
            } else {
                if(args[0].equalsIgnoreCase("reload")) { // /randomtp reload
                    if(sender.hasPermission("randomtp.command.reload")) {
                        RandomTP.getPluginConfig().load();
                        sender.sendMessage(GREEN + "[RandomTP] Reload complete.");
                    } else {
                        sender.sendMessage(RandomTP.getPluginConfig().getPermissionErrorMsg());
                    }
                } else {
                    if(sender.hasPermission("randomtp.command.other")) { // /randomtp <target>
                        Player target = Bukkit.getPlayer(args[0]);
                        if(target != null) {
                            target.sendMessage(RandomTP.getPluginConfig().getTeleportSelfMsg());
                            randomTeleport(target, target.getWorld());
                            sender.sendMessage(RandomTP.getPluginConfig().getTeleportOtherMsg(target.getName()));
                        } else {
                            sender.sendMessage(RandomTP.getPluginConfig().getPlayerNotFoundMsg(args[0]));
                        }
                    } else {
                        sender.sendMessage(RandomTP.getPluginConfig().getPermissionErrorMsg());
                    }
                }
            }
        }
        return true;
    }

    private static Location randomLocation(World world) {
        Random random = new Random();
        Configuration pluginConfig = RandomTP.getPluginConfig();
        int x = random.nextInt(pluginConfig.getMinX(), pluginConfig.getMaxX());
        int z = random.nextInt(pluginConfig.getMinZ(), pluginConfig.getMaxZ());
        int y = world.getHighestBlockYAt(x, z);
        return new Location(world, x + 0.5, y + 1, z + 0.5);
    }

    private static void randomTeleport(Player player, World world) {
        Location location = randomLocation(world);
        Material matDown = location.getBlock().getRelative(BlockFace.DOWN).getType();
        Material matUp = location.getBlock().getRelative(BlockFace.UP).getType();
        while((!matUp.isAir())
                || (matDown == Material.LAVA
                || matDown == Material.FIRE
                || matDown == Material.MAGMA_BLOCK
                || matDown == Material.CACTUS
                || matDown == Material.WATER)) {
            location = randomLocation(world);
            matDown = location.getBlock().getRelative(BlockFace.DOWN).getType();
            matUp = location.getBlock().getRelative(BlockFace.UP).getType();
        }
        player.teleport(location);
    }
}
