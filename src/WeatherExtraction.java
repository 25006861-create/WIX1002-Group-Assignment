public class WeatherExtraction {

    public static String getTodayWeather(String locationEncoded) throws Exception {
        API api = new API();

        String url =
            "https://api.data.gov.my/weather/forecast/"
            + "?contains=" + locationEncoded
            + "@location__location_name"
            + "&sort=date&limit=1";

        String response = api.get(url);

        String summmaryForecast = extractSummaryForecast(response);

        return mapWeather(summmaryForecast);
    }

    private static String extractSummaryForecast(String json) {
        String key = "\"summary_forecast\":\"";
        int start = json.indexOf(key) + key.length();
        int end = json.indexOf("\"", start);

        if (start < key.length() || end == -1) {
            return "Unknown";
        }

        return json.substring(start, end);
    }

    private static String mapWeather (String summary) {
        if (summary == null) {
            return "Unknown";
        }

        summary = summary.toLowerCase();   

        if (summary.contains("ribut petir")) {
            return "Stormy";
        } else if (summary.contains("tiada hujan")) {
            return "Sunny";
        } else if (summary.contains("hujan")) {
            return "Rainy";
        } else if (summary.contains("berjerebu")) {
            return "Hazy";
        } else {
            return "Unknown";
        }
    }

}
