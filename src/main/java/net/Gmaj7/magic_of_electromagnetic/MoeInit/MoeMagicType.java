package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import net.minecraft.network.chat.Component;

public enum MoeMagicType {
    RAY,
    ATTRACT,
    LIGHTING_STRIKE,
    PULSED_PLASMA,
    ELECTRIC_FIELD_DOMAIN,
    ELECTRIC_ENERGY_RELEASE,
    NERVE_BLOCKING,
    EXCITING,
    PROTECT,
    TREE_CURRENT,
    REFRACTION,
    PLASMA_TORCH,
    ELECTROMAGNETIC_ASSAULT,
    ST_ELMO_S_FIRE,
    MAGMA_LIGHTING,
    HYDROGEN_BOND_FRACTURE,
    MAGNET_RESONANCE,
    DISTURBING_BY_HIGH_INTENSITY_MAGNETIC,
    COULOMB_DOMAIN,
    DOMAIN_RECONSTRUCTION,
    ERROR,
    EMPTY;
    private MoeMagicType(){

    }

    public static Component getTranslate(MoeMagicType type){
        String translate;
        switch (type){
            case RAY -> translate = "item.magic_of_electromagnetic.ray_module";
            case ATTRACT -> translate = "item.magic_of_electromagnetic.attract_module";
            case LIGHTING_STRIKE -> translate = "item.magic_of_electromagnetic.lighting_strike_module";
            case PULSED_PLASMA -> translate = "item.magic_of_electromagnetic.pulsed_plasma_module";
            case ELECTRIC_FIELD_DOMAIN -> translate = "item.magic_of_electromagnetic.electric_field_domain_module";
            case EXCITING -> translate = "item.magic_of_electromagnetic.exciting_module";
            case PROTECT -> translate = "item.magic_of_electromagnetic.protecting_module";
            case TREE_CURRENT -> translate = "item.magic_of_electromagnetic.tree_current_module";
            case NERVE_BLOCKING -> translate = "item.magic_of_electromagnetic.block_nerve_module";
            case ELECTRIC_ENERGY_RELEASE -> translate = "item.magic_of_electromagnetic.electric_energy_release_module";
            case REFRACTION -> translate = "item.magic_of_electromagnetic.refraction_module";
            case PLASMA_TORCH -> translate = "item.magic_of_electromagnetic.plasma_torch_module";
            case ELECTROMAGNETIC_ASSAULT -> translate = "item.magic_of_electromagnetic.electromagnetic_assault_module";
            case ST_ELMO_S_FIRE -> translate = "item.magic_of_electromagnetic.st_elmo_s_fire_module";
            case MAGMA_LIGHTING -> translate = "item.magic_of_electromagnetic.magma_lighting_module";
            case HYDROGEN_BOND_FRACTURE -> translate = "item.magic_of_electromagnetic.hydrogen_bond_fracture_module";
            case DISTURBING_BY_HIGH_INTENSITY_MAGNETIC -> translate = "item.magic_of_electromagnetic.disturbing_by_high_intensity_magnetic_module";
            case COULOMB_DOMAIN -> translate = "item.magic_of_electromagnetic.coulomb_domain_module";
            case MAGNET_RESONANCE -> translate = "item.magic_of_electromagnetic.magnet_resonance_module";
            case DOMAIN_RECONSTRUCTION -> translate = "item.magic_of_electromagnetic.domain_reconstruction_module";
            default -> translate = "moe_no_magic";
        }
        return Component.translatable(translate);
    }

    public static boolean isEmpty(MoeMagicType type){
        if(type == EMPTY || type == ERROR) return true;
        else return false;
    }
}
