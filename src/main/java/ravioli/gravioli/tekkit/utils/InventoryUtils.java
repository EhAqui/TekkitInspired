package ravioli.gravioli.tekkit.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryUtils
{
    public static String itemStackArrayToBase64(ItemStack... items) throws IllegalStateException
    {
        try
        {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(items.length);

            // Save every element in the list
            for (int i = 0; i < items.length; i++)
            {
                dataOutput.writeObject(items[i]);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e)
        {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException
    {
        try
        {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            // Read the serialized inventory
            for (int i = 0; i < items.length; i++)
            {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException e)
        {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public static boolean isInventoryEmpty(Inventory inventory)
    {
        for (ItemStack item : inventory.getContents())
        {
            if (item != null)
            {
                return false;
            }
        }
        return true;
    }

    public static ItemStack getFirstItemInInventory(Inventory inventory)
    {
        for (ItemStack item : inventory.getContents())
        {
            if (item != null)
            {
                return item;
            }
        }
        return null;
    }

    public static List<ItemStack> getNonNullInventoryContents(Inventory inventory)
    {
        List<ItemStack> inventoryItems = new ArrayList(Arrays.asList(inventory.getContents()));
        inventoryItems.removeAll(Arrays.asList("", null));
        return inventoryItems;
    }

    public static boolean canFitIntoInventory(Inventory inventory, ItemStack... items)
    {
        Inventory inv = Bukkit.createInventory(null, inventory.getSize());
        inv.setContents(inventory.getContents().clone());

        boolean fits = true;
        for (ItemStack item : items.clone())
        {
            int amount = item.getAmount();
            if (!inv.addItem(item).isEmpty())
            {
                fits = false;
                item.setAmount(amount);
                break;
            }
        }
        inv = null;

        return fits;
    }
}