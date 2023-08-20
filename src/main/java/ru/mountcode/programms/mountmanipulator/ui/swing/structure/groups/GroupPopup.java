package ru.mountcode.programms.mountmanipulator.ui.swing.structure.groups;

import java.awt.BorderLayout;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

import ru.mountcode.programms.mountmanipulator.MountManipulator;
import ru.mountcode.programms.mountmanipulator.transformers.ITransformer;
import ru.mountcode.programms.mountmanipulator.ui.swing.structure.groups.GroupsPanel.GroupsTree;
import ru.mountcode.programms.mountmanipulator.ui.swing.tree.GroupTreeNode;
import ru.mountcode.programms.mountmanipulator.ui.swing.tree.selection.TransformerSelection;
import ru.mountcode.programms.mountmanipulator.ui.swing.tree.selection.TransformerSelection.TreeNode;

public class GroupPopup extends JPopupMenu {

  public GroupPopup(GroupsTree tree, GroupTreeNode treeNode) {
    this.setLayout(new BorderLayout());

    JMenuItem addItem = new JMenuItem("Добавить трансформер");
    addItem.addActionListener((listener) -> {
      TransformerSelection selection = new TransformerSelection(tree.getParent());
      selection.setVisible(true);

      if (selection.tree.getSelectionPath() == null) {
        TreeNode node = (TreeNode) selection.tree.getLastSelectedPathComponent();
        if (node != null && !node.isFolder()) {
          try {
            ITransformer transformer = node.transformerClass.newInstance();
            treeNode.getTransformersGroup().addTransformer(transformer);
            treeNode.add(new GroupTreeNode(transformer));
            tree.repaint();
            MountManipulator.getInstance().getConfiguration().saveConfiguration();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      } else {
        for (TreePath path : selection.tree.getSelectionPaths()) {
          TreeNode node = (TreeNode) path.getLastPathComponent();
          if (node != null && !node.isFolder()) {
            try {
              ITransformer transformer = node.transformerClass.newInstance();
              treeNode.getTransformersGroup().addTransformer(transformer);
              treeNode.add(new GroupTreeNode(transformer));
              tree.repaint();
              MountManipulator.getInstance().getConfiguration().saveConfiguration();
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }
    });

    this.add(addItem);
  }
}
