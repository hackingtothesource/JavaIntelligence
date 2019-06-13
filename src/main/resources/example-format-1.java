public class findsushu {
public static void main(String[] args) {
    int i,j;
    int a[]=new int[101];
    for (i=1; i<101;i++){
        a[i]=1;//将整个数组初始化为1
    }
    for(i=2;i<101;i++){
        if(a[i]!=0)
        {

            for(j=i+i;j<101;){
                if(j%i==0)
                {
                    a[j]=0;
//将所有2、3、5、7等等的倍数都标记为0（由于4和6是2的倍数已经被标记为0了，所以跳过）；
                    j=j+i;

                }
            }

        }
    }
    for(i=2;i<101;i++){
        if(a[i]!=0)
        {
            System.out.print(i+" ");
            }
    }

    }


}