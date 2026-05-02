package edu.epam.fop.lambdas;

import java.util.function.DoubleFunction;

public interface PercentageFormatter {
  DoubleFunction<String> INSTANCE = d -> ((Math.round(d * 1000.0) / 10.0) % 1 == 0)
          ? String.format("%d %%", (long)(Math.round(d * 1000.0) / 10.0))
          : String.format("%.1f %%", Math.round(d * 1000.0) / 10.0);
}