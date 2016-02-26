package midas.algorithm;
import java.util.List;


/**
 * Created by xli1 on 12/1/15.
 */
public class Misc {
  public static void main(String[] args) {

  }
}

class Vector2D {
  private List<List<Integer>> list;
  private int outerCurser = 0;
  private int innerCurser = 0;

  public Vector2D(List<List<Integer>> vec2d) {
    list = vec2d;
    while(outerCurser < list.size() && innerCurser >= list.get(outerCurser).size()) {
      outerCurser++;
    }
  }

  public int next() {
    int val = 0;
    if (hasNext()) {
      val = list.get(outerCurser).get(innerCurser);
      innerCurser++;
      while (outerCurser < list.size() && innerCurser >= list.get(outerCurser).size()) {
        outerCurser++;
        innerCurser= 0;
      }
      return val;
    }
    throw new ArrayIndexOutOfBoundsException();
  }

  public boolean hasNext() {
    return list != null && outerCurser < list.size() && innerCurser < list.get(outerCurser).size();
  }
}




