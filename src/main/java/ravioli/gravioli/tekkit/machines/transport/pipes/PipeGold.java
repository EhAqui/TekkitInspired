package ravioli.gravioli.tekkit.machines.transport.pipes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import ravioli.gravioli.tekkit.Tekkit;
import ravioli.gravioli.tekkit.machines.transport.PipeTransportGeneric;

import java.util.List;

public class PipeGold extends Pipe
{
    public PipeGold(Tekkit plugin)
    {
        super(plugin, new PipeTransportGeneric());
    }

    @Override
    public void runMachine()
    {
        transport.update();
    }

    @Override
    public void onEnable()
    {
        updateTask(2);
        getWorld().getNearbyEntities(getLocation(), 2.0, 2.0, 2.0).stream().filter(entity -> entity instanceof ArmorStand && !entity.hasMetadata("display") && ((ArmorStand) entity).isMarker()).forEach(entity -> entity.remove());
    }

    @Override
    public Recipe getRecipe()
    {
        ItemStack item = new ItemStack(Material.STAINED_GLASS, 1, (byte) 4);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Golden Pipe");
        item.setItemMeta(itemMeta);

        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape("WGW");
        recipe.setIngredient('W', Material.GOLD_INGOT);
        recipe.setIngredient('G', Material.GLASS);

        return recipe;
    }

    @Override
    public String getTableName()
    {
        return "GoldPipe";
    }

    @Override
    public String getName()
    {
        return "goldenpipe";
    }

    @Override
    public double getSpeed()
    {
        return 0.15;
    }
}
