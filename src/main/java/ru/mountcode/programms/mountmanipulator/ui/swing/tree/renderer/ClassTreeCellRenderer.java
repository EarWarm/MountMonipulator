package ru.mountcode.programms.mountmanipulator.ui.swing.tree.renderer;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import ru.mountcode.programms.mountmanipulator.ui.swing.tree.ClassTreeNode;
import ru.mountcode.programms.mountmanipulator.ui.utils.SwingUtils;
import ru.mountcode.programms.mountmanipulator.utils.asm.Access;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class ClassTreeCellRenderer extends DefaultTreeCellRenderer implements Opcodes {

    private static final long serialVersionUID = 1L;

    private static final Icon pack, clazz, innerClazz, mainClazz, enu, itf, ignoreOverlay;

    static {
        pack = SwingUtils.getIcon("classes/package.svg", true);
        clazz = SwingUtils.getIcon("classes/class.svg");
        innerClazz = SwingUtils.getIcon("classes/innerClass.svg");
        mainClazz = SwingUtils.getIcon("classes/mainClass.svg");
        enu = SwingUtils.getIcon("classes/enum.svg");
        itf = SwingUtils.getIcon("classes/interface.svg");
        ignoreOverlay = SwingUtils.getIcon("overlays/ignore.svg", 10, 10, true);
    }

    @Override
    public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel,
                                                  final boolean expanded, final boolean leaf, final int row,
                                                  final boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        if (node instanceof ClassTreeNode) {
            ClassNode member = ((ClassTreeNode) node).member;
            if (member != null) {
                if (Access.isInterface(member.access)) {
                    this.setIcon(itf);
                } else if (Access.isEnum(member.access)) {
                    this.setIcon(enu);
                } else {
                    if (member.methods.stream().anyMatch(mn -> mn.name.equals("main") && mn.desc.equals("([Ljava/lang/String;)V"))) {
                        this.setIcon(mainClazz);
                    } else if (member.name.contains("$") && member.outerClass != null) {
                        this.setIcon(innerClazz);
                    } else {
                        this.setIcon(clazz);
                    }
                }
            } else {
                this.setIcon(pack);
            }
        }
        return this;
    }

    @Override
    public Font getFont() {
        return new Font(Font.SANS_SERIF, Font.PLAIN, new JLabel().getFont().getSize());
    }
}
