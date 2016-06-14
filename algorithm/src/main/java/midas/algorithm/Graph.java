package midas.algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


public class Graph {
  public List<String> findItinerary(String[][] tickets) {
    HashMap<String, PriorityQueue<String>> adj = new HashMap<String, PriorityQueue<String>>(); // use pq not treeset in case of duplicates
    for (String[] t : tickets) {
      if (!adj.containsKey(t[0])) {
        adj.put(t[0], new PriorityQueue<String>());
      }
      adj.get(t[0]).add(t[1]);
    }
    List<String> ret = new LinkedList<String>();
    dfs(adj, ret, "JFK");
    return ret;
  }
  private void dfs(HashMap<String, PriorityQueue<String>> adj, List<String> ret, String start) {
    PriorityQueue<String> next = adj.get(start);
    while (next != null && !next.isEmpty()) { // this is the key. if it is the destination, next will be null or empty
      dfs(adj, ret, next.poll());
    }
    ret.add(0, start);
  }

//  public List<String> findItineraryIterative(String[][] tickets) {
//    HashMap<String, PriorityQueue<String>> adj = new HashMap<String, PriorityQueue<String>>();
//    for (String[] t : tickets) {
//      if (!adj.containsKey(t[0])) {
//        adj.put(t[0], new PriorityQueue<String>());
//      }
//      adj.get(t[0]).add(t[1]);
//    }
//    List<String> ret = new LinkedList<String>();
//    Stack<String> stack = new Stack<String>();
//
//    return ret;
//  }

}
