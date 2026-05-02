package edu.epam.fop.lambdas.calculator;

import edu.epam.fop.lambdas.insurance.*;

import java.math.BigInteger;
import java.time.Period;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

public final class PersonInsurancePolicies {

  private PersonInsurancePolicies() {}

  public static InsuranceCalculator<Person> childrenDependent(int threshold) {
    return p -> Optional.ofNullable(p)
            .flatMap(Person::family)
            .map(Family::children)
            .filter(c -> c != null && !c.isEmpty())
            .map(c -> {
              int value = (c.size() * 100) / threshold;
              return InsuranceCoefficient.of(Math.min(100, value));
            })
            .or(() -> Optional.of(InsuranceCoefficient.MIN));
  }

  public static InsuranceCalculator<Person> employmentDependentInsurance(BigInteger salaryThreshold, Set<Currency> currencies) {
      return p -> Optional.ofNullable(p)
            .filter(person -> person.employmentHistory() != null
                    && person.employmentHistory().size() >= 4)
            .filter(person -> person.account() != null
                    && person.account().size() > 1)
            .filter(person -> person.injuries() == null || person.injuries().isEmpty())
            .filter(person -> person.accommodations() != null
                    && !person.accommodations().isEmpty())
            .flatMap(person -> person.employmentHistory().stream().max(Comparator.naturalOrder()))
            .filter(emp -> emp.endDate().isEmpty())
            .flatMap(emp -> emp.salary())
            .filter(s -> s.amount() != null
                    && s.amount().compareTo(salaryThreshold) >= 0
                    && currencies.contains(s.currency()))
            .map(__ -> InsuranceCoefficient.MED);
  }

  public static InsuranceCalculator<Person> accommodationEmergencyInsurance(Set<Accommodation.EmergencyStatus> statuses) {
    return p -> Optional.ofNullable(p)
            .filter(person -> person.accommodations() != null
                    && !person.accommodations().isEmpty())
            .flatMap(person -> person.accommodations().stream().min(Comparator.naturalOrder()))
            .flatMap(Accommodation::emergencyStatus)
            .filter(statuses::contains)
            .map(status -> {
              int total = Accommodation.EmergencyStatus.values().length;
              int value = (int) (100 * (1.0 - ((double) status.ordinal() / total)));
              return InsuranceCoefficient.of(value);
            });
  }

  public static InsuranceCalculator<Person> injuryAndRentDependentInsurance(BigInteger rentThreshold) {
    return p -> Optional.ofNullable(p)
            .filter(person -> person.injuries() != null && !person.injuries().isEmpty())
            .flatMap(person -> person.injuries().stream().max(Comparator.naturalOrder()))
            .flatMap(injury -> injury.culprit()
                    .filter(c -> c == p)
                    .map(__ -> p))
            .filter(person -> person.accommodations() != null
                    && !person.accommodations().isEmpty())
            .flatMap(person -> person.accommodations().stream().max(Comparator.naturalOrder()))
            .flatMap(Accommodation::rent)
            .filter(r -> r.currency() == Currency.GBP && r.amount() != null)
            .map(r -> {
              BigInteger percent = r.amount().multiply(BigInteger.valueOf(100)).divide(rentThreshold);
              if (percent.compareTo(BigInteger.valueOf(100)) >= 0) {
                return InsuranceCoefficient.MAX;
              }
              return InsuranceCoefficient.of(percent);
            });
  }
}