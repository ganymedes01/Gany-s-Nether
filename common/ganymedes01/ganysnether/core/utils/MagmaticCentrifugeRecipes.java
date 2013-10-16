package ganymedes01.ganysnether.core.utils;

import ganymedes01.ganysnether.items.ModItems;
import ganymedes01.ganysnether.lib.Reference;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Gany's Nether
 * 
 * @author ganymedes01
 * 
 */

public class MagmaticCentrifugeRecipes {

	private static ArrayList<CentrifugeRecipe> recipes;

	public static void initRecipes() {
		recipes = new ArrayList<CentrifugeRecipe>();

		recipes.add(new CentrifugeRecipe(new ItemStack(Item.glowstone), new ItemStack(Block.netherrack), new ItemStack(Item.redstone)));
		recipes.add(new CentrifugeRecipe(new ItemStack(Item.magmaCream), new ItemStack(Item.magmaCream), new ItemStack(Item.blazePowder, 2), new ItemStack(Item.slimeBall, 2)));
		recipes.add(new CentrifugeRecipe(new ItemStack(ModItems.glowingReed), new ItemStack(ModItems.glowingReed), new ItemStack(Item.glowstone, 2), new ItemStack(Item.sugar, 2)));
		recipes.add(new CentrifugeRecipe(new ItemStack(ModItems.quarzBerry), new ItemStack(Item.glassBottle), new ItemStack(Item.potion)));
		recipes.add(new CentrifugeRecipe(new ItemStack(Block.glowStone), new ItemStack(Item.flint), new ItemStack(Item.glowstone, 6)));
		recipes.add(new CentrifugeRecipe(new ItemStack(Block.gravel), new ItemStack(Block.gravel), new ItemStack(Item.flint, 2)));
		recipes.add(new CentrifugeRecipe(new ItemStack(Item.eyeOfEnder), new ItemStack(Item.eyeOfEnder), new ItemStack(Item.blazePowder, 2), new ItemStack(Item.enderPearl, 2)));
		recipes.add(new CentrifugeRecipe(new ItemStack(ModItems.glowingReed), new ItemStack(Item.potion), new ItemStack(Item.reed), new ItemStack(Item.glassBottle)));
		recipes.add(new CentrifugeRecipe(new ItemStack(Block.dirt), new ItemStack(Block.sand), new ItemStack(Item.clay)));
	}

	public static void addRecipe(String sender, ItemStack material1, ItemStack material2, ItemStack... result) {
		if (result.length > 4 && material1 != null && material2 != null) {
			Logger.getLogger(Reference.MOD_ID).log(Level.INFO, sender + " attempted to add an invalid recipe to the Magmatic Centrifuge.");
			return;
		} else {
			for (ItemStack stack : result)
				if (stack == null)
					return;
			CentrifugeRecipe newRecipe = new CentrifugeRecipe(material1, material2, result);
			if (!recipes.contains(newRecipe)) {
				recipes.add(newRecipe);
				Logger.getLogger(Reference.MOD_ID).log(Level.INFO, sender + " successfully added a recipe to the Magmatic Centrifuge.");
			} else
				Logger.getLogger(Reference.MOD_ID).log(Level.INFO, sender + " attempted to add an existing recipe to the Magmatic Centrifuge.");
		}
	}

	public static boolean isValidRecipe(CentrifugeRecipe recipe) {
		return recipes.contains(recipe);
	}

	public static ItemStack[] getResult(CentrifugeRecipe recipe) {
		CentrifugeRecipe foundRecipe = recipes.get(recipes.indexOf(recipe));
		return foundRecipe != null ? foundRecipe.getResult() : null;
	}

	public static class CentrifugeRecipe {

		private final ItemStack material1;
		private final ItemStack material2;
		private final ItemStack[] result;

		public CentrifugeRecipe(ItemStack material1, ItemStack material2) {
			this(material1, material2, (ItemStack) null);
		}

		public CentrifugeRecipe(ItemStack material1, ItemStack material2, ItemStack... result) {
			this.material1 = material1;
			this.material2 = material2;
			this.result = result;
		}

		public ItemStack getMaterial(int num) {
			return num == 1 ? material1.copy() : num == 2 ? material2.copy() : null;
		}

		public ItemStack[] getResult() {
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj != null && obj instanceof CentrifugeRecipe) {
				CentrifugeRecipe recipe = (CentrifugeRecipe) obj;
				ItemStack material1 = recipe.getMaterial(1);
				ItemStack material2 = recipe.getMaterial(2);
				if (material1 == null || material2 == null)
					return false;
				else {
					if (material1.itemID == this.material1.itemID && material1.getItemDamage() == this.material1.getItemDamage())
						if (material2.itemID == this.material2.itemID && material2.getItemDamage() == this.material2.getItemDamage())
							return true;
					if (material2.itemID == this.material1.itemID && material2.getItemDamage() == this.material1.getItemDamage())
						if (material1.itemID == this.material2.itemID && material1.getItemDamage() == this.material2.getItemDamage())
							return true;
				}
			}
			return false;
		}
	}
}
