package com.epam.rd.autotasks.sprintplanning;

import com.epam.rd.autotasks.sprintplanning.tickets.Bug;
import com.epam.rd.autotasks.sprintplanning.tickets.Ticket;
import com.epam.rd.autotasks.sprintplanning.tickets.UserStory;

public class Sprint {

    private int capacity;
    private int ticketLimit;
    private Ticket[] tickets;
    private int size = 0;
    private int totalEstimate = 0;

    public Sprint(int capacity, int ticketsLimit) {
        this.capacity = capacity;
        this.ticketLimit = ticketsLimit;
        this.tickets = new Ticket[ticketsLimit];
    }

    public boolean addUserStory(UserStory userStory) {
        if(!canAddTicket(userStory)){
            return false;
        }
        for (UserStory dependency : userStory.getDependencies()) {
            if (!dependency.isCompleted()) {
                return false;
            }
        }
        addTicket(userStory);
        return true;
    }

    public boolean addBug(Bug bugReport) {
        if(!canAddTicket(bugReport)) return false;
        addTicket(bugReport);
        return true;
    }

    public Ticket[] getTickets() {
        Ticket[] copy = new Ticket[size];
        System.arraycopy(tickets, 0, copy, 0, size);
        return copy;
    }

    public int getTotalEstimate() {
        return totalEstimate;
    }

    private void addTicket(Ticket ticket) {
        tickets[size++] = ticket;
        totalEstimate += ticket.getEstimate();
    }

    private boolean canAddTicket(Ticket ticket){
        if(ticket == null) return false;
        if(ticket.isCompleted()) return false;
        if(size>=ticketLimit) return false;
        if (totalEstimate + ticket.getEstimate() > capacity) return false;
        return true;
    }

}

