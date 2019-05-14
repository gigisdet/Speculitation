package edu.ktu.appsas;

public class AchievementsEntry {
    private int mID;
    private String mName;
    private int mTurns;
    private int mNumber;
    private String mDifficulty;

    public AchievementsEntry() {
        mID = 0;
        mName = "";
        mTurns = 0;
        mNumber = 0;
        mDifficulty = "";
    }

    public AchievementsEntry(int id, String name, int turns, int number, String difficulty) {
        mID = id;
        mName = name;
        mNumber = number;
        mTurns = turns;
        mDifficulty = difficulty;
    }

    public void setID(int val) {
        mID = val;
    }

    public int getID() {
        return mID;
    }

    public void setName(String val) {
        mName = val;
    }

    public String getName() {
        return mName;
    }

    public void setNumber(int val) {
        mNumber = val;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setTurns(int val) {
        mTurns = val;
    }

    public int gerTurns() {
        return mTurns;
    }

    public void setDifficulty(String val) {
        mDifficulty = val;
    }

    public String getDifficulty() {return  mDifficulty;}
}
