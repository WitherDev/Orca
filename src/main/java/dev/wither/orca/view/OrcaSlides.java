package dev.wither.orca.view;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class OrcaSlides {

    @Getter @Setter private int current;
    @Getter private final List<OrcaPage> slides;

    public OrcaSlides() {

        slides = new ArrayList<>();

        current = 0;

    }

    public OrcaSlides(OrcaPage page) {

        slides = new ArrayList<>();
        slides.add(page);

        current = 0;

    }

    public OrcaSlides(List<OrcaPage> slides) {

        this.slides = slides;

        current = 0;

    }

    public void addSlide(OrcaPage page) {

        slides.add(page);

    }

    public void addSlide(int position, OrcaPage page) {

        slides.add(position, page);

    }

    public void deleteSlide(OrcaPage page) {

        slides.remove(page);

    }

    public OrcaPage next() {

        return next(1);

    }

    public OrcaPage next(int steps) {

        int max = slides.size()-1;
        int next = current + steps;

        if (next > max) return null;

        setCurrent(next);
        return slides.get(current);

    }

    public OrcaPage back() {

        return back(1);

    }

    public OrcaPage back(int steps) {

        int next = current - steps;

        if (next < 0) return null;

        setCurrent(next);
        return slides.get(current);

    }

    public OrcaPage getCurrentSlide() {

        return slides.get(getCurrent());

    }

}
