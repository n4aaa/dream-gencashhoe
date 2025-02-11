package cc.dreamcode.plugingencashhoe;

import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

@Data
public class HoeItemSerializer implements ObjectSerializer<HoeItem> {

    @Override
    public boolean supports(@NonNull Class<? super HoeItem> type) {
        return HoeItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull HoeItem object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("hoe-id", object.getId());
        data.add("hoe-itemstack", object.getItemStack());
        data.add("hoe-default-lore", object.isDefaultLore());
        data.add("hoe-size", object.getSize());
        data.add("hoe-breakables", object.getBreakables());
    }

    @Override
    public HoeItem deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new HoeItem(
                data.get("hoe-id", Integer.class),
                data.get("hoe-itemstack", ItemStack.class),
                data.get("hoe-default-lore", Boolean.class),
                data.get("hoe-size", Integer.class),
                data.getAsList("hoe-breakables", XMaterial.class)
        );
    }
}