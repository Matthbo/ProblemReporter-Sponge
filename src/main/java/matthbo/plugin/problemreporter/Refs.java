package matthbo.plugin.problemreporter;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

public class Refs {

    public static final String NAME = "ProblemReporter";
    public static final String VERSION = "1.0-snapshot-1";

    public static final String pluginMSG = "[ProblemReporter] ";

    public static Text wrngSender(){
        return Texts.builder(pluginMSG).color(TextColors.AQUA).append(Texts.builder("Player Command Only!").color(TextColors.RED).build()).build();
    }

}
