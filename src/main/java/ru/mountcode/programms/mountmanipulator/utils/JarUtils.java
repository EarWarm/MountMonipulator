package ru.mountcode.programms.mountmanipulator.utils;

import org.objectweb.asm.tree.ClassNode;
import ru.mountcode.programms.mountmanipulator.code.ClassInfo;
import ru.mountcode.programms.mountmanipulator.io.FixedJarInputStream;
import ru.mountcode.programms.mountmanipulator.model.ClassCollection;
import ru.mountcode.programms.mountmanipulator.model.JarObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class JarUtils {

    public static JarObject readJar(File jar) throws IOException {
        HashMap<String, ClassInfo> classes = new HashMap<>();
        HashMap<String, byte[]> extraFiles = new HashMap<>();

        try (FixedJarInputStream jarStream = new FixedJarInputStream(jar, false)) {
            JarEntry jarEntry;
            while ((jarEntry = jarStream.getNextJarEntry()) != null) {
                if (jarEntry.isDirectory()) {
                    continue;
                }

                String name = jarEntry.getName();
                if (name.endsWith(".class")) {
                    byte[] bytes = IOUtils.readStreamFully(jarStream);
                    if (bytes.length > 0) {
                        ClassInfo classInfo = new ClassInfo(loadClass(bytes));
                        classes.put(classInfo.name(), classInfo);
                    }
                } else {
                    extraFiles.put(name, IOUtils.readStreamFully(jarStream));
                }
            }
        }
        return new JarObject(jar.getName(), new ClassCollection(classes), extraFiles);
    }

    public static ClassNode loadClass(byte[] classBytes) {
        return IOUtils.readClassFromBytes(classBytes);
    }

    public static ClassCollection loadClasses(File jar) throws IOException {
        HashMap<String, ClassInfo> classes = new HashMap<>();

        try (FixedJarInputStream jarInputStream = new FixedJarInputStream(jar, false)) {
            JarEntry jarEntry;

            while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                if (jarEntry.isDirectory()) {
                    continue;
                }

                String name = jarEntry.getName();
                if (name.endsWith(".class")) {
                    byte[] bytes = IOUtils.readStreamFully(jarInputStream);
                    if (bytes.length > 0) {
                        ClassInfo classInfo = new ClassInfo(loadClass(bytes));
                        classes.put(classInfo.name(), classInfo);
                    }
                }
            }
        }
        return new ClassCollection(classes);
    }

    public static void writeJar(JarObject jar, File outputFile) throws IOException {
        if (outputFile.exists()) {
            outputFile.delete();
        }

        Set<String> dirs = new HashSet<>();
        try (JarOutputStream jarOutputStream = new JarOutputStream(Files.newOutputStream(outputFile.toPath()))) {
            for (ClassInfo classInfo : jar.classCollection().classes().values()) {
                try {
                    addDirectories(classInfo.name(), dirs);
                    jarOutputStream.putNextEntry(new JarEntry(classInfo.name() + ".class"));
                    jarOutputStream.write(IOUtils.writeClassToBytes(classInfo.classNode()));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    jarOutputStream.closeEntry();
                }
            }
            if (!jar.extraFiles().isEmpty()) {
                for (Map.Entry<String, byte[]> entry : jar.extraFiles().entrySet()) {
                    addDirectories(entry.getKey(), dirs);
                    jarOutputStream.putNextEntry(new JarEntry(entry.getKey()));
                    jarOutputStream.write(entry.getValue());
                    jarOutputStream.closeEntry();
                }
            }

            for (String dirPath : dirs) {
                jarOutputStream.putNextEntry(new JarEntry(dirPath + "/"));
                jarOutputStream.closeEntry();
            }
            jarOutputStream.flush();
        }
    }

    private static void addDirectories(String filePath, Set<String> dirs) {
        int i = filePath.lastIndexOf('/');
        if (i >= 0) {
            String dirPath = filePath.substring(0, i);
            if (dirs.add(dirPath)) {
                addDirectories(dirPath, dirs);
            }
        }
    }
}
