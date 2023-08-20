package ru.mountcode.programms.mountmanipulator.ui.swing.tree;

import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;

import ru.mountcode.programms.mountmanipulator.transformers.ITransformer;
import ru.mountcode.programms.mountmanipulator.transformers.TransformersGroup;

public class GroupTreeNode extends DefaultMutableTreeNode {

  private final Type type;

  private TransformersGroup transformersGroup;
  private ITransformer transformer;
  private String name;

  public GroupTreeNode() {
    this.type = Type.ROOT;
  }

  public GroupTreeNode(TransformersGroup transformersGroup) {
    this.type = Type.GROUP;
    this.name = transformersGroup.getName();
    this.transformersGroup = transformersGroup;
  }

  public GroupTreeNode(ITransformer transformer) {
    this.type = Type.TRANSFORMER;
    this.name = transformer.getName();
    this.transformer = transformer;
  }

  public void loadTransformers() {
    if (transformersGroup == null) {
      return;
    }

    this.children = new Vector<>();
    for (ITransformer transformer : transformersGroup.getTransformers()) {
      this.add(new GroupTreeNode(transformer));
    }
  }

  public Type getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }

  public TransformersGroup getTransformersGroup() {
    return transformersGroup;
  }

  public ITransformer getTransformer() {
    return transformer;
  }

  public enum Type {
    ROOT,
    GROUP,
    TRANSFORMER;
  }
}
