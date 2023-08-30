package ru.mountcode.programms.mountmanipulator.ui.utils;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StageInteractor {
    private Stage stage;
    private Scene scene;

    private boolean isDraggable = false;
    private boolean isDragging = false;
    private boolean allowDragging = true;
    private double dragMarginTop = 0.0;
    private double dragMarginRight = 0.0;
    private double dragMarginBottom = 0.0;
    private double dragMarginLeft = 0.0;

    private boolean isFullscreenable = false;
    private boolean isFullscreen = false;
    private boolean allowFullscreen = true;
    private double fullscreenMarginTop = 0.0;
    private double fullscreenMarginRight = 0.0;
    private double fullscreenMarginBottom = 0.0;
    private double fullscreenMarginLeft = 0.0;
    private double stageWidthBeforeFullscreen = 0.0;
    private double stageHeightBeforeFullscreen = 0.0;
    private double stageXBeforeFullscreen = 0.0;
    private double stageYBeforeFullscreen = 0.0;

    private boolean isResizable = false;
    private boolean isResizing = false;
    private boolean allowResizing = true;
    private ResizeDirection resizeDirection = null;
    private double resizeMarginTop = 0.0;
    private double resizeMarginRight = 0.0;
    private double resizeMarginBottom = 0.0;
    private double resizeMarginLeft = 0.0;

    private double resizeStartFromSceneX = 0.0;
    private double resizeStartFromSceneY = 0.0;
    private double resizeStartFromScreenX = 0.0;
    private double resizeStartFromScreenY = 0.0;
    private double resizeStartStageWidth = 0.0;
    private double resizeStartStageHeight = 0.0;

    private double dragStartOffsetX = 0.0;
    private double dragStartOffsetY = 0.0;

    public StageInteractor(Stage stage) {
        this.stage = stage;
        this.scene = stage.getScene();
    }

    public StageInteractor makeDraggable(
            double marginTop,
            double marginRight,
            double marginBottom,
            double marginLeft
    ) {
        dragMarginTop = marginTop;
        dragMarginRight = marginRight;
        dragMarginBottom = marginBottom;
        dragMarginLeft = marginLeft;

        if (!isDraggable) {
            isDraggable = true;

            scene.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
                boolean isWithinBounds = detectDraggingBounds(event);

                if (isDraggable && allowDragging && isWithinBounds) {
                    scene.setCursor(Cursor.OPEN_HAND);
                } else {
                    if (scene.getCursor() == Cursor.OPEN_HAND) {
                        scene.setCursor(Cursor.DEFAULT);
                    }
                }
            });

            scene.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                dragStartOffsetX = stage.getX() - event.getScreenX();
                dragStartOffsetY = stage.getY() - event.getScreenY();
            });

            scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
                boolean isWithinBounds = detectDraggingBounds(event);

                if (isDraggable && allowDragging && isWithinBounds) {
                    isDragging = true;
                    scene.setCursor(Cursor.CLOSED_HAND);
                }

                if (isDragging) {
                    stage.setX(event.getScreenX() + dragStartOffsetX);
                    stage.setY(event.getScreenY() + dragStartOffsetY);
                }
            });

            scene.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
                if (isDragging) {
                    isDragging = false;
                    scene.setCursor(Cursor.DEFAULT);
                }
            });
        }

        return this;
    }

    private boolean detectDraggingBounds(MouseEvent event) {
        return event.getSceneY() <= dragMarginTop
                || scene.getHeight() - event.getSceneY() <= dragMarginBottom
                || event.getSceneX() <= dragMarginLeft
                || scene.getWidth() - event.getSceneX() <= dragMarginRight;
    }

    public StageInteractor makeFullscreenable(
            double marginTop,
            double marginRight,
            double marginBottom,
            double marginLeft
    ) {
        fullscreenMarginTop = marginTop;
        fullscreenMarginRight = marginRight;
        fullscreenMarginBottom = marginBottom;
        fullscreenMarginLeft = marginLeft;

        if (!isFullscreenable) {
            isFullscreenable = true;

            scene.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                boolean isDoubleClick = event.getButton() == MouseButton.PRIMARY && event.getClickCount() >= 2;

                if (isFullscreenable && allowFullscreen && isDoubleClick && detectFullscreenBounds(event)) {
                    if (isFullscreen) {
                        isFullscreen = false;
                        allowDragging = true;
                        allowResizing = true;

                        stage.setX(stageXBeforeFullscreen);
                        stage.setY(stageYBeforeFullscreen);
                        stage.setWidth(stageWidthBeforeFullscreen);
                        stage.setHeight(stageHeightBeforeFullscreen);
                    } else {
                        isFullscreen = true;
                        allowDragging = false;
                        allowResizing = false;
                        stageWidthBeforeFullscreen = stage.getWidth();
                        stageHeightBeforeFullscreen = stage.getHeight();
                        stageXBeforeFullscreen = stage.getX();
                        stageYBeforeFullscreen = stage.getY();

                        Screen screen = Screen.getPrimary();
                        double newWidth = Math.min(stage.getMaxWidth(), screen.getVisualBounds().getWidth());
                        double newHeight = Math.min(stage.getMaxHeight(), screen.getVisualBounds().getHeight());

                        stage.setWidth(newWidth);
                        stage.setHeight(newHeight);
                        stage.setX(screen.getVisualBounds().getMinX());
                        stage.setY(screen.getVisualBounds().getMinY());
                    }
                }
            });
        }

        return this;
    }

    private boolean detectFullscreenBounds(MouseEvent event) {
        boolean isWithinBounds = event.getSceneY() <= fullscreenMarginTop
                || scene.getHeight() - event.getSceneY() <= fullscreenMarginBottom
                || event.getSceneX() <= fullscreenMarginLeft
                || scene.getWidth() - event.getSceneX() <= fullscreenMarginRight;

        ResizeDirection resizeDirection = detectResizeDirection(event);

        return isWithinBounds && resizeDirection == null;
    }

    public StageInteractor makeResizable(
            double marginTop,
            double marginRight,
            double marginBottom,
            double marginLeft
    ) {
        resizeMarginTop = marginTop;
        resizeMarginRight = marginRight;
        resizeMarginBottom = marginBottom;
        resizeMarginLeft = marginLeft;

        if (!isResizable) {
            isResizable = true;

            scene.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
                if (isResizable && allowResizing && !isResizing) {
                    ResizeDirection direction = detectResizeDirection(event);
                    if (direction == null) {
                        scene.setCursor(Cursor.DEFAULT);
                        return;
                    }
                    switch (direction) {
                        case NORTH_WEST, NORTH_EAST -> scene.setCursor(Cursor.NW_RESIZE);
                        case SOUTH_WEST, SOUTH_EAST -> scene.setCursor(Cursor.SE_RESIZE);
                        case NORTH -> scene.setCursor(Cursor.N_RESIZE);
                        case SOUTH -> scene.setCursor(Cursor.S_RESIZE);
                        case WEST -> scene.setCursor(Cursor.W_RESIZE);
                        case EAST -> scene.setCursor(Cursor.E_RESIZE);
                        default -> scene.setCursor(Cursor.DEFAULT);
                    }
                }
            });

            scene.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                if (isResizable && allowResizing && !isResizing) {
                    resizeDirection = detectResizeDirection(event);

                    if (resizeDirection != null) {
                        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() >= 2) {
                            Screen screen = Screen.getPrimary();
                            double screenMinX = screen.getVisualBounds().getMinX();
                            double screenMinY = screen.getVisualBounds().getMinY();

                            if (resizeDirection == ResizeDirection.NORTH || resizeDirection == ResizeDirection.NORTH_WEST || resizeDirection == ResizeDirection.NORTH_EAST) {
                                stage.setHeight(ensureStageHeightIsWithinLimits(stage.getHeight() + stage.getY() - screenMinY));
                                stage.setY(screenMinY);
                            }

                            if (resizeDirection == ResizeDirection.SOUTH || resizeDirection == ResizeDirection.SOUTH_WEST || resizeDirection == ResizeDirection.SOUTH_EAST) {
                                stage.setHeight(ensureStageHeightIsWithinLimits(screen.getVisualBounds().getHeight() - stage.getY() + screenMinY));

                                if (stage.getHeight() == screen.getVisualBounds().getHeight()) {
                                    stage.setY(screenMinY);
                                }
                            }

                            if (resizeDirection == ResizeDirection.WEST || resizeDirection == ResizeDirection.NORTH_WEST || resizeDirection == ResizeDirection.SOUTH_WEST) {
                                stage.setWidth(ensureStageWidthIsWithinLimits(stage.getWidth() + stage.getX()));
                                stage.setX(screenMinX);
                            }

                            if (resizeDirection == ResizeDirection.EAST || resizeDirection == ResizeDirection.NORTH_EAST || resizeDirection == ResizeDirection.SOUTH_EAST) {
                                stage.setWidth(ensureStageWidthIsWithinLimits(screen.getVisualBounds().getWidth() - stage.getX()));

                                if (stage.getWidth() == screen.getVisualBounds().getWidth()) {
                                    stage.setX(screenMinX);
                                }
                            }
                        } else {
                            isResizing = true;
                            isDraggable = false;
                            isFullscreenable = false;

                            resizeStartFromScreenX = event.getScreenX();
                            resizeStartFromScreenY = event.getScreenY();
                            resizeStartFromSceneX = event.getSceneX();
                            resizeStartFromSceneY = event.getSceneY();
                            resizeStartStageWidth = stage.getWidth();
                            resizeStartStageHeight = stage.getHeight();
                        }
                    }
                }
            });

            scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
                if (isResizing) {
                    if (resizeDirection == ResizeDirection.NORTH || resizeDirection == ResizeDirection.NORTH_WEST || resizeDirection == ResizeDirection.NORTH_EAST) {
                        double newHeight = ensureStageHeightIsWithinLimits(resizeStartStageHeight + (resizeStartFromScreenY - event.getScreenY()));
                        double newY = newHeight == stage.getMaxHeight() || newHeight == stage.getMinHeight() ? stage.getY() : event.getScreenY() - resizeStartFromSceneY;

                        stage.setHeight(newHeight);
                        stage.setY(newY);
                    }

                    if (resizeDirection == ResizeDirection.SOUTH || resizeDirection == ResizeDirection.SOUTH_WEST || resizeDirection == ResizeDirection.SOUTH_EAST) {
                        double newHeight = ensureStageHeightIsWithinLimits(resizeStartStageHeight + (event.getScreenY() - resizeStartFromScreenY));
                        stage.setHeight(newHeight);
                    }

                    if (resizeDirection == ResizeDirection.WEST || resizeDirection == ResizeDirection.NORTH_WEST || resizeDirection == ResizeDirection.SOUTH_WEST) {
                        double newWidth = ensureStageWidthIsWithinLimits(resizeStartStageWidth + (resizeStartFromScreenX - event.getScreenX()));
                        double newX = newWidth == stage.getMaxWidth() || newWidth == stage.getMinWidth() ? stage.getX() : event.getScreenX() - resizeStartFromSceneX;

                        stage.setWidth(newWidth);
                        stage.setX(newX);
                    }

                    if (resizeDirection == ResizeDirection.EAST || resizeDirection == ResizeDirection.NORTH_EAST || resizeDirection == ResizeDirection.SOUTH_EAST) {
                        double newWidth = ensureStageWidthIsWithinLimits(resizeStartStageWidth + (event.getScreenX() - resizeStartFromScreenX));
                        stage.setWidth(newWidth);
                    }
                }
            });

            scene.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
                if (isResizing) {
                    isResizing = false;
                    isDraggable = true;
                    isFullscreenable = true;
                }
            });
        }

        return this;
    }

    private ResizeDirection detectResizeDirection(MouseEvent event) {
        boolean isNorthResize = event.getSceneY() <= resizeMarginTop;
        boolean isSouthResize = scene.getHeight() - event.getSceneY() <= resizeMarginBottom;
        boolean isWestResize = event.getSceneX() <= resizeMarginLeft;
        boolean isEastResize = scene.getWidth() - event.getSceneX() <= resizeMarginRight;
        boolean isNorthWestResize = isNorthResize && isWestResize;
        boolean isNorthEastResize = isNorthResize && isEastResize;
        boolean isSouthWestResize = isSouthResize && isWestResize;
        boolean isSouthEastResize = isSouthResize && isEastResize;

        if (isNorthWestResize) {
            return ResizeDirection.NORTH_WEST;
        } else if (isNorthEastResize) {
            return ResizeDirection.NORTH_EAST;
        } else if (isSouthWestResize) {
            return ResizeDirection.SOUTH_WEST;
        } else if (isSouthEastResize) {
            return ResizeDirection.SOUTH_EAST;
        } else if (isNorthResize) {
            return ResizeDirection.NORTH;
        } else if (isSouthResize) {
            return ResizeDirection.SOUTH;
        } else if (isWestResize) {
            return ResizeDirection.WEST;
        } else if (isEastResize) {
            return ResizeDirection.EAST;
        } else {
            return null;
        }
    }

    private double ensureStageWidthIsWithinLimits(double width) {
        Screen screen = Screen.getPrimary();

        if (width > stage.getMaxWidth()) {
            return stage.getMaxWidth();
        } else if (width < stage.getMinWidth()) {
            return stage.getMinWidth();
        } else if (width > screen.getVisualBounds().getWidth()) {
            return screen.getVisualBounds().getWidth();
        } else {
            return width;
        }
    }

    private double ensureStageHeightIsWithinLimits(double height) {
        Screen screen = Screen.getPrimary();

        if (height > stage.getMaxHeight()) {
            return stage.getMaxHeight();
        } else if (height < stage.getMinHeight()) {
            return stage.getMinHeight();
        } else if (height > screen.getVisualBounds().getHeight()) {
            return screen.getVisualBounds().getHeight();
        } else {
            return height;
        }
    }

    enum ResizeDirection {
        NORTH_WEST,
        NORTH_EAST,
        SOUTH_WEST,
        SOUTH_EAST,
        NORTH,
        SOUTH,
        WEST,
        EAST
    }
}
