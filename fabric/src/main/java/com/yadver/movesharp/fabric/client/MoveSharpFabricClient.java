package com.yadver.movesharp.fabric.client;

import com.yadver.movesharp.movement.PlayerSharp;
import com.yadver.movesharp.PlayerSharpAccess;
import com.yadver.movesharp.utils.VoxelWall;
import com.yadver.movesharp.client.MoveSharpClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;

public class MoveSharpFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MoveSharpClient.init();
        //  TODO: Добавить комментарии.
        //  TODO: Сделать конфиг с возможностью включить отображение для дебага. Перенести на Forge версию.
//        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
//            LocalPlayer player = Minecraft.getInstance().player;
//            PlayerSharpAccess access = (PlayerSharpAccess) player;
//            assert access != null;
//            PlayerSharp playerSharp = access.moveSharp$getWrapper();
//
//            if (playerSharp != null) {
//                Vec3 camPos = context.camera().getPosition();
//                List<VoxelWall.WallElement> wall = playerSharp.voxelWall.voxelWall;
//                List<AABB> ledges = playerSharp.voxelWall.voxelLedge;
//                for (VoxelWall.WallElement element : wall) {
//                    LevelRenderer.renderLineBox(
//                            context.matrixStack(),
//                            Objects.requireNonNull(context.consumers()).getBuffer(RenderType.lines()),
//                            element.box().move(
//                                    element.pos().getX()-camPos.x,
//                                    element.pos().getY()-camPos.y,
//                                    element.pos().getZ()-camPos.z
//                            ),
//                            0.25f, 0f, 1f, 0.5f
//                    );
//                }
//                for (AABB box : ledges) {
//                    LevelRenderer.renderLineBox(
//                            context.matrixStack(),
//                            Objects.requireNonNull(context.consumers()).getBuffer(RenderType.lines()),
//                            box.move(
//                                    -camPos.x,
//                                    -camPos.y,
//                                    -camPos.z
//                            ),
//                            1f, 0f, 0.25f, 1f
//                    );
//                }
//            }
//        });
    }
}
