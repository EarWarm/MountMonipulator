package ru.mountcode.programms.mountmanipulator.ui;

import com.github.weisj.darklaf.settings.ThemeSettings;
import ru.mountcode.programms.mountmanipulator.MountManipulator;
import ru.mountcode.programms.mountmanipulator.ui.listeners.ExitListener;
import ru.mountcode.programms.mountmanipulator.ui.swing.MainPanel;
import ru.mountcode.programms.mountmanipulator.ui.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame {

    private static AppWindow instance;

    public MainPanel mainPanel;

    public AppWindow() {
        instance = this;

        // size and position
        this.initBounds();
        this.setIconImage(SwingUtils.iconToFrameImage(SwingUtils.getIcon("favicon.svg", true), this));
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new ExitListener(this));

        this.initFrame();
        this.initMenu();
    }

    public static AppWindow getInstance() {
        return instance != null ? instance : new AppWindow();
    }

    private void initFrame() {
        JPanel contentPanel = new JPanel(new BorderLayout());

        contentPanel.add(this.mainPanel = new MainPanel());

        this.setContentPane(contentPanel);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Файл");
        JMenuItem resetWorkspaceItem = new JMenuItem("Сбросить");
        resetWorkspaceItem.addActionListener(l -> {
            if (JOptionPane.showConfirmDialog(this, "Вы действительно хотите сбросить рабочую область?",
                    "Подтверждение сброса", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                MountManipulator.getInstance().getWorkspace().reset();
                this.mainPanel.structurePanel.classesPanel.reset();
                this.setTitle(null);
            }
        });
        fileMenu.add(resetWorkspaceItem);
        menuBar.add(fileMenu);

        JMenu settingsMenu = new JMenu("Настройки");

        JMenuItem themeSettingsItem = new JMenuItem("Тема");
        themeSettingsItem.setIcon(ThemeSettings.getIcon());
        themeSettingsItem.addActionListener(l -> ThemeSettings.showSettingsDialog(this));
        settingsMenu.add(themeSettingsItem);
        menuBar.add(settingsMenu);

        this.setJMenuBar(menuBar);
    }

    private void initBounds() {
        Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setBounds(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2, width, height);
        setMinimumSize(new Dimension((int) (width / 1.25), (int) (height / 1.25)));
    }

}
