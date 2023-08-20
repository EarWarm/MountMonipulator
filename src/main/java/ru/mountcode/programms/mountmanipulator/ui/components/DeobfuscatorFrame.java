package ru.mountcode.programms.mountmanipulator.ui.components;

import javax.swing.JFrame;

public class DeobfuscatorFrame extends JFrame {
//
//  private GridBagConstraints gridBagConstraints;
//
//  public JList<JTransformersGroup> transformersGroups;
//
//  public DeobfuscatorFrame() {
//    this.setName("MountDeobfuscator");
//    this.setTitle("MountDeobfuscator | Интерфейс");
//    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//    this.getContentPane().setLayout(new GridBagLayout());
//    this.getContentPane().setBackground(ru.mountcode.programms.mountdeobfuscator.gui.DeobfuscatorFrame.getBackgroundColor());
//
//    this.setBounds(new Rectangle(900, 500));
//    this.moveToCenter();
//
//    initMainPanels();
//
//    this.setVisible(true);
//  }
//
//  public void moveToCenter() {
//    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
//    this.setLocation((dimension.width - this.getWidth()) / 2, (dimension.height - this.getHeight()) / 2);
//  }
//
//  private void initMainPanels() {
//    this.gridBagConstraints = new GridBagConstraints();
//    this.gridBagConstraints.fill = GridBagConstraints.BOTH;
//    this.gridBagConstraints.weightx = 1;
//    this.gridBagConstraints.weighty = 1;
//
//    GridLayout gridLayout = new GridLayout();
//    gridLayout.setColumns(1);
//
//    JPanel structurePanel = new JPanel(gridLayout);
//    structurePanel.setBackground(ru.mountcode.programms.mountdeobfuscator.gui.DeobfuscatorFrame.getBackgroundColor());
//    initStructurePanel(structurePanel);
//
//    JPanel editorPanel = new JPanel();
//    editorPanel.setBackground(ru.mountcode.programms.mountdeobfuscator.gui.DeobfuscatorFrame.getBackgroundColor());
//
//    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, structurePanel, editorPanel);
//    splitPane.setUI(new BasicSplitPaneUI() {
//      @Override
//      public BasicSplitPaneDivider createDefaultDivider() {
//        return new BasicSplitPaneDivider(this) {
//          @Override
//          public void setBorder(Border border) {
//          }
//
//          @Override
//          public void paint(Graphics g) {
//            g.setColor(ru.mountcode.programms.mountdeobfuscator.gui.DeobfuscatorFrame.getBroderColor());
//            g.fillRect(0, 0, getSize().width, getSize().height);
//            super.paint(g);
//          }
//
//
//        };
//      }
//    });
//    splitPane.setContinuousLayout(true);
//    splitPane.setBorder(null);
//    splitPane.setDividerLocation(this.getWidth() / 4);
//    splitPane.setDividerSize(1);
//
//    this.add(splitPane, this.gridBagConstraints);
//  }
//
//  private void initStructurePanel(JPanel structurePanel) {
//    JScrollPane scrollPane = new JScrollPane();
//    scrollPane.getViewport().setBackground(ru.mountcode.programms.mountdeobfuscator.gui.DeobfuscatorFrame.getBackgroundColor());
//    scrollPane.setBorder(null);
//
//    JList<JLabel> transformers = new JList<>();
//    transformers.setBackground(ru.mountcode.programms.mountdeobfuscator.gui.DeobfuscatorFrame.getBackgroundColor());
//
//    JLabel label1 = new JLabel("DupPopTransformer-1");
//    label1.setForeground(ru.mountcode.programms.mountdeobfuscator.gui.DeobfuscatorFrame.getTextColor());
//    JLabel label2 = new JLabel("DupPopTransformer-2");
//    label2.setForeground(ru.mountcode.programms.mountdeobfuscator.gui.DeobfuscatorFrame.getTextColor());
//    JLabel label3 = new JLabel("DupPopTransformer-3");
//    label3.setForeground(ru.mountcode.programms.mountdeobfuscator.gui.DeobfuscatorFrame.getTextColor());
//
//    transformers.setListData(new JLabel[]{label1, label2, label3});
//
//    scrollPane.getViewport().add(transformers);
//
//    structurePanel.add(scrollPane);
//
////    this.transformersGroups = new JList<>();
////    this.transformersGroups.setLayout(new GridBagLayout());
////    for (String transformer : Transformers.transformers.keySet()) {
////      JPanel transformerPanel = new JPanel(new BorderLayout());
////      JLabel label = new JLabel(transformer);
////      transformerPanel.add(label);
////      System.out.println(transformerPanel);
////      this.transformersGroups.add(transformerPanel);
////    }
////
////    scrollPane.add(this.transformersGroups);
////    structurePanel.add(scrollPane);
//
//  }
}
