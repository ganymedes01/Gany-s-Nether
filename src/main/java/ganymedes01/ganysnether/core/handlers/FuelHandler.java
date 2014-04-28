package ganymedes01.ganysnether.core.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

/**
 * Gany's Nether
 * 
 * @author ganymedes01
 * 
 */

public class FuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel.getItem() == Item.getItemFromBlock(Blocks.fire))
			return 8000;
		return 0;
	}
}