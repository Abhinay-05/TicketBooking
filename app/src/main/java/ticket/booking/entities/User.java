package ticket.booking.entities;

import java.util.List;

public class User {

    private String name;
    private String password;
    private String hashPassword;
    private List<Ticket> ticketsBooked;
    private String userID;

    public User(){
    }

    public User(String name, String userID, String password, String hashPassword, List<Ticket> ticketsBooked){
        this.name = name;
        this.userID = userID;
        this.password = password;
        this.hashPassword = hashPassword;
        this.ticketsBooked = ticketsBooked;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public String getHashedPassword(){
        return hashPassword;
    }

    public String getUserID(){
        return userID;
    }

    public List<Ticket> getTicketsBooked() {
        return ticketsBooked;
    }

    public void printTickets(){
        int n = ticketsBooked.size();
        for (int i = 0; i < n; i++) {
            System.out.println(ticketsBooked.get(i).getTicketInfo());
        }
    }

    public void setName(String name){
        this.name = name;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setHashPassword(String hashPassword){
        this.hashPassword = hashPassword;
    }

    public void setTicketsBooked(List<Ticket> ticketsBooked){
        this.ticketsBooked = ticketsBooked;
    }

}
