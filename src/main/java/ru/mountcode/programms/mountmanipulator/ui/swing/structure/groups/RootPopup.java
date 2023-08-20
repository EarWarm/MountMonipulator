package ru.mountcode.programms.mountmanipulator.ui.swing.structure.groups;

import java.awt.BorderLayout;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import ru.mountcode.programms.mountmanipulator.MountManipulator;
import ru.mountcode.programms.mountmanipulator.ui.swing.structure.groups.GroupsPanel.GroupsTree;
import ru.mountcode.programms.mountmanipulator.ui.swing.tree.GroupTreeNode;
import ru.mountcode.programms.mountmanipulator.transformers.Transformers;
import ru.mountcode.programms.mountmanipulator.transformers.TransformersGroup;

public class RootPopup extends JPopupMenu {

  public RootPopup(GroupsTree tree) {
    this.setLayout(new BorderLayout());

    JMenuItem addItem = new JMenuItem("Создать группу");
    addItem.addActionListener((listener) -> {
      String name;
      do {
        name = JOptionPane.showInputDialog("Укажите уникальное название группы");
      } while (MountManipulator.getInstance().getConfiguration().getTransformersGroups().containsKey(name) && name != null && !name.isEmpty());

      if (name != null && !name.isEmpty()) {
        TransformersGroup group = new TransformersGroup(name);
        MountManipulator.getInstance().getConfiguration().getTransformersGroups().put(group.getName(), group);
        MountManipulator.getInstance().getConfiguration().saveConfiguration();

        GroupTreeNode rootNode = (GroupTreeNode) tree.getModel().getRoot();
        rootNode.add(new GroupTreeNode(group));

        tree.refresh();
      }
    });

    this.add(addItem);
  }
}
