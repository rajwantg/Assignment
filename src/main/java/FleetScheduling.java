import java.util.*;

public class FleetScheduling {
    static class Truck {
        int id;
        double batteryCapacity;
        double currentCharge;

        public Truck(int id, double batteryCapacity, double currentCharge) {
            this.id = id;
            this.batteryCapacity = batteryCapacity;
            this.currentCharge = currentCharge;
        }

        public double getRemainingCharge() {
            return batteryCapacity - currentCharge;
        }
    }

    static class Charger {
        int id;
        double chargingRate;

        public Charger(int id, double chargingRate) {
            this.id = id;
            this.chargingRate = chargingRate;
        }
    }

    public static void main(String[] args) {
        List<Truck> trucks = Arrays.asList(
                new Truck(1, 100, 30),
                new Truck(2, 100, 60),
                new Truck(3, 100, 70),
                new Truck(4, 100, 80)
        );

        List<Charger> chargers = Arrays.asList(
                new Charger(1, 20),
                new Charger(2, 10)
        );

        int availableHours = 5;

        try {
            Map<Charger, List<Truck>> schedule = scheduleCharging(trucks, chargers, availableHours);

            for (Map.Entry<Charger, List<Truck>> entry : schedule.entrySet()) {
                System.out.print("Charger " + entry.getKey().id + ": ");
                for (Truck truck : entry.getValue()) {
                    System.out.print(truck.id + " ");
                }
                System.out.println();
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Map<Charger, List<Truck>> scheduleCharging(List<Truck> trucks, List<Charger> chargers, int availableHours) {
        if (trucks == null || trucks.isEmpty()) {
            throw new IllegalArgumentException("No trucks available for charging.");
        }
        if (chargers == null || chargers.isEmpty()) {
            throw new IllegalArgumentException("No chargers available.");
        }
        if (availableHours <= 0) {
            throw new IllegalArgumentException("Available hours must be positive.");
        }

        trucks.sort(Comparator.comparingDouble(Truck::getRemainingCharge));

        Map<Charger, List<Truck>> schedule = new HashMap<>();
        for (Charger charger : chargers) {
            schedule.put(charger, new ArrayList<>());
        }

        for (Truck truck : trucks) {
            boolean charged = false;
            for (Charger charger : chargers) {
                double timeRequired = truck.getRemainingCharge() / charger.chargingRate;
                if (timeRequired <= availableHours) {
                    schedule.get(charger).add(truck);
                    availableHours -= timeRequired;
                    charged = true;
                    break;
                }
            }
            if (!charged) {
                System.out.println("Truck " + truck.id + " cannot be fully charged within the available hours.");
            }
        }
        return schedule;
    }
}
