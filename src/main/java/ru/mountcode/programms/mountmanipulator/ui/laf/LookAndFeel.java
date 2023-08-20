package ru.mountcode.programms.mountmanipulator.ui.laf;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import com.github.weisj.darklaf.theme.HighContrastDarkTheme;
import com.github.weisj.darklaf.theme.HighContrastLightTheme;
import com.github.weisj.darklaf.theme.IntelliJTheme;
import com.github.weisj.darklaf.theme.OneDarkTheme;
import com.github.weisj.darklaf.theme.Theme;
import com.github.weisj.darklaf.theme.info.DefaultThemeProvider;
import java.awt.Color;
import javax.swing.plaf.ColorUIResource;

public class LookAndFeel {

  public static void init() {
    // most linux distros have ugly font rendering, but these here can fix that:
    System.setProperty("awt.useSystemAAFontSettings", "on");
    System.setProperty("swing.aatext", "true");
    System.setProperty("sun.java2d.xrender", "true");

    LafManager.setThemeProvider(new DefaultThemeProvider(
        new IntelliJTheme(),
        new OneDarkTheme(),
        new HighContrastLightTheme(),
        new HighContrastDarkTheme()
    ));
  }

  public static void setLookAndFeel() {
    LafManager.registerDefaultsAdjustmentTask((t, d) -> {
      if (Theme.isDark(t)) {
        Object p = d.get("backgroundContainer");
        if (p instanceof Color) {
          d.put("backgroundContainer", new ColorUIResource(((Color) p).darker()));
        }
      }
    });
    LafManager.installTheme(new DarculaTheme());
  }
}
