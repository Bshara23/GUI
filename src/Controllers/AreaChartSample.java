package Controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class AreaChartSample extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Area Chart Sample");
		final NumberAxis xAxis = new NumberAxis(1, 31, 1);
		final NumberAxis yAxis = new NumberAxis();
		final AreaChart<Integer, Integer> ac = new AreaChart(xAxis, yAxis);
		ac.setTitle("Temperature Monitoring (in Degrees C)");

		XYChart.Series<Integer, Integer> seriesApril = new XYChart.Series<Integer, Integer>();
		seriesApril.setName("April");
		seriesApril.getData().add(new XYChart.Data<Integer, Integer>(1, 4));
		seriesApril.getData().add(new XYChart.Data<Integer, Integer>(3, 10));
		seriesApril.getData().add(new XYChart.Data<Integer, Integer>(6, 15));
		seriesApril.getData().add(new XYChart.Data<Integer, Integer>(9, 8));
		seriesApril.getData().add(new XYChart.Data<Integer, Integer>(12, 5));
		seriesApril.getData().add(new XYChart.Data<Integer, Integer>(15, 18));
		seriesApril.getData().add(new XYChart.Data<Integer, Integer>(18, 15));
		seriesApril.getData().add(new XYChart.Data<Integer, Integer>(21, 13));
		seriesApril.getData().add(new XYChart.Data<Integer, Integer>(24, 19));
		seriesApril.getData().add(new XYChart.Data<Integer, Integer>(27, 21));
		seriesApril.getData().add(new XYChart.Data<Integer, Integer>(30, 21));

		XYChart.Series<Integer, Integer> seriesMay = new XYChart.Series<Integer, Integer>();
		seriesMay.setName("May");
		seriesMay.getData().add(new XYChart.Data<Integer, Integer>(1, 20));
		seriesMay.getData().add(new XYChart.Data<Integer, Integer>(3, 15));
		seriesMay.getData().add(new XYChart.Data<Integer, Integer>(6, 13));
		seriesMay.getData().add(new XYChart.Data<Integer, Integer>(9, 12));
		seriesMay.getData().add(new XYChart.Data<Integer, Integer>(12, 14));
		seriesMay.getData().add(new XYChart.Data<Integer, Integer>(15, 18));
		seriesMay.getData().add(new XYChart.Data<Integer, Integer>(18, 25));
		seriesMay.getData().add(new XYChart.Data<Integer, Integer>(21, 25));
		seriesMay.getData().add(new XYChart.Data<Integer, Integer>(24, 23));
		seriesMay.getData().add(new XYChart.Data<Integer, Integer>(27, 26));
		seriesMay.getData().add(new XYChart.Data<Integer, Integer>(31, 26));

		Scene scene = new Scene(ac, 800, 600);
		ac.getData().addAll(seriesApril, seriesMay);
		stage.setScene(scene);
		stage.show();

		for (XYChart.Data<Integer, Integer> d : seriesApril.getData()) {
			Tooltip.install(d.getNode(),
					new Tooltip(d.getXValue().toString() + "\n" + "Number Of Events : " + d.getYValue()));

			// Adding class on hover
			d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

			// Removing class on exit
			d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}