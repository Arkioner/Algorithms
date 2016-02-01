package algorithms.quickfind;

import java.time.LocalTime;

public class QuickFindUF {
    public int[] id;

    public QuickFindUF(int N) {
        id =  new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public void union(int p, int q) {
        System.out.println("Union type: " + this.getClass().getSimpleName());
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) { id[i] = qid; }
        }
    }

    public static void main(String[] args) {
        int before = LocalTime.now().getNano();
        QuickFindUF quickFindUF = new QuickFindUF(1000000);
        quickFindUF.union(0, 1);
        System.out.println((LocalTime.now().getNano() - before) / 1000000);
        System.out.println(quickFindUF.id[0]);
    }
}
