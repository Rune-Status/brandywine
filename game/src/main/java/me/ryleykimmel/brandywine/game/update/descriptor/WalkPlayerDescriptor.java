package me.ryleykimmel.brandywine.game.update.descriptor;

import me.ryleykimmel.brandywine.game.model.Direction;
import me.ryleykimmel.brandywine.game.model.player.Player;
import me.ryleykimmel.brandywine.game.update.PlayerDescriptor;
import me.ryleykimmel.brandywine.network.frame.FrameBuilder;

/**
 * A PlayerDescriptor which encodes the walking movement of a Player.
 */
public class WalkPlayerDescriptor extends PlayerDescriptor {

  /**
   * The Direction of movement.
   */
  private final Direction direction;

  /**
   * Constructs a new WalkPlayerDescriptor.
   *
   * @param player The Player who is moving.
   */
  public WalkPlayerDescriptor(Player player) {
    this(player, player.getFirstDirection());
  }

  /**
   * Constructs a new WalkPlayerDescriptor.
   *
   * @param player The Player who is moving.
   * @param direction The Direction of movement.
   */
  public WalkPlayerDescriptor(Player player, Direction direction) {
    super(player);
    this.direction = direction;
  }

  @Override
  public void encodeState(FrameBuilder builder, FrameBuilder blockBuilder) {
    builder.putBits(1, 1);
    builder.putBits(2, 1);
    builder.putBits(3, direction.getValue());
    builder.putBits(1, isBlockUpdatedRequired() ? 1 : 0);
  }

}
