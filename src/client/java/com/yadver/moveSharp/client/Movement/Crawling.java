package com.yadver.moveSharp.client.Movement;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class Crawling {
    private ClientPlayerEntity player;
    private boolean isCrawling;

    public ClientPlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(ClientPlayerEntity player) {
        this.player = player;
    }

    public boolean isCrawling() {
        return isCrawling;
    }

    private void setCrawling(boolean crawling) {
        isCrawling = crawling;
    }

    public Crawling(ClientPlayerEntity player) {
        setPlayer(player);
    }

    protected boolean wouldPoseNotCollide(EntityPose pose) {
        return player.getWorld().isSpaceEmpty(calculateBoundsForPose(pose));
    }

    protected Box calculateBoundsForPose(EntityPose pos) {
        EntityDimensions entityDimensions = player.getDimensions(pos);
        float f = entityDimensions.width / 2.0F;
        Vec3d vec3d = new Vec3d(player.getX() - f, player.getY(), player.getZ() - f);
        Vec3d vec3d2 = new Vec3d(player.getX() + f, player.getY() + entityDimensions.height, player.getZ() + f);
        return new Box(vec3d, vec3d2);
    }
}
