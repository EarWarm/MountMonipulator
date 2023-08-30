package ru.mountcode.programms.mountmanipulator.transformers.impl.optimizations;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import ru.mountcode.programms.mountmanipulator.api.Identifier;
import ru.mountcode.programms.mountmanipulator.code.ClassInfo;
import ru.mountcode.programms.mountmanipulator.transformers.ITransformer;

import java.util.HashMap;

public class DupPopTransformer implements ITransformer {

    public static final Identifier IDENTIFIER = new Identifier("Optimizations", "DupPopTransformer");

    @Override
    public void transform(HashMap<String, ClassInfo> classes, HashMap<String, ClassInfo> classpath) {
        for (ClassInfo classInfo : classes.values()) {
            if (classInfo.classNode().methods.isEmpty()) {
                return;
            }

            for (MethodNode methodNode : classInfo.classNode().methods) {
                if (methodNode.instructions.size() <= 0) {
                    continue;
                }

                recursiveRemove(methodNode.instructions);
            }
        }
    }

    private void recursiveRemove(InsnList instructions) {
        boolean modified = true;

        while (modified) {
            modified = false;
            for (int i = 0; i < instructions.size(); ++i) {
                AbstractInsnNode insnNode = instructions.get(i);

                if (insnNode.getType() == Opcodes.DUP) {
                    if (i + 1 >= instructions.size()) {
                        continue;
                    }

                    AbstractInsnNode nextInsnNode = insnNode.getNext();
                    if (nextInsnNode.getType() == Opcodes.POP) {
                        instructions.remove(insnNode);
                        instructions.remove(nextInsnNode);
                        modified = true;

                        break;
                    }
                }
            }
        }
    }

    @Override
    public Identifier getIdentifier() {
        return IDENTIFIER;
    }
}
