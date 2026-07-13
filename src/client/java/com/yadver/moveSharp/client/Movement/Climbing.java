package com.yadver.moveSharp.client.Movement;

import com.yadver.moveSharp.client.ModNetwork;
import com.yadver.moveSharp.client.Utils.SmoothAcceleration;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class Climbing extends Crawling{
    private boolean isClimbing;
    private SmoothAcceleration smoothClimb;

    public Climbing(ClientPlayerEntity player) {
        super(player);
    }

    public boolean isClimbing() {
        return isClimbing;
    }

    public void setClimbing(boolean climbing) {
        if (isClimbing != climbing) {
            ModNetwork.playerClimbing(climbing);
        }
        isClimbing = climbing;
    }

    public void setSmoothClimb(SmoothAcceleration accelerator) {
        this.smoothClimb = accelerator;
    }

    protected void Climb() {
        getPlayer().setVelocity(0, smoothClimb.update(), 0);
//        getPlayer().addVelocity(0, 0.08, 0);
    }

    protected void EndClimbing(Vec3d p_look) {
        getPlayer().setVelocity(0.1 * p_look.x, 0, 0.1 * p_look.y);
    }
}
