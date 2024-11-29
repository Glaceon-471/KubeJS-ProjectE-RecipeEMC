package com.glaceon_471.kubejs.projecte_recipe_emc;

import com.mojang.logging.LogUtils;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import java.util.HashMap;

@Mod(KubeJSProjectERecipeEMC.ModId)
public class KubeJSProjectERecipeEMC {
    public static final String ModId = "kubejs_projecte_recipe_emc";
    public static final Logger Logger = LogUtils.getLogger();

    public KubeJSProjectERecipeEMC() {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, KubeJSProjectERecipeEMC::serverReload);
    }

    public static void serverReload(TagsUpdatedEvent event) {
        KubeJSProjectERecipeMapper.Instance.Functions = new HashMap<>();
        KubeJSProjectERecipeEMCPlugin.SetRecipeEMC.post(ScriptType.SERVER, KubeJSProjectERecipeEMCPlugin.SetRecipeEMCEventJS.Instance);
    }
}
