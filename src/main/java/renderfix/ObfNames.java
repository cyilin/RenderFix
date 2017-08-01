package renderfix;

import java.lang.reflect.Field;

public class ObfNames {
    public static String ParticleManager;
    public static String EntityRenderer;
    public static String EntityLivingBase;
    public static String EnchantmentHelper;
    static String MC_VERSION = "";

    public static void init() {
        MC_VERSION = getMinecraftVersion();
        if (MC_VERSION.equals("1.11.2")) {
            ParticleManager = "bov";
            EntityRenderer = "bqe";
            EntityLivingBase = "sw";
            EnchantmentHelper = "aik";
        } else if (MC_VERSION.equals("1.12-pre5")) {
            ParticleManager = "btf";
            EntityRenderer = "bup";
            EntityLivingBase = "vn";
            EnchantmentHelper = "alj";
        } else if (MC_VERSION.equals("1.12-pre6") || MC_VERSION.equals("1.12-pre7") || MC_VERSION.equals("1.12")) {
            ParticleManager = "bte";
            EntityRenderer = "buo";
            EntityLivingBase = "vn";
            EnchantmentHelper = "alk";
        } else if (MC_VERSION.equals("17w31a")) {
            ParticleManager = "btg";
            EntityRenderer = "buq";
            EntityLivingBase = "vp";
            EnchantmentHelper = "alm";
        }
    }

    public static String getMinecraftVersion() {
        try {
            Class realmsSharedConstants = Class.forName("net.minecraft.realms.RealmsSharedConstants");
            Field field = realmsSharedConstants.getField("VERSION_STRING");
            return (String) field.get(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "";
    }
}  