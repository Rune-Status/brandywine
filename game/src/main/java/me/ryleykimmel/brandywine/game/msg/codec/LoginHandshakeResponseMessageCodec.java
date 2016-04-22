package me.ryleykimmel.brandywine.game.msg.codec;

import me.ryleykimmel.brandywine.game.msg.LoginHandshakeResponseMessage;
import me.ryleykimmel.brandywine.network.frame.DataType;
import me.ryleykimmel.brandywine.network.frame.FrameBuilder;
import me.ryleykimmel.brandywine.network.frame.FrameReader;
import me.ryleykimmel.brandywine.network.msg.MessageCodec;

/**
 * MessageCodec for the {@link LoginHandshakeResponseMessage}.
 */
public final class LoginHandshakeResponseMessageCodec
    implements MessageCodec<LoginHandshakeResponseMessage> {

  @Override
  public void encode(LoginHandshakeResponseMessage message, FrameBuilder builder) {
    builder.put(DataType.BYTE, message.getStatus());
    builder.putBytes(message.getDummy());
    builder.put(DataType.LONG, message.getSessionKey());
  }

  @Override
  public LoginHandshakeResponseMessage decode(FrameReader frame) {
    return null;
  }

}