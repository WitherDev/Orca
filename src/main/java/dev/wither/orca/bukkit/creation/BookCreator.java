package dev.wither.orca.bukkit.creation;

import dev.wither.orca.Orca;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

public class BookCreator extends ItemCreator {

    public BookCreator() {

        super(Material.BOOK);

    }

    public BookCreator setAuthor(String author) {

        BookMeta meta = (BookMeta) getItem().getItemMeta();

        meta.setAuthor(Orca.addColor(author));

        getItem().setItemMeta(meta);

        return this;

    }

    public BookCreator setTitle(String title) {

        BookMeta meta = (BookMeta) getItem().getItemMeta();

        meta.setTitle(Orca.addColor(title));

        getItem().setItemMeta(meta);

        return this;

    }

    public BookCreator setPage(int page, String content) {

        BookMeta meta = (BookMeta) getItem().getItemMeta();

        meta.setPage(page, Orca.addColor(content));

        getItem().setItemMeta(meta);

        return this;

    }

    public BookCreator addPages(String... pages) {

        BookMeta meta = (BookMeta) getItem().getItemMeta();

        val newPages = meta.getPages();

        for (String page : pages) {

            newPages.add(Orca.addColor(page));

        }

        meta.setPages(newPages);

        getItem().setItemMeta(meta);

        return this;

    }

    public BookCreator setPages(List<String> pages) {

        BookMeta meta = (BookMeta) getItem().getItemMeta();

        List<String> colored = Orca.addColor(pages);
        meta.setPages(colored);

        getItem().setItemMeta(meta);

        return this;

    }

    /**
     * Disables the modification of the material for the backing ItemStack, since this should only be used on a Book,
     * @param material provided material.
     * @return this BookCreator.
     */
    @Override
    public BookCreator setType(Material material) {

        return this;

    }

}
