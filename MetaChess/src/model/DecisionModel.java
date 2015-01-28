package model;

public class DecisionModel implements Comparable<DecisionModel> {

    //defines order in which decisions are handled in the piece
    protected int priority;
    private boolean cooldown;
    private boolean locks;
    private int key;
    private String name;

    public boolean isCooldown() {
        return cooldown;
    }

    public void setCooldown(boolean cooldown) {
        this.cooldown = cooldown;
    }

    public boolean isLocks() {
        return locks;
    }

    public void setLocks(boolean locks) {
        this.locks = locks;
    }

    @Override
    public int compareTo(DecisionModel other) {
        // compareTo should return < 0 if this is supposed to be
        // less than other, > 0 if this is supposed to be greater than 
        // other and 0 if they are supposed to be equal

        //if equal than they're deleted from set
        // so never equal
        if (priority - other.priority >= 0) {
            return 1;
        }
        return -1;
    }

    public int getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public DecisionModel(boolean cooldown, boolean locks, int priority,
            int key, String name) {
        super();
        this.priority = priority;
        this.locks = locks;
        this.cooldown = cooldown;
        this.key = key;
        this.name = name;
    }
}
