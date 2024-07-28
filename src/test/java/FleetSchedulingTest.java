import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class FleetSchedulingTest {
    @Test
    void testNoTrucks() {
        List<FleetScheduling.Truck> trucks = new ArrayList<>();
        List<FleetScheduling.Charger> chargers = Arrays.asList(new FleetScheduling.Charger(1, 20));
        int availableHours = 5;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FleetScheduling.scheduleCharging(trucks, chargers, availableHours);
        });

        assertEquals("No trucks available for charging.", exception.getMessage());
    }

    @Test
    void testNoChargers() {
        List<FleetScheduling.Truck> trucks = Arrays.asList(new FleetScheduling.Truck(1, 100, 30));
        List<FleetScheduling.Charger> chargers = new ArrayList<>();
        int availableHours = 5;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FleetScheduling.scheduleCharging(trucks, chargers, availableHours);
        });

        assertEquals("No chargers available.", exception.getMessage());
    }

    @Test
    void testNegativeAvailableHours() {
        List<FleetScheduling.Truck> trucks = Arrays.asList(new FleetScheduling.Truck(1, 100, 30));
        List<FleetScheduling.Charger> chargers = Arrays.asList(new FleetScheduling.Charger(1, 20));
        int availableHours = -1;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FleetScheduling.scheduleCharging(trucks, chargers, availableHours);
        });

        assertEquals("Available hours must be positive.", exception.getMessage());
    }

    @Test
    void testScheduling() {
        List<FleetScheduling.Truck> trucks = Arrays.asList(
                new FleetScheduling.Truck(1, 100, 30),
                new FleetScheduling.Truck(2, 100, 60),
                new FleetScheduling.Truck(3, 100, 70),
                new FleetScheduling.Truck(4, 100, 80)
        );

        List<FleetScheduling.Charger> chargers = Arrays.asList(
                new FleetScheduling.Charger(1, 20),
                new FleetScheduling.Charger(2, 10)
        );

        int availableHours = 5;

        Map<FleetScheduling.Charger, List<FleetScheduling.Truck>> schedule = FleetScheduling.scheduleCharging(trucks, chargers, availableHours);

        assertEquals(3, schedule.get(chargers.get(0)).size());
        assertEquals(0, schedule.get(chargers.get(1)).size());
    }
}
