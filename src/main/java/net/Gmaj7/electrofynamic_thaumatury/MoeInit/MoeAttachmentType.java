package net.Gmaj7.electrofynamic_thaumatury.MoeInit;

import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class MoeAttachmentType {
    public static final DeferredRegister<AttachmentType<?>> MOE_ATTACHMENT_TYPE = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MagicOfElectromagnetic.MODID);

    public static void register(IEventBus eventBus){
        MOE_ATTACHMENT_TYPE.register(eventBus);
    }
}
