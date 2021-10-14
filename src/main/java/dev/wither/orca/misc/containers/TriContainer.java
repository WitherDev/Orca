package dev.wither.orca.misc.containers;

import lombok.Getter;

/**
 * Used as a container to pass multiple objects inside of a single wrapper.
 * @param <A>
 * @param <B>
 * @param <C>
 */
public class TriContainer<A, B, C> {

    @Getter private final A a;
    @Getter private final B b;
    @Getter private final C c;

    public TriContainer(A a, B b, C c) {

        this.a = a;
        this.b = b;
        this.c = c;

    }

}
