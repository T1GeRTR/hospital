package net.thumbtack.school.hospital.response.statictics;

public class StatisticsAppointments {
    private int all;
    private int ticket;
    private int busy;
    private int free;

    public StatisticsAppointments(int all, int ticket, int busy, int free) {
        this.all = all;
        this.ticket = ticket;
        this.busy = busy;
        this.free = free;
    }

    public StatisticsAppointments() {
    }

    public void setAll(int all) {
        this.all = all;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public void setBusy(int busy) {
        this.busy = busy;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public int getAll() {
        return all;
    }

    public int getTicket() {
        return ticket;
    }

    public int getBusy() {
        return busy;
    }

    public int getFree() {
        return free;
    }
}
