import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import static java.util.stream.Collectors.toMap;

public class FlightDiary {
    public static void main(String[] args) throws IOException {
        String csvFile = "fd.csv";
        BufferedReader br;
        String line;
        String cvsSplitBy = ";";
        ArrayList<String> regs = new ArrayList<>();
        br = new BufferedReader(new FileReader(csvFile));
        while ((line = br.readLine()) != null) {
            String[] s = line.split(cvsSplitBy);
            if(!s[9].equals("")) regs.add(s[9]);
        }
        countFrequencies(regs);
    }

    private static void countFrequencies(ArrayList<String> list) {
        Map<String, Integer> hm = new HashMap<>();
        for (String i : list) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }
        System.out.println("Antall forskjellige regs: "+hm.size());
        Map<String, Integer> sorted = hm
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        for (Map.Entry<String, Integer> val : sorted.entrySet()) {
            System.out.printf("%s forekommer: %d %s%n",
                    val.getKey(), val.getValue(), (val.getValue() == 1) ? "gang" : "ganger");
        }
    }
}
