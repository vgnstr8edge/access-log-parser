import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Statistics {
    int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    public Statistics() {
        totalTraffic = 0;
        minTime = LocalDateTime.MAX;
        maxTime = LocalDateTime.MIN;
    }

    public void addEntry(LogEntry entry) {
        totalTraffic += entry.getDataSize();
        if (entry.getDateTime().isBefore(minTime)) {
            minTime = entry.getDateTime();
        }
        if (entry.getDateTime().isAfter(maxTime)) {
            maxTime = entry.getDateTime();
        }
    }

    public double getTrafficRate() {
        long timeDiff = ChronoUnit.HOURS.between(minTime, maxTime);
        return (double) totalTraffic / timeDiff;
    }
}

