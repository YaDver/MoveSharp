package com.YaDver.MoveSharp;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;

public final class MoveSharp {
    public static final String MOD_ID = "movesharp";

    
    // private static final Identifier CHANNEL_slide = new Identifier(MOD_ID, "slide");
    // private static final Identifier CHANNEL_crawl = new Identifier(MOD_ID, "crawl");
    // private static final Identifier CHANNEL_climb = new Identifier(MOD_ID, "climb");
    public static final EntityDataAccessor<Boolean> IS_CRAWLING = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_CLIMBING = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);

    public static void init() {
        // Write common init code here.
    }
}
