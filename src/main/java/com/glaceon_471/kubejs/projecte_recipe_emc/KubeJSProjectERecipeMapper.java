package com.glaceon_471.kubejs.projecte_recipe_emc;

import moze_intel.projecte.api.mapper.collector.IMappingCollector;
import moze_intel.projecte.api.mapper.recipe.INSSFakeGroupManager;
import moze_intel.projecte.api.mapper.recipe.IRecipeTypeMapper;
import moze_intel.projecte.api.mapper.recipe.RecipeTypeMapper;
import moze_intel.projecte.api.nss.NormalizedSimpleStack;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import java.util.HashMap;
import java.util.Map;

@RecipeTypeMapper
public class KubeJSProjectERecipeMapper implements IRecipeTypeMapper {
    @RecipeTypeMapper.Instance
    public static KubeJSProjectERecipeMapper Instance = new KubeJSProjectERecipeMapper();
    public Map<RecipeType<?>, HandleRecipeFunction> Functions = new HashMap<>();

    @Override
    public String getName() {
        return "KubeJS ProjectE";
    }

    @Override
    public String getDescription() {
        return "RecipeMapper created in KubeJS";
    }

    @Override
    public boolean canHandle(RecipeType<?> type) {
        return Functions.containsKey(type);
    }

    @Override
    public boolean handleRecipe(
        IMappingCollector<NormalizedSimpleStack, Long> collector,
        Recipe<?> recipe, RegistryAccess access,
        INSSFakeGroupManager manager
    ) {
        HandleRecipeFunction data = Functions.getOrDefault(recipe.getType(), HandleRecipeFunction.Empty);
        return data.run(collector, recipe, access, manager);
    }

    @FunctionalInterface
    public interface HandleRecipeFunction {
        boolean run(
            IMappingCollector<NormalizedSimpleStack, Long> collector,
            Recipe<?> recipe, RegistryAccess access,
            INSSFakeGroupManager manager
        );

        HandleRecipeFunction Empty = (collector, recipe, access, manager) -> false;
    }
}
