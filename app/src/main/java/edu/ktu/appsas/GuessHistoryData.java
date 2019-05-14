package edu.ktu.appsas;

public class GuessHistoryData {
    public int number;
    public int result;
    public int turnsLeft;

    public GuessHistoryData() {
        number = 0;
        result = 0;
        turnsLeft = 0;
    }

    public GuessHistoryData(int inNumber, int inResult, int inTurnsLeft) {
        number = inNumber;
        result = inResult;
        turnsLeft = inTurnsLeft;
    }
}
