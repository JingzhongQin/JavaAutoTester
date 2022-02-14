package dt002g.com.java.test;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;


public class JUnitRunner {
    private final SummaryGeneratingListener listener = new SummaryGeneratingListener();

    public static void main(String[] args) throws ClassNotFoundException {
        JUnitRunner jUnitRunner = new JUnitRunner();
        jUnitRunner.runAll();

        TestExecutionSummary summary = jUnitRunner.listener.getSummary();
        summary.printTo(new PrintWriter(System.out));

        /***********************
         TestSuite mySuite = new ActiveTestSuite();
         JUnitCore junit = new JUnitCore();
         TextListener textListener = new TextListener(new RealSystem());
         junit.addListener(textListener);

         //Get class by "string"
         Class<?> tempClass = Class.forName("ThirdUnitTest");
         System.out.println(tempClass);
         mySuite.addTest(new RepeatedTest(new JUnit4TestAdapter(FirstUnitTest.class), 5));
         mySuite.addTest(new RepeatedTest(new JUnit4TestAdapter(SecondUnitTest.class), 3));
         mySuite.addTest(new RepeatedTest(new JUnit4TestAdapter(tempClass), 3));
         junit.run(tempClass);
         ***********/


    }

    public void runOne(Class<?> c){
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(c)).build();
        Launcher launcher = LauncherFactory.create();
        TestPlan testPlan = launcher.discover(request);
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
    }

    public void runAll(){
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectPackage("dt002g.com.java.testCollection")).filters(includeClassNamePatterns(".*Test")).build();
        Launcher launcher = LauncherFactory.create();
        TestPlan testPlan = launcher.discover(request); // Testing
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
    }
}
