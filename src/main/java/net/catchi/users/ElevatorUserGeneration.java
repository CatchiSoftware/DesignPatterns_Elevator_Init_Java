package net.catchi.users;

import java.util.List;

import net.catchi.elevator.*;
import net.catchi.users.ElevatorUser;

public class ElevatorUserGeneration
{
    public ElevatorUserGeneration(int _divisor,
                           int _minFloor,
                           int _maxFloor,
                           ElevatorBank _bank) {
        divisor = _divisor;
        minFloor = _minFloor;
        maxFloor = _maxFloor;
        bank = _bank;

        users = new java.util.LinkedList<ElevatorUser>();
    }

    static int userNumber;
    int divisor;
    int minFloor;
    int maxFloor;
    ElevatorBank bank;
    List<ElevatorUser> users;
    private java.util.Random m_random = new java.util.Random();

    public void registerUser(int stepNumber)
    {
        if(stepNumber % divisor != 0)
            return;

        int delta = maxFloor - minFloor;
        int randomStart = m_random.nextInt(delta);
        int randomEnd = m_random.nextInt(delta);

        if(randomStart == randomEnd)
            return;

       System.out.println( "Creating a user moving from floor: "
                + Integer.toString(minFloor + randomStart)
                + " to floor: "
                + Integer.toString(minFloor + randomEnd));


        ElevatorUser user = new ElevatorUser(userNumber++,
                minFloor + randomStart,
                minFloor + randomEnd);

        users.add(user);
        user.registerWithBank(bank);
    }
};
