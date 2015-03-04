package matthbo.plugin.problemreporter;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.message.Message;
import org.spongepowered.api.text.message.Messages;

public class Refs {

    public static final String NAME = "ProblemReporter";
    public static final String VERSION = "1.0";

    public static final String pluginMSG = "[ProblemReporter] ";

    public static final String Usage = "/<command> <message>";

    public static Message wrngSender(){
        return Messages.builder(pluginMSG).color(TextColors.AQUA).append(Messages.builder("Player Command Only!").color(TextColors.RED).build()).build();
    }

}
