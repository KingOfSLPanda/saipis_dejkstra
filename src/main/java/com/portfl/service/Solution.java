package com.portfl.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by User on 11.05.2017.
 */
public class Solution {
    private static int INF = Integer.MAX_VALUE / 2;

    private int n; //количество вершин в орграфе
    private int m; //количествое дуг в орграфе

    public static int getINF() {
        return INF;
    }

    public static void setINF(int INF) {
        Solution.INF = INF;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public ArrayList<Integer>[] getAdj() {
        return adj;
    }

    public void setAdj(ArrayList<Integer>[] adj) {
        this.adj = adj;
    }

    public ArrayList<Integer>[] getWeight() {
        return weight;
    }

    public void setWeight(ArrayList<Integer>[] weight) {
        this.weight = weight;
    }

    public boolean[] getUsed() {
        return used;
    }

    public void setUsed(boolean[] used) {
        this.used = used;
    }

    public int[] getDist() {
        return dist;
    }

    public void setDist(int[] dist) {
        this.dist = dist;
    }

    public int[] getPred() {
        return pred;
    }

    public void setPred(int[] pred) {
        this.pred = pred;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public BufferedReader getCin() {
        return cin;
    }

    public void setCin(BufferedReader cin) {
        this.cin = cin;
    }

    public PrintWriter getCout() {
        return cout;
    }

    public void setCout(PrintWriter cout) {
        this.cout = cout;
    }

    public StringTokenizer getTokenizer() {
        return tokenizer;
    }

    public void setTokenizer(StringTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public ArrayList<Integer> adj[]; //список смежности
    private ArrayList<Integer> weight[]; //вес ребра в орграфе
    private boolean used[]; //массив для хранения информации о пройденных и не пройденных вершинах
    private int dist[]; //массив для хранения расстояния от стартовой вершины
    //массив предков, необходимых для восстановления кратчайшего пути из стартовой вершины
    private int pred[];
    int start; //стартовая вершина, от которой ищется расстояние до всех других
    int result;
    int resultValue;

    private BufferedReader cin;
    private PrintWriter cout;
    private StringTokenizer tokenizer;

    //процедура запуска алгоритма Дейкстры из стартовой вершины
    private void dejkstra(int s) {
        dist[s] = 0; //кратчайшее расстояние до стартовой вершины равно 0
        for (int iter = 0; iter < n; ++iter) {
            int v = -1;
            int distV = INF;
            //выбираем вершину, кратчайшее расстояние до которого еще не найдено
            for (int i = 0; i < n; ++i) {
                if (used[i]) {
                    continue;
                }
                if (distV < dist[i]) {
                    continue;
                }
                v = i;
                distV = dist[i];
            }
            //рассматриваем все дуги, исходящие из найденной вершины
            for (int i = 0; i < adj[v].size(); ++i) {
                int u = adj[v].get(i);
                int weightU = weight[v].get(i);
                //релаксация вершины
                if (dist[v] + weightU < dist[u]) {
                    dist[u] = dist[v] + weightU;
                    pred[u] = v;
                }
            }
            //помечаем вершину v просмотренной, до нее найдено кратчайшее расстояние
            used[v] = true;
        }
    }

    private void readData(int array[]){
        start--;
        result--;
        resultValue=-1;
        cin = new BufferedReader(new InputStreamReader(System.in));
        cout = new PrintWriter(System.out);

        adj= new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }

        weight = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            weight[i] = new ArrayList<Integer>();
        }

        for (int i = 0; i < m; ++i) {
            if(array[i]!=-1) {
                int u = i / n;
                int v = i % n;
                int w = array[i];
                adj[u].add(v);
                weight[u].add(w);
            }
        }
        used = new boolean[n];
        Arrays.fill(used, false);

        pred = new int[n];
        Arrays.fill(pred, -1);

        dist = new int[n];
        Arrays.fill(dist, INF);
    }

    //процедура считывания входных данных с консоли
    private void readData() throws IOException {
        cin = new BufferedReader(new InputStreamReader(System.in));
        cout = new PrintWriter(System.out);
        tokenizer = new StringTokenizer(cin.readLine());

        n = Integer.parseInt(tokenizer.nextToken()); //считываем количество вершин графа
        m = Integer.parseInt(tokenizer.nextToken()); //считываем количество ребер графа
        start = Integer.parseInt(tokenizer.nextToken()) - 1;

        //инициализируем списка смежности графа размерности n
        adj = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            adj[i] = new ArrayList<Integer>();
        }
        //инициализация списка, в котором хранятся веса ребер
        weight = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            weight[i] = new ArrayList<Integer>();
        }

        //считываем граф, заданный списком ребер
        for (int i = 0; i < m; ++i) {
            tokenizer = new StringTokenizer(cin.readLine());
            int u = Integer.parseInt(tokenizer.nextToken());
            int v = Integer.parseInt(tokenizer.nextToken());
            int w = Integer.parseInt(tokenizer.nextToken());
            u--;
            v--;
            adj[u].add(v);
            weight[u].add(w);
        }

        used = new boolean[n];
        Arrays.fill(used, false);

        pred = new int[n];
        Arrays.fill(pred, -1);

        dist = new int[n];
        Arrays.fill(dist, INF);

    }

    //процедура восстановления кратчайшего пути по массиву предком
    void printWay(int v) {
        if (v == -1) {
            return;
        }
        printWay(pred[v]);
        cout.print((v + 1) + " ");
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getResultValue() {
        return resultValue;
    }

    public void setResultValue(int resultValue) {
        this.resultValue = resultValue;
    }

    //процедура вывода данных в консоль
    private void printData() throws IOException {
        for (int v = 0; v < n; ++v) {
            if (dist[v] != INF) {
                cout.print(dist[v] + " ");
                if(v==result){
                    resultValue=dist[v];
                }
            } else {
                cout.print("-1 ");
            }
        }
        cout.println();
        for (int v = 0; v < n; ++v) {
            cout.print((v + 1) + ": ");
            if (dist[v] != INF) {
                printWay(v);
            }
            cout.println();
        }

        cin.close();
        cout.close();
    }

    public void run(int array[]) throws IOException {
        readData(array);
        dejkstra(start);
        printData();
        start++;
        result++;

        cin.close();
        cout.close();
    }
}
