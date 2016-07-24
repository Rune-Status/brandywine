package me.ryleykimmel.brandywine.game.msg;

import me.ryleykimmel.brandywine.network.msg.Message;

/**
 * A {@link Message} sent by the client to indicate a change in the client's focus (i.e. if it is the active window).
 */
public final class FocusUpdateMessage extends Message {

  /**
   * Indicates whether the client is focused or not.
   */
  private final boolean focused;

  /**
   * Creates a new focus update message.
   *
   * @param focused The data received.
   */
  public FocusUpdateMessage(boolean focused) {
    this.focused = focused;
  }

  /**
   * Indicates whether or not the client is focused.
   *
   * @return {@code true} if the client is focused.
   */
  public boolean isFocused() {
    return focused;
  }

}
