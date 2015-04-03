package ess.mg.optimizers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.Timer;
import ess.mg.driver.MgWebReader;
import ess.mg.driver.MgWebReader.Newspaper;

public class NewspaperOptimizer {

    public static void main(final String... args) {
        new NewspaperOptimizer(10).createReport();
    }

    private final int paperCount;
    private final int timeout;

    public NewspaperOptimizer(final int paperCount) {
        this(paperCount, 0);
    }

    public NewspaperOptimizer(final int paperCount, final int timeout) {
        this.paperCount = paperCount;
        this.timeout = timeout;
    }

    public List<Newspaper> createReport() {
        final MgWebReader reader = new MgWebReader();

        if (timeout > 0) {
            final Timer timer = new Timer(timeout, new ActionListener() {

                @Override
                public void actionPerformed(final ActionEvent e) {
                    reader.close();
                    System.exit(1);
                }

            });
            timer.setRepeats(false);
            timer.start();
        }

        final Set<Newspaper> papers = new HashSet<>();
        reader.login();
        for (int index = 1; index <= paperCount; ++index) {
            final Newspaper paper = reader.fetchNewspaper(index);
            if (paper.getPrice() != null) {
                papers.add(paper);
            }
        }
        reader.close();

        final List<Newspaper> top = new ArrayList<>(papers);
        Collections.sort(top, new Comparator<Newspaper>() {

            @Override
            public int compare(final Newspaper o1, final Newspaper o2) {
                int cmp = o1.getPrice().compareTo(o2.getPrice());
                if (cmp == 0) {
                    cmp = o2.getSold().compareTo(o1.getSold());
                    if (cmp == 0) {
                        cmp = o2.getVotes().compareTo(o1.getVotes());
                    }
                }
                return cmp;
            }

        });

        for (final Newspaper paper : top) {
            System.out.println(paper);
        }

        return top;
    }

}
