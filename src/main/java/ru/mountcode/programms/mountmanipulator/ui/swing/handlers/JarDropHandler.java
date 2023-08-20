package ru.mountcode.programms.mountmanipulator.ui.swing.handlers;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;
import javax.swing.TransferHandler;
import ru.mountcode.programms.mountmanipulator.ui.interfaces.IFileLoader;

public class JarDropHandler extends TransferHandler {

  private static final long serialVersionUID = -1L;
  private final IFileLoader loader;

  public JarDropHandler(IFileLoader loader) {
    this.loader = loader;
  }

  @Override
  public boolean canImport(TransferHandler.TransferSupport info) {
    info.setShowDropLocation(false);
    return info.isDrop() && info.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean importData(TransferHandler.TransferSupport info) {
    if (!info.isDrop()) {
      return false;
    }
    Transferable t = info.getTransferable();
    List<File> data = null;
    try {
      data = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
    } catch (Exception e) {
      return false;
    }
    for (File jar : data) {
      if (jar.getName().toLowerCase().matches(".*(\\.jar|\\.zip)")) {
        loader.onFileDrop(jar);
        return true;
      }
    }
    return false;
  }
}
