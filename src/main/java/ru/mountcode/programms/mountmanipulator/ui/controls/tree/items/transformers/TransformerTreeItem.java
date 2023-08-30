package ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.transformers;

import javafx.scene.control.TreeItem;
import ru.mountcode.programms.mountmanipulator.transformers.ITransformer;

public class TransformerTreeItem extends TreeItem<String> {

    private final ITransformer transformer;

    public TransformerTreeItem(ITransformer transformer) {
        super(transformer.getIdentifier().toString());
        this.transformer = transformer;
    }

    public ITransformer getTransformer() {
        return transformer;
    }
}
