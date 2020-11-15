package me.ryvador.deathtp;


import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class DeathTP extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("-------------------");
        System.out.println("");
        System.out.println("DeathTP Enabled!");
        System.out.println("");
        System.out.println("-------------------");
        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("-------------------");
        System.out.println("");
        System.out.println("DeathTP Disabled!");
        System.out.println("");
        System.out.println("-------------------");

    }
    

    public Map<Player, Location> playersDeathLocation = new HashMap<>();

    public static boolean tpAllowed;

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player player = e.getEntity();
        Location deathLocation = player.getLocation();
        playersDeathLocation.put(player, deathLocation);
        TextComponent message = new TextComponent("Click me to teleport back to your death location!");
        message.setColor(ChatColor.GOLD);
        message.setBold(true);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/deathtp"));
        player.spigot().sendMessage(message);
        tpAllowed = true;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("deathtp")){

            if (sender instanceof Player){

                Player player = (Player) sender;
                Location loc = playersDeathLocation.getOrDefault(player, null);
                if (tpAllowed){
                    if (loc != null) player.teleport(loc);
                    tpAllowed = false;
                } else {
                    player.sendMessage(ChatColor.RED + "You have already used this command once from when you died!");
                }


                return true;

            } else {
                System.out.println("Console cannot run this command!");
            }

        }





        return true;
    }
}
