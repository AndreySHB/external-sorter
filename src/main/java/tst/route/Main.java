package tst.route;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<ClosedRoute.GasStation> stations = new ArrayList<>();
        stations.add(new ClosedRoute.GasStation(8, 5));
        stations.add(new ClosedRoute.GasStation(12, 20));
        stations.add(new ClosedRoute.GasStation(10, 5));//true
        stations.add(new ClosedRoute.GasStation(12, 10));
        stations.add(new ClosedRoute.GasStation(8, 10));
        ClosedRoute closedRoute = new ClosedRoute(stations);
        System.out.printf("Total fuel: %s%n", closedRoute.getAllFuel());
        System.out.printf("Total distance: %s%n", closedRoute.getDistance());
        System.out.printf("Fuel consumption: %s%n", closedRoute.getFuelConsumption());
        System.out.printf("Station to start from: %s%n", closedRoute.getStationToStartFrom());
    }
}
