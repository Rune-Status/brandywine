package me.ryleykimmel.brandywine.network.game.frame;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.ryleykimmel.brandywine.network.game.GameSession;

/**
 * Encodes Frames in a stream-like fashion from a message to {@link ByteBuf}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class FrameEncoder extends MessageToByteEncoder<Frame> {

	/**
	 * The GameSession to encode Frames for.
	 */
	private final GameSession session;

	/**
	 * Constructs a new {@link FrameEncoder} with the specified GameSession.
	 *
	 * @param session The GameSession to encode Frames for.
	 */
	public FrameEncoder(GameSession session) {
		this.session = session;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Frame frame, ByteBuf out) {

		// Only write the opcode and length if the Frame has a valid opcode.
		if (frame.hasValidOpcode()) {
			out.writeByte(session.isCipheringFrames() ? session.encipherFrameOpcode(frame.getOpcode()) : frame.getOpcode());

			switch (frame.getType()) {
			case VARIABLE_BYTE:
				out.writeByte(frame.getLength());
				break;

			case VARIABLE_SHORT:
				out.writeShort(frame.getLength());
				break;

			// Only variable byte and short frames need their length written, ignore others.
			default:
				break;
			}
		}

		ByteBuf payload = frame.getPayload();
		out.writeBytes(payload);
		payload.release();
	}

}