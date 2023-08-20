package ru.mountcode.programms.mountmanipulator.ui.listeners;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ExitListener extends WindowAdapter {

  private final JFrame frame;

  public ExitListener(JFrame frame) {
    this.frame = frame;
  }

  @Override
  public void windowClosing(WindowEvent we) {
    if (JOptionPane.showConfirmDialog(frame, "Вы действительно хотите выйти?", "Подтверждение выхода",
        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
      Runtime.getRuntime().exit(0);
    }
  }

}
