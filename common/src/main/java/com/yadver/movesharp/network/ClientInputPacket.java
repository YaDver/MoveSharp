package com.yadver.movesharp.network;

import com.yadver.movesharp.MoveSharp;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ClientInputPacket(String keyID, boolean isPressed) implements CustomPacketPayload {
    private static final ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(
            MoveSharp.MOD_ID, "client-input"
    );

    public static final Type<ClientInputPacket> TYPE = new Type<>(resourceLocation);

    public static final StreamCodec<? super RegistryFriendlyByteBuf, ClientInputPacket> CODEC = StreamCodec.of(
            (FriendlyByteBuf buf, ClientInputPacket value) -> {
                buf.writeUtf(value.keyID());
                buf.writeBoolean(value.isPressed());
            },
            (FriendlyByteBuf buf) -> new ClientInputPacket(buf.readUtf(), buf.readBoolean())
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
