package com.glaceon_471.kubejs.projecte_recipe_emc;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import moze_intel.projecte.api.ItemInfo;
import moze_intel.projecte.api.mapper.collector.IMappingCollector;
import moze_intel.projecte.api.nss.NormalizedSimpleStack;
import moze_intel.projecte.emc.nbt.NBTManager;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Range;
import java.util.Arrays;
import java.util.stream.Stream;

public class KubeJSProjectERecipeEMCPlugin extends KubeJSPlugin {
    public static EventGroup Group = EventGroup.of("ProjectERecipeEMCEvents");
    public static EventHandler SetRecipeEMC = Group.server("setRecipeEMC", () -> SetRecipeEMCEventJS.class);

    @Override
    public void registerEvents() {
        Group.register();
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("ProjectERecipeEMCHelper", new ProjectERecipeEMCHelper());
        event.add("EMCHelper", new KubeJSEMCHelper());
    }

    public static class SetRecipeEMCEventJS extends EventJS {
        public static SetRecipeEMCEventJS Instance = new SetRecipeEMCEventJS();

        public void setRecipeEMC(RecipeType<?> type, KubeJSProjectERecipeMapper.HandleRecipeFunction function) {
            KubeJSProjectERecipeMapper.Instance.Functions.put(type, function);
        }

        public void setRecipeEMC(String namespace, String id, KubeJSProjectERecipeMapper.HandleRecipeFunction function) {
            KubeJSProjectERecipeMapper.Instance.Functions.put(BuiltInRegistries.RECIPE_TYPE.get(new ResourceLocation(namespace, id)), function);
        }
    }

    public static class ProjectERecipeEMCHelper {
        public RecipeType<?> getRecipeType(String namespace, String id) {
            return BuiltInRegistries.RECIPE_TYPE.get(new ResourceLocation(namespace, id));
        }

        public IngredientHelper getIngredientHelper(IMappingCollector<NormalizedSimpleStack, Long> collector) {
            return new IngredientHelper(collector);
        }

        public Stream<ItemStack> getIngredientItems(Ingredient ingredient) {
            return Arrays.stream(ingredient.getItems());
        }
    }

    public static class KubeJSEMCHelper {
        public boolean doesItemHaveEmc(ItemInfo info) {
            return EMCHelper.getEmcValue(info) > 0;
        }

        public boolean doesItemHaveEmc(ItemLike item) {
            return EMCHelper.getEmcValue(item) > 0;
        }

        @Range(from = 0, to = Long.MAX_VALUE)
        public long getEmcValue(ItemLike item) {
            return item == null ? 0 : EMCHelper.getEmcValue(ItemInfo.fromItem(item));
        }

        @Range(from = 0, to = Long.MAX_VALUE)
        public long getEmcValue(ItemInfo info) {
            return NBTManager.getEmcValue(info);
        }
    }
}
