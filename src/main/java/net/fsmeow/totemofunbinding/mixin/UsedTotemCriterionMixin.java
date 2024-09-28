package net.fsmeow.totemofunbinding.mixin;

import net.minecraft.advancement.criterion.UsedTotemCriterion;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(UsedTotemCriterion.class)
public abstract class UsedTotemCriterionMixin {
    @Inject(method="trigger", at=@At("TAIL"))
    public void trigger(ServerPlayerEntity player, ItemStack stack, CallbackInfo ci) {
        var playerArmor = player.getInventory().armor;
        for (ItemStack itemStack : playerArmor) {
            if (itemStack.getEnchantments().getLevel(player.getWorld().getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.BINDING_CURSE)) > 0) {
                player.dropItem(itemStack.copy(), true, true);
                itemStack.decrement(itemStack.getCount());
            }
        }
    }
}
