package me.ryleykimmel.brandywine.network.msg.codec;

import me.ryleykimmel.brandywine.game.update.PlayerDescriptor;
import me.ryleykimmel.brandywine.network.game.frame.Frame;
import me.ryleykimmel.brandywine.network.game.frame.FrameBuilder;
import me.ryleykimmel.brandywine.network.game.frame.FrameType;
import me.ryleykimmel.brandywine.network.msg.Encodes;
import me.ryleykimmel.brandywine.network.msg.MessageEncoder;
import me.ryleykimmel.brandywine.network.msg.impl.PlayerUpdateMessage;

/**
 * Encodes the {@link PlayerUpdateMessage}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@Encodes(PlayerUpdateMessage.class)
public final class PlayerUpdateMessageEncoder implements MessageEncoder<PlayerUpdateMessage> {

	@Override
	public Frame encode(PlayerUpdateMessage message) {
		FrameBuilder builder = new FrameBuilder(81, FrameType.VARIABLE_SHORT);
		FrameBuilder blockBuilder = new FrameBuilder();
		builder.switchToBitAccess();

		message.getDescriptor().encode(message, builder, blockBuilder);
		builder.putBits(8, message.getLocalPlayerCount());

		for (PlayerDescriptor descriptor : message.getDescriptors()) {
			descriptor.encode(message, builder, blockBuilder);
		}

		if (blockBuilder.getLength() > 0) {
			builder.putBits(11, 2047);
			builder.switchToByteAccess();
			builder.putBytes(blockBuilder);
		} else {
			builder.switchToByteAccess();
		}

		return builder.build();
	}

}