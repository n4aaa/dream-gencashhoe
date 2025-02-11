package cc.dreamcode.plugingencashhoe;

import com.cryptomorin.xseries.XMaterial;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
public class HoeItem {
    private final int id;

    private final ItemStack itemStack;

    private final boolean defaultLore;

    private final int size;
    private final List<XMaterial> breakables;
}
