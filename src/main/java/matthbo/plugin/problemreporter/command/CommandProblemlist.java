package matthbo.plugin.problemreporter.command;

import com.google.common.base.Optional;
import matthbo.plugin.problemreporter.ProblemReporter;
import matthbo.plugin.problemreporter.Refs;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;

public class CommandProblemlist implements CommandCallable {

    ProblemReporter PR = ProblemReporter.instance;

    public void fileToMessage(CommandSource sender){
        try{
            File dataFolder = PR.getDataFolder();

            File list = new File(dataFolder, "/ProblemList.txt");
            if(!list.exists()) {sender.sendMessage(filedoesntExist());}

            if(list.exists()){
                BufferedReader br = new BufferedReader(new FileReader(dataFolder + "/ProblemList.txt"));
                String str;
                while((str = br.readLine()) != null){
                    sender.sendMessage(Texts.of(str));
                }
                br.close();
            }
        }catch(Exception e){e.printStackTrace();}
    }

    @Override
    public boolean call(CommandSource sender, String args, List<String> parents) throws CommandException {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(player.hasPermission("problemreporter.readhelplist")){

                player.sendMessage(loading());
                fileToMessage(player);
            }else{}
        }else{
            sender.sendMessage(Refs.wrngSender());
        }
        return true;
    }

    @Override
    public boolean testPermission(CommandSource source) {
        return true;
    }

    @Override
    public Optional<String> getShortDescription() {
        return desc;
    }

    @Override
    public Optional<String> getHelp() {
        return desc;
    }//TODO check if changes need to be made!

    @Override
    public String getUsage() {
        return Refs.Usage;
    }

    @Override
    public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
        return Collections.emptyList();
    }

    private final Optional<String> desc = Optional.of("Shows the ProblemList.txt in chat");

    private Text loading(){
        return Texts.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Texts.builder("Loading File...").color(TextColors.RESET).build()).build();
    }

    private Text filedoesntExist(){
        return Texts.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Texts.builder("Nothing!").color(TextColors.RESET).build()).build();
    }
}
