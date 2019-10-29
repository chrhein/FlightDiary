import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import static java.util.stream.Collectors.toMap;

public class FlightDiary extends HistogramGenerator {
    private static LinkedHashMap<String, Integer> sorted;
    private static int amountToShow;

    public static void main(String[] args) throws IOException {
        String csvFile = "Extras/fd.csv";
        BufferedReader br;
        String line;
        String cvsSplitBy = ";";
        ArrayList<String> regs = new ArrayList<>();
        br = new BufferedReader(new FileReader(csvFile));
        while ((line = br.readLine()) != null) {
            String[] s = line.split(cvsSplitBy);
            String reg = s[9];
            if (!reg.equals("")) regs.add(reg);
        }
//        Scanner in = new Scanner(System.in);
//        System.out.print("How many unique registrations do you want to see? ");
//        amountToShow = in.nextInt();
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
        //printOut(sorted);
    }

    private static void printOut(Map<String, Integer> hm) {
        System.out.println("Unique registrations: " + hm.size());
        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            System.out.printf("%s: %d %s%n",
                    val.getKey(), val.getValue(), (val.getValue() == 1) ? "time" : "times");
        }
    }

    Map<String, Integer> getRegistrations() {
        return sorted;
    }

    int getAmountToShow() {
        return amountToShow;
    }
}
