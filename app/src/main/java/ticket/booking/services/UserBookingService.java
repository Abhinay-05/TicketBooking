package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;
import ticket.booking.service.TrainService;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserBookingService {
    private User user;
    private List<User> userList;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String USERS_PATH = "app/src/main/java/ticket/booking/localDB/users.json";

    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers();
    }

    public UserBookingService() throws IOException{
        loadUsers();
    }

    public List<User> loadUsers() throws IOException{
        File users = new File(USERS_PATH);
        return userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public void fetchBooking(){
        user.printTickets();
    }

    public boolean cancelBooking(String ticketID){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the ticket id to cancel");
        ticketID = s.next();

        if(ticketID == null){
            return Boolean.FALSE;
        }

        List<Ticket> tickets = this.user.getTicketsBooked();
        if(tickets == null){
            return Boolean.FALSE;
        }

        int n = tickets.size();
        for(int i = 0 ; i < n ; i++){
            if(tickets.get(i).getTicketID().equals(ticketID)){
                tickets.remove(i);
                System.out.println("Ticket with ID " + ticketID + " has been canceled.");
                return Boolean.TRUE;
            }
        }
        System.out.println("No ticket found with ID " + ticketID);
        return Boolean.FALSE;
    }

    public List<Train> getTrains(String source, String destination) {
        TrainService trainService = new TrainService();
        return trainService.searchTrains(source, destination);
    }

    public void fetchBookings(){
        Optional<User> userFetched = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        if(userFetched.isPresent()){
            userFetched.get().printTickets();
        }
    }

    public List<List<Integer>> fetchSeats(Train train){
        return train.getSeats();
    }

    public Boolean bookTrainSeat(Train train, int row, int seat) {
        TrainService trainService = new TrainService();
        List<List<Integer>> seats = train.getSeats();
        if(row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
            if(seats.get(row).get(seat) == 0) {
                seats.get(row).set(seat, 1);
                train.setSeats(seats);
                trainService.addTrain(train);
                return true; // Booking successful
            }
            else{
                return false; // Seat is already booked
            }
        }
        else{
            return false; // Invalid row or seat index
        }
    }
}
