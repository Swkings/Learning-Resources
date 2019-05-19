#include<iostream>
#include<algorithm>
#include<cstdio>
#include<ctime>
#include<bits/stdc++.h>
using namespace std;
int randNum;
int Rseed = time(NULL) ;

void randSeed(int seed){
    randNum = seed;
}
/**线性同余： Tn = (A*Tn-1+B) mod C  易知Tn 为 0~C 之间的数
* @author Swking
* @method pseudoRand
* @parame
* @return randNum 伪随机数
*/
double pseudoRand(){
    randNum = ((randNum*5623)%49853  + 49297)%49999 ;
    //randNum = ((randNum*11)%17  + 19)%37 ;
    return randNum;
}
/**
 * 49919 49921 49927 49937 49939 49943 49957 49991 49993 49999
 * 48857 48859 48869 48871 48883 48889 48907 48947 48953 48973
 * 47143 47147 47149 47161 47189 47207 47221 47237 47251 4726
 * 5623 5639 5641 5647 5651 5653 5657 5659 5669 5683 9301
 * 5689 5693 5701 5711 5717 5737 5741 5743 5749 5779
 */

int main()
{
    fstream fin, fout;
    fin.open("randin.txt",ios::in|ios::out);
    fout.open("randout.txt",ios::out);
    srand(time(NULL));
    int examNum = 100;
    while(examNum--){
        fin << 1000+rand()%50 << endl;
        fin << 1 << ' ' << 20+rand()%20 <<endl;
    }
    fin.close();

    fin.open("randin.txt",ios::in|ios::out);

    randSeed(Rseed);
    int couter = 0;
    while(!fin.eof()){
        int n=0;
        int a,b;
        int num;

        fin >> n;
        int N=n;
        if(n!=0){
            fin >> a >> b;
            int t[b+1];
            for(int i=0;i<=b;i++){
                t[i]=0;
            }
            fout << "-- "<<couter<<" --" << "a:"<<a<<"  --"<<"b:"<<b<<" ---"<<endl;
            couter++;
            while(n--){
                num = a+(int)pseudoRand()%(b-a);
                fout << num << ' ';
                t[num] = t[num] + 1;
            }
            for(int i=a; i<b; i++){
                float re = t[i]*100*1.00/N;
                fout<< i<< ":  "<< re <<"%"<<endl;
            }
        }
        fout << endl <<endl;
    }
    fout << endl;
    cout << "Completed!" << endl;
    fout.close();
    fin.close();
    return 0;
}
