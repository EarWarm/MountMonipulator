package ru.mountcode.programms.mountmanipulator.transformers;

import java.util.HashMap;
import ru.mountcode.programms.mountmanipulator.transformers.impl.optimizations.DupPopTransformer;
import ru.mountcode.programms.mountmanipulator.transformers.impl.test.TestTransformer;

public class Transformers {

  private static final HashMap<String, Class<? extends ITransformer>> transformers = new HashMap<>();

  static {
    transformers.put("DupPopTransformer", DupPopTransformer.class);
    transformers.put("TestTransformer", TestTransformer.class);
  }

  public static HashMap<String, Class<? extends ITransformer>> getTransformers() {
    return transformers;
  }
}
