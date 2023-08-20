package ru.mountcode.programms.mountmanipulator.ui.swing.structure;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import ru.mountcode.programms.mountmanipulator.ui.swing.structure.groups.GroupsPanel;

public class StructurePanel extends JPanel {

  public ClassesPanel classesPanel;
  public GroupsPanel groupsPanel;

  public StructurePanel() {
    this.setLayout(new BorderLayout());

    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, classesPanel = new ClassesPanel(), groupsPanel =
        new GroupsPanel());
    splitPane.setResizeWeight(0.5);
    splitPane.setContinuousLayout(true);
    this.add(splitPane);
  }
}
