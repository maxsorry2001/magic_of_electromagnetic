package net.Gmaj7.electrofynamic_thaumatury.MoeParticle.custom;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class TorchParticleIn extends TextureSheetParticle {
    private final SpriteSet spriteSet;
    public TorchParticleIn(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.gravity = 0;
        this.setSpriteFromAge(spriteSet);
        this.lifetime = 100;
        this.rCol = 0.8F;
        this.alpha = 0.75F;
        this.quadSize = 0.2F;
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        super.tick();
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        if(age < 80) return this.quadSize * Math.min(this.age, 30);
        else return this.quadSize * (this.lifetime - this.age) * 1.5F;
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        Quaternionf quaternionf = new Quaternionf();
        Vector3f vector3f = renderInfo.getLookVector();
        float theta = (float) Math.PI / 2;
        if(vector3f.y() < 0) theta = - theta;
        if(this.age >= 50) quaternionf.rotationXYZ(theta, 0, (float) ((50 - this.age) * Math.PI / 25));
        else quaternionf.rotationX(theta);
        this.renderRotatedQuad(buffer, renderInfo, quaternionf, partialTicks);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType>{
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z, double xv, double yv, double zv) {
            return new TorchParticleIn(clientLevel, x, y, z, spriteSet);
        }
    }
}
