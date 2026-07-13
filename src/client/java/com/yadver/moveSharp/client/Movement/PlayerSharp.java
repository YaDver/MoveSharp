package com.yadver.moveSharp.client.Movement;

import com.yadver.moveSharp.client.Utils.SmoothAcceleration;
import com.yadver.moveSharp.client.Utils.VoxelWall;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerSharp extends Sliding {
    private static final Logger log = LoggerFactory.getLogger(PlayerSharp.class);
    private World world;
    public VoxelWall voxelWall;

    static double climbingSpeed = 0.15;

    public PlayerSharp(ClientPlayerEntity player) {
        super(player);
        world = player.getWorld();
        voxelWall = new VoxelWall();
        setSmoothClimb((getPlayer().getVelocity().y > 0) ?
                new SmoothAcceleration(getPlayer().getVelocity().y, climbingSpeed/2 , 0.1) :
                new SmoothAcceleration(0, climbingSpeed, 0.1));;
    }

    public void update(boolean SprintKey, boolean SneakKey) {
        if (SprintKey) {
            getPlayer().sendMessage(Text.of("freeBelow:"+freeBelow()+" isClimbing:"+isClimbing()), true);
            if (!isClimbing() && freeBelow()) {
                voxelWall.restore(world, getPlayer().getPos(), getNormalPlayerRotationVector());
                voxelWall.scanWall();
                voxelWall.searchLedge();
                setClimbing(!voxelWall.voxelLedge.isEmpty());
            } else {
                if (isClimbing() && getPlayer().getPos().y < voxelWall.voxelLedge.get(voxelWall.voxelLedge.size() - 1).minY) {
                    Climb();
                    if (getPlayer().getPos().y >= voxelWall.voxelLedge.get(voxelWall.voxelLedge.size() - 1).minY) {
                        EndClimbing(getNormalPlayerRotationVector());
                        setClimbing(false);
                    }
                }
            }

        } else {
            setClimbing(false);
        }
    }

    public boolean xORz() {
        Vec3d playerRotationVector = getPlayer().getRotationVector();
        return Math.abs(playerRotationVector.x) > Math.abs(playerRotationVector.z);
    }

    private Vec3d getNormalPlayerRotationVector() {
        Vec3d playerRotationVector = getPlayer().getRotationVector();
        return new Vec3d(
                (xORz()) ? ((playerRotationVector.x < 0) ?
                        -Math.ceil(-playerRotationVector.x) : Math.ceil(playerRotationVector.x)) : 0,
                0,
                (!xORz()) ? ((playerRotationVector.z < 0) ?
                        -Math.ceil(-playerRotationVector.z) : Math.ceil(playerRotationVector.z)) : 0
        );
    }

    public boolean getFrontCollide(Box box) {
        Vec3d p_look = getNormalPlayerRotationVector();
        return !world.isSpaceEmpty(box.offset(p_look.x / 16, 0, p_look.z / 16));
    }

    public boolean freeBelow() {
        Box pBox = getPlayer().getBoundingBox();
        return world.isSpaceEmpty(new Box(
                pBox.minX, pBox.minY-1.1, pBox.minZ,
                pBox.maxX, pBox.minY, pBox.maxZ
        ));
    }
}