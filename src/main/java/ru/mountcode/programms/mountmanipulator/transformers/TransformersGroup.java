package ru.mountcode.programms.mountmanipulator.transformers;

import java.util.ArrayList;
import java.util.List;

public class TransformersGroup {

    private final String name;
    private final List<ITransformer> transformers;

    public TransformersGroup(String name) {
        this.name = name;
        this.transformers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addTransformer(ITransformer transformer) {
        this.transformers.add(transformer);
    }

    public List<ITransformer> getTransformers() {
        return transformers;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
