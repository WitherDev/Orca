package dev.wither.orca.view.builder.integrated;

import dev.wither.orca.view.OrcaPage;
import dev.wither.orca.view.OrcaSlides;
import dev.wither.orca.view.builder.OrcaSlidesBuilder;
import dev.wither.orca.view.touch.Touch;
import lombok.Getter;
import lombok.val;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * SmartSlidesBuilder is an implementation of the OrcaSlidesBuilder integrated into Orca API.
 * The SmartSlidesBuilder takes in a template OrcaPage that will then be populated with the ItemStack and Touch objects.
 * When the template page is full, another page will be created and added to the OrcaSlides so that it can be populated with the remaining objects.
 */
public class SmartSlidesBuilder implements OrcaSlidesBuilder {

    @Getter private final OrcaSlides slides;

    public SmartSlidesBuilder(OrcaPage template, HashMap<ItemStack, Touch> content) {

        val iterator = content.entrySet().iterator();

        slides = new OrcaSlides();

        OrcaPage page = template.clone();

        boolean end = true;
        while (end) {

            if (page.nextEmpty() == -1) {

                slides.addSlide(page.clone());
                page = template.clone();
                continue;

            }

            val entry = iterator.next();

            page.slot(page.nextEmpty(), entry.getValue(), entry.getKey());

            if (!iterator.hasNext()) {

                end = false;
                slides.addSlide(page.clone());
                page = null;

            }

        }



    }

}
