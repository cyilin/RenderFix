package renderfix;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.launchwrapper.LogWrapper;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassTweaker implements ITweaker {
    private List<String> args = new ArrayList<>();
    private File gameDir;
    private File assetsDir;
    private String profile;
    private List<String> tweakClasses;
    private List<String> tweaks;
    private boolean hasForge = false;
    private boolean hasLiteLoader = false;

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.args.addAll(args);
        this.gameDir = gameDir;
        this.assetsDir = assetsDir;
        this.profile = profile;
        tweakClasses = (List<String>) Launch.blackboard.get("TweakClasses");
        tweaks = (List<String>) Launch.blackboard.get("Tweaks");
        for (String s : tweakClasses) {
            if (s.contains("net.minecraftforge.")) {
                hasForge = true;
            } else if (s.contains("org.spongepowered.") || s.contains("com.mumfrey.liteloader.")) {
                hasLiteLoader = true;
            }
        }
        ObfNames.init();
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        if (!ObfNames.MC_VERSION.equals("") && ObfNames.EnchantmentHelper != null) {
            LogWrapper.log(Level.INFO, "[RenderFix] Minecraft Version " + ObfNames.MC_VERSION);
            classLoader.registerTransformer("renderfix.ClassTransformer");
        } else {
            LogWrapper.log(Level.ERROR, "[RenderFix] Unsupported Minecraft Version " + ObfNames.MC_VERSION);
        }
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        if (hasForge || hasLiteLoader) {
            return new String[0];
        }
        if (!args.contains("--gameDir")) {
            args.add("--gameDir");
            args.add(this.gameDir.getPath());
        }
        if (!args.contains("--assetsDir")) {
            args.add("--assetsDir");
            args.add(this.assetsDir.getPath());
        }
        if (!args.contains("--version")) {
            args.add("--version");
            args.add(this.profile);
        }
        if (!args.contains("--version")) {
            args.add("--version");
            args.add(this.profile);
        }

        return args.toArray(new String[args.size()]);
    }
}
