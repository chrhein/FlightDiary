import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class FlightDiary extends Application {
    private static LinkedHashMap<String, Integer> sorted;

    public static void main(String[] args) throws IOException {
        String csvFile = "fd.csv";
        BufferedReader br;
        String line;
        String cvsSplitBy = ";";
        ArrayList<String> regs = new ArrayList<>();
        br = new BufferedReader(new FileReader(csvFile));
        while ((line = br.readLine()) != null) {
            String[] s = line.split(cvsSplitBy);
            String reg = s[9];
            if(!reg.equals("")) regs.add(reg);
        }
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
        System.out.println("Unique registrations: "+hm.size());
        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            System.out.printf("%s: %d %s%n",
                    val.getKey(), val.getValue(), (val.getValue() == 1) ? "time" : "times");
        }
    }

    public Map<String, Integer> getRegistrations() {
        return sorted;
    }

    @Override public void start(Stage stage) {
        List<String> keys = sorted.keySet().stream()
                .limit(10)
                .collect(Collectors.toList());
        List<Integer> values = sorted.values().stream()
                .limit(10)
                .collect(Collectors.toList());
        stage.setTitle("Flight Diary");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Flight Diary");
        xAxis.setLabel("Registration");
        yAxis.setLabel("Times flown");

        XYChart.Series topRegistrations = new XYChart.Series();
        for(int i = 0; i < 10; i++) {
            topRegistrations.getData().add(new XYChart.Data(keys.get(i), values.get(i)));
        }
        Scene scene  = new Scene(bc,1200,1000);
        bc.getData().addAll(topRegistrations);
        stage.setScene(scene);
        stage.show();
    }

}
