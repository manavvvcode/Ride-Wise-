package com.example.RideWise.ride.wise.cab.sharing.Service;

import com.example.RideWise.ride.wise.cab.sharing.Entity.Driver;
import com.example.RideWise.ride.wise.cab.sharing.Strategy.FareCalculationStrategy;
import com.example.RideWise.ride.wise.cab.sharing.Strategy.RideAllocationStrategyInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Data
public class StrategySelector {
    private final RideAllocationStrategyInterface nearestDriver;
    private final RideAllocationStrategyInterface leastActiveDriver;
    private final FareCalculationStrategy standardFare;
    private final FareCalculationStrategy peakHourFare;

    public StrategySelector(
            @Qualifier("nearestDriver") RideAllocationStrategyInterface nearestDriver,
            @Qualifier("leastActiveDriver") RideAllocationStrategyInterface leastActiveDriver,
            @Qualifier("standardFare") FareCalculationStrategy standardFare,
            @Qualifier("peakHourFare") FareCalculationStrategy peakHourFare
    ) {
        this.nearestDriver = nearestDriver;
        this.leastActiveDriver = leastActiveDriver;
        this.standardFare = standardFare;
        this.peakHourFare = peakHourFare;
    }

    public RideAllocationStrategyInterface getDriverStrategy(List<Driver> driverList) {
        if (driverList.size() < 5) {
            return leastActiveDriver;
        }
        return nearestDriver;
    }

    public FareCalculationStrategy getFareStrategy() {
        int hour = LocalDateTime.now().getHour();
        if ((hour >= 8 && hour <= 10) || (hour >= 17 && hour <= 20)) {
            return peakHourFare;
        }
        return standardFare;
    }
}
