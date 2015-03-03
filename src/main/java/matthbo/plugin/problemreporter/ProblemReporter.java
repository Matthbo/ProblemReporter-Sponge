package matthbo.plugin.problemreporter;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import matthbo.plugin.problemreporter.command.CommandClearproblemlist;
import matthbo.plugin.problemreporter.command.CommandProblem;
import matthbo.plugin.problemreporter.command.CommandProblemlist;
import matthbo.plugin.problemreporter.command.CommandTest;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.message.CommandEvent;
import org.spongepowered.api.event.state.ServerStartingEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.command.CommandService;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandMapping;
import org.spongepowered.api.util.event.Subscribe;

import java.io.File;

@Plugin(id = Refs.NAME, name = Refs.NAME, version = Refs.VERSION)
public class ProblemReporter {

    public static ProblemReporter instance;

    @Inject
    private Logger logger;

    @Inject
    private Game game;

    private File dataFolder = new File("mods/",Refs.NAME);

    @Subscribe
    public void onEnable(ServerStartingEvent event){
        instance = this;

        CommandService cmdService = game.getCommandDispatcher();
        cmdService.register(instance, new CommandProblem(), "problem");
        cmdService.register(instance, new CommandProblemlist(), "problemlist");
        cmdService.register(instance, new CommandClearproblemlist(), "clearproblemlist");
        //cmdService.register(instance, new CommandTest(), "test");

        getLogger().info("ProblemReporter has been Activated");
    }

    @Subscribe
    public void onDisable(ServerStoppingEvent event){
        getLogger().info("ProblemReporter now Closed! We'll be back Tomorrow");
    }

    //this is garbage! (for sponge...)
    public void onCommand(CommandEvent event){
        if(event.getCommand() == "problem"){
            //TODO stuff!
        }
    }


    public Logger getLogger() {
        return logger;
    }

    public Game getGame(){
        return game;
    }

    public File getDataFolder(){
        return dataFolder;
    }

}
