package com.model;

import org.junit.Test;

import com.model.enums.MarketAlertStates;
import com.model.main.marketAlertUMApp;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;

import java.util.Random;

/**
 * Unit test for simple App.
 */
public class MarketAlertUMModelTest implements FsmModel{
    // SUT
    private marketAlertUMApp SUT = new marketAlertUMApp();

    // State variables
    private MarketAlertStates modelAlertUM = MarketAlertStates.LOGIN_PAGE;

    @Override
    public Object getState() {
        return modelAlertUM;
    }

    @Override
    public void reset(boolean rest_fsm) {
        if (rest_fsm) { 
            SUT = new marketAlertUMApp();
        }
    }

    public boolean userValidLoginGuard() { 
        return getState().equals(MarketAlertStates.LOGIN_PAGE);
    }

    public @Action void userValidLogin() { 
        // updating SUT
        SUT.userValidLogin();

        // updating model 
        modelAlertUM = MarketAlertStates.ALERT_PAGE;
    }
   
    public boolean userLoggedOutGuard() { 
        return !getState().equals(MarketAlertStates.LOGIN_PAGE);
    }

    public @Action void userLoggedOut() { 
        SUT.userLoggedOut();
    }

    // Test runner 
    @Test 
    public void MarketAlertUMModelRunner() { 
        final GreedyTester tester = new GreedyTester(new MarketAlertUMModelTest());
        tester.setRandom(new Random());
        tester.buildGraph();
        tester.addListener(new StopOnFailureListener());
        tester.addListener("verbose"); 
		tester.addCoverageMetric(new TransitionPairCoverage());
		tester.addCoverageMetric(new StateCoverage()); 
		tester.addCoverageMetric(new ActionCoverage());
		tester.generate(500); 
		tester.printCoverage();
    }
}
