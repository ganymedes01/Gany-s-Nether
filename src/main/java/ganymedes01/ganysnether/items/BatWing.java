package ganymedes01.ganysnether.items;

import ganymedes01.ganysnether.GanysNether;
import ganymedes01.ganysnether.IConfigurable;
import ganymedes01.ganysnether.core.utils.Utils;
import ganymedes01.ganysnether.lib.Strings;
import net.minecraft.item.Item;

/**
 * Gany's Nether
 *
 * @author ganymedes01
 *
 */

public class BatWing extends Item implements IConfigurable {

	public BatWing() {
		setTextureName(Utils.getItemTexture(Strings.Items.BAT_WING_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.Items.BAT_WING_NAME));
		setCreativeTab(GanysNether.enableReproducerAndDrops ? GanysNether.netherTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysNether.enableReproducerAndDrops;
	}
}