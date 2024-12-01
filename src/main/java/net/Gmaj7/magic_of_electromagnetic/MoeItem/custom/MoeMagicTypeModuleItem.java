package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.world.item.Item;

public class MoeMagicTypeModuleItem extends Item implements IMoeModuleItem{
    private final MoeMagicType magicType;
    public MoeMagicTypeModuleItem(MoeMagicType magicType, Properties properties) {
        super(properties);
        this.magicType = magicType;
    }

    public MoeMagicType getMagicType() {
        return magicType;
    }

    public boolean isEmpty(){
        return magicType == MoeMagicType.EMPTY;
    }

    public String getTexture(){
        String location;
        switch (this.magicType){
            case RAY -> location = "ray";
            case PULSED_PLASMA -> location = "plasma";
            case GLOWING -> location = "glowing";
            case PROTECT -> location = "protect";
            default -> location = "null";
        }
        location = location + "_module.png";
        return location;
    }
}
