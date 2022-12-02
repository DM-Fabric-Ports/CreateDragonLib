package plus.dragons.createdragonlib.init;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.jetbrains.annotations.ApiStatus;

import com.simibubi.create.foundation.item.CreateItemGroupBase;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

/**
 * Event to add items to Create's creative mode tab.
 */
public class FillCreateItemGroupEvent {

    @FunctionalInterface
    public static interface Callback {
        void listen(FillCreateItemGroupEvent event);
    }

    public static Event<Callback> INSTANCE = EventFactory.createArrayBacked(Callback.class, listeners -> event -> {
        for (Callback listener : listeners)
            listener.listen(event);
    });

    private final CreateItemGroupBase itemGroup;
    private final NonNullList<ItemStack> items;
    private final Map<Item, List<ItemStack>> insertions = new IdentityHashMap<>();

    public FillCreateItemGroupEvent(CreateItemGroupBase itemGroup, NonNullList<ItemStack> items) {
        this.itemGroup = itemGroup;
        this.items = items;
    }

    /**
     * Get the creative mod tab, so you could determine which tab are you adding
     * into.
     * 
     * @return the creative mod tab
     */
    public CreateItemGroupBase getItemGroup() {
        return itemGroup;
    }

    /**
     * Get all Create's items in the tab. <br>
     * For adding items after certain item in the tab,
     * use {@link FillCreateItemGroupEvent#addInsertion(ItemLike, ItemStack)}
     * and {@link FillCreateItemGroupEvent#addInsertions(ItemLike, Collection)} for
     * convenience.
     * 
     * @return a modifiable list of all Create's items in the tab
     */
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    /**
     * Add an {@link ItemStack} after an {@link Item}, should only target Create's
     * existing items in the tab.
     * 
     * @param target the item to target
     * @param stack  the item stack to add
     */
    public void addInsertion(ItemLike target, ItemStack stack) {
        insertions.computeIfAbsent(target.asItem(), $ -> new ArrayList<>()).add(stack);
    }

    /**
     * Add some {@link ItemStack}s after an {@link Item}, should only target
     * Create's existing items in the tab.
     * 
     * @param target the item to target
     * @param stacks the item stacks to add
     */
    public void addInsertions(ItemLike target, Collection<ItemStack> stacks) {
        insertions.computeIfAbsent(target.asItem(), $ -> new ArrayList<>()).addAll(stacks);
    }

    @ApiStatus.Internal
    public void apply() {
        ListIterator<ItemStack> it = items.listIterator();
        while (it.hasNext()) {
            Item item = it.next().getItem();
            if (insertions.containsKey(item)) {
                for (var inserted : insertions.get(item)) {
                    it.add(inserted);
                }
                insertions.remove(item);
            }
        }
    }

}
