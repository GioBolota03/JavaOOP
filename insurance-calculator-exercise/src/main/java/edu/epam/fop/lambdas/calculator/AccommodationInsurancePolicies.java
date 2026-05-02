package edu.epam.fop.lambdas.calculator;

import edu.epam.fop.lambdas.insurance.Accommodation;
import edu.epam.fop.lambdas.insurance.Currency;

import java.math.BigInteger;
import java.time.Period;
import java.util.Optional;

public final class AccommodationInsurancePolicies {

  private AccommodationInsurancePolicies() {}

  static InsuranceCalculator<Accommodation> rentDependentInsurance(BigInteger divider) {
    return acc -> Optional.ofNullable(acc)
            .flatMap(Accommodation::rent)
            .filter(r -> r != null
                    && r.amount() != null
                    && r.amount().compareTo(BigInteger.ZERO) > 0
                    && r.currency() == Currency.USD
                    && r.unit() != null
                    && r.unit().getMonths() > 0).map(r -> {
              BigInteger percent = r.amount().multiply(BigInteger.valueOf(100)).divide(divider);
              if (percent.compareTo(BigInteger.valueOf(100)) >= 0) {
                return InsuranceCoefficient.MAX;
              }
              return InsuranceCoefficient.of(percent);
            });
  }

  static InsuranceCalculator<Accommodation> priceAndRoomsAndAreaDependentInsurance(BigInteger priceThreshold, int roomsThreshold, BigInteger areaThreshold) {
      return acc -> Optional.ofNullable(acc)
              .map(a -> {
                  if (a.price() != null
                          && a.rooms() != null
                          && a.area() != null
                          && a.price().compareTo(priceThreshold) >= 0
                          && a.rooms() >= roomsThreshold
                          && a.area().compareTo(areaThreshold) >= 0) {
                      return InsuranceCoefficient.MAX;
                  }
                  return InsuranceCoefficient.MIN;
              }).or(() -> Optional.of(InsuranceCoefficient.MIN));
  }
}
