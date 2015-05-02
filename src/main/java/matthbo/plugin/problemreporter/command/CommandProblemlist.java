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
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

    private Text loading(){
        return Texts.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Texts.builder("Loading File...").color(TextColors.RESET).build()).build();
    }

    private Text filedoesntExist(){
        return Texts.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Texts.builder("Nothing!").color(TextColors.RESET).build()).build();
    }

    @Override
    public Optional<CommandResult> process(CommandSource sender, String args) throws CommandException {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(player.hasPermission("problemreporter.readhelplist")){

                player.sendMessage(loading());
                fileToMessage(player);
            }else{}
        }else{
            sender.sendMessage(Refs.wrngSender());
        }
        return Optional.of(CommandResult.success());
    }

    private Object desc = "Shows the ProblemList.txt in chat";
    private Object usage = "/<command>";

    @Override
    public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
        return null;
    }

    @Override
    public boolean testPermission(CommandSource source) {
        return false;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(Texts.of(desc));
    }

    @Override
    public Optional<Text> getHelp(CommandSource source) {
        return Optional.of(Texts.of(desc));
    }

    @Override
    public Text getUsage(CommandSource source) {
        return Texts.of(usage);
    }
}
