package ru.mountcode.programms.mountmanipulator.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class FixedJarInputStream extends JarInputStream {

  private final Manifest manifest;

  public FixedJarInputStream(File file, boolean verify) throws IOException {
    super(Files.newInputStream(file.toPath()), verify);

    try (JarFile jar = new JarFile(file)) {
      JarEntry manifestEntry = jar.getJarEntry(JarFile.MANIFEST_NAME);
      if (manifestEntry == null) {
        this.manifest = null;
        return;
      }
      this.manifest = new Manifest(jar.getInputStream(manifestEntry));
    }
  }

  @Override
  public Manifest getManifest() {
    return manifest;
  }
}
