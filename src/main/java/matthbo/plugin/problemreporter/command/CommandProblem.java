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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CommandProblem implements CommandCallable {

    ProblemReporter PR = ProblemReporter.instance;

    public void logToFile(String msg){
        try{
            File dataFolder = PR.getDataFolder();
            if(!dataFolder.exists()) dataFolder.mkdir();

            File saveTo = new File(dataFolder, "ProblemList.txt");
            if(!saveTo.exists()) saveTo.createNewFile();
            FileWriter fw = new FileWriter(saveTo, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(msg);
            pw.flush();
            pw.close();
        }catch(IOException e){e.printStackTrace();}
    }

    public Text reported(Player player){
        return Texts.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Texts.builder("Thank you " + player.getName()).color(TextColors.RESET).append(Texts.builder(", Your question wil be answered as soon as possible!").color(TextColors.DARK_AQUA).build()).build()).build();
    }

    public Text usage(){
        return Texts.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Texts.builder("Usage /problem <args>").color(TextColors.RED).build()).build();
    }

    public Text wrngSender(){
        return Texts.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Texts.builder("What do you think?").color(TextColors.RED).build()).build();
    }

    public Text notify(Player player, String args){
        return Texts.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Texts.builder(player.getName()).color(TextColors.RESET).append(Texts.builder(": ").append(Texts.builder(args).build()).build()).build()).build();
    }

    @Override
    public Optional<CommandResult> process(CommandSource sender, String arguments) throws CommandException {
        String[] args = arguments.split(" ");
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(args.length > 1){

                StringBuilder str = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    str.append(args[i] + " ");
                }
                String bc = str.toString();
                //player.sendMessage(player.getName()+ ": " + bc);

                logToFile(player.getName()+ ": " + bc);
                //player.sendMessage(Refs.pluginMSG + "Thank you " + player.getName() + TextColors.DARK_AQUA.getColor() + ", Your question wil be answered as soon as possible!");
                //player.sendMessage(Messages.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Messages.builder("Thank you " + player.getName()).append(Messages.builder(", Your question wil be answered as soon as possible!").color(TextColors.DARK_AQUA).build()).build()).build());
                player.sendMessage(reported(player));
                Object[] ops = PR.getGame().getServer().getOnlinePlayers().toArray();
                for(int j = 0; j < ops.length; j++){
                    Player target = (Player)ops[j];
                    if(target.hasPermission("problemreporter.notify")) target.sendMessage(notify(player, bc));
                }
            }else{
                player.sendMessage(usage());
            }
        }else{
            sender.sendMessage(wrngSender());
            sender.sendMessage(Texts.of("matthbo was here!"));
        }
        return Optional.of(CommandResult.success());
    }

    private final Object desc = "Adds a message in the Problem List";
    private final Object usage = "/<command> <your problem>...";

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
