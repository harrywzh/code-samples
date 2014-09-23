//CS240 Assignemt 1 
//Question 5
//Harry Zihui Wang
//20436476
//Due 2013/06/05

#include <iostream>

using namespace std;

int n;


int main (){	
	cin >> n;
 	int arr[n];
	int max;
	int min;
	if (n > 0){
		cin >> arr[0];
		max = arr[0];
		min = arr[0];
	}
	//read into array and find max and min values
	for (int i=1; i<n;i++){
		cin >> arr[i];
		if (arr[i] > max) max = arr[i];
		else if (arr[i] < min) min = arr[i];
	}
	//add counts using min as offset
	int k = max - min +1;
	int counts[k];
	for (int i=0; i<k; i++){
		counts[i] = 0;
	}
	for (int i=0; i<n; i++){
		counts[arr[i]-min]++;
	}
	//change counts to indexing
	for (int i=1; i<k; i++){
		counts[i] = counts[i-1] + counts[i];
	}
	int out[n];
	//perform countsort, using offset still
	for (int i=n-1;i>-1;i--){
		out[counts[arr[i]-min]-1] = arr[i];
		counts[arr[i]-min]--;
	}
	for (int i=0; i<n;i++){
		cout << out[i] << endl;
	}
  	return 0;
}



