package me.ryleykimmel.brandywine.game.message;

import me.ryleykimmel.brandywine.network.message.Message;

/**
 * A {@link Message} sent by the client when the user has pressed an arrow key.
 */
public final class ArrowKeyMessage extends Message {

  /**
   * The camera roll.
   */
  private final int roll;

  /**
   * The camera yaw.
   */
  private final int yaw;

  /**
   * Creates a new arrow key message.
   *
   * @param roll The camera roll.
   * @param yaw The camera yaw.
   */
  public ArrowKeyMessage(int roll, int yaw) {
    this.roll = roll;
    this.yaw = yaw;
  }

  /**
   * Gets the roll of the camera.
   *
   * @return The roll.
   */
  public int getRoll() {
    return roll;
  }

  /**
   * Gets the yaw of the camera.
   *
   * @return The yaw.
   */
  public int getYaw() {
    return yaw;
  }

}
