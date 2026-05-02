package edu.epam.fop.lambdas.calculator;

import edu.epam.fop.lambdas.insurance.Car;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

public final class CarInsurancePolicies {

  private CarInsurancePolicies() {}

  public static InsuranceCalculator<Car> ageDependInsurance(LocalDate baseDate) {
      return car -> Optional.ofNullable(car)
              .map(Car::manufactureDate)
              .map(date -> {
                  long days = java.time.temporal.ChronoUnit.DAYS.between(date, baseDate);

                  if (days <= 365) return InsuranceCoefficient.MAX;
                  if (days <= 5 * 365) return InsuranceCoefficient.of(70);
                  if (days <= 10 * 365) return InsuranceCoefficient.of(30);
                  return InsuranceCoefficient.MIN;
              });
  }

  public static InsuranceCalculator<Car> priceAndOwningOfFreshCarInsurance(LocalDate baseDate, BigInteger priceThreshold, Period owningThreshold) {
      return car -> Optional.ofNullable(car)
              .filter(c -> c.soldDate().isEmpty())
              .filter(c -> c.price() != null && c.price().compareTo(priceThreshold) >= 0)
              .filter(c -> c.purchaseDate() != null
                      && !c.purchaseDate().plus(owningThreshold).isBefore(baseDate))
              .map(c -> {
                  BigInteger price = c.price();

                  if (price.compareTo(priceThreshold.multiply(BigInteger.valueOf(3))) >= 0) {
                      return InsuranceCoefficient.MAX;
                  }
                  if (price.compareTo(priceThreshold.multiply(BigInteger.valueOf(2))) >= 0) {
                      return InsuranceCoefficient.MED;
                  }
                  return InsuranceCoefficient.MIN;
              });
  }
}