package com.yadver.movesharp.fabric;

import com.yadver.movesharp.MoveSharp;
import net.fabricmc.api.ModInitializer;

public final class MoveSharpFabric implements ModInitializer {
        @Override
    public void onInitialize() {
        MoveSharp.init();
    }
}
