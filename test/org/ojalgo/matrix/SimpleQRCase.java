/*
 * Copyright 1997-2015 Optimatika (www.optimatika.se)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.ojalgo.matrix;

import java.math.BigDecimal;

import org.ojalgo.TestUtils;
import org.ojalgo.matrix.decomposition.QR;
import org.ojalgo.matrix.store.MatrixStore;

/**
 * Gilbert Strang, Linear Algebra and its Applications III, Problem 3.4.16
 *
 * @author apete
 */
public class SimpleQRCase extends BasicMatrixTest {

    public static BigMatrix getFactorQ() {
        final BigMatrix tmpMtrx = BigMatrix.FACTORY.rows(new double[][] { { 1.0 / 3.0, 0.0 }, { 2.0 / 3.0, 1.0 / Math.sqrt(2.0) },
                { 2.0 / 3.0, -1.0 / Math.sqrt(2.0) } });
        return tmpMtrx.enforce(DEFINITION);
    }

    public static BigMatrix getFactorR() {
        final BigMatrix tmpMtrx = BigMatrix.FACTORY.rows(new double[][] { { 3.0, 3.0 }, { 0.0, Math.sqrt(2.0) } });
        return tmpMtrx.enforce(DEFINITION);
    }

    public static BigMatrix getOriginal() {
        final BigMatrix tmpMtrx = BigMatrix.FACTORY.rows(new double[][] { { 1.0, 1.0 }, { 2.0, 3.0 }, { 2.0, 1.0 } });
        return tmpMtrx.enforce(DEFINITION);
    }

    public SimpleQRCase() {
        super();
    }

    public SimpleQRCase(final String arg0) {
        super(arg0);
    }

    @Override
    public void testData() {

        myExpMtrx = SimpleQRCase.getOriginal();
        final BigMatrix tmpFactorQ = SimpleQRCase.getFactorQ();
        final BigMatrix tmpFactorR = SimpleQRCase.getFactorR();
        myActMtrx = tmpFactorQ.multiply(tmpFactorR);

        TestUtils.assertEquals(myExpMtrx, myActMtrx, EVALUATION);
    }

    @Override
    public void testProblem() {

        // QR

        final QR<BigDecimal> tmpQR = QR.makeBig();
        tmpQR.compute(SimpleQRCase.getOriginal().toBigStore());

        final MatrixStore<BigDecimal> tmpQ = tmpQR.getQ();
        final MatrixStore<BigDecimal> tmpR = tmpQR.getR();

        myExpMtrx = SimpleQRCase.getOriginal();
        myActMtrx = BigMatrix.FACTORY.copy(tmpQ.multiply(tmpR));

        TestUtils.assertEquals(myExpMtrx, myActMtrx, EVALUATION);

        // Q

        myExpMtrx = SimpleQRCase.getFactorQ();
        myActMtrx = BigMatrix.FACTORY.copy(tmpQ);

        // TODO JUnitUtils.assertEquals(myExpected, myActual);

        // R

        myExpMtrx = SimpleQRCase.getFactorR();
        myActMtrx = BigMatrix.FACTORY.copy(tmpR);

        // TODO JUnitUtils.assertEquals(myExpected, myActual);
    }

    @Override
    protected void setUp() throws Exception {

        DEFINITION = DEFINITION.newScale(18);
        EVALUATION = EVALUATION.newScale(9).newPrecision(15);

        myBigAA = SimpleQRCase.getFactorQ();
        myBigAX = SimpleQRCase.getFactorR();
        myBigAB = SimpleQRCase.getOriginal();

        myBigI = BasicMatrixTest.getIdentity(myBigAA.countRows(), myBigAA.countColumns(), EVALUATION);
        myBigSafe = BasicMatrixTest.getSafe(myBigAA.countRows(), myBigAA.countColumns(), EVALUATION);

        super.setUp();
    }

}
