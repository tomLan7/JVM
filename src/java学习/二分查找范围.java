package java学习;
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int arr[]=new int[2];
 
        int n=nums.length,l=0,r=n-1;
        while(l<r){
            int m=l+(r-l)/2;
            if(nums[m]>=target) r=m;
            else l=m+1;
        }
        arr[0]=l;
        System.out.println(l);
        r=n;
        while(l<r){
        	int m=l+(r-l)/2;
        	if(nums[m]<=target){
        		l=m+1;
        	}else{
        		r=m;
        	}
        }
        arr[1]=r-1;
       /*
        while(r-l>0){//找左边的临界点
            mid=(l+r)/2;
            if(nums[mid]==target&&mid>0?nums[mid-1]!=target:true){
            	break;
            }else
            if(nums[mid]<target){
                l=mid+1;
            }else if(nums[mid]>target){
                r=mid;
            }else{
            	r=mid;
            }
            
        }
        if(nums[mid]==target){//没找到
        	System.out.println("找到了左边缘"+mid);
        }else{
        	System.out.println("没找到");
        }
        */
        /*
        l=0;
        r=nums.length;
        mid=0;
        while(r-l>0){
            mid=(l+r)/2;
            if(nums[mid]==target&&mid<(nums.length-1)?nums[mid+1]!=target:true){
            	break;
            }else
            if(nums[mid]<target){
                l=mid+1;
            }else if(nums[mid]>target){
                r=mid;
            }else if(mid<(nums.length-1)?nums[mid+1]!=target:false){
            	l=mid;
            }
            
        }
        if(nums[mid]==target){//没找到
        	System.out.println("找到了右边缘"+mid);
        }else{
        	System.out.println("没找到");
        }*/
        
        return arr;
    }
}
public class 二分查找范围 {

	public static void main(String[] args) {
		Solution s=new Solution();
		int a[]={1,3,4,5,55,55,55,55,55,66};
		s.searchRange(a,55);

	}

}
