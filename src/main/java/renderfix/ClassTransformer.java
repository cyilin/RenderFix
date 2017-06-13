package renderfix;


import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.LogWrapper;
import org.apache.logging.log4j.Level;

public class ClassTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass == null) {
            return null;
        }
        if (name.equals(ObfNames.ParticleManager)) {
            LogWrapper.log(Level.INFO, "[RenderFix] Patching ParticleManager");
            return new ParticleManager().transform(name, transformedName, basicClass);
        } else if (name.equals(ObfNames.EntityRenderer)) {
            LogWrapper.log(Level.INFO, "[RenderFix] Patching EntityRenderer");
            return new EntityRenderer().transform(name, transformedName, basicClass);
        }
        return basicClass;
    }
}
