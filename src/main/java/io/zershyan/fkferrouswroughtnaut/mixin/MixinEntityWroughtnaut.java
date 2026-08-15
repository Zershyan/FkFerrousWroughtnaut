package io.zershyan.fkferrouswroughtnaut.mixin;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.bobmowzie.mowziesmobs.server.entity.wroughtnaut.EntityWroughtnaut;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityWroughtnaut.class)
public abstract class MixinEntityWroughtnaut extends MowzieLLibraryEntity {
    @Shadow(remap = false)
    public boolean vulnerable;

    public MixinEntityWroughtnaut(EntityType<? extends MowzieEntity> type, Level world) {
        super(type, world);
    }

    @Inject(
            method = "hurt",
            at = @At(
                    value = "FIELD",
                    target = "Lcom/bobmowzie/mowziesmobs/server/entity/wroughtnaut/EntityWroughtnaut;vulnerable:Z",
                    opcode = Opcodes.GETFIELD,
                    remap = false
            ),
            cancellable = true
    )
    private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.vulnerable = true;
        cir.setReturnValue(super.hurt(source, amount));
        cir.cancel();
    }
}
