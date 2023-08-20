package ru.mountcode.programms.mountmanipulator.utils;

public class OSUtils {

    /**
     * Checks if the OS contains 'win'
     *
     * @return true if the os.name property contains 'win'
     */
    public static boolean isWindows()
    {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
