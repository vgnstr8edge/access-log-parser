import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;


public class Statistics {
    int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private static HashSet<String> existingPages = new HashSet<>();
    private static HashMap<String, Integer> osFrequency = new HashMap<>();

    public Statistics() {
        totalTraffic = 0;
        minTime = LocalDateTime.MAX;
        maxTime = LocalDateTime.MIN;
    }

    public static HashSet<String> getExistingPages() {
        return existingPages;
    }

    public static HashMap<String, Double> getOsStatistics() {
        HashMap<String, Double> osStatistics = new HashMap<>();
        int totalOsCount = 0;
        for (int count : osFrequency.values()) {
            totalOsCount += count;
        }
        for (String os : osFrequency.keySet()) {
            double ratio = (double) osFrequency.get(os) / totalOsCount;
            osStatistics.put(os, ratio);
        }
        return osStatistics;
    }

    public void addEntry(LogEntry entry) {
        totalTraffic += entry.getDataSize();
        if (entry.getDateTime().isBefore(minTime)) {
            minTime = entry.getDateTime();
        }
        if (entry.getDateTime().isAfter(maxTime)) {
            maxTime = entry.getDateTime();
        }
        if (entry.getResponseCode() == 200) {
            existingPages.add(entry.getPath());
        }
        String os = entry.getUserAgent().getOs();
        if (osFrequency.containsKey(os)) {
            osFrequency.put(os, osFrequency.get(os) + 1);
        } else {
            osFrequency.put(os, 1);
        }
    }

    public double getTrafficRate() {
        long timeDiff = ChronoUnit.HOURS.between(minTime, maxTime);
        return (double) totalTraffic / timeDiff;
    }

}

