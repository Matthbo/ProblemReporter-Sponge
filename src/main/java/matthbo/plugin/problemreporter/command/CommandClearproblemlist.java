package matthbo.plugin.problemreporter.command;

import com.github.boformer.doublecheck.Request;
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

    private Text clearing(){
        return Texts.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Texts.builder("Clearing ProblemList...").color(TextColors.RESET).build()).build();
    }
    private Text denied(){
        return Texts.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Texts.builder("Clearing cancelled!").color(TextColors.RESET).build()).build();
    }

    @Override
    public Optional<CommandResult> process(CommandSource sender, String args) throws CommandException {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(player.hasPermission("problemreporter.clearhelplist")){
                PR.getCService().send(player, new ClearRequest());
            }else{}
        }else{
            sender.sendMessage(Refs.wrngSender());
        }
        return Optional.of(CommandResult.success());
    }

    private final Object desc = "Clears ProblemList.exe";
    private final Object usage = "/<command>";

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

    private class ClearRequest implements Request{

        @Override
        public Text getMessage() {
            return Texts.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Texts.builder("Are you sure you want to clear the problemlist?").color(TextColors.RESET).build()).build();
        }

        @Override
        public void confirm(CommandSource source) {
            source.sendMessage(clearing());
            clearList();
        }

        @Override
        public void deny(CommandSource source) {
            source.sendMessage(denied());
        }
    }
}
