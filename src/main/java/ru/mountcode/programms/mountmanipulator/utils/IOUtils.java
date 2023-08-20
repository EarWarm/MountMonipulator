package ru.mountcode.programms.mountmanipulator.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public class IOUtils {

  public static byte[] readStreamFully(InputStream inputStream) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(Math.max(8192, inputStream.available()));
    byte[] buffer = new byte[8192];
    int read;
    while ((read = inputStream.read(buffer)) >= 0) {
      byteArrayOutputStream.write(buffer, 0, read);
    }

    return byteArrayOutputStream.toByteArray();
  }

  public static ClassNode readClassFromBytes(byte[] bytes) {
    ClassReader classReader = new ClassReader(bytes);
    ClassNode classNode = new ClassNode();

    classReader.accept(classNode, 0);
    return classNode;
  }

  public static byte[] writeClassToBytes(ClassNode classNode) {
    ClassWriter classWriter = new ClassWriter(0);
    classNode.accept(classWriter);
    return classWriter.toByteArray();
  }

  public static URL getResourceURL(String url) {
    return IOUtils.class.getResource('/' + url);
  }
}
