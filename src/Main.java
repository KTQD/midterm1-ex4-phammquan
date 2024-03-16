
public class Main implements Runnable {
        public int[] arr;
        public static int sum = 0;
        public static int max = Integer.MIN_VALUE;
        public int begin;
        public int end;
        public String taskType;

        public Main(int[] arr, int begin, int end, String taskType) {
            this.arr = arr;
            this.begin = begin;
            this.end = end;
            this.taskType = taskType;
        }

        @Override
        public void run() {
            if (taskType.equals("sum")) {
                for (int i = begin; i < end; i++) {
                    synchronized (this) {
                        sum += arr[i];
                    }
                }
            } else if (taskType.equals("max")) {
                for (int i = begin; i < end; i++) {
                    synchronized (this) {
                        if (arr[i] > max) {
                            max = arr[i];
                        }
                    }
                }
            }
        }

        public static void main(String[] args) throws InterruptedException {
            int[] arr = {1, 3, 5, 6, 2, 7, 8, 0, 4, 3, 9, 2, 8, 1, 0, 5, 7, 4, 6, 9, 3, 2, 1, 8, 4, 0, 6, 7, 9, 5, 3, 2, 1, 0, 8, 4, 6, 7, 9, 5, 3, 2, 1, 0, 8, 4, 6, 7, 9, 5, 3, 2, 1, 0, 8, 4, 6, 7, 9, 5, 3, 2, 1, 0, 8, 4, 6, 7, 9, 5, 3, 2, 1, 0};
            int so_luong_thread = 2;
            int chunk = (int) Math.ceil((double) arr.length / so_luong_thread);
            Thread[] threads = new Thread[so_luong_thread * 2];

            for (int i = 0; i < so_luong_thread; i++) {
                int begin = i * chunk;
                int end = Math.min(arr.length, (i + 1) * chunk);
                threads[i] = new Thread(new Main(arr, begin, end, "sum"));
                threads[i].start();
                threads[i + so_luong_thread] = new Thread(new Main(arr, begin, end, "max"));
                threads[i + so_luong_thread].start();
            }
            for (Thread thread : threads) {
                thread.join();
            }
            System.out.println("Tổng các phần tử trong mảng: " + sum);
            System.out.println("Phần tử lớn nhất trong mảng: " + max);
        }
    }

