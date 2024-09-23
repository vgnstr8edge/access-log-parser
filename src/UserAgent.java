public class UserAgent {
    private final String os;
    private final String browser;

    public UserAgent(String userAgentString) {

        os = getOs(userAgentString);
        browser = getBrowser(userAgentString);
    }

    private String getOs(String userAgentString) {
        if (userAgentString.contains("Windows")) {
            return "Windows";
        } else if (userAgentString.contains("Mac OS X") || userAgentString.contains("macOS")) {
            return "macOS";
        } else if (userAgentString.contains("Linux")) {
            return "Linux";
        } else {
            return "Другая";
        }
    }

    private String getBrowser(String userAgentString) {
        if (userAgentString.contains("Edge")) {
            return "Edge";
        } else if (userAgentString.contains("Firefox")) {
            return "Firefox";
        } else if (userAgentString.contains("Chrome")) {
            return "Chrome";
        } else if (userAgentString.contains("Opera")) {
            return "Opera";
        } else {
            return "Другой";
        }
    }

    public String getOs() {
        return os;
    }

    public String getBrowser() {
        return browser;
    }

    public boolean isBot() {
        return browser.toLowerCase().contains("bot");
    }
}
