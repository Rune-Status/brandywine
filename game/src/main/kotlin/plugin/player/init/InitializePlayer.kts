package plugin.player.init

import me.ryleykimmel.brandywine.game.model.player.InitializePlayerEvent
import me.ryleykimmel.brandywine.game.model.skill.LevelUpSkillListener
import me.ryleykimmel.brandywine.game.model.skill.SynchronizationSkillListener
import me.ryleykimmel.brandywine.game.msg.InitializePlayerMessage
import me.ryleykimmel.brandywine.game.msg.RebuildRegionMessage
import me.ryleykimmel.brandywine.network.frame.codec.CipheredFrameCodec
import me.ryleykimmel.brandywine.network.frame.codec.FrameCodec
import me.ryleykimmel.brandywine.network.frame.codec.FrameMessageCodec
import me.ryleykimmel.brandywine.network.isaac.IsaacRandomPair
import plugin.message
import plugin.message.MessageRegistrar
import plugin.on

on(InitializePlayerEvent::class, { event ->
    with(event.player) {
        val pipeline = session.channel.pipeline()
        pipeline.replace(FrameCodec::class.java, "ciphered_frame_codec", CipheredFrameCodec(session, MessageRegistrar.gameMetadata, IsaacRandomPair.fromSeed(credentials.sessionKeys)))
        pipeline.replace(FrameMessageCodec::class.java, "message_codec", FrameMessageCodec(MessageRegistrar.gameMetadata))

        lastKnownRegion = position
        teleport(position)

        write(InitializePlayerMessage(isMember, index))
        write(RebuildRegionMessage(position))

        skills.addListener(SynchronizationSkillListener(this))
        skills.addListener(LevelUpSkillListener(this))
        skills.refresh()

        message("Welcome to Brandywine.")
    }
})
