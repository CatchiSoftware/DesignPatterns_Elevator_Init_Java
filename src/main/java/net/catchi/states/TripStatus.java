package net.catchi.states;

/**
 * Created by schwartz on 5/1/17.
 */
public class TripStatus {  
    public TripStatus(int _userID,
                                      int _sourceFloor,
                                      int _targetFloor) {
        userID = _userID;
        sourceFloor = _sourceFloor;
        targetFloor = _targetFloor;
        wasRegistered = false;
        registrationTime = 0;
        wasPickedUp = false;
        timeToPickup = 0;
        wasDroppedOff = false;
        timeToDropoff = 0;
    }

    public int userID;
    public int sourceFloor;
    public int targetFloor;
    public boolean wasRegistered;
    public int registrationTime;
    public boolean wasPickedUp;
    public int timeToPickup;
    public boolean wasDroppedOff;
    public int timeToDropoff;

}
