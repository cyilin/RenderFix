package renderfix;


import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.LogWrapper;
import org.apache.logging.log4j.Level;
import org.objectweb.asm.*;

import java.util.ArrayDeque;

public class ParticleManager implements IClassTransformer {
    public static int maxParticleCount = Integer.parseInt(System.getProperty("maxParticleCount", "3072"));

    public static int getSize(ArrayDeque arrayDeque) {
        if (arrayDeque.size() > maxParticleCount) {
            while (arrayDeque.size() > maxParticleCount) {
                arrayDeque.removeFirst();
            }
        }
        return arrayDeque.size();
    }

    public byte[] transform(String s, String s1, byte[] bytes) {
        LogWrapper.log(Level.INFO, "Max Particle Count: " + maxParticleCount);
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        ParticleManagerClassVisitor visitor = new ParticleManagerClassVisitor(writer);
        reader.accept(visitor, 0);
        return writer.toByteArray();
    }
}

class ParticleManagerClassVisitor extends ClassVisitor {

    public ParticleManagerClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return new ParticleManagerMethodVisitor(super.visitMethod(access, name, desc, signature, exceptions), access, name, desc);
    }
}

class ParticleManagerMethodVisitor extends MethodVisitor {
    private final String name;
    private final String desc;

    public ParticleManagerMethodVisitor(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv);
        this.name = name;
        this.desc = desc;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (opcode == Opcodes.INVOKEVIRTUAL && owner.equals("java/util/ArrayDeque") && name.equals("size") && desc.equals("()I")) {
            LogWrapper.log(Level.INFO, this.name + this.desc);
            super.visitMethodInsn(Opcodes.INVOKESTATIC, "renderfix/ParticleManager", "getSize", "(Ljava/util/ArrayDeque;)I", false);
        } else {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }
    }
}
