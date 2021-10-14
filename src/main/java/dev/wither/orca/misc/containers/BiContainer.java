package dev.wither.orca.misc.containers;

import lombok.Getter;

/**
 * Used as a container to pass multiple objects inside of a single wrapper.
 * @param <A>
 * @param <B>
 */
public class BiContainer<A, B> {

    @Getter private final A a;
    @Getter private final B b;

    public BiContainer(A a, B b) {

        this.a = a;
        this.b = b;

    }

}
