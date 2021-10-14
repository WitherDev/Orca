package dev.wither.orca.cmd.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) @Target({ElementType.TYPE, ElementType.METHOD})
public @interface Command {

    /**
     * Command name used to run the command.
     */
    String name();

    /**
     * Permission value that must be met by a sender.
     */
    String perm() default "none";

    /**
     * Other values that trigger this command.
     */
    String[] aliases() default {};

    /**
     * Whether a Player can run the command.
     */
    boolean player() default true;

    /**
     * Whether a non-Player can run the command.
     */
    boolean console() default true;

    /**
     * Whether the command is enabled.
     */
    boolean enabled() default true;

    /**
     * When true, the command will be completely ignored and skipped in registering.
     */
    boolean ignore() default false;

    /**
     * Whether the method for the command should be run asynchronously.
     * Remember you may incur errors when running some code async.
     */
    boolean async() default false;

    /**
     * The description that will be applied to the command.
     */
    String description() default "none";

}
