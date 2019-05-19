#include<bits/stdc++.h>
#include<iostream>
#include<algorithm>
#include<cstdio>
#include<fstream>
#include<ctime>
using namespace std;

struct Goods{
   int w; //物品重量
   int v; //物品价值
   double quality; //物品价值性价比
   int No; //物品编号
   int selected = 0;  //是否被选择
};

bool cmp1(Goods a, Goods b){
    return a.quality > b.quality;
}
bool cmp2(Goods a, Goods b){
    return a.No < b.No;
}

/**
* @author Swking
* @method Gready_01
* @parame
*    Goods* goods 物品
*    int n 物品总和
*    int capacity 背包容量
*    int& execNum 执行次数
* @return valueSum 价值总和
*/
int Gready_01(Goods* goods, int n, int capacity, int& exceNum){
    int valueSum = 0;
    for(int i=0; i<n; i++){
        exceNum++;
        if(goods[i].w <= capacity){ //物品小于背包剩余容量，则被选择
            goods[i].selected = 1;
            capacity -= goods[i].w;
            valueSum += goods[i].v;
        }
    }
    return valueSum;
}

/**
* @author Swking
* @method Dynamic_01
* @parame
*    Goods* goods 物品
*    int n 物品总和
*    int capacity 背包容量
*    int** Table 解空间表
*    int& execNum 执行次数
* @return valueSum 价值总和
*/
int Dynamic_01(Goods* goods, int n, int capacity, int** Table, int& execNum){
    //Table中的第一个量为物品编号，第二个量为总容量
    int valueSum;
    for(int i=1; i<=n; i++){
        for(int j=1; j<=capacity; j++){
            /*
             * 1、j<goods[i-1].w时，放不下物品i，直接不选择，即Table[i][j] = Table[i-1][j]
             * 2、否则选择对与当前状态最优的结果，即选择 装入背包和不装入背包中价值的较大者，即
             *     Table[i][j] = max(Table[i-1][j], Table[i-1][j-goods[i-1].w]+goods[i-1].v);
             *
             */
            if(j<goods[i-1].w){
                Table[i][j] = Table[i-1][j];
            }else{
                Table[i][j] = max(Table[i-1][j], Table[i-1][j-goods[i-1].w]+goods[i-1].v);
            }
            execNum++;
        }
    }
    /* 通过Table表回溯寻找路径
     * 1、Table[i][j] = Table[i-1][j]时，说明第i个物品没有被选择，则回溯到Table[i-1][j]
     * 2、Table[i][j] = Table[i-1][j-goods[i-1].w]+goods[i-1].v,则第i个物品被选择，并回溯到装i物品之前Table[i-1][j-goods[i-1].w]
     */
    int i=n;
    int j=capacity;
    while(i>0&&j>0){
       if(Table[i][j] == Table[i-1][j]){
            i--;
       }else{
            j -= goods[i-1].w;
            goods[i-1].selected = 1;
            i--;
       }
       //execNum++;
    }
    valueSum = Table[n][capacity]; //capacity处的值则为背包总价值
    return valueSum;
}

int main()
{
    fstream fin,fout;

    /*
     * 模拟数据
     * 第一行为背包容量c
     * 第二行为物品个数n
     * 第三行为物品重量w
     * 第四行为物品价值v
     */
    fout.open("in.txt",ios::out);
    cout << "producting the file..." << endl;
    //第一组数据，贪心能得到最优解
    fout << 5 << endl;
    fout << 3 << endl;
    fout << 1 << setw(3) << 2 << setw(3) << 3 << endl;
    fout << 6 << setw(3) << 10 << setw(3) << 12 << endl << endl;

    //第二组数据，贪心得不到最优解
    fout << 15 << endl;
    fout << 5 << endl;
    fout << 5 << setw(3) << 4 << setw(3) << 7 << setw(3) << 2 << setw(3) << 6 << endl;
    fout << 12 << setw(3) << 3 << setw(3) << 10 << setw(3) << 3 << setw(3) << 6 << endl << endl;
    srand(time(NULL));
    int exampleSum = 20 + rand()%30;
    while(exampleSum--){
        int capacity = 100 + rand()%100;
        fout << capacity << endl;
        int elementNum = 10 + rand()%30;
        fout << elementNum << endl;
        for(int i=0; i<elementNum; i++){
            fout << setw(3) << 1 + rand()%99;
        }
        fout << endl;
        for(int i=0; i<elementNum; i++){
            fout << setw(3) << 1 + rand()%99;
        }
        fout << endl << endl;
    }
    fout.close();

    /*
     * 读取文件数据
     */
    cout << "reading the file..." << endl;
    fin.open("in.txt",ios::in);
    fout.open("out.csv",ios::out);
    cout << "Computing..." << endl;
    while(!fin.eof()){
        int capacity, n;
        n = 0;
        fin >> capacity >> n;
        if(n!=0){
            Goods* goods = new Goods[n+1];
            for(int i=0; i<n; i++){
                fin >> goods[i].w; //读物品重量w
                goods[i].No = i+1; //从1开始编号
            }
            for(int i=0; i<n; i++){
                fin >> goods[i].v; //读物品价值v
                goods[i].quality =  goods[i].v / goods[i].w;  //计算物品性价比
            }
            /*
             * 按性价比排序
             */
            sort(goods,goods+n,cmp1);
            int valueSum;
            /*
             *  贪心
             */
             int execNum = 0;
             valueSum = Gready_01(goods, n, capacity, execNum);
            /*
             * 将计算结果写入文件
             */
             sort(goods,goods+n,cmp2);
             fout << "Gready:" << ','<< "ValueSum:" << ',' << valueSum << ',' << "exceNum:" << ',' << execNum << endl;
             fout << ',' << "No." << ',';
             for(int i=0; i<n; i++){
                 fout << goods[i].No << ',';
             }
             fout << endl << ',' << "Selected" << ',';
             for(int i=0; i<n; i++){
                 fout << goods[i].selected << ',';
             }
             fout << endl;
             /*
             *  动规
             */
             //清除贪心的结果
             for(int i=0; i<n; i++){
                goods[i].selected = 0;
             }
             //创建表格，储存抉择路径
             int** Table = new int* [n+1];
             for(int i=0; i<=n; i++){
                Table[i] = new int[capacity+1];
             }
             //初始化第一列为0
             for(int i=0; i<=n; i++){
                Table[i][0] = 0;
             }
             //初始化第一行为0
             for(int i=0; i<=capacity; i++){
                Table[0][i] = 0;
             }
             execNum = 0;
             valueSum = Dynamic_01(goods, n, capacity, Table, execNum);
             fout << "Dynamic:" << ',' << "ValueSum:" << ',' << valueSum << ',' << "exceNum:" << ',' << execNum << endl;
             fout << ',' << "No." << ',';
             for(int i=0; i<n; i++){
                 fout << goods[i].No << ',';
             }
             fout << endl << ',' << "Selected" << ',';
             for(int i=0; i<n; i++){
                 fout << goods[i].selected << ',';
             }
             //打印Table
             fout << endl << "Table" << ',';
             for(int i=0; i<=capacity; i++){
                fout << i << ',';
             }
             fout << endl;
             for(int i=0; i<=n; i++){
                fout << i << ',';
                for(int j=0; j<=capacity; j++){
                    fout << Table[i][j] << ',';
                }
                fout << endl;
             }
             fout << endl;

             //释放空间
             for(int i=0; i<n; i++){
                 delete [] Table[i];
             }
             delete[] Table;

        }
    }
    fout.close();
    fin.close();
    cout << "Completed!" << endl;
    return 0;
}
