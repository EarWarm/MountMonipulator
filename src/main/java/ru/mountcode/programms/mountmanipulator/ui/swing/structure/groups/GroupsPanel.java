package ru.mountcode.programms.mountmanipulator.ui.swing.structure.groups;

import com.github.weisj.darklaf.components.OverlayScrollPane;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import javax.swing.JPanel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import ru.mountcode.programms.mountmanipulator.MountManipulator;
import ru.mountcode.programms.mountmanipulator.ui.swing.tree.GroupTreeNode;
import ru.mountcode.programms.mountmanipulator.ui.swing.tree.GroupTreeNode.Type;
import ru.mountcode.programms.mountmanipulator.ui.swing.tree.JTreeWithHint;
import ru.mountcode.programms.mountmanipulator.ui.swing.tree.renderer.GroupTreeCellRenderer;
import ru.mountcode.programms.mountmanipulator.transformers.Transformers;
import ru.mountcode.programms.mountmanipulator.transformers.TransformersGroup;

public class GroupsPanel extends JPanel {

  public GroupsTree tree;
  public DefaultTreeModel model;

  public GroupsPanel() {
    this.setLayout(new BorderLayout());
    this.add(new OverlayScrollPane(tree = new GroupsTree()), BorderLayout.CENTER);

    loadGroups();
  }

  public void loadGroups() {
    GroupTreeNode rootNode = new GroupTreeNode();
    model = new DefaultTreeModel(rootNode);

    for (TransformersGroup group : MountManipulator.getInstance().getConfiguration().getTransformersGroups().values()) {
      GroupTreeNode groupNode = new GroupTreeNode(group);
      groupNode.loadTransformers();
      rootNode.add(groupNode);
    }

    tree.setModel(model);
  }

  public class GroupsTree extends JTreeWithHint {

    public GroupsTree() {
      super("Создайте свою группу трансформеров");
      this.setRootVisible(false);
      this.setShowsRootHandles(true);
      this.setFocusable(true);
      this.setCellRenderer(new GroupTreeCellRenderer());
      this.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

      GroupTreeNode rootNode = new GroupTreeNode();
      model = new DefaultTreeModel(rootNode);

      this.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          System.out.println("x: " + e.getX() + ", y: " + e.getY());

          GroupTreeNode rootNode = (GroupTreeNode) tree.getModel().getRoot();
          for (int i = 0; i < rootNode.getChildCount(); ++i) {
            GroupTreeNode treeNode = (GroupTreeNode) rootNode.getChildAt(i);
            System.out.println(tree.getPathForLocation(e.getX(), e.getY()));
            System.out.println(Arrays.toString(treeNode.getPath()));
          }

          if (e.getButton() == 3) {
            TreePath path = tree.getPathForLocation(e.getX(), e.getY());
            if (path == null) {
              new RootPopup(tree).show(e.getComponent(), e.getX(), e.getY());
            } else {
              Object object = path.getLastPathComponent();
              if (object instanceof GroupTreeNode) {
                GroupTreeNode treeNode = (GroupTreeNode) object;
                if (treeNode.getType() == Type.GROUP) {
                  new GroupPopup(tree, treeNode).show(e.getComponent(), e.getX(), e.getY());
                } else if (treeNode.getType() == Type.TRANSFORMER) {

                }
              }
            }
          }
        }

      });
    }

    public void refresh() {
      this.repaint();
      model.reload();
    }
  }
}
