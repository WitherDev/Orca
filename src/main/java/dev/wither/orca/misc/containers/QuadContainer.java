package dev.wither.orca.misc.containers;

import lombok.Getter;

/**
 * Used as a container to pass multiple objects inside of a single wrapper.
 * @param <A>
 * @param <B>
 * @param <C>
 */
public class QuadContainer<A, B, C, D> {

    @Getter private final A a;
    @Getter private final B b;
    @Getter private final C c;
    @Getter private final D d;

    public QuadContainer(A a, B b, C c, D d) {

        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;

    }

}
