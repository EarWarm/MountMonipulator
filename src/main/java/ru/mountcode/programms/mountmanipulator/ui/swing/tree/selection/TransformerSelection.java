package ru.mountcode.programms.mountmanipulator.ui.swing.tree.selection;

import com.github.weisj.darklaf.components.OverlayScrollPane;
import com.github.weisj.darklaf.components.border.DarkBorders;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.objectweb.asm.Opcodes;
import ru.mountcode.programms.mountmanipulator.ui.utils.SwingUtils;
import ru.mountcode.programms.mountmanipulator.transformers.ITransformer;
import ru.mountcode.programms.mountmanipulator.transformers.Transformers;

public class TransformerSelection extends JDialog {

  public JTree tree;
  public JButton submitButton;

  public TransformerSelection(Component parent) {
    this.setLocationRelativeTo(parent);
    this.setModalityType(ModalityType.APPLICATION_MODAL);
    this.setTitle("Выберите один или несколько трансформеров");
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setSize(450, 300);
    this.setMinimumSize(new Dimension(450, 300));
    this.getContentPane().setLayout(new BorderLayout());

    JPanel mainPanel = new JPanel();
    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    this.getContentPane().add(mainPanel, BorderLayout.CENTER);
    mainPanel.setLayout(new BorderLayout());

    this.tree = new TransformerSelectionTree();
    JPanel treePanel = new JPanel(new BorderLayout());
    treePanel.setBorder(DarkBorders.createLineBorder(1, 1, 1, 1));
    treePanel.add(new OverlayScrollPane(this.tree), BorderLayout.CENTER);
    mainPanel.add(treePanel);

    JPanel buttonsPanel = new JPanel();
    buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

    submitButton = new JButton("OK");
    submitButton.setEnabled(false);
    submitButton.addActionListener(e -> dispose());
    submitButton.setActionCommand("OK");
    this.getRootPane().setDefaultButton(submitButton);
    buttonsPanel.add(submitButton);

    JButton cancelButton = new JButton("Отменить");
    cancelButton.setActionCommand("Cancel");
    cancelButton.addActionListener((listener) -> {
      this.tree.clearSelection();
      this.dispose();
    });
    buttonsPanel.add(cancelButton);

    this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
  }

  public class TransformerSelectionTree extends JTree implements TreeSelectionListener {

    public TransformerSelectionTree() {
      this.setRootVisible(false);
      this.setShowsRootHandles(true);
      this.setFocusable(true);
      this.setCellRenderer(new TransformerSelectionCellRenderer());
      TreeNode root = new TreeNode("");
      DefaultTreeModel model = new DefaultTreeModel(root);
      this.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
      Transformers.getTransformers().forEach((name, cl) -> addTransformer(root, name, cl));
      this.setModel(model);
      ToolTipManager.sharedInstance().registerComponent(this);
      this.addTreeSelectionListener(this);
      this.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (e.getClickCount() == 2) {
            TreeNode treeNode = (TreeNode) getLastSelectedPathComponent();
            if (treeNode != null && !treeNode.isFolder()) {
              TransformerSelection.this.dispose();
            }
          }
        }
      });
      this.addTreeSelectionListener(e -> {
        TreePath[] paths = e.getPaths();
        for (TreePath path : paths) {
          DefaultMutableTreeNode tn = (DefaultMutableTreeNode) path.getLastPathComponent();
          if (tn.getChildCount() > 0) {
            removeSelectionPath(path);
          }
        }
      });
    }

    private void addTransformer(TreeNode rootNode, String name, Class<? extends ITransformer> transformer) {
      if (name.contains(".")) {
        String[] split = name.split("\\.");
        addTreeNode(rootNode, name, transformer, split, 0);
      } else {
        addTreeNode(rootNode, name, transformer, new String[0], 0);
      }
    }

    private void addTreeNode(TreeNode currentNode, String name, Class<? extends ITransformer> transformer,
        String[] subFolders, int folder) {
      String node = subFolders[folder];
      if (subFolders.length - folder == 1) {
        currentNode.add(new TreeNode(node, transformer));
        return;
      }

      for (int i = 0; i < currentNode.getChildCount(); i++) {
        TreeNode childNode = (TreeNode) currentNode.getChildAt(i);
        if (childNode.toString().equals(node)) {
          addTreeNode(childNode, name, transformer, subFolders, ++folder);
          return;
        }
      }

      TreeNode newChild = new TreeNode(node);
      currentNode.add(newChild);
      addTreeNode(newChild, name, transformer, subFolders, ++folder);
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
      TreeNode treeNode = (TreeNode) tree.getLastSelectedPathComponent();
      submitButton.setEnabled(treeNode != null && !treeNode.isFolder());
    }
  }

  public static class TreeNode extends DefaultMutableTreeNode {

    public String name;
    public Class<? extends ITransformer> transformerClass;

    public TreeNode(String folder) {
      this.name = folder;
    }

    public TreeNode(String name, Class<? extends ITransformer> transformerClass) {
      this.name = name;
      this.transformerClass = transformerClass;
    }

    public boolean isFolder() {
      return this.transformerClass == null;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  public static class TransformerSelectionCellRenderer extends DefaultTreeCellRenderer implements Opcodes {

    private final Icon executionPurple, directory;

    public TransformerSelectionCellRenderer() {
      this.directory = SwingUtils.getIcon("folder.svg", true);
      this.executionPurple = SwingUtils.getIcon("transformer_purple.svg");
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
        int row, boolean hasFocus) {
      super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
      if (node instanceof TreeNode) {
        TreeNode treeNode = (TreeNode) node;

        if (treeNode.isFolder()) {
          this.setIcon(this.directory);
        } else {
          this.setIcon(this.executionPurple);
        }
      }
      return this;
    }
  }
}
