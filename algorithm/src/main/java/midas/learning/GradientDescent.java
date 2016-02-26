package midas.learning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 * Gradient descent algorithm is the most commonly used algorithms for optimization problem. This class implements
 * both the batch gradient descent and stochastic gradient descent algorithms. It is a single node mode.
 *
 * Created by xli1 on 2/10/16.
 */
public class GradientDescent {
  private static final Logger GRADIENT_LOGGER = Logger.getLogger(GradientDescent.class.getName());
  private static double _learningRate = .1;
  private static double _convergeThreshold = .001;

  private InputStream _trainingSetStream = null;
  private List<Double[]> _features = null;
  private int totalFeatures = -1;

  public GradientDescent(InputStream in) {
    _trainingSetStream = in;
    _features = new ArrayList<Double[]>();
  }

  public static void setLearningRate(double rate) {
    _learningRate = rate;
  }

  public static void setConvergeThreshold(double threshold) {
    _convergeThreshold = threshold;
  }

  private void initFeatureMatrix() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(_trainingSetStream));
    totalFeatures = -1;
    try {
      String line = null;
      while ((line = reader.readLine()) != null) {
        String[] elems = line.split("\t");
        if (elems != null && elems.length > 0) {
          Double[] featureList = new Double[elems.length];
          for (int i = 0; i < elems.length; i++) {
            try {
              featureList[i] = Double.parseDouble(elems[i]);
            } catch (Exception e) {
              GRADIENT_LOGGER.info("Cannot parse double: " + elems[i]);
              featureList[i] = .0;
            }
          }
          if (totalFeatures > 0 && totalFeatures != featureList.length - 1) {
            GRADIENT_LOGGER.info("Unmatched number of features in instance: " + line);
          } else {
            if (totalFeatures < 0) {
              totalFeatures = featureList.length - 1;
            }
            _features.add(featureList);
          }
        }
      }
    } catch (IOException e) {
      GRADIENT_LOGGER.severe(e.getMessage());
    }
  }

  private void doGradientDescent(boolean isStochastic) {
    if (totalFeatures < 0 || _features.isEmpty()) {
      return;
    }
    double[] oldWeights = new double[totalFeatures];
    double[] newWeights = new double[totalFeatures];
    boolean converge = false;
    do {
      // update weights


    } while(isConverge(oldWeights, newWeights));
  }

  private void updateWeights(boolean isStochastic, double[] oldWeights, double[] newWeights) {
    if (isStochastic) {
      for (Double[] featureList : _features) {
        double estimate = 0;
        for (int i = 0; i < oldWeights.length; i++) {
          estimate += oldWeights[i] * featureList[i];
        }
        for (int i = 0; i < oldWeights.length; i++) {
          double response = featureList[featureList.length - 1];
          newWeights[i] = oldWeights[i] + _learningRate * Math.abs(estimate - response) * featureList[i];
        }
      }
    }
  }

  private boolean isConverge(double[] oldWeights, double[] newWeights) {
    if (oldWeights == null || newWeights == null || oldWeights.length != newWeights.length) {
      return false;
    }
    for (int i = 0; i < oldWeights.length; i++) {
      if (oldWeights[i] - newWeights[i] > _convergeThreshold) {
        return false;
      }
    }
    return true;
  }
}
