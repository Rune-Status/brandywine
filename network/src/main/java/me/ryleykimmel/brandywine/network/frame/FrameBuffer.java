package me.ryleykimmel.brandywine.network.frame;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCounted;
import me.ryleykimmel.brandywine.common.Assertions;

/**
 * A wrapper for an I/O (reader-writer) {@link ByteBuf} for writing and reading Frames.
 */
abstract class FrameBuffer implements ReferenceCounted {

  /**
   * An array of bit masks. The element {@code n} is equal to {@code 2<sup>n</sup> - 1}.
   */
  static final int[] BIT_MASKS = {0x0, 0x1, 0x3, 0x7, 0xf, 0x1f, 0x3f, 0x7f, 0xff, 0x1ff, 0x3ff,
      0x7ff, 0xfff, 0x1fff, 0x3fff, 0x7fff, 0xffff, 0x1ffff, 0x3ffff, 0x7ffff,
      0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff, 0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff,
      0x1fffffff, 0x3fffffff, 0x7fffffff, -1
  };

  /**
   * The backing ByteBuf.
   */
  final ByteBuf buffer;

  /**
   * The current bit index of this FrameBuffer.
   */
  int bitIndex;

  /**
   * The current AccessMode this FrameBuffer is in, default is {@link AccessMode#BYTE_ACCESS}.
   */
  private AccessMode mode = AccessMode.BYTE_ACCESS;

  /**
   * Constructs a new {@link FrameBuffer} with the specified ByteBuf backing.
   *
   * @param buffer The backing ByteBuf.
   */
  FrameBuffer(ByteBuf buffer) {
    this.buffer = buffer;
  }

  /**
   * Switches this FrameBuffers AccessMode to {@link AccessMode#BYTE_ACCESS}.
   */
  public final void switchToByteAccess() {
    checkBitAccess();
    buffer.writerIndex((bitIndex + 7) >> 3);
    mode = AccessMode.BYTE_ACCESS;
  }

  /**
   * Switches this FrameBuffers AccessMode to {@link AccessMode#BIT_ACCESS}.
   */
  public final void switchToBitAccess() {
    checkByteAccess();
    bitIndex = buffer.readerIndex() << 3;
    mode = AccessMode.BIT_ACCESS;
  }

  /**
   * Returns the backing ByteBuf for this FrameBuffer.
   *
   * @return The backing ByteBuf for this FrameBuffer.
   */
  public ByteBuf getBuffer() {
    Assertions.checkPositive(buffer.refCnt(), "Illegal reference count: " + buffer.refCnt());
    return buffer;
  }

  @Override
  public int refCnt() {
    return buffer.refCnt();
  }

  @Override
  public FrameBuffer retain() {
    buffer.retain();
    return this;
  }

  @Override
  public FrameBuffer retain(int increment) {
    buffer.retain(increment);
    return this;
  }

  @Override
  public ReferenceCounted touch() {
    return buffer.touch();
  }

  @Override
  public ReferenceCounted touch(Object hint) {
    return buffer.touch(hint);
  }

  @Override
  public boolean release() {
    return buffer.release();
  }

  @Override
  public boolean release(int decrement) {
    return buffer.release(decrement);
  }

  /**
   * Checks if this FrameBuffer is currently within {@link AccessMode#BYTE_ACCESS}, if not an IllegalArgumentException is thrown.
   */
  final void checkByteAccess() {
    Preconditions.checkArgument(mode == AccessMode.BYTE_ACCESS,
        "For byte-based calls to work, the mode must be byte access");
  }

  /**
   * Checks if this FrameBuffer is currently within {@link AccessMode#BIT_ACCESS}, if not an IllegalArgumentException is thrown.
   */
  final void checkBitAccess() {
    Preconditions.checkArgument(mode == AccessMode.BIT_ACCESS,
        "For bit-based calls to work, the mode must be bit access");
  }

}
