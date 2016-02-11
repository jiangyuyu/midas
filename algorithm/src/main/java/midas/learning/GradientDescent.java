package midas.learning;

import java.io.InputStream;

/**
 * Gradient descent algorithm is the most commonly used algorithms for optimization problem. This class implements
 * both the batch gradient descent and stochastic gradient descent algorithms. It is a single node mode.
 *
 * Created by xli1 on 2/10/16.
 */
public class GradientDescent {
  private final double _learningRate = .1;
  private InputStream _trainingSetStream = null;

  public GradientDescent(InputStream in) {
    _trainingSetStream = in;
  }

}
