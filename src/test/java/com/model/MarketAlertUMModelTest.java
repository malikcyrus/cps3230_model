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
import static org.junit.Assert.assertEquals;

public class MarketAlertUMModelTest implements FsmModel{
    // SUT
    private marketAlertUMApp SUT = new marketAlertUMApp();
    private boolean userLoggedOn = false; 

    // State variables
    private MarketAlertStates modelAlertUM = MarketAlertStates.LOGIN_PAGE;

    public MarketAlertStates getState() {
        return modelAlertUM;
    }

    public void reset(boolean rest_fsm) {
        if (rest_fsm) { 
            SUT = new marketAlertUMApp();
        }
        modelAlertUM = MarketAlertStates.LOGIN_PAGE;
        userLoggedOn = false;
    }

    public boolean userValidLoginGuard() { 
        return getState().equals(MarketAlertStates.LOGIN_PAGE);
    }

    public @Action void userValidLogin() { 
        // updating SUT
        SUT.userValidLogin();

        // updating model 
        modelAlertUM = MarketAlertStates.ALERT_PAGE;
        userLoggedOn = true;

        assertEquals("The SUT does not match the model after logging In", userLoggedOn, SUT.isUserLoggedOn());
    }
   
    public boolean userLoggedOutGuard() { 
        return !getState().equals(MarketAlertStates.LOGIN_PAGE);
    }

    public @Action void userLoggedOut() { 
        SUT.userLoggedOut();

        // updating model 
        modelAlertUM = MarketAlertStates.LOGIN_PAGE;
        userLoggedOn = false;

        assertEquals("The SUT does not match the model after logging Out", userLoggedOn, SUT.isUserLoggedOn());
    }

    public boolean alertCreatedGuard() { 
        return getState().equals(MarketAlertStates.CREATE_ALERTS);
    }

    public @Action void alertCreated() { 
        SUT.alertCreated();

        // updating model 
        modelAlertUM = MarketAlertStates.CREATE_ALERTS;
    }

    public boolean alertsDeletedGuard() { 
        return getState().equals(MarketAlertStates.DELETE_ALERTS);
    }

    public @Action void alertsDeleted() { 
        SUT.alertsDeleted();

        // updating Model
        modelAlertUM = MarketAlertStates.DELETE_ALERTS;
    }

    public boolean userViewedAlertsGuard() { 
        return getState().equals(MarketAlertStates.ALERT_PAGE);
    }

    public @Action void userViewedAlerts() { 
        SUT.userViewedAlerts();

        // updating model 
        modelAlertUM = MarketAlertStates.ALERT_PAGE;
        userLoggedOn = true; 

        assertEquals("The SUT does not match the model after viewing alerts", userLoggedOn, SUT.isUserLoggedOn());
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
		tester.generate(100); 
		tester.printCoverage();
    }
}
