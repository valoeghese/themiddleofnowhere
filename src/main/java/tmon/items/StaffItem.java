package tmon.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;

public class StaffItem extends ToolItem {
	public StaffItem(ToolMaterial material, Item.Settings settings) {
		super(material, settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext ctx) {
//		BlockPos pos = ctx.getBlockPos();
//		World world = ctx.getWorld();
//		BlockEntity be = world.getBlockEntity(pos);
//		if (be instanceof AltarBlockEntity) {
//			if (!world.isClient
//					&& ((AltarBlockEntity) be).activate(ctx.getPlayer())) {
//				ItemStack stack = ctx.getStack();
//				stack.damage(1, ctx.getPlayer(), item -> {
//					ctx.getPlayer().sendToolBreakStatus(ctx.getHand());
//				});
//			}
//			return ActionResult.SUCCESS;
//		}

		return super.useOnBlock(ctx);
	}

}
