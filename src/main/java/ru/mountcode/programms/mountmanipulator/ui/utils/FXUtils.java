package ru.mountcode.programms.mountmanipulator.ui.utils;


import org.girod.javafx.svgimage.SVGImage;
import org.objectweb.asm.tree.ClassNode;
import ru.mountcode.programms.mountmanipulator.ui.resources.Icons;
import ru.mountcode.programms.mountmanipulator.utils.asm.Access;

public class FXUtils {

    public static SVGImage getClassIcon(ClassNode classNode) {
        if (Access.isAbstract(classNode.access)) {
            return Icons.iconClassAbstract();
        } else if (Access.isInterface(classNode.access)) {
            return Icons.iconInterface();
        } else if (Access.isEnum(classNode.access)) {
            return Icons.iconEnum();
        } else if (Access.isAnnotation(classNode.access)) {
            return Icons.iconAnnotation();
        } else if (Access.isRecord(classNode.access)) {
            return Icons.iconRecord();
        } else {
            return Icons.iconClass();
        }
    }
}
