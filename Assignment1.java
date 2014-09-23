//CS3230 Assignment 1
//Zihui Wang
//A0120868

import java.io.*;
import java.util.*;

class Assignment1 { // in Mooshak online judge, make sure that Java file name = class name that contains Main method
  public static void main(String[] args) {
    IntegerScanner sc = new IntegerScanner(System.in);
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out))); // use this (a much faster output routine) instead of Java System.out.println (slow)

//--BEGIN OWN CODE
	
	//TreeSet<Integer> scoreTree = new TreeSet<Integer>();
	ArrayList<Integer> scores = new ArrayList<Integer>();
	HashMap<Integer,Integer> rankMap = new HashMap<Integer,Integer>();
	int counter = 0;
    boolean stillMore = true;
	int size = 0;
	AvlTree t = new AvlTree();

	while (true) {
		int CMD = sc.nextInt(), V = sc.nextInt();
		//System.out.println("Input " + CMD + " " + V);
		
		if (CMD == 5) break;
		if (CMD == 1){
			t.insert(V);
			size++;
		} else if (CMD == 2){
			t.remove(V);
			size--;
		} else if (CMD == 3){
			
			if (V > size){
				pr.printf("-1\n");
			} else {
				pr.printf(t.getAtIndex(V-1) + "\n");
			}
		} else if (CMD == 4){
			//Integer rank = rankMap.get(V);
			//pr.printf((rank==null ? -2 : rank) + "\n");	 
			pr.printf((t.findIndex(t.root, 0, V)+1) + "\n");
		}
    }

    pr.close(); // do not forget to use this
  }

}


class AvlTree {

 protected Node root; 
 
 public void insert(int k) {
  Node n = new Node(k);
  insertAVL(this.root,n);
 }
 
 public void insertAVL(Node p, Node q) {
  
  if(p==null) {
	this.root=q;
  } else {
   

	   if(q.key<p.key) {
			if(p.left==null) {
				p.left = q;
				q.parent = p;
				recursiveBalance(p);
			} else {
				insertAVL(p.left,q);
				setHeight(p);
				
			 //setSize(p);
			 //p.size = p.size+1;
			}
		
	   } else if(q.key>p.key) {
			if(p.right==null) {
				 p.right = q;
				 q.parent = p;
				 recursiveBalance(p);
			} else {
				insertAVL(p.right,q);
				setHeight(p);
			
			 //setSize(p);
			 //p.size = p.size+1;
			}
	   } 
	   
  }
 }
 
 public void recursiveBalance(Node cur) {
  setHeight(cur);
  setBalance(cur);
  int balance = cur.balance;

  if(balance==-2) {
   
   if(height(cur.left.left)>=height(cur.left.right)) {
    cur = rotateRight(cur);
   } else {
    cur = doubleRotateLeftRight(cur);
   }
  } else if(balance==2) {
   if(height(cur.right.right)>=height(cur.right.left)) {
    cur = rotateLeft(cur);
   } else {
    cur = doubleRotateRightLeft(cur);
   }
  }
	setSize(cur);
	
  if(cur.parent!=null) {
   recursiveBalance(cur.parent);
  } else {
   this.root = cur;
  }
 }


 public void remove(int k) {
  removeAVL(this.root,k);
 }

 public void removeAVL(Node p,int q) {
  if(p==null) {
   return;
  } else {
   if(p.key>q)  {
    removeAVL(p.left,q);
   } else if(p.key<q) {
    removeAVL(p.right,q);
   } else if(p.key==q) {
    removeFoundNode(p);
   }
  }
 }
 

 public void removeFoundNode(Node q) {
  Node r;
 
  if(q.left==null || q.right==null) {

	   if(q.parent==null) {
		this.root=null;
		q=null;
		return;
	   }
	   r = q;
  } else {
   
	   r = successor(q);
	   q.key = r.key;
  }
  
  Node p;
  if(r.left!=null) {
		p = r.left;
  } else {
		p = r.right;
  }
  
	if(p!=null) {
		p.parent = r.parent;
	}
  
	  if(r.parent==null) {
			this.root = p;
	  } else {
			if(r==r.parent.left) {
				r.parent.left=p;
			} else {
				r.parent.right = p;
			}  
			recursiveBalance(r.parent);
	  }
  r = null;
 }
 
 public Node rotateLeft(Node n) {
  
  Node v = n.right;
  v.parent = n.parent;
  
  n.right = v.left;
  
  if(n.right!=null) {
   n.right.parent=n;
  }
  
  v.left = n;
  n.parent = v;
  
  if(v.parent!=null) {
   if(v.parent.right==n) {
    v.parent.right = v;
   } else if(v.parent.left==n) {
    v.parent.left = v;
   }
  }
  
  setBalance(n);
  setBalance(v);
  setSize(n);
  setSize(v);
  setHeight(n);
  setHeight(v);
  return v;
 }
 
 public Node rotateRight(Node n) {
  
  Node v = n.left;
  v.parent = n.parent;
  
  n.left = v.right;
  
  if(n.left!=null) {
   n.left.parent=n;
  }
  
  v.right = n;
  n.parent = v;
  
  
  if(v.parent!=null) {
   if(v.parent.right==n) {
    v.parent.right = v;
   } else if(v.parent.left==n) {
    v.parent.left = v;
   }
  }
  
  setBalance(n);
  setBalance(v);
  setSize(n);
  setSize(v);
  setHeight(n);
  setHeight(v);
  return v;
 }

 public Node doubleRotateLeftRight(Node u) {
  u.left = rotateLeft(u.left);
  return rotateRight(u);
 }

 public Node doubleRotateRightLeft(Node u) {
  u.right = rotateRight(u.right);
  return rotateLeft(u);
 }
 

 public Node successor(Node q) {
  if(q.right!=null) {
   Node r = q.right;
   while(r.left!=null) {
    r = r.left;
   }
   return r;
  } else {
   Node p = q.parent;
   while(p!=null && q==p.right) {
    q = p;
    p = q.parent;
   }
   return p;
  }
 }
 

 private int height(Node cur) {
  if(cur==null) {
   return -1;
  }
  if(cur.left==null && cur.right==null) {
   return 0;
  } else if(cur.left==null) {
   return 1+height(cur.right);
  } else if(cur.right==null) {
   return 1+height(cur.left);
  } else {
   return 1+getMax(height(cur.left),height(cur.right));
  }
 }
 
 private int calcSize(Node cur) {
	  if(cur==null) {
			return 0;
	  } else {
			return 1+calcSize(cur.left)+calcSize(cur.right);
	  }
 }
 private int size(Node cur) {
	if (cur!=null) {
		if (cur.left == null){
			return 1 + (cur.right==null ? 0 : cur.right.size);
		} else if (cur.right == null){
			return 1 + cur.left.size;
		} else {
			return 1 + cur.left.size+cur.right.size;
		}
	} else {
			return 0;
	 } 
 }
 public int getAtIndex(int i){
	return getIndex(this.root, 0, i); 
 }
 public int getIndex(Node n, int prev, int i){
	//printNode(n);
	if (n == null){
		return -1001;
	} else {
		
		int rightIndex = getRightIndex(n);
		//System.out.println(rightIndex + " " + prev);
		if (i == rightIndex + prev){
			return n.key;
		} else if (i < rightIndex + prev){
			return getIndex(n.right, prev, i);
		} else {
			return getIndex(n.left, rightIndex + prev + 1, i);
		}
	}
}
	public int findIndex(Node n, int prev, int V){
		//printNode(n);
		if (n == null) {
			return -3;
		} else {
			if (n.key == V){
				return prev + getRightIndex(n);
			} else if (V < n.key){
				return findIndex(n.left, prev + getRightIndex(n) + 1, V);
			} else {
				return findIndex(n.right, prev, V);
			}
		}	
	}
	private int getRightIndex(Node n){
		return (n.right==null ? 0 : n.right.size);
	}
	private int getLeftIndex(Node n){
		return (n.left==null ? 0 : n.left.size);
	}
	
	private void printNode(Node n){
		if (n!=null){
			System.out.println("Node is " + n.key + " / size " + n.size);
			if (n.left == null && n.right == null) System.out.println("null null");
			else if (n.left == null) System.out.println("null " + n.right.key);
			else if (n.right == null) System.out.println(n.left.key + " null");
			else System.out.println(n.left.key + " " + n.right.key);
		} else {
			System.out.println("Node NULL");
		}
	}
	

 private void setSize(Node cur){
	cur.size = size(cur);
 }


 private int getMax(int a, int b) {
	return ((a<b) ? b : a);
 }
 private void setBalance(Node cur) {
  cur.balance = getHeight(cur.right)-getHeight(cur.left);
 } 

 private int calcHeight(Node cur) {
	if (cur==null) {
		return -1;
	} else {
		return 1 + getMax(calcHeight(cur.left),calcHeight(cur.right));
	}
 } 
 private int getHeight(Node cur) {
	if (cur==null) {
		return -1;
	} else {
		return cur.height;
	}
 } 
private void setHeight(Node cur) {
	if (cur!=null) {
		if (cur.left == null){
			cur.height = (cur.right==null ? 0 : 1 + cur.right.height);
		} else if (cur.right == null){
			cur.height = 1 + cur.left.height;
		} else {
			cur.height = 1 + getMax(cur.left.height,cur.right.height);
		}
	}
 } 
 
}


class Node {
 public Node left;
 public Node right;
 public Node parent;
 public int key;
 public int balance;
 public int size;
 public int height;
 
 public Node(int k) {
  left = right = parent = null;
  balance = 0;
  key = k;
  height = 0;
  size = 1;
 }
 public String toString() {
  return "" + key;
 }

}


//--END OWN CODE

class IntegerScanner { // use this (a much faster input routine) instead of standard Java Scanner class (slow)
  BufferedInputStream bis;
  IntegerScanner(InputStream is) {
    bis = new BufferedInputStream(is, 1000000);
  }
  
  public int nextInt() {    
    int result = 0;
    try {
      int cur = bis.read();
      if(cur == -1)
        return -1;
      
      while(cur < 48 || cur > 57) {
        cur = bis.read();
      }
      while(cur >= 48 && cur <= 57) {
        result = result * 10 + (cur - 48);
        cur = bis.read();
      }
      return result;
    }
    catch(IOException ioe) {
      return -1;
    }
  }
}