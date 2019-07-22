package tmon.items;

import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

// Exposes constructor
public class TMoNPickaxeItem extends PickaxeItem {
	public TMoNPickaxeItem(ToolMaterial material, int damage, float speed, Item.Settings settings) {
		super(material, damage, speed, settings);
	}
}
