package renderfix;


import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.LogWrapper;
import org.apache.logging.log4j.Level;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public class EntityRenderer implements IClassTransformer {

    public byte[] transform(String s, String s1, byte[] bytes) {
        ClassReader cr = new ClassReader(bytes);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        EntityRendererClassVisitor cv = new EntityRendererClassVisitor(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }
}

class EntityRendererClassVisitor extends ClassVisitor {

    public EntityRendererClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return new EntityRendererMethodVisitor(super.visitMethod(access, name, desc, signature, exceptions), access, name, desc);
    }

    @Override
    public void visitEnd() {
        MethodVisitor mv = super.visitMethod(ACC_PUBLIC + ACC_STATIC, "owo", "(L" + ObfNames.EntityLivingBase + ";)I", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, ObfNames.EnchantmentHelper, "d", "(L" + ObfNames.EntityLivingBase + ";)I", false);
        mv.visitVarInsn(ISTORE, 1);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitVarInsn(ILOAD, 1);
        mv.visitInsn(ICONST_3);
        Label l2 = new Label();
        mv.visitJumpInsn(IF_ICMPLE, l2);
        mv.visitInsn(ICONST_3);
        Label l3 = new Label();
        mv.visitJumpInsn(GOTO, l3);
        mv.visitLabel(l2);
        mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]{Opcodes.INTEGER}, 0, null);
        mv.visitVarInsn(ILOAD, 1);
        mv.visitLabel(l3);
        mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{Opcodes.INTEGER});
        mv.visitInsn(IRETURN);
        Label l4 = new Label();
        mv.visitLabel(l4);
        mv.visitLocalVariable("e", "L" + ObfNames.EntityLivingBase + ";", null, l0, l4, 0);
        mv.visitLocalVariable("l", "I", null, l1, l4, 1);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }
}

class EntityRendererMethodVisitor extends MethodVisitor {

    private final String name;
    private final String desc;

    public EntityRendererMethodVisitor(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv);
        this.name = name;
        this.desc = desc;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (opcode == INVOKESTATIC && owner.equals(ObfNames.EnchantmentHelper) &&
                name.equals("d") && desc.equals("(L" + ObfNames.EntityLivingBase + ";)I")) {
            LogWrapper.log(Level.INFO, this.name + this.desc);
            super.visitMethodInsn(INVOKESTATIC, ObfNames.EntityRenderer, "owo", "(L" + ObfNames.EntityLivingBase + ";)I", false);
        } else {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }
    }
}
