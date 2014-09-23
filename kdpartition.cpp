//CS240 Assignemt 5
//Question 5a
//Harry Zihui Wang
//20436476
//Due 2013/07/17

#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;

int n;

//Running time (See Answer to 5b for more details):
//   In each recursive call for a subproblem of size k, (suppose the call is for x)
// 	 The forloop iterates k times, each iteration performing at most 2 assignment operations
//   for a run time of 2k which is O(k). The forloop produces the 2 sets of points in
//   each region sorted according to y (as well as partitioning the x sorted input)
// 	 By the statement in Question 5b, the algorithm runs in O(nlogn) as the initial sorts are assumned to be O(nlogn)

struct Point {
	int x;
	int y;
};

bool compX(Point a, Point b){
	return a.x < b.x;
}
bool compY(Point a, Point b){
	return a.y < b.y;
}
//sorts a vector, prints the root (index floor(n/2))  and then
//recursively calls on the two halves of a vector based on a provided "reference"
//that has been sorted using the other parameter
 
void kdtreeY (vector<Point> v, vector<Point> xsort);

void kdtreeX (vector<Point> v, vector<Point> ysort) {
	//v is the input list that has been sorted the appropriate way
	//ysort is the same elements, sorted the other way, used as a reference of the other order
	if (!v.empty()){
		int root = v.size()/2;
		cout << v[root].x << " " << v[root].y << endl;
		if (v.size() > 1){
			vector<Point> xs;
			vector<Point> xl;
			vector<Point> ys;
			vector<Point> yl;
			for (int i=0; i < v.size(); i++) {
				//create two new lists < and > than root, still in sorted order
    			if (i<root){
					xs.push_back(v[i]);
    			} else if (i>root){
					xl.push_back(v[i]);
    			}
    			//use the ysort list and place then in the same order onto
    			//the smaller or larger list, according to those elements in the X value
    			if (ysort[i].x < v[root].x){
					ys.push_back(ysort[i]);
    			} else if (ysort[i].x > v[root].x) {
					yl.push_back(ysort[i]);
    			}
			}
			//make the recursive call
			//the Y lists is now the "input" and the halves of the X lists are
			//now the "sort-order" references
			kdtreeY(ys,xs);
			kdtreeY(yl,xl);
		}
	}
			
}

void kdtreeY (vector<Point> v, vector<Point> xsort) {
	if (!v.empty()){
		int root = v.size()/2;
		cout << v[root].x << " " << v[root].y << endl;
		if (v.size() > 1){
			vector<Point> xs;
			vector<Point> xl;
			vector<Point> ys;
			vector<Point> yl;
			for (int i=0; i < v.size(); i++) {
    			if (i<root){
					ys.push_back(v[i]);
    			} else if (i>root){
					yl.push_back(v[i]);
    			}
    			if (xsort[i].y < v[root].y){
					xs.push_back(xsort[i]);
    			} else if (xsort[i].y > v[root].y) {
					xl.push_back(xsort[i]);
    			}
			}
			kdtreeX(xs,ys);
			kdtreeX(xl,yl);
		}
	}
			
}
int main (){	
	cin >> n;
	//stores points as a pair in a struct
 	Point * pts = new Point[n];
	for (int i=0; i<n;i++){
		cin >> pts[i].x;
		cin >> pts[i].y;
	}

	vector<Point> xsorted (pts, pts+n);
	vector<Point> ysorted (pts,pts+n);
	stable_sort(xsorted.begin(), xsorted.end(), compX);
	stable_sort(ysorted.begin(), ysorted.end(), compY);

	//starts off by partitioning on X sort
	kdtreeX(xsorted, ysorted);
	


  	return 0;
}


