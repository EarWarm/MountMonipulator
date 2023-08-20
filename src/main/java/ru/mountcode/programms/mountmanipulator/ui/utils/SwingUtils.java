package ru.mountcode.programms.mountmanipulator.ui.utils;

import com.github.weisj.darklaf.properties.icons.IconLoader;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.Border;
import ru.mountcode.programms.mountmanipulator.MountManipulator;

public class SwingUtils {

  public static final IconLoader ICON_LOADER = IconLoader.get(MountManipulator.class);

  public static JComponent horizontallyDivided(JComponent top, JComponent bottom) {
    JPanel content = new JPanel(new BorderLayout());
    JPanel topHolder = new JPanel(new BorderLayout());
    topHolder.add(top, BorderLayout.CENTER);
    topHolder.add(SwingUtils.createHorizontalSeparator(8), BorderLayout.SOUTH);
    content.add(topHolder, BorderLayout.CENTER);
    content.add(bottom, BorderLayout.SOUTH);
    return content;
  }

  public static JComponent verticallyDivided(JComponent left, JComponent right) {
    JPanel content = new JPanel(new BorderLayout());
    JPanel leftHolder = new JPanel(new BorderLayout());
    leftHolder.add(left, BorderLayout.CENTER);
    leftHolder.add(SwingUtils.createVerticalSeparator(8), BorderLayout.EAST);
    content.add(leftHolder, BorderLayout.CENTER);
    content.add(right, BorderLayout.EAST);
    return content;
  }

  public static JComponent createHorizontalSeparator() {
    return createHorizontalSeparator(0);
  }

  public static JComponent createHorizontalSeparator(int padding) {
    return withEmptyBorder(wrap(new JSeparator(JSeparator.HORIZONTAL)), padding, 0, padding, 0);
  }

  public static JComponent createVerticalSeparator() {
    return createVerticalSeparator(0);
  }

  public static JComponent createVerticalSeparator(int padding) {
    return withEmptyBorder(wrap(new JSeparator(JSeparator.VERTICAL)), 0, padding, 0, padding);
  }

  public static <T extends JComponent> T withEmptyBorder(T comp, int pad) {
    return withEmptyBorder(comp, pad, pad, pad, pad);
  }

  public static <T extends JComponent> T withEmptyBorder(T comp, int top, int left, int bottom, int right) {
    return withBorder(comp, BorderFactory.createEmptyBorder(top, left, bottom, right));
  }

  public static <T extends JComponent> T withBorder(T comp, Border border) {
    comp.setBorder(border);
    return comp;
  }

  public static JComponent wrap(JComponent component) {
    JPanel wrap = new JPanel(new BorderLayout());
    wrap.add(component);
    return wrap;
  }

  public static Image iconToFrameImage(Icon icon, Window window) {
    return IconLoader.createFrameIcon(icon, window);
  }

  public static Icon getIcon(String path) {
    return getIcon(path, false);
  }

  public static Icon getIcon(String path, boolean themed) {
    return ICON_LOADER.getIcon(path, themed);
  }

  public static Icon getIcon(String path, int width, int height) {
    return ICON_LOADER.getIcon(path, width, height, false);
  }

  public static Icon getIcon(String path, int width, int height, boolean themed) {
    return ICON_LOADER.getIcon(path, width, height, themed);
  }
}
