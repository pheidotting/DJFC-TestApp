package nl.lakedigital.djfc.testapp.testcases;

import nl.lakedigital.djfc.testapp.teststappen.*;

public class TestCase1 extends AbstractTestCase {

    @Override
    public void initTestStappen() {
        voegTestStapToe(new OpslaanRelatieEnControleer());
        voegTestStapToe(new ToevoegenAdresEnControleer());
        voegTestStapToe(new ToevoegenRekeningEnControleer());
        voegTestStapToe(new ToevoegenTelefoonnummerEnControleer());

        nieuweTrackAndTraceId();
        voegTestStapToe(new WijzigAdresEnControleer());

        nieuweTrackAndTraceId();
        voegTestStapToe(new ToevoegenAdresEnControleer());

        voegTestStapToe(new ToevoegenPolissenEnControleer());
    }
}
