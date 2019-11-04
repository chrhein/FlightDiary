import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;

public class HistogramGenerator extends Application {
    @Override public void start(Stage stage) {
        FlightDiary fd = new FlightDiary();
        int n = Math.min(fd.getAmountToShow(), 25);
        List<String> keys = fd.getRegistrations().keySet().stream()
                .limit(n).collect(Collectors.toList());
        List<Integer> values = fd.getRegistrations().values().stream()
                .limit(n).collect(Collectors.toList());
        stage.setTitle("Flight Diary");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis(0, values.get(0), 1);
        final BarChart<String,Number> bc =
                new BarChart<>(xAxis,yAxis);
        bc.setTitle("Flight Diary");
        xAxis.setLabel(fd.getCategory());
        yAxis.setLabel("Times flown");
        yAxis.setMinorTickVisible(false);
        bc.setLegendVisible(false);
        XYChart.Series topRegistrations = new XYChart.Series();
        for(int i = 0; i < n; i++) {
            topRegistrations.getData().add(new XYChart.Data(keys.get(i), values.get(i)));
        }
        Scene scene  = new Scene(bc,1200,1000);
        bc.getData().addAll(topRegistrations);
        stage.setScene(scene);
        stage.show();
    }
}