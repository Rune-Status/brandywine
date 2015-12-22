package me.ryleykimmel.brandywine.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class StringsTest {

  @Test
  public void testToFirstUpper() {
    String input = "hEllo WORLd";
    String expected = "Hello world";

    assertEquals(expected, Strings.toFirstUpper(input));
  }

  @Test
  public void testToFirstUpperInternational() {
    String input = "ππππππππ";
    String expected = "π�������";

    assertEquals(expected, Strings.toFirstUpper(input));
  }

  @Test(expected = IllegalArgumentException.class)
  public void failToFirstUpperZeroLength() {
    Strings.toFirstUpper("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void failToFirstUpperNull() {
    Strings.toFirstUpper(null);
  }

}
