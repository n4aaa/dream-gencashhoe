package cc.dreamcode.plugingencashhoe;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class HoeCreatorItem {
    private int id;

    private ItemStack itemStack;

    private int size;
    private List<XMaterial> breakables;
}
