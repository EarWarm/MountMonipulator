package ru.mountcode.programms.mountmanipulator.transformers.impl.optimizations;

import java.util.HashMap;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import ru.mountcode.programms.mountmanipulator.transformers.ITransformer;

public class DupPopTransformer implements ITransformer {

  @Override
  public void transform(ClassNode classNode, HashMap<String, ClassNode> classes,
      HashMap<String, ClassNode> classpath) {
    if (classNode.methods.isEmpty()) {
      return;
    }

    for (MethodNode methodNode : classNode.methods) {
      if (methodNode.instructions.size() <= 0) {
        continue;
      }

      recursiveRemove(methodNode.instructions);
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
  public String getName() {
    return "DupPopTransformer";
  }
}
