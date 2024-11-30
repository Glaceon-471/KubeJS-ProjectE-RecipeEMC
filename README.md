KubeJSでProjectEのレシピで自動的にEMCを
割り振ってくれるものを作れるようにするMod

例 :
```js
// In server_scripts
// Botaniaのアイテムにマナを注入するレシピの対応
ProjectERecipeEMCEvents.setRecipeEMC(event => {
  event.setRecipeEMC(
    ProjectERecipeEMCHelper.getRecipeType("botania", "mana_infusion"),
    (collector, recipe, access, manager) => {
      let output = recipe.getResultItem(access);
      if (output.isEmpty() || EMCHelper.doesItemHaveEmc(output)) {
        return false;
      }
      let helper = ProjectERecipeEMCHelper.getIngredientHelper(collector);
      recipe.getIngredients().forEach(representation => {
        let item = ProjectERecipeEMCHelper.toStream(representation.getStacks()).min(
          (a, b) => EMCHelper.getEmcValue(a) - EMCHelper.getEmcValue(b)
        );
        if (item.isEmpty() || item.get().isEmpty()) {
          return;
        }
        helper.put(item.get());
      });
      return helper.addAsConversion(output);
    }
  );
});
```
