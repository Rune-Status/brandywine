package me.ryleykimmel.brandywine.game.msg.event;

import me.ryleykimmel.brandywine.game.event.EventConsumer;
import me.ryleykimmel.brandywine.game.login.LoginSession;
import me.ryleykimmel.brandywine.game.msg.LoginHandshakeMessage;
import me.ryleykimmel.brandywine.game.msg.LoginHandshakeResponseMessage;

/**
 * Handles the {@link LoginHandshakeMessage}.
 */
public final class LoginHandshakeMessageConsumer implements EventConsumer<LoginHandshakeMessage> {

  /**
   * The GenericSession which intercepted this MessageReceivedEvent.
   */
  private final LoginSession session;

  /**
   * Constructs a new {@link LoginHandshakeMessageConsumer}.
   * 
   * @param session The GenericSession which intercepted this MessageReceivedEvent.
   */
  public LoginHandshakeMessageConsumer(LoginSession session) {
    this.session = session;
  }

  /**
   * The status which indicates that the server is ready to exchange data with the client.
   */
  private static final int STATUS_EXCHANGE_DATA = 0;

  /**
   * The 8 dummy bytes the client expects to initiate login.
   */
  private static final byte[] EXPECTED_DUMMY = {0, 0, 0, 0, 0, 0, 0, 0};

  @Override
  public void accept(LoginHandshakeMessage event) {
    session.voidWriteAndFlush(new LoginHandshakeResponseMessage(STATUS_EXCHANGE_DATA,
        EXPECTED_DUMMY, session.getSessionKey()));
  }

}