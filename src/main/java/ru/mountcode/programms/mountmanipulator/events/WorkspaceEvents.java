package ru.mountcode.programms.mountmanipulator.events;

import ru.mountcode.programms.mountmanipulator.api.event.Event;
import ru.mountcode.programms.mountmanipulator.api.event.EventFactory;
import ru.mountcode.programms.mountmanipulator.model.JarObject;

public class WorkspaceEvents {

    public static final Event<Reset> RESET = EventFactory.createArrayBacked(Reset.class, callbacks -> () -> {
        for (Reset callback : callbacks) {
            callback.onWorkspaceReset();
        }
    });

    public static final Event<LoadInput> LOAD_INPUT = EventFactory.createArrayBacked(LoadInput.class, callbacks -> (jarObject) -> {
        for (LoadInput callback : callbacks) {
            callback.onWorkspaceLoadInput(jarObject);
        }
    });

    public static final Event<StartExecuting> START_EXECUTING = EventFactory.createArrayBacked(StartExecuting.class, callbacks -> () -> {
        for (StartExecuting callback : callbacks) {
            callback.onStartExecuting();
        }
    });

    public static final Event<StopExecuting> STOP_EXECUTING = EventFactory.createArrayBacked(StopExecuting.class, callbacks -> () -> {
        for (StopExecuting callback : callbacks) {
            callback.onStopExecuting();
        }
    });

    @FunctionalInterface
    public interface Reset {
        void onWorkspaceReset();
    }

    @FunctionalInterface
    public interface LoadInput {
        void onWorkspaceLoadInput(JarObject jarObject);
    }

    @FunctionalInterface
    public interface StartExecuting {
        void onStartExecuting();
    }

    @FunctionalInterface
    public interface StopExecuting {
        void onStopExecuting();
    }
}
