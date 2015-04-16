package ess.gt.tactics;

import java.util.ArrayList;
import java.util.List;
import ess.gt.tactics.players.AlexandruCsonka;
import ess.gt.tactics.players.AlexandruMititean;
import ess.gt.tactics.players.AlexandruStan;
import ess.gt.tactics.players.AurelMihai;
import ess.gt.tactics.players.BocuBasca;
import ess.gt.tactics.players.BogdanCislaru;
import ess.gt.tactics.players.DaniBolunlon;
import ess.gt.tactics.players.EronimQuoc;
import ess.gt.tactics.players.FlorinelCristian;
import ess.gt.tactics.players.MadalinAlex;
import ess.gt.tactics.players.MarinescuIuhas;
import ess.gt.tactics.players.MariusBelu;
import ess.gt.tactics.players.NicolaePutinenu;
import ess.gt.tactics.players.PirleaYonutz;
import ess.gt.tactics.players.RomocsaCurelea;
import ess.gt.tactics.players.RonaldAttila;
import ess.gt.tactics.players.SiscuBalan;
import ess.gt.tactics.players.VasilicaPavel;
import ess.gt.tactics.players.VisoiuStan;
import ess.gt.tactics.players.VladutzuAioanei;

public class Team {

    public static Team getInstance() {
        return instance;
    }

    static private final Team  instance = new Team();

    private final List<Player> players  = new ArrayList<>();
    {
        players.add(AlexandruCsonka.getInstance());
        players.add(MarinescuIuhas.getInstance());
        players.add(MadalinAlex.getInstance());
        players.add(VladutzuAioanei.getInstance());
        players.add(AlexandruMititean.getInstance());
        players.add(PirleaYonutz.getInstance());
        players.add(VasilicaPavel.getInstance());
        players.add(BocuBasca.getInstance());
        players.add(MariusBelu.getInstance());
        players.add(FlorinelCristian.getInstance());
        players.add(DaniBolunlon.getInstance());
        players.add(NicolaePutinenu.getInstance());
        players.add(RonaldAttila.getInstance());
        players.add(SiscuBalan.getInstance());
        players.add(EronimQuoc.getInstance());
        players.add(AlexandruStan.getInstance());
        players.add(RomocsaCurelea.getInstance());
        players.add(BogdanCislaru.getInstance());
        players.add(VisoiuStan.getInstance());
        players.add(AurelMihai.getInstance());
    }

    private Team() {
        super();
    }

}
