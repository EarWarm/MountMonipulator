package ru.mountcode.programms.mountmanipulator.helpers;

import ru.mountcode.programms.mountmanipulator.utils.OSUtils;

import java.io.File;

public class IOHelper {

    private static final String fileSeparator = System.getProperty("file.separator");
    private static final String lineSeparator = System.getProperty("line.separator");

    private static final File workingDirectory = new File(System.getProperty("user.home") + fileSeparator + ".MountManipulator");

    public static String getFileSeparator() {
        return fileSeparator;
    }

    public static String getLineSeparator() {
        return lineSeparator;
    }

    public static File getWorkingDirectory() {
        while (!workingDirectory.exists()) {
            workingDirectory.mkdirs();
        }

        //Hide directory
        if (OSUtils.isWindows() && !workingDirectory.isHidden()) {
            new Thread(() -> {
                try {
                    // Hide file by running attrib system command (on Windows)
                    Process p = new ProcessBuilder(
                            "attrib",
                            "+H",
                            workingDirectory.getAbsolutePath()
                    ).start();
                } catch (Exception ignored) {
                }
            }, "Hide MountManipulator Directory").start();
        }

        return workingDirectory;
    }
}
