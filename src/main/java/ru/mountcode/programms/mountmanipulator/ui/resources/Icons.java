package ru.mountcode.programms.mountmanipulator.ui.resources;

import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;
import ru.mountcode.programms.mountmanipulator.ui.AppWindow;

public class Icons {

    public static SVGImage iconClass() {
        return SVGLoader.load(AppWindow.getResourceUrl("icons/nodes/class.svg"));
    }

    public static SVGImage iconClassAbstract() {
        return SVGLoader.load(AppWindow.getResourceUrl("icons/nodes/class_abstract.svg"));
    }

    public static SVGImage iconAnnotation() {
        return SVGLoader.load(AppWindow.getResourceUrl("icons/nodes/annotation.svg"));
    }

    public static SVGImage iconRecord() {
        return SVGLoader.load(AppWindow.getResourceUrl("icons/nodes/record.svg"));
    }

    public static SVGImage iconEnum() {
        return SVGLoader.load(AppWindow.getResourceUrl("icons/nodes/enum.svg"));
    }

    public static SVGImage iconException() {
        return SVGLoader.load(AppWindow.getResourceUrl("icons/nodes/exception.svg"));
    }

    public static SVGImage iconInterface() {
        return SVGLoader.load(AppWindow.getResourceUrl("icons/nodes/interface.svg"));
    }

    public static SVGImage iconPackage() {
        return SVGLoader.load(AppWindow.getResourceUrl("icons/nodes/package.svg"));
    }

    public static SVGImage iconFolder() {
        return SVGLoader.load(AppWindow.getResourceUrl("icons/nodes/folder.svg"));
    }

    public static SVGImage iconTransformer() {
        return SVGLoader.load(AppWindow.getResourceUrl("icons/nodes/transformer.svg"));
    }

    public static SVGImage iconRun() {
        return SVGLoader.load(AppWindow.getResourceUrl("icons/actions/run.svg"));
    }
}
