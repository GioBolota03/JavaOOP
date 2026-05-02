package edu.epam.fop.lambdas;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Throwable> {

    R apply(T arg) throws E;

    static <T, R, E extends Throwable> Function<T, R> quiet(ThrowingFunction<T, R, E> function) {
        if (function == null) {
            return null;
        }
        return new Function<T, R>() {
            @Override
            public R apply(T arg) {
                try {
                    return function.apply(arg);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}