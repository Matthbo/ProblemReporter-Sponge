package matthbo.plugin.problemreporter.command;

import com.google.common.base.Optional;
import matthbo.plugin.problemreporter.ProblemReporter;
import matthbo.plugin.problemreporter.Refs;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.message.Message;
import org.spongepowered.api.text.message.Messages;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandSource;

import java.io.File;
import java.util.List;

public class CommandClearproblemlist implements CommandCallable {

    ProblemReporter PR = ProblemReporter.instance;

    public void clearList(){
        try{
            File dataFolder = PR.getDataFolder();

            File list = new File(dataFolder, "ProblemList.txt");
            if(list.exists())list.delete();
        }catch(Exception e){e.printStackTrace();}
    }

    @Override
    public boolean call(CommandSource sender, String args, List<String> parents) throws CommandException {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(player.hasPermission("problemreporter.clearhelplist")){

                player.sendMessage(clearing());
                clearList();
            }else{}
        }else{
            sender.sendMessage(Refs.wrngSender());
        }
        return true;
    }

    @Override
    public boolean testPermission(CommandSource source) {
        return false;
    }

    @Override
    public Optional<String> getShortDescription() {
        return null;
    }

    @Override
    public Optional<String> getHelp() {
        return null;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
        return null;
    }

    private Message clearing(){
        return Messages.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Messages.builder("Clearing ProblemList...").color(TextColors.RESET).build()).build();
    }
}