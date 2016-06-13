package com.bitcup.whoseturn.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * @author bitcup
 */
public class LinearEquationSolverTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinearEquationSolverTest.class);

    static final double DELTA = 0.01;

    @Test
    public void testSolve1() throws Exception {
        double[][] coefficients = {{1, 1, 1, 1}, {1, -2, 0, 0}, {1, 0, -3, 0}, {1, 0, 0, -4}};
        double[][] constants = {{1}, {0}, {0}, {0}};
        double[] probabilities = LinearEquationSolver.solve(coefficients, constants);
        LOGGER.info("probabilities: {}", probabilities);
        assertEquals(4, probabilities.length);
        assertEquals(0.48, probabilities[0], DELTA);
        assertEquals(0.24, probabilities[1], DELTA);
        assertEquals(0.16, probabilities[2], DELTA);
        assertEquals(0.12, probabilities[3], DELTA);
        assertEquals(1.0, probabilities[0] + probabilities[1] + probabilities[2] + probabilities[3], DELTA);
    }

    @Test
    public void testSolve2() throws Exception {
        double[][] coefficients = {{1, 1, 1}, {1, -5, 0}, {1, 0, -3.5}};
        double[][] constants = {{1}, {0}, {0}};
        double[] probabilities = LinearEquationSolver.solve(coefficients, constants);
        LOGGER.info("probabilities: {}", probabilities);
        assertEquals(3, probabilities.length);
        assertEquals(0.67, probabilities[0], DELTA);
        assertEquals(0.13, probabilities[1], DELTA);
        assertEquals(0.19, probabilities[2], DELTA);
        assertEquals(1.0, probabilities[0] + probabilities[1] + probabilities[2], DELTA);
    }
}
