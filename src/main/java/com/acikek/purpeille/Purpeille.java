package com.acikek.purpeille;

import com.acikek.purpeille.advancement.ModCriteria;
import com.acikek.purpeille.attribute.ModAttributes;
import com.acikek.purpeille.block.ModBlocks;
import com.acikek.purpeille.block.PurpurRemnants;
import com.acikek.purpeille.block.ancient.gateway.AncientGatewayBlockEntity;
import com.acikek.purpeille.block.ancient.oven.AncientOvenBlockEntity;
import com.acikek.purpeille.command.WarpathCommand;
import com.acikek.purpeille.item.ModItems;
import com.acikek.purpeille.recipe.oven.AncientOvenRecipe;
import com.acikek.purpeille.recipe.warpath.WarpathCreateRecipe;
import com.acikek.purpeille.recipe.warpath.WarpathRemoveRecipe;
import com.acikek.purpeille.sound.ModSoundEvents;
import com.acikek.purpeille.world.gen.EndCityProximityPlacementModifier;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;

public class Purpeille implements ModInitializer {

    public static final String ID = "purpeille";

    public static final ItemGroup ITEM_GROUP = QuiltItemGroup.createWithIcon(
            id("main"),
            ModItems.PURPEILLE_INGOT::getDefaultStack
    );

    public static Identifier id(String key) {
        return new Identifier(ID, key);
    }

    public static final Logger LOGGER = LogManager.getLogger(ID);

    @Override
    public void onInitialize(ModContainer mod) {
        LOGGER.info("Embark on a journey of allegiance");
        ModBlocks.register();
        System.out.println("blocks");
        ModItems.register();
        System.out.println("items");
        ModAttributes.register();
        System.out.println("attributes");
        ModCriteria.register();
        System.out.println("criteria");
        ModSoundEvents.register();
        System.out.println("sound");
        AncientGatewayBlockEntity.register();
        AncientOvenBlockEntity.register();
        System.out.println("BEs");
        WarpathCreateRecipe.register();
        WarpathRemoveRecipe.register();
        AncientOvenRecipe.register();
        System.out.println("recipes");
        EndCityProximityPlacementModifier.register();
        System.out.println("placement modifier");
        PurpurRemnants.build();
        System.out.println("remnants");
        CommandRegistrationCallback.EVENT.register((dispatcher, integrated, dedicated) -> WarpathCommand.register(dispatcher));
    }
}
