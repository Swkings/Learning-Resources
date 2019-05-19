#include<iostream>
#include<cstdio>
#include<ctime>
#include<time.h>
#include<random>
#include<fstream>
using namespace std;

/**
* @author Swking
* @method Part
* @parame
*    int a[] 待排数组
*    int low 待排数组的最低位
*    int high 待排数组的最高位
*    int &timeNum 执行次数
* @return j 基准位置
*/

int Part(int a[], int low, int high, int &timeNum){
    int i=low,j=high;
    srand(time(NULL));
    int f = low + rand()%(high-low+1);//随机挑选基准数位置
    //int f = low; //普通方法挑选基准，挑选第一个,用于模拟最坏情况
    //int f = (low + high)/2; //用于模拟最好情况

    int flag = a[f];  //选出基准数
    a[f] = a[low]; //空出第一个位置
    a[low] = 0;
    //cout << f << " ";
    for(; i<=j ;){
        if(a[i]==0){  //如果开头位置空出，则要从末尾挑选比基准数小的数，空出末尾位置
            if(flag >= a[j]){  //当要排的数据全部相等时，如果不交换就如同最坏情况，交换则是最好情况，每个数都是中位数
                a[i] = a[j];
                a[j] = 0;
                i++;
            }else{
                j--;
            }
            timeNum++; //计算执行次数
            if(i>=j){
                break;
            }
        }
        if(a[j]==0){ //如果末尾位置空出，则要从开头挑选比基准数大的数，空出开头位置
            if(flag < a[i]){
                a[j] = a[i];
                a[i] = 0;
                j--;
            }else{
                i++;
            }
            timeNum++; //计算执行次数
            if(i>=j){
                break;
            }
        }
    }
    a[j] = flag; //将基准数放回数组
    //cout<< j<<endl;
    return j;
}

/**
* @author Swking
* @method FastSort
* @parame
*    int a[] 待排数组
*    int low 待排数组的最低位
*    int high 待排数组的最高位
* @return timeNum 执行次数
*/
int FastSort(int a[], int low, int high, int &timeNum){
    if(low < high){
        int flag = Part(a, low, high, timeNum); //找基准元素
        FastSort(a, low, flag-1, timeNum); //对左半部排序
        FastSort(a, flag+1, high, timeNum); //对右半部排序
    }
    return timeNum;
}

int main()
{
    fstream fio;
    ofstream fout;
    fout.open("out.txt",ios::out);

    /**
    * 模拟数据文件
    */
    fio.open("in.txt",ios::out);
    cout << "producting the file..." << endl;

    int eNum = 50;//元素个数

    //模拟最好情况和最坏情况，由于最好情况的数据难以模拟，所以在取基准时做模拟
    fio << eNum << endl;
    for(int i=1; i<=eNum; i++){
        fio << i << ' ';
    }
    fio << endl;

    //模拟普通数据
    srand(time(NULL));
    //int exampleSum = 50 + rand()%50;
    int exampleSum = 50;
    while(exampleSum--){
        //int elementNum = 20 + rand()%80;
        int elementNum = eNum;
        fio << elementNum << endl;
        while(elementNum--){
            fio << rand()%100 << ' ';
        }
        fio << endl;
    }
    fio.close();

    /**
    *  读取数据文件
    */
    fio.open("in.txt",ios::in);
    cout << "reading the file..." << endl;
    cout << "sorting..." << endl;
    while(!fio.eof()){
        int n = 0;
        fio >> n;
        if(n!=0){ //控制最后一行不多输出
            int a[n];
            for(int i=0; i<n; i++){
                fio >> a[i];
               // cout << a[i] << ' ';
            }
            int exec = 0;
            clock_t start = clock();
            int execNum = FastSort(a, 0, n-1, exec); //排序
            clock_t ends = clock();

            //输出
            fout << n << endl;
            for(int i=0; i<n; i++){
                fout << a[i] << ' ';
            }
            fout << endl;

            fout << "execNum:" << execNum << "  time:" << (double)(ends - start)*1000/CLOCKS_PER_SEC << "ms" << endl << endl;
        }

    }
    cout << "sorted!" << endl;
    fio.close();
    fout.close();
    return 0;
}

