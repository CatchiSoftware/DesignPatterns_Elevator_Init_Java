package net.catchi.elevator;

import net.catchi.elevator.implementation.StepAction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by schwartz on 5/1/17.
 */
public class GameClock
{
    public GameClock(int numSteps) {
        m_numSteps = numSteps;
        m_currentStepNumber = 0;
    }

    public void run()
    {
        while(m_currentStepNumber < m_numSteps)
        {
            System.out.println("Executing step: " + Integer.toString(m_currentStepNumber));

            List<StepAction> clonedActions = new LinkedList<StepAction>();
            clonedActions.addAll(m_actions.values());
            clonedActions.forEach((action)->
            {
                try {
                    action.Execute(m_currentStepNumber);
                }
                catch (Exception exc) {
                    System.out.println(
                            "Error executing operation: " +
                                    action.name +
                                    " with exception: " +
                                    exc.getMessage());
                }
            });

            m_currentStepNumber += 1;
        }
    }

    public int currentStep()
    {
        return m_currentStepNumber;
    }

    public String registerStepAction(StepAction action)
    {
        String toReturn = java.util.UUID.randomUUID().toString();
        m_actions.put(toReturn, action);
        return toReturn;
    }
    
    public void unregisterStepAction(String uuid)
    {
        m_actions.remove(uuid);
    }

    private int m_numSteps;
    private int m_currentStepNumber;
    private HashMap<String, StepAction> m_actions = new HashMap<String, StepAction>();

};
