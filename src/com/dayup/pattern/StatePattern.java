package com.dayup.pattern;

enum StateEnum {
    HAPPY, SAD, NORMAL;
}

interface Actor {
    void showActor();
}

class HappyActor implements Actor{

    @Override
    public void showActor() {
        System.out.println("I'm happy");
    }
}

class SadActor implements Actor{

    @Override
    public void showActor() {
        System.out.println("I'm sad");
    }
}

class NormalActor implements Actor{

    @Override
    public void showActor() {
        System.out.println("I'm normal");
    }
}

class StateActor {
    private StateEnum stateEnum = StateEnum.HAPPY;
    public void setStateEnum(StateEnum stateEnum) {
        this.stateEnum = stateEnum;
    }
    public void Actor() {
        System.out.println("current state is " + stateEnum);
        switch (stateEnum) {
            case HAPPY:
                new HappyActor().showActor();
                break;
            case NORMAL:
                new NormalActor().showActor();
                break;
            case SAD:
                new SadActor().showActor();
                break;
        }
    }
}

public class StatePattern {
    public static void test() {
        StateActor stateActor = new StateActor();
        stateActor.Actor();
        stateActor.setStateEnum(StateEnum.SAD);
        stateActor.Actor();
        stateActor.setStateEnum(StateEnum.NORMAL);
        stateActor.Actor();
    }
}
