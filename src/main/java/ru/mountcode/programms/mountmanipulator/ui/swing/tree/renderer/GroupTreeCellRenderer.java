package ru.mountcode.programms.mountmanipulator.ui.swing.tree.renderer;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.objectweb.asm.Opcodes;
import ru.mountcode.programms.mountmanipulator.ui.swing.tree.GroupTreeNode;
import ru.mountcode.programms.mountmanipulator.ui.swing.tree.GroupTreeNode.Type;
import ru.mountcode.programms.mountmanipulator.ui.utils.SwingUtils;

public class GroupTreeCellRenderer extends DefaultTreeCellRenderer implements Opcodes {

  private final Icon executionPurple, directory;

  public GroupTreeCellRenderer() {
    this.directory = SwingUtils.getIcon("folder.svg", true);
    this.executionPurple = SwingUtils.getIcon("transformer_purple.svg");
  }

  @Override
  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
      int row, boolean hasFocus) {
    super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
    if (node instanceof GroupTreeNode) {
      GroupTreeNode treeNode = (GroupTreeNode) node;

      if (treeNode.getType() == Type.GROUP) {
        this.setIcon(this.directory);
      } else {
        this.setIcon(this.executionPurple);
      }
    }
    return this;
  }
}
