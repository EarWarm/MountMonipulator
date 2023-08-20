package ru.mountcode.programms.mountmanipulator.ui.swing.structure;

import com.github.weisj.darklaf.components.OverlayScrollPane;
import org.objectweb.asm.tree.ClassNode;
import ru.mountcode.programms.mountmanipulator.MountManipulator;
import ru.mountcode.programms.mountmanipulator.events.WorkspaceEvents;
import ru.mountcode.programms.mountmanipulator.ui.interfaces.IFileLoader;
import ru.mountcode.programms.mountmanipulator.ui.swing.handlers.JarDropHandler;
import ru.mountcode.programms.mountmanipulator.ui.swing.tree.ClassTreeNode;
import ru.mountcode.programms.mountmanipulator.ui.swing.tree.JTreeWithHint;
import ru.mountcode.programms.mountmanipulator.ui.swing.tree.renderer.ClassTreeCellRenderer;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.io.File;
import java.util.Collection;
import java.util.Collections;

public class ClassesPanel extends JPanel implements IFileLoader {

    private final ClassTree tree;
    public DefaultTreeModel model;

    public ClassesPanel() {
        this.setLayout(new BorderLayout());
        this.add(new OverlayScrollPane(tree = new ClassTree()), BorderLayout.CENTER);
        this.setTransferHandler(new JarDropHandler(this));

        WorkspaceEvents.RESET.register(this::reset);
    }

    public void reset() {
        this.model = new DefaultTreeModel(new ClassTreeNode(""));
        this.tree.setModel(this.model);
        this.repaint();
    }

    @Override
    public Dimension getMinimumSize() {
        Dimension minSize = super.getMinimumSize();
        minSize.width = 150;
        return minSize;
    }

    private JPanel createButtons() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue());
        return panel;
    }

    public void updateAllNames(ClassTreeNode root) {
        root.updateClassName();
        for (int i = 0; i < root.getChildCount(); i++) {
            ClassTreeNode child = (ClassTreeNode) root.getChildAt(i);
            updateAllNames(child);
        }
    }

    @Override
    public void onFileDrop(File input) {
        try {
            if (input.isDirectory() || (!input.getName().endsWith(".jar") && !input.getName().endsWith(".zip"))) {
                return;
            }

            MountManipulator.getInstance().getWorkspace().loadInputJar(input);

            loadTree(MountManipulator.getInstance().getWorkspace().getJarObject().classCollection().classes().values());
            model.reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTree(Collection<ClassNode> classes) {
        ClassTreeNode root = new ClassTreeNode("");
        model = new DefaultTreeModel(root);
        classes.forEach(classNode -> {
            String[] packages = classNode.name.split("/");
            if (classNode.name.contains("//") || packages.length >= 256) {
                String last = packages[packages.length - 1];
                boolean valid = last.chars().mapToObj(i -> (char) i).allMatch(Character::isJavaIdentifierPart);
                packages = new String[]{"<html><font color=\"red\">$invalid_name",
                        valid ? last : ("<html><font color=\"red\">$" + last.hashCode())};
            }
            addToTree((ClassTreeNode) model.getRoot(), classNode, packages, 0);
        });
        for (Object n : Collections.list(root.depthFirstEnumeration())) {
            ClassTreeNode node = (ClassTreeNode) n;
            if (!node.isLeaf() && node != root) {
                if (node.getChildCount() == 1) {
                    ClassTreeNode child = (ClassTreeNode) node.getChildAt(0);
                    if (child.member == null) {
                        node.combinePackage(child);
                    }
                }
            }
            node.sort();
        }
        tree.setModel(model);
        SwingUtilities.invokeLater(() -> {
            // Expand the tree to the open the first package with more than one child.
            boolean rootVisible = tree.isRootVisible();
            TreeNode node = (TreeNode) model.getRoot();
            boolean expanding = node.getChildCount() >= 1;
            while (expanding) {
                if (node.getChildCount() > 1) {
                    expanding = false;
                }
                if (rootVisible || node != model.getRoot()) {
                    TreeNode[] path = node instanceof DefaultMutableTreeNode ? ((DefaultMutableTreeNode) node).getPath()
                            : model.getPathToRoot(node);
                    tree.expandPath(new TreePath(path));
                }
                if (node.isLeaf()) {
                    return;
                }
                node = node.getChildAt(0);
            }
        });
    }

    public void addToTree(ClassTreeNode current, ClassNode classNode, String[] packages, int pckg) {
        String node = packages[pckg];
        if (packages.length - pckg <= 1) {
            current.add(new ClassTreeNode(classNode));
            return;
        }
        for (int i = 0; i < current.getChildCount(); i++) {
            ClassTreeNode child = (ClassTreeNode) current.getChildAt(i);
            if (child.toString().equals(node) && child.member == null) {
                addToTree(child, classNode, packages, ++pckg);
                return;
            }
        }
        ClassTreeNode newChild = new ClassTreeNode(node);
        current.add(newChild);
        addToTree(newChild, classNode, packages, ++pckg);
    }

    public class ClassTree extends JTreeWithHint {

        private static final long serialVersionUID = 1L;

        public ClassTree() {
            super("Переместите сюда ваш Jar файл");
            this.setRootVisible(false);
            this.setShowsRootHandles(true);
            this.setFocusable(true);
            this.setCellRenderer(new ClassTreeCellRenderer());
            model = new DefaultTreeModel(new ClassTreeNode(""));
            this.setModel(model);
            ToolTipManager.sharedInstance().registerComponent(this);
            this.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
        }

    }
}
