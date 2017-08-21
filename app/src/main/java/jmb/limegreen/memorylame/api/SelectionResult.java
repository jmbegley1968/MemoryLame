package jmb.limegreen.memorylame.api;


public class SelectionResult<T> {
    public boolean selectionSuccess;
    public boolean gridSuccess;
    public boolean alreadySelectedError;
    public int currentScore;
    public T cellValue;
}

