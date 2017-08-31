package net.catchi.elevator.implementation;

/**
 * Created by schwartz on 5/1/17.
 */
public class ElevatorAction
{
    ElevatorAction(String _actionName,
                int _terminalHeight) {
        actionName = _actionName;
        terminalHeight = _terminalHeight;
        duration = 1;
    }

    ElevatorAction(String _actionName,
                   int _terminalHeight,
                   int _duration) {
        actionName = _actionName;
        terminalHeight = _terminalHeight;
        duration = _duration;

    }

    String actionName;
    int terminalHeight;
    int duration;

    public static final String  Move_Up = "Move_Up";
    public static final String Move_Down = "Move_Down";
    public static final String Stopping = "Stopping";
    public static final String Stopped = "Stopped";
    public static final String Opening = "Opening";
};
