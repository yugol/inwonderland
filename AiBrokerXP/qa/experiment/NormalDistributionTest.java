package experiment;

import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.junit.Test;

public class NormalDistributionTest {

    @Test
    public void test() {
        final AbstractRealDistribution dist = new NormalDistribution();
        // System.out.println(Arrays.toString(dist.sample(10)));
        System.out.println(dist.probability(-1, +1));
        System.out.println(dist.inverseCumulativeProbability(0.5));
        for (double x = 1; x < 2; x += 0.01) {
            System.out.println(dist.probability(-x, +x) + " / " + x);
        }
    }

}
