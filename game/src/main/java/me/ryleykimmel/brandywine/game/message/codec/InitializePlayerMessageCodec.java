package me.ryleykimmel.brandywine.game.message.codec;

import me.ryleykimmel.brandywine.game.message.InitializePlayerMessage;
import me.ryleykimmel.brandywine.network.frame.DataOrder;
import me.ryleykimmel.brandywine.network.frame.DataTransformation;
import me.ryleykimmel.brandywine.network.frame.DataType;
import me.ryleykimmel.brandywine.network.frame.FrameBuilder;
import me.ryleykimmel.brandywine.network.message.MessageCodec;

/**
 * MessageCodec for the {@link InitializePlayerMessage}.
 */
public final class InitializePlayerMessageCodec extends MessageCodec<InitializePlayerMessage> {

  @Override
  public void encode(InitializePlayerMessage message, FrameBuilder builder) {
    builder.put(DataType.BYTE, DataTransformation.ADD, message.isMember() ? 1 : 0);
    builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, message.getIndex());
  }

}
