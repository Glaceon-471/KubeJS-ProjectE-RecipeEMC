package com.glaceon_471.kubejs.projecte_recipe_emc;

import moze_intel.projecte.api.ItemInfo;
import moze_intel.projecte.api.mapper.collector.IMappingCollector;
import moze_intel.projecte.api.nss.NSSItem;
import moze_intel.projecte.api.nss.NormalizedSimpleStack;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.world.item.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class IngredientHelper {
    private final IMappingCollector<NormalizedSimpleStack, Long> Mapper;
    private final Map<NormalizedSimpleStack, Integer> IngredientMap = new HashMap<>();
    private boolean IsValid = true;

    public IngredientHelper(IMappingCollector<NormalizedSimpleStack, Long> mapper) {
        this.Mapper = mapper;
    }

    public void put(NormalizedSimpleStack stack, int amount) {
        if (IsValid) {
            if (IngredientMap.containsKey(stack)) {
                long newAmount = IngredientMap.get(stack) + (long) amount;
                if (newAmount > Integer.MAX_VALUE || newAmount < Integer.MIN_VALUE) {
                    IsValid = false;
                } else {
                    IngredientMap.put(stack, (int) newAmount);
                }
            } else {
                if (stack instanceof NSSItem item && EMCHelper.doesItemHaveEmc(ItemInfo.fromNSS(item))) {
                    IsValid = false;
                } else {
                    IngredientMap.put(stack, amount);
                }
            }
        }
    }

    public void put(NormalizedSimpleStack stack, long amount) {
        if (amount > Integer.MAX_VALUE || amount < Integer.MIN_VALUE) {
            IsValid = false;
        } else {
            put(stack, (int) amount);
        }
    }

    public void put(ItemStack stack, int amount) {
        put(NSSItem.createItem(stack), amount);
    }

    public void put(ItemStack stack) {
        put(NSSItem.createItem(stack), stack.getCount());
    }

    public boolean addAsConversion(NormalizedSimpleStack output, int outputAmount) {
        if (IsValid) {
            Mapper.addConversion(outputAmount, output, IngredientMap);
            return true;
        }
        return false;
    }

    public boolean addAsConversion(NormalizedSimpleStack output, long outputAmount) {
        if (outputAmount > Integer.MAX_VALUE) {
            return false;
        }
        return addAsConversion(output, (int) outputAmount);
    }

    public boolean addAsConversion(ItemStack stack) {
        return addAsConversion(NSSItem.createItem(stack), stack.getCount());
    }
}
