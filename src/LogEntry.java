import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class LogEntry {
    private final String ip;
    private final LocalDateTime dateTime;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int dataSize;
    private final String referer;
    private final UserAgent userAgent;

    public LogEntry(String logString) {

        String[] parts = logString.split(" ");
        ip = parts[0];
        dateTime = LocalDateTime.parse(parts[3].substring(1) + " " + parts[4].substring(0, parts[4].indexOf("]")), DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH));
        method = HttpMethod.valueOf(parts[5].replaceAll("\"", ""));
        path = parts[6] + " " + parts[7];
        responseCode = Integer.parseInt(parts[8]);
        dataSize = Integer.parseInt(parts[9]);
        referer = parts[10].substring(1, parts[10].indexOf("\"", 2));
        userAgent = new UserAgent(parts[11].replaceAll("\"", ""));
    }

    public String getIp() {
        return ip;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getDataSize() {
        return dataSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }


    public enum HttpMethod {
        GET, POST, PUT, DELETE, HEAD, OPTIONS, PATCH
    }
}


