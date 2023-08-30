package ru.mountcode.programms.mountmanipulator.workspace;

import ru.mountcode.programms.mountmanipulator.MountManipulator;
import ru.mountcode.programms.mountmanipulator.code.ClassInfo;
import ru.mountcode.programms.mountmanipulator.events.WorkspaceEvents;
import ru.mountcode.programms.mountmanipulator.model.ClassCollection;
import ru.mountcode.programms.mountmanipulator.model.JarObject;
import ru.mountcode.programms.mountmanipulator.transformers.ITransformer;
import ru.mountcode.programms.mountmanipulator.transformers.TransformersGroup;
import ru.mountcode.programms.mountmanipulator.utils.JarUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Workspace {

    private JarObject jarObject = null;

    private HashMap<String, ClassCollection> libraries = null;
    private ClassCollection classpath = new ClassCollection(new HashMap<>());

    private boolean executing = false;

    public void addLibrary(String name, ClassCollection classCollection) {
        this.libraries.put(name, classCollection);
        this.classpath.addClasses(classCollection);
    }

    public JarObject getJarObject() {
        return jarObject;
    }

    public HashMap<String, ClassCollection> getLibraries() {
        return libraries;
    }

    public ClassCollection getClasspath() {
        return classpath;
    }

    private void executeTransformer(ITransformer transformer) {
        transformer.transform(this.jarObject.classCollection().classes(), this.classpath.classes());
    }

    public void execute(ITransformer transformer) {
        if (!canExecuted()) {
            return;
        }
        this.executing = true;
        WorkspaceEvents.START_EXECUTING.invoker().onStartExecuting();
        this.executeTransformer(transformer);
        this.executing = false;
        WorkspaceEvents.STOP_EXECUTING.invoker().onStopExecuting();
    }

    public void execute(TransformersGroup group) {
        if (!canExecuted()) {
            return;
        }
        this.executing = true;
        WorkspaceEvents.START_EXECUTING.invoker().onStartExecuting();
        for (ITransformer transformer : group.getTransformers()) {
            this.executeTransformer(transformer);
        }
        this.executing = false;
        WorkspaceEvents.STOP_EXECUTING.invoker().onStopExecuting();
    }

    public boolean canExecuted() {
        return !this.executing && this.jarObject != null && !this.jarObject.classCollection().classes().isEmpty();
    }

    public void loadInputJar(File inputFile) {
        if (inputFile == null || !inputFile.exists() || inputFile.isDirectory()) {
            MountManipulator.LOGGER.error("Error on loading input jar");
            return;
        }

        try {
            this.jarObject = JarUtils.readJar(inputFile);
            WorkspaceEvents.LOAD_INPUT.invoker().onWorkspaceLoadInput(this.jarObject);
        } catch (IOException e) {
            MountManipulator.LOGGER.error("Error on parsing input jar", e);
        }
    }

    public void reset() {
        this.jarObject = null;
        this.libraries = null;
        this.classpath.clear();
        WorkspaceEvents.RESET.invoker().onWorkspaceReset();
    }
}
