package ru.mountcode.programms.mountmanipulator.ui.swing.tree;

import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class JTreeWithHint extends JTree {

  private final JLabel hintRenderer;

  public JTreeWithHint(String hint) {
    hintRenderer = new JLabel(hint);
    hintRenderer.setOpaque(false);
  }

  @Override
  public void doLayout() {
    super.doLayout();
    hintRenderer.setSize(hintRenderer.getPreferredSize());
    hintRenderer.doLayout();
  }

  @Override
  public void updateUI() {
    super.updateUI();
    if (hintRenderer != null) {
      hintRenderer.updateUI();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    DefaultMutableTreeNode tn = (DefaultMutableTreeNode) getModel().getRoot();
    if (tn.getChildCount() == 0) {
      int x = (getWidth() - hintRenderer.getWidth()) / 2;
      int y = (getHeight() - hintRenderer.getHeight()) / 2;
      g.translate(x, y);
      hintRenderer.setEnabled(isEnabled());
      hintRenderer.paint(g);
    }
  }
}
