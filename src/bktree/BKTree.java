/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bktree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Emmanuel
 */
public class BKTree {
 private static final int TOL = 3;
    	private Node root;
    	private List<Node> nodes = new ArrayList<Node>();
        
        private BKTree(){
     
    	}
        
        public static void main(String[] args) {
    		String dictionary[] = {"hell","help","shel","smell",
                    "fell","felt","oops","pop","oouch","halt"};
    		BKTree tree = BKTree.buildBKTree(dictionary);
    		System.out.println(tree.getSimilarWords("ops"));
    		System.out.println(tree.getSimilarWords("helt"));
    	}
     
    	
     
    	public static BKTree buildBKTree(final String[] dictWords){
    		BKTree tree = new BKTree();
    		tree.root = new Node(dictWords[0]);
    		for(int i=1; i< dictWords.length; i++){
    			makeBKTree(tree,tree.root,dictWords[i]);
    		}
    		return tree;
    	}
     
    	private static void makeBKTree(BKTree tree, Node root, String curWord) {
    		int distance = findMinimumEditDistance(curWord,root.word);
    		Integer ptr = root.nodePos.get(distance);
    		if(null == ptr){
    			tree.nodes.add(new Node(curWord));
    			root.nodePos.put(distance,tree.nodes.size()-1);
    		}
    		else {
    			makeBKTree(tree, tree.nodes.get(ptr), curWord);
    		}
    	}
     
    	public List<String> getSimilarWords(String search){
           List<String> result = new ArrayList<String>();
           getSimilarWords(root,result,search);
           return result;
    	}
     
    	private void getSimilarWords(Node root, List<String> result, String search) {
    		int distance = findMinimumEditDistance(search,root.word);
    		if(distance <= TOL){
    			result.add(root.word);
    		}
    		int start = distance - TOL;
    		if(start < 0){
    			start = 1;
    		}
    		while(start < distance + TOL){
    			Integer ptr = root.nodePos.get(start);
    			if(null != ptr){
    				getSimilarWords(nodes.get(ptr), result, search);
    			}
    			start ++;
    		}
     
    	}
     
    	private static class Node {
    		private final String word;
    		private final Map<Integer,Integer> nodePos; 
    		private Node(final String word) {
    			this.word = word;
    			nodePos = new HashMap<Integer, Integer>();
    		}
    	}
     
     
    	
     
    	private static int findMinimumEditDistance(String src,String dest){
    		int sLength = src.length();
    		int dLength = dest.length();
    		int[][] editDP = new int[sLength+1][dLength+1];
    		for(int s=0; s <= sLength; s++){
    			for(int d=0; d<= dLength; d++){
    				if(0 == s){
    					editDP[s][d] = d;
    				}
    				else if(0 == d){
    					editDP[s][d] = s;
    				}
    				else if(src.charAt(s-1) ==  dest.charAt(d-1)){
    					editDP[s][d] = editDP[s-1][d-1];
    				}
    				else {
     
    					editDP[s][d] = 1 + Math.min(Math.min(editDP[s][d-1],editDP[s-1][d]),editDP[s-1][d-1]);
    				}		
    			}
    		}
    		return editDP[sLength][dLength];		
    	}
}