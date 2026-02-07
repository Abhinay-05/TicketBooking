package ticket.booking.entities;

import java.sql.Time;
import java.util.List;
import java.util.Map;

public class Ticket {
    private String trainID;
    private String trainNo;
    private List<List<Integer>> seats;
    private Map<String, Time> stationTime;
    private List<String> stations;

}
