package me.ryleykimmel.brandywine.game.update;

import java.util.HashMap;
import java.util.Map;
import me.ryleykimmel.brandywine.game.model.Mob;
import me.ryleykimmel.brandywine.network.frame.FrameBuilder;

/**
 * Represents a Descriptor which encodes UpdateBlocks and other Descriptors.
 *
 * @param <T> The Mob who owns this Descriptor.
 */
public abstract class Descriptor<T extends Mob> {

  /**
   * The Mob who owns this Descriptor.
   */
  protected final T mob;

  /**
   * A Map of UpdateBlock types to UpdateBlocks.
   */
  private final Map<Class<? extends UpdateBlock>, UpdateBlock> blocks = new HashMap<>();

  /**
   * The update mask for this Descriptor.
   */
  protected int mask = 0;

  /**
   * Constructs a new {@link Descriptor} with the specified Mob and Updater.
   *
   * @param mob The Mob who owns this Descriptor.
   */
  public Descriptor(T mob) {
    this.mob = mob;

    // Append all of the mobs update blocks to this Descriptor.
    mob.getPendingUpdates().forEach(this::addBlock);
  }

  /**
   * Adds the specified UpdateBlock to this Descriptor.
   *
   * @param block The UpdateBlock to add.
   */
  public final void addBlock(UpdateBlock block) {
    blocks.putIfAbsent(block.getClass(), block);
    mask |= block.getMask();
  }

  /**
   * Removes the specified UpdateBlock.
   *
   * @param type The UpdateBlocks type.
   */
  public final void removeBlock(Class<? extends UpdateBlock> type) {
    UpdateBlock block = blocks.remove(type);
    if (block != null) {
      mask &= ~block.getMask();
    }
  }

  /**
   * Tests whether or not an UpdateBlock update is required.
   *
   * @return {@code true} if and only if an UpdateBlock update is required.
   */
  public final boolean isBlockUpdatedRequired() {
    return !blocks.isEmpty();
  }

  /**
   * Clears this Descriptors UpdateBlock map.
   */
  public final void clear() {
    blocks.clear();
    mask = 0;
  }

  /**
   * Encodes an UpdateBlock.
   *
   * @param blockBuilder The UpdateBlocks FrameBuilder.
   * @param type The UpdateBlocks type.
   */
  public final void encodeBlock(FrameBuilder blockBuilder, Class<? extends UpdateBlock> type) {
    UpdateBlock block = blocks.get(type);
    if (block != null) {
      block.encode(blockBuilder);
    }
  }

  /**
   * Encodes this Descriptor.
   *
   * @param builder The FrameBuilder.
   * @param blockBuilder The UpdateBlock FrameBuilder.
   */
  public abstract void encode(FrameBuilder builder, FrameBuilder blockBuilder);

  /**
   * Encodes the state of this Descriptor.
   *
   * @param builder The FrameBuilder.
   * @param blockBuilder The UpdateBlock FrameBuilder.
   */
  public abstract void encodeState(FrameBuilder builder, FrameBuilder blockBuilder);

}
