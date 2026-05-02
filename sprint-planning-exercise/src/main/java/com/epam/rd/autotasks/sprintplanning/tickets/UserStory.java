package com.epam.rd.autotasks.sprintplanning.tickets;

public class UserStory extends Ticket {
    private UserStory[] dependencies;

    public UserStory(int id, String name, int estimate, UserStory... dependsOn) {
        super(id, name, estimate);
        if(dependsOn == null){
            this.dependencies = new UserStory[0];
        }
        else {
            this.dependencies = new UserStory[dependsOn.length];
            System.arraycopy(dependsOn, 0, this.dependencies, 0, dependsOn.length);
        }
    }

    @Override
    public void complete() {
        for(UserStory dep : dependencies){
            if(!dep.isCompleted()){
                return;
            }
        }
        super.complete();
    }

    public UserStory[] getDependencies() {
        UserStory[] copy = new UserStory[dependencies.length];
        System.arraycopy(dependencies, 0, copy, 0, dependencies.length);
        return copy;
    }

    @Override
    public String toString() {
        return "[US " + getId() + "] " + getName();
    }
}
