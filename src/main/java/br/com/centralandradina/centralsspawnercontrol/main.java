package br.com.centralandradina.centralsspawnercontrol;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin implements Listener 
{
	FileConfiguration config;
	
	@Override
    public void onEnable()
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		// Config
		config = this.getConfig();
		config.addDefault("Delay", 20);
		config.addDefault("SpawnCount", 4);
		config.addDefault("MaxSpawnDelay", 800);
		config.addDefault("MinSpawnDelay", 200);
		config.options().copyDefaults(true);
		saveConfig();
	}
	
	@EventHandler(priority = EventPriority.LOW)
    public void onBlockPlace(BlockPlaceEvent event){
		
		ItemStack spawner = event.getItemInHand();
	    if (spawner.getType().equals(Material.SPAWNER) && !event.isCancelled()) {
        	
	    	Block block = event.getBlockPlaced();
	    			
	    	BlockState blockState = block.getState();
            CreatureSpawner spawnerItem = ((CreatureSpawner)blockState);
            
            getLogger().info("--------- SPAWN " + spawnerItem.getSpawnCount() + " DELAY " + spawnerItem.getDelay());
            getLogger().info("--------- MAX " + spawnerItem.getMaxSpawnDelay() + " MIN " + spawnerItem.getMinSpawnDelay());
            
            
            spawnerItem.setDelay(config.getInt("maxFakePlayers")); // primeiro spawn quando o player esta perto
            spawnerItem.setSpawnCount(config.getInt("SpawnCount")); // quantos mobs por spawn
            
            
            spawnerItem.setMaxSpawnDelay(config.getInt("MaxSpawnDelay")); // default 800
            spawnerItem.setMinSpawnDelay(config.getInt("MinSpawnDelay")); // default 200
            
            blockState.update();
	    	
        }
	    
    }
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
    	String commandName = cmd.getName().toLowerCase();
    	
    	if(commandName.equals("csspawnercontrol")) {
			if(args.length > 0) {
				if(args[0].equals("reload")) {
					getLogger().info("-- Central Spawner Control: RECARREGADO");
					this.reloadConfig();
				}
			}
    	}
    	
		return false;
    }
}
