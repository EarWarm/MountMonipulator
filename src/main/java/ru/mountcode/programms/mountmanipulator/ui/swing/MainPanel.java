package ru.mountcode.programms.mountmanipulator.ui.swing;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import ru.mountcode.programms.mountmanipulator.ui.swing.editor.EditorPanel;
import ru.mountcode.programms.mountmanipulator.ui.swing.structure.StructurePanel;

public class MainPanel extends JPanel {

  public StructurePanel structurePanel;
  public EditorPanel editorPanel;

  public MainPanel() {
    this.setLayout(new BorderLayout());

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, structurePanel = new StructurePanel(),
        editorPanel = new EditorPanel());
    splitPane.setResizeWeight(0.5);
    splitPane.setContinuousLayout(true);
    this.add(splitPane);
  }
}
