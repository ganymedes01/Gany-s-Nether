package ganymedes01.ganysnether.blocks;

import ganymedes01.ganysnether.GanysNether;
import ganymedes01.ganysnether.core.utils.Utils;
import ganymedes01.ganysnether.lib.ModIDs;
import ganymedes01.ganysnether.lib.Strings;
import ganymedes01.ganysnether.tileentities.TileEntitySoulChest;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Nether
 * 
 * @author ganymedes01
 * 
 */

public class SoulChest extends InventoryBlock {

	SoulChest() {
		this(ModIDs.SOUL_CHEST_ID);
		setCreativeTab(GanysNether.netherTab);
	}

	SoulChest(int id) {
		super(id, Material.sand);
		setHardness(2.5F);
		setStepSound(soundSandFootstep);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.Blocks.SOUL_CHEST_NAME));
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (entity instanceof EntityItem && !world.isRemote) {
			TileEntitySoulChest tile = (TileEntitySoulChest) world.getBlockTileEntity(x, y, z);
			Utils.addEntitytoInventory(tile, (EntityItem) entity);
		} else {
			entity.motionX *= 0.4D;
			entity.motionZ *= 0.4D;
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		byte b0 = 0;
		int l1 = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (l1 == 0)
			b0 = 2;
		if (l1 == 1)
			b0 = 5;
		if (l1 == 2)
			b0 = 3;
		if (l1 == 3)
			b0 = 4;

		world.setBlockMetadataWithNotify(x, y, z, b0, 3);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		IInventory iinventory = getInventory(world, x, y, z);
		if (!Utils.addStackToInventory(iinventory, player.inventory.getCurrentItem())) {
			if (iinventory != null)
				player.displayGUIChest(iinventory);
			return true;
		}

		return true;
	}

	public IInventory getInventory(World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);

		if (tile == null)
			return null;
		else if (BlockChest.isOcelotBlockingChest(world, x, y, z))
			return null;

		return (IInventory) tile;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySoulChest();
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int par5) {
		return Container.calcRedstoneFromInventory(getInventory(world, x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockIcon = reg.registerIcon("soul_sand");
	}
}