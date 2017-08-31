package net.catchi.states;

/**
 * Created by schwartz on 5/1/17.
 */
public class ElevatorMovement
{
    ElevatorMovement(
            boolean _isStopped,
            boolean _isAscending,
            boolean _isDescending)
    {
        isStopped = _isStopped;
        isAscending =_isAscending;
        isDescending = _isDescending;
        stoppingDistance=0;
    }


    public boolean isStopped;
    public boolean isAscending;
    public boolean isDescending;
    public int stoppingDistance;

    public static ElevatorMovement Stopped()
    {
        return new ElevatorMovement(true, false, false);
    }

    public static ElevatorMovement Moving(boolean upOrDown)
    {
        return new  ElevatorMovement(false,
                upOrDown,
                !upOrDown);
    }
}