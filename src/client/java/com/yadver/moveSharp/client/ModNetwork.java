package com.yadver.moveSharp.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class ModNetwork {
    private static final Identifier CHANNEL_slide = new Identifier(MoveSharpClient.MOD_ID, "slide");
    private static final Identifier CHANNEL_crawl = new Identifier(MoveSharpClient.MOD_ID, "crawl");
    private static final Identifier CHANNEL_climb = new Identifier(MoveSharpClient.MOD_ID, "climb");

    public static void playerSliding(boolean isSliding) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(isSliding);
        ClientPlayNetworking.send(CHANNEL_slide, buf);
    }
    public static void playerCrawling(boolean isCrawling) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(isCrawling);
        ClientPlayNetworking.send(CHANNEL_crawl, buf);
    }
    public static void playerClimbing(boolean isClimbing) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(isClimbing);
        ClientPlayNetworking.send(CHANNEL_climb, buf);
    }
}