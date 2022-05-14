package comp2011.project;
import java.util.Scanner;
public class Q1_20104379d_GaoZhilin{
	/*
	 *We use a min-heap to maintain the order of the arrays.
	 *Instead of the lengths, we store indices in the original group.
	 *The two shortest arrays can be found by two remove root operations.
	 *After that, we insert the new array into the heap.
	 *To save some time, we directly save the result into the second array,
	 *replacing remove root and insert operation into a single update root.
	 *
	 *The time complexity is O(nk). (n: total length of arrays, k: number of arrays)
	 *Each element is copied k-1 times, which takes O(nk) time.
	 *Using a heap to maintain the lengths involves k-1 iterations, each being O(log k), so it takes O(k log k) time.
	 *Since k<=n, O(nk + k log k) is O(nk).
	 *
	 *The amount of extra space needed is O(n+k).
	 *O(n) for a temparary array in merging, O(k) for the heap.
	 *Since the arrays have various and unknown lengths, it is much better to use ArrayList, in practice.
	 */
	final static int maxn=1000;//maximum total length of all arrays
	
	public static void main(String args[]){
		Scanner scanner=new Scanner(System.in);
		System.out.print("Input bumber of arrays: ");
		int k=scanner.nextInt();
		scanner.nextLine();
		int[][] a=new int[k][maxn];
		int[] l=new int[k];//length
		int[] heap=new int[k];//stores numeric pointers to the arrays
		for(int i=0;i<k;i++)heap[i]=i;
		
		System.out.println("Input each array (numbers only):");
		for(int i=0;i<k;i++){
			String arr=scanner.nextLine();
			int j=0;
			for(String s:arr.replace(","," ").split(" ")){
				a[i][j]=Integer.parseInt(s);
				j++;
			}
			l[i]=j;
		}
		
		//heapify
		int smallc;
		for(int i=k/2-1;i>=0;i--)
			for(int j=i;2*j+1<k;j=smallc){
				int lc=2*j+1, rc=lc+1;
				if(rc>=k)smallc=lc;
				else smallc=l[heap[lc]]<l[heap[rc]]?lc:rc;
				if(l[heap[j]]>l[heap[smallc]]){
					int temp=heap[j];
					heap[j]=heap[smallc];
					heap[smallc]=temp;
				}else break;
			}
		
		while(k>1){//k is heap size
			//remove root
			int arr1=heap[0];
			heap[0]=heap[k-1];
			k--;
			for(int j=0;2*j+1<k;j=smallc){
				int lc=2*j+1, rc= lc+1;
				if(rc>=k)smallc=lc;
				else smallc=l[heap[lc]]<l[heap[rc]]?lc:rc;
				if(l[heap[j]]>l[heap[smallc]]){
					int temp=heap[j];
					heap[j]=heap[smallc];
					heap[smallc]=temp;
				}else break;
			}
			
			//merge the previous array into whhere the root points to
			//after the root is changed, sift down.
			int arr2=heap[0];
			int[] temparr=new int[maxn];
			int i1=0,i2=0,i3=0;
			while(i1<l[arr1] && i2<l[arr2]){
				if(a[arr1][i1]<=a[arr2][i2])temparr[i3++]=a[arr1][i1++];
				else temparr[i3++]=a[arr2][i2++];
			}
			while(i1<l[arr1])temparr[i3++]=a[arr1][i1++];
			while(i2<l[arr2])temparr[i3++]=a[arr2][i2++];
			a[arr2]=temparr;
			l[arr2]+=l[arr1];
			for(int j=0;2*j+1<k;j=smallc){
				int lc=2*j+1, rc=lc+1;
				if(rc>=k)smallc=lc;
				else smallc=l[heap[lc]]<l[heap[rc]]?lc:rc;				
				if(l[heap[j]]>l[heap[smallc]]){
					int temp=heap[j];
					heap[j]=heap[smallc];
					heap[smallc]=temp;
				}else break;
			}
		}
		for(int i=0;i<l[heap[0]];i++)
			System.out.print(a[heap[0]][i]+" ");
		System.out.println();
	}
}