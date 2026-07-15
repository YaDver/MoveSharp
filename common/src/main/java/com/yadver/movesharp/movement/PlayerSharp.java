package com.yadver.movesharp.movement;

import com.yadver.movesharp.ModNetwork;
import com.yadver.movesharp.utils.VoxelWall;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

//  TODO: Добавить комментарии. А то хули, опять забью на мод и вот гадай, что за велосипед я тут изобрёл.
public class PlayerSharp {
    private final Player player;
    private final Level level;
    private final boolean isClient;

    public VoxelWall voxelWall;

    public Climbing climb_manager = new Climbing(this);
    public Sliding slide_manager = new Sliding(this);
    public Crawling crawl_manager = new Crawling(this);

    private boolean isSneaking = false;
    private boolean isSprinting = false;

    public Player getPlayer() {
        return player;
    }

    public Level getLevel() {
        return level;
    }

    public PlayerSharp(Player player) {
        this.player = player;
        this.level = player.level();
        this.voxelWall = new VoxelWall();
        this.isClient = level.isClientSide();
    }

    public void setSneaking(boolean bool) {
        if (this.isSneaking != bool) {
            if (isClient) ModNetwork.playerSneaking(bool);
            this.isSneaking = bool;
        }
    }

    public boolean getSneaking() {return this.isSneaking;}

    public void setSprinting(boolean bool) {
        if (this.isSprinting != bool) {
            if (isClient) ModNetwork.playerSprinting(bool);
            this.isSprinting = bool;
        }
    }

    public boolean getSprinting() {return this.isSprinting;}

    public boolean isClient() {
        return this.isClient;
    }

    public void restoreVoxelWall() {
        voxelWall.restore(level, player.position(), getNormalPlayerRotationVector());
    }

    public boolean xORz() {
        Vec3 playerRotationVector = getPlayer().getLookAngle();
        return Math.abs(playerRotationVector.x) > Math.abs(playerRotationVector.z);
    }

    public Vec3 getNormalPlayerRotationVector() {
        Vec3 playerRotationVector = getPlayer().getLookAngle();
        return new Vec3(
                (xORz()) ? ((playerRotationVector.x < 0) ?
                            -Math.ceil(-playerRotationVector.x) : Math.ceil(playerRotationVector.x)) : 0,
                0,
                (!xORz()) ? ((playerRotationVector.z < 0) ?
                             -Math.ceil(-playerRotationVector.z) : Math.ceil(playerRotationVector.z)) : 0
        );
    }

    public boolean isFrontCollide() {
        Vec3 p_look = getNormalPlayerRotationVector();
        AABB box = player.getBoundingBox();
        return !level.noCollision(box.move(p_look.x / 16, 0, p_look.z / 16));
    }

    public boolean isAboveCollide() {
        AABB box = player.getBoundingBox();
        return !level.noCollision(box.move(0, (double) 1 / 16, 0));
    }

    public boolean freeBelow() {
        AABB pBox = getPlayer().getBoundingBox();
        return level.noCollision(new AABB(
                pBox.minX, pBox.minY-1.1, pBox.minZ,
                pBox.maxX, pBox.minY, pBox.maxZ
        ));
    }

    public void tick() {
        climb_manager.update();
        slide_manager.update();
    }
}
