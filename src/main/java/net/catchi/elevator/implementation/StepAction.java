package net.catchi.elevator.implementation;

/**
 * Created by schwartz on 5/1/17.
 */
public abstract class StepAction {
    public StepAction(String _name) {
        name = _name;
    }

    public String name;
    public abstract  void Execute(int stepNumber);
}
