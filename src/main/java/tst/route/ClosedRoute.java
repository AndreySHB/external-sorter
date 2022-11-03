package tst.route;

import java.util.List;

public class ClosedRoute {

    static class GasStation {
        final double fuelAmount;
        final double distanceToNextStation;

        public GasStation(double fuelAmount, double distanceToNextStation) {
            this.fuelAmount = fuelAmount;
            this.distanceToNextStation = distanceToNextStation;
        }
    }

    public final List<GasStation> stations;

    public ClosedRoute(List<GasStation> stations) {
        this.stations = stations;
    }

    public double getAllFuel() {
        return stations.stream()
                .map(gasStation -> gasStation.fuelAmount)
                .reduce(0d, Double::sum);
    }

    public double getDistance() {
        return stations.stream()
                .map(gasStation -> gasStation.distanceToNextStation)
                .reduce(0d, Double::sum);
    }
    /**
     * all fuel is consumed on the route
     */
    public double getFuelConsumption() {
        return getAllFuel() / getDistance();
    }

    /**
     * returned numeration starts from 1, not 0
     */
    public int getStationToStartFrom() {
        double fuelConsumption = getFuelConsumption();
        int pos = 0;
        double fuelInCar = 0;
        for (int i = 0; i < stations.size(); i++) {
            fuelInCar += stations.get(i).fuelAmount;
            double fuelToNextStation = stations.get(i).distanceToNextStation * fuelConsumption;
            if (fuelInCar < fuelToNextStation) {
                pos = i + 1;
                fuelInCar = 0;
                continue;
            }
            fuelInCar -= fuelToNextStation;
        }
        return pos + 1;
    }
}
