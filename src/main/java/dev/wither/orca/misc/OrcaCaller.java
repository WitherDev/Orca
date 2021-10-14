package dev.wither.orca.misc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OrcaCaller {

    /**
     * Invoke a method while automatically giving in the correct parameters, in the correct order.
     *
     * Solves the problem of potentially not knowing the exact parameter needs of a method, but knowing the general intended values that the method should accept.
     * This method will iterate through each parameter, determine the class, and insert an Object with the matching class.
     * When no parameter of a matching class is found, null will be provided instead.
     *
     * @param method the method to be invoked.
     * @param target the object upon which the method will be invoked.
     * @param params the potential parameter values to be fed into the method invocation.
     */
    public static void call(Method method, Object target, Object... params) {

        Object[] args = new Object[method.getParameterCount()];

        // Loop through each parameter
        one:
        for (int i = 0; i != args.length; i++) {

            // Loop through each provided potential parameter to determine the needed one.
            for (Object o : params) {

                if (o.getClass().equals(method.getParameterTypes()[i]) || o.getClass().isAssignableFrom(method.getParameterTypes()[i]) || method.getParameterTypes()[i].isAssignableFrom(o.getClass())) {
                    args[i] = o;
                    continue one;

                }

            }

            args[i] = null;

        }

        try {
            method.setAccessible(true);
            method.invoke(target, args);
            method.setAccessible(false);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * Equivalent to the #call method above, but the task of calling the method is run async through the BukkitScheduler.
     *
     * @param plugin plugin calling this method.
     * @param method the method to be invoked.
     * @param target the object upon which the method will be invoked.
     * @param params the potential parameter values to be fed into the method invocation.
     */
    public static void callAsync(JavaPlugin plugin, Method method, Object target, Object... params) {

        Object[] args = new Object[method.getParameterCount()];

        for (int i = 0; i != args.length; i++) {

            for (Object o : params) {

                if (o.getClass() == method.getParameterTypes()[i]) {

                    args[i] = o;
                    break;

                }

            }

            args[i] = null;

        }

        method.setAccessible(true);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, ()-> {

            try {
                method.invoke(target, args);
            } catch (IllegalAccessException | InvocationTargetException exception) {
                exception.printStackTrace();
            }

        });
        method.setAccessible(false);

    }

}
