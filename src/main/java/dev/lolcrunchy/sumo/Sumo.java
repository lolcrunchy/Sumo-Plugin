package dev.lolcrunchy.sumo;

import dev.lolcrunchy.sumo.commands.sumoargs.SumoCommand;
import dev.lolcrunchy.sumo.config.Configuration;
import dev.lolcrunchy.sumo.management.GameListener;
import dev.lolcrunchy.sumo.management.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
//import org.yaml.snakeyaml.Yaml;
//import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class Sumo extends JavaPlugin {

    //Configuration class
    public Configuration configuration;

    //Manager class for managing players and such...
    public GameManager gameManager;

    //Command class for /sumo
    private SumoCommand sumoCommand;
    private GameListener listener;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        loadConfiguration();
        this.gameManager = new GameManager(this);
        this.sumoCommand = new SumoCommand(this);

        getCommand("sumo").setExecutor(this.sumoCommand);

        this.listener = new GameListener(gameManager);
        getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void onDisable() {

    }

    private void loadConfiguration() {
//        Yaml yaml = new Yaml(new CustomClassLoaderConstructor(Configuration.class.getClassLoader()));
        try(InputStream in = Files.newInputStream(Paths.get(getDataFolder().getPath()+"/config.yml"))){
//            this.configuration = yaml.loadAs(in, Configuration.class);
        }catch(IOException e){
            System.err.println("Kunne ikke læse config.yml data!");
            this.getPluginLoader().disablePlugin(this);
        }
    }

    public void notifyAllUsers(String message){
        Bukkit.getOnlinePlayers().stream().forEach(player -> player.sendMessage(message));
    }
}
