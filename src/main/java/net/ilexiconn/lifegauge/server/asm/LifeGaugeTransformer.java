package net.ilexiconn.lifegauge.server.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class LifeGaugeTransformer implements IClassTransformer {
    public static ClassNode createClassFromByteArray(byte[] classBytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(classBytes);
        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);
        return classNode;
    }

    public static byte[] createByteArrayFromClass(ClassNode classNode, int flags) {
        ClassWriter classWriter = new ClassWriter(flags);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    public byte[] transform(String name, String transformedName, byte[] classBytes) {
        if (transformedName.equals("net.minecraft.entity.EntityLivingBase")) {
            return transformEntityLivingBase(classBytes);
        } else {
            return classBytes;
        }
    }

    public byte[] transformEntityLivingBase(byte[] classBytes) {
        ClassNode classNode = createClassFromByteArray(classBytes);

        String entityLivingBaseClass = LifeGaugePlugin.isDeobfuscated ? "pr" : "net/minecraft/entity/EntityLivingBase";
        String potionEffectClass = LifeGaugePlugin.isDeobfuscated ? "pf" : "net/minecraft/potion/PotionEffect";

        String onNewPotionEffect = LifeGaugePlugin.isDeobfuscated ? "a" : "onNewPotionEffect";
        String onNewPotionEffectDeobf = "onNewPotionEffect";
        String onNewPotionEffectDesc = String.format("(L%s;)V", potionEffectClass);

        String onChangedPotionEffect = LifeGaugePlugin.isDeobfuscated ? "a" : "onChangedPotionEffect";
        String onChangedPotionEffectDeobf = "onChangedPotionEffect";
        String onChangedPotionEffectDesc = String.format("(L%s;Z)V", potionEffectClass);

        String onFinishedPotionEffect = LifeGaugePlugin.isDeobfuscated ? "b" : "onFinishedPotionEffect";
        String onFinishedPotionEffectDeobf = "onFinishedPotionEffect";
        String onFinishedPotionEffectDesc = String.format("(L%s;)V", potionEffectClass);

        for (MethodNode method : classNode.methods) {
            if (onNewPotionEffect.equals(method.name) && onNewPotionEffectDesc.equals(method.desc)) {
                transform(method, onNewPotionEffectDeobf, potionEffectClass, entityLivingBaseClass);
            } else if (onChangedPotionEffect.equals(method.name) && onChangedPotionEffectDesc.equals(method.desc)) {
                transform(method, onChangedPotionEffectDeobf, potionEffectClass, entityLivingBaseClass);
            } else if (onFinishedPotionEffect.equals(method.name) && onFinishedPotionEffectDesc.equals(method.desc)) {
                transform(method, onFinishedPotionEffectDeobf, potionEffectClass, entityLivingBaseClass);
            }
        }

        return createByteArrayFromClass(classNode, ClassWriter.COMPUTE_MAXS);
    }

    public void transform(MethodNode methodNode, String name, String potionEffectClass, String entityLivingBaseClass) {
        for (int i = 0; i < methodNode.instructions.size(); i++) {
            AbstractInsnNode insnNode = methodNode.instructions.get(i);
            if (insnNode.getOpcode() == Opcodes.GETSTATIC) {
                InsnList insnList = new InsnList();
                insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/ilexiconn/lifegauge/server/asm/LifeGaugeHooks", name, String.format("(L%s;L%s;)V", potionEffectClass, entityLivingBaseClass), false));
                methodNode.instructions.insertBefore(insnNode, insnList);
                break;
            }
        }
    }
}
