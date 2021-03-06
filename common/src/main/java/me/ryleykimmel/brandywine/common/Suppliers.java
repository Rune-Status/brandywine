package me.ryleykimmel.brandywine.common;

import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A static-utility class containing helper methods for supplying objects.
 */
public final class Suppliers {

  /**
   * Sole private constructor to discourage instantiation of this class.
   */
  private Suppliers() {
  }

  /**
   * Supplies a {@link Collection} to a {@link Collector}, ideal for supplying custom collections to {@link Stream#collect}
   *
   * @param collection The Collection to supply.
   * @return a Collector which collects all the input elements into a Collection, in encounter order.
   */
  public static <T, C extends Collection<T>> Collector<T, ?, C> collection(C collection) {
    return Collectors.toCollection(() -> collection);
  }

}
