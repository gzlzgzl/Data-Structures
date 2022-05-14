package comp2011.project;
import java.util.Scanner;
public class Q2_20104379d_GaoZhilin{
	/*
	 *The idea is to convert the compact representation to its orignal matrix,
	 *and then sort the rows using a modified version of quicksort. The reason
	 *is that comparisons between two rows take O(n) time (n: number of rows),
	 *but those between a row and a "pivot" only take O(1) time. In each iteration,
	 *we separate the rows into two groups, one containing 0 and the other
	 *containing 1 at some specific column. 
	 *
	 *The time comlexity is O(n^2) because constructing the original matrix
	 *takes O(n^2) time, and sorting the rows take O(n^2) time in the worst case and
	 *O(n log n) time normally. On average, this is linear to the length of
	 *the input. The length of the compact representation is the number of 1s
	 *in the original matrix, which is on average (n^2)/2. Thus, this
	 *algorithm runs in the most efficient asymptotic time, although the
	 *constant factor may not be optimal.
	 *
	 *The space complexity is O(n^2). Storing the original matrix takes O(n^2) space,
	 *and storing the list of 0 to n-1 (each refering to a row to be sorted) takes O(n) space.
	 *
	 *ps: this solution also works for non-symmetric matrices.
	 */
	final static int maxn=100;//maximum number of rows
	private static int a[][]=new int[maxn][maxn];//original binary matrix
	private static class Pair{
		public int x,y;
		public Pair(int x, int y){
			this.x=x;this.y=y;
		}
	}
	private static Pair qsort(int[] list, int l, int r, int iteration, int maxiteration){
		//the i^th iteration (count from 0) on the interval [l,r] (inclusive, in which identical rows potentially exist)
		//separates the row list into two parts
		//part 1: column i of the row is 0, part 2: column i of the row is 1
		
		/*
		for(int i=0;i<maxn;i++)System.out.print(list[i]+", ");
		System.out.println("iteration "+iteration+" l="+l+" r="+r);
		*/
		
		if(l>=r)return null;//the interval contains only one row, then there are no identical rows
		
		//after n iterations, if an interval has size >=2, all rows it contains must be identical
		if(iteration>maxiteration)
			return new Pair(list[l], list[l+1]);
		
		//partition the list by moving smaller rows to the left, larger ones to the right
		int i=l,j=r;
		while(true){
			//if all rows belong to the same part,start the next iteration on the whole interval
			while(i<=r && a[list[i]][iteration]==0)i++;
			if(i==r+1)
				return qsort(list, l, r, iteration+1, maxiteration);
			while(j>=l && a[list[j]][iteration]==1)j--;
			if(j==l-1)
				return qsort(list, l, r, iteration+1, maxiteration);
				
			if(i<j){
				int temp=list[i];
				list[i]=list[j];
				list[j]=temp;
			}else{
				while(a[list[i]][iteration]==1)i--;
				j=i+1;
				break;
			}
		}
		
		//start the next iteration in both parts
		Pair ans=qsort(list, l, i, iteration+1, maxiteration);
		if(ans!=null)return ans;
		ans=qsort(list, j, r, iteration+1, maxiteration);
		return ans;
	}
	
	public static void main(String args[]){
		Scanner scanner=new Scanner(System.in);
		System.out.print("Input matrix:");
		String s=scanner.nextLine();
		s=s.replace(" ","").replace("[]","[-1]");
		s=s.substring(2,s.length()-2);
		int n=0;
		for(String st:s.split("],\\[")){
			for(String str:st.split(",")){
				int m=Integer.parseInt(str);
				if(m==-1)break;
				a[n][m]=1;
			}
			n++;
		}
		int[] list=new int[maxn];
		for(int i=0;i<n;i++)list[i]=i;
		Pair ans=qsort(list,0,n-1,0,n-1);
		if(ans==null)System.out.println("(0,0)");
		else System.out.println("("+ans.x+","+ans.y+")");
	}
}