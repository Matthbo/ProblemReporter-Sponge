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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
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

    @Override
    public boolean call(CommandSource sender, String args, List<String> parents) throws CommandException {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(args.length() > 1){

                /*StringBuilder str = new StringBuilder();
                for (int i = 0; i < args.length(); i++) {
                    str.append(args[i] + " ");
                }
                String bc = str.toString();
                //player.sendMessage(player.getName()+ ": " + bc);*/

                logToFile(player.getName()+ ": " + args);
                //player.sendMessage(Refs.pluginMSG + "Thank you " + player.getName() + TextColors.DARK_AQUA.getColor() + ", Your question wil be answered as soon as possible!");
                //player.sendMessage(Messages.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Messages.builder("Thank you " + player.getName()).append(Messages.builder(", Your question wil be answered as soon as possible!").color(TextColors.DARK_AQUA).build()).build()).build());
                player.sendMessage(reported(player));
                Object[] ops = PR.getGame().getServer().get().getOnlinePlayers().toArray();
                for(int j = 0; j < ops.length; j++){
                    Player target = (Player)ops[j];
                    if(target.hasPermission("problemreporter.notify")) target.sendMessage(notify(player, args));
                }
            }else{
                player.sendMessage(usage());
            }
        }else{
            sender.sendMessage(wrngSender());
            sender.sendMessage("matthbo was here!");
        }
        return true;
    }

    @Override
    public boolean testPermission(CommandSource source) {
        return false;
    }

    @Override
    public Optional<String> getShortDescription() {
        return Optional.of("make Desc");
    }

    @Override
    public Optional<String> getHelp() {
        return Optional.of("make Help");
    }

    @Override
    public String getUsage() {
        return "make Usage";
    }

    @Override
    public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
        return null;
    }

    public Message reported(Player player){
        return Messages.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Messages.builder("Thank you " + player.getName()).color(TextColors.RESET).append(Messages.builder(", Your question wil be answered as soon as possible!").color(TextColors.DARK_AQUA).build()).build()).build();
    }

    public Message usage(){
        return Messages.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Messages.builder("Usage /problem <args>").color(TextColors.RED).build()).build();
    }

    public Message wrngSender(){
        return Messages.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Messages.builder("What do you think?").color(TextColors.RED).build()).build();
    }

    public Message notify(Player player, String args){
        return Messages.builder(Refs.pluginMSG).color(TextColors.AQUA).append(Messages.builder(player.getName()).color(TextColors.RESET).append(Messages.builder(": ").append(Messages.builder(args).build()).build()).build()).build();
    }
}
