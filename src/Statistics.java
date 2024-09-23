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
    private static HashSet<String> nonExistingPages = new HashSet<>();
    private static HashMap<String, Integer> browserFrequency = new HashMap<>();
    private int userVisits;
    private int errorRequests;
    private static HashSet<String> uniqueUserIps = new HashSet<>();

    public Statistics() {
        totalTraffic = 0;
        minTime = LocalDateTime.MAX;
        maxTime = LocalDateTime.MIN;
        userVisits = 0;
        errorRequests = 0;
    }

    public static HashSet<String> getExistingPages() {
        return existingPages;
    }

    public static HashSet<String> getNonExistingPages() {
        return nonExistingPages;
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

    public static HashMap<String, Double> getBrowserStatistics() {
        HashMap<String, Double> browserStatistics = new HashMap<>();
        int totalBrowsers = 0;
        for (int count : browserFrequency.values()) {
            totalBrowsers += count;
        }
        for (String browser : browserFrequency.keySet()) {
            double ratio = (double) browserFrequency.get(browser) / totalBrowsers;
            browserStatistics.put(browser, ratio);
        }
        return browserStatistics;
    }

    public void addEntry(LogEntry entry) {
        String browser = entry.getUserAgent().getBrowser();
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
        if (entry.getResponseCode() == 404) {
            nonExistingPages.add(entry.getPath());
        }
        if (browserFrequency.containsKey(browser)) {
            browserFrequency.put(browser, browserFrequency.get(browser) + 1);
        } else {
            browserFrequency.put(browser, 1);
        }

        if (!entry.getUserAgent().isBot()) {
            userVisits++;
            uniqueUserIps.add(entry.getIp());
        }

        if (entry.getResponseCode() >= 400 && entry.getResponseCode() < 600) {
            errorRequests++;
        }
    }

    public double getTrafficRate() {
        long timeDiff = ChronoUnit.HOURS.between(minTime, maxTime);
        return (double) totalTraffic / timeDiff;
    }

    public double getAverageUserVisitsPerHour() {
        long timeDiff = ChronoUnit.HOURS.between(minTime, maxTime);
        return (double) userVisits / timeDiff;
    }

    public double getAverageErrorRequestsPerHour() {
        long timeDiff = ChronoUnit.HOURS.between(minTime, maxTime);
        return (double) errorRequests / timeDiff;
    }

    public double getAverageVisitsPerUser() {
        return (double) userVisits / uniqueUserIps.size();
    }

}

