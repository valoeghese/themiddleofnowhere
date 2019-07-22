package tmon.items;

import java.util.function.Supplier;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;

public class TMoNToolMaterial implements ToolMaterial {
	private final int miningLevel;
	private final int itemDurability;
	private final float miningSpeed;
	private final float attackDamage;
	private final int enchantability;
	private final Lazy<Ingredient> repairIngredient;

	public TMoNToolMaterial(int miningLevel, int itemDurebility, float miningSpeed, float attackDamage, int enchantablity, Supplier<Ingredient> repairIngredient) {
		this.miningLevel = miningLevel;
		this.itemDurability = itemDurebility;
		this.miningSpeed = miningSpeed;
		this.attackDamage = attackDamage;
		this.enchantability = enchantablity;
		this.repairIngredient = new Lazy<>(repairIngredient);
	}

	@Override
	public int getDurability() {
		return this.itemDurability;
	}

	@Override
	public float getMiningSpeed() {
		return this.miningSpeed;
	}

	@Override
	public float getAttackDamage() {
		return this.attackDamage;
	}

	@Override
	public int getMiningLevel() {
		return this.miningLevel;
	}

	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}
}
