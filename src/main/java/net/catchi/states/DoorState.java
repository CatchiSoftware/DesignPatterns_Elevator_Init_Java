package net.catchi.states;

/**
 * Created by schwartz on 5/1/17.
 */
public class DoorState 
{
    DoorState(boolean isOpen,
              boolean isClosing,
              boolean isOpening,
              boolean isClosed)
    {
        IsStopped = false;
        IsEmergency = false;

        IsOpen = isOpen;
        IsClosing =isClosing;
        IsOpening =isOpening;
        IsClosed =isClosed;
    }


    DoorState(boolean isOpen,
              boolean isClosing,
              boolean isOpening,
              boolean isClosed,
              boolean isStopped)
    {
        IsStopped = isStopped;
        IsEmergency = false;

        IsOpen = isOpen;
        IsClosing =isClosing;
        IsOpening =isOpening;
        IsClosed =isClosed;
    }

    public boolean IsOpen;
    public boolean IsClosing;
    public boolean IsOpening;
    public boolean IsClosed;

    public  boolean IsStopped;
    public boolean IsEmergency;

    public static DoorState Opening(boolean isClosed)
    {
        return new DoorState(false, false, true, isClosed);
    }

    public static DoorState Open()
    {
        return new DoorState(true, false, false, false);
    }

    public static DoorState Closing(boolean isOpen)
    {
        return new DoorState(isOpen, true, false, false);
    }

    public static DoorState Closed()
    {
        return new DoorState(false, false, false, true);
    }
};
