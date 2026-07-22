package com.yadver.movesharp;

import com.yadver.movesharp.network.ModNetwork;

public final class MoveSharp {
    public static final String MOD_ID = "movesharp";

//    public static final EntityDataAccessor<Boolean> IS_CRAWLING = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
//    public static final EntityDataAccessor<Boolean> IS_CLIMBING = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
//    public static final EntityDataAccessor<Boolean> IS_SNEAK_BUTTON = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
//    public static final EntityDataAccessor<Boolean> IS_SPRINT_BUTTON = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);

    public static void init() {
        ModNetwork.registerReceiver();
    }
}
