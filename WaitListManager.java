package flightreservation;

import java.util.HashMap;
import java.util.Map;

public class WaitListManager {
    private Map<String, PriorityQueue<WaitlistEntry>> flightWaitlists;
    
    public WaitListManager() {
        this.flightWaitlists = new HashMap<>();
    }
    
    public void addToWaitlist(WaitlistEntry entry) {
        String flightId = entry.getFlight().getFlightId();
        
        if (!flightWaitlists.containsKey(flightId)) {
            // Create new priority queue for this flight
            flightWaitlists.put(flightId, new PriorityQueue<>((e1, e2) -> {
                // Compare by priority (higher first) and then by request time (earlier first)
                if (e1.getPriority() != e2.getPriority()) {
                    return e2.getPriority() - e1.getPriority();
                }
                return e1.getRequestTime().compareTo(e2.getRequestTime());
            }));
        }
        
        flightWaitlists.get(flightId).add(entry);
    }
    
    public WaitlistEntry getNextInWaitlist(String flightId) {
        if (flightWaitlists.containsKey(flightId) && !flightWaitlists.get(flightId).isEmpty()) {
            return flightWaitlists.get(flightId).poll();
        }
        return null;
    }
    
    public int getWaitlistSize(String flightId) {
        if (flightWaitlists.containsKey(flightId)) {
            return flightWaitlists.get(flightId).size();
        }
        return 0;
    }
    
    public void removeFromWaitlist(User user, String flightId) {
        if (flightWaitlists.containsKey(flightId)) {
            PriorityQueue<WaitlistEntry> currentQueue = flightWaitlists.get(flightId);
            PriorityQueue<WaitlistEntry> newQueue = new PriorityQueue<>((e1, e2) -> {
                if (e1.getPriority() != e2.getPriority()) {
                    return e2.getPriority() - e1.getPriority();
                }
                return e1.getRequestTime().compareTo(e2.getRequestTime());
            });
            
            // Transfer all entries except the one to remove
            while (!currentQueue.isEmpty()) {
                WaitlistEntry entry = currentQueue.poll();
                if (!entry.getUser().getUserId().equals(user.getUserId())) {
                    newQueue.add(entry);
                }
            }
            
            flightWaitlists.put(flightId, newQueue);
        }
    }
}