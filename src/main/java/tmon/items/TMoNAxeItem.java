package tmon.items;

import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

// Exposes constructor
public class TMoNAxeItem extends AxeItem {
	public TMoNAxeItem(ToolMaterial material, int damage, float speed, Item.Settings settings) {
		super(material, damage, speed, settings);
	}

}
