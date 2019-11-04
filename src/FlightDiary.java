import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import static java.util.stream.Collectors.toMap;

public class FlightDiary extends HistogramGenerator {
    private static LinkedHashMap<String, Integer> sorted;
    private static int amountToShow;
    private static String category;

    public static void main(String[] args) throws IOException {
        String csvFile = "assets/fd.csv";
        BufferedReader br;
        String line;
        String csvSplitBy = ",";
        int n = categoryChooser();
        ArrayList<String> regs = new ArrayList<>();
        br = new BufferedReader(new FileReader(csvFile));
        boolean skipFirstLine = true;
        while ((line = br.readLine()) != null) {
            String[] s = line.split(csvSplitBy);
            String reg = s[n];
            if (!reg.equals("") && !skipFirstLine) regs.add(reg);
            skipFirstLine = false;
        }
        amountToShow = 10;
        countFrequencies(regs);
        launch(args);
    }

    private static void countFrequencies(ArrayList<String> list) {
        Map<String, Integer> hm = new HashMap<>();
        for (String i : list) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }
        sorted = hm
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        printOut(sorted);
    }

    private static void printOut(Map<String, Integer> hm) {
        String s = category.toLowerCase();
        System.out.println("Unique " + s + "s: " + hm.size());
        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            System.out.printf("%s: %d %s%n",
                    val.getKey(), val.getValue(), (val.getValue() == 1) ? "time" : "times");
        }
    }

    private static int categoryChooser() {
        Scanner in = new Scanner(System.in);
        category = in.nextLine();
        int n;
        switch (category) {
            case "Date":
                n = 0;
                break;
            case "Flight number":
                n = 1;
                break;
            case "Airline":
                n = 7;
                break;
            case "Seat":
                n = 10;
                break;
            default:
                category = "Registration";
                n = 9;
                break;
        }
        return n;
    }

    Map<String, Integer> getRegistrations() {
        return sorted;
    }

    int getAmountToShow() {
        return amountToShow;
    }

    String getCategory() {
        return category;
    }
}
