import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(reader.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int fuel = Integer.parseInt(st.nextToken());
        boolean[][] wall = new boolean[N+1][N+1];
        for (int r = 1; r <= N; r++) {
            st = new StringTokenizer(reader.readLine());
            for (int c = 1; c <= N; c++) {
                wall[r][c] = (st.nextToken().equals("1"));
            }
        }

        st = new StringTokenizer(reader.readLine());
        Point taxi = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        Map<Point, Passenger> passengers = new HashMap<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(reader.readLine());
            Point from = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            Point to = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            passengers.put(from, new Passenger(from, to));
        }

        final int[] dr = { -1,  0,  1,  0 };
        final int[] dc = {  0, -1,  0,  1 };
        Queue<Entry> queue = new ArrayDeque<>();
        queue.add(new Entry(taxi, 0));
        for (int i = 0; i < M; i++) {
            Passenger passenger = null;
            Point initialPoint = queue.peek().p;
            if (passengers.containsKey(initialPoint)) {
                passenger = passengers.get(initialPoint);
                queue.clear();
            }
            int usage = 0;
            boolean[][] visited = prepareVisited(wall);
            while (!queue.isEmpty()) {
                Entry cur = queue.remove();
                if (cur.dist == fuel) break;
                if (passenger != null && usage <= cur.dist) break;

                for (int d = 0; d < 4; d++) {
                    Point next = new Point(cur.p.r + dr[d], cur.p.c + dc[d]);
                    if (next.inRange(N, N) && !visited[next.r][next.c]) {
                        visited[next.r][next.c] = true;
                        if (passengers.containsKey(next)) {
                            Passenger newPassenger = passengers.get(next);
                            if (passenger == null) {
                                passenger = newPassenger;
                                usage += cur.dist + 1;
                            } else if (newPassenger.from.compareTo(passenger.from) < 0 && usage == cur.dist + 1) {
                                passenger = newPassenger;
                            }
                        } else {
                            queue.add(new Entry(next, cur.dist + 1));
                        }
                    }
                }
            }
            if (passenger == null) {
                System.out.println(-1);
                return;
            }
            fuel -= usage;
            usage = 0;
            queue.clear();
            queue.add(new Entry(passenger.from, 0));
            boolean arrived = false;
            visited = prepareVisited(wall);
            outer: while (!queue.isEmpty()) {
                Entry cur = queue.remove();
                if (cur.dist == fuel) break;

                for (int d = 0; d < 4; d++) {
                    Point next = new Point(cur.p.r + dr[d], cur.p.c + dc[d]);
                    if (next.inRange(N, N) && !visited[next.r][next.c]) {
                        visited[next.r][next.c] = true;
                        if (passenger.to.equals(next)) {
                            arrived = true;
                            usage = cur.dist + 1;
                            break outer;
                        } else {
                            queue.add(new Entry(next, cur.dist + 1));
                        }
                    }
                }
            }

            if (arrived) {
                fuel -= usage;  // 연료 소비
                if (fuel < 0) {  // 연료가 바닥난 경우 체크
                    System.out.println(-1);
                    return;
                }
                fuel += usage * 2;  // 소비한 연료의 두 배 충전
                queue.clear();
                queue.add(new Entry(passenger.to, 0));
                passengers.remove(passenger.from);
            } else {
                System.out.println(-1);
                return;
            }
        }
        System.out.println(fuel);
    }
		
    static boolean[][] prepareVisited(boolean[][] wall) {
        boolean[][] result = new boolean[wall.length][wall[0].length];
        for (int r = 0; r < wall.length; r++) {
            System.arraycopy(wall[r], 0, result[r], 0, result[r].length);
        }
        return result;
    }

    static class Entry {
        Point p;
        int dist;

        public Entry(Point p, int dist) {
            this.p = p;
            this.dist = dist;
        }

        @Override
        public String toString() {
            return "E" + p + ":" + dist;
        }
    }

    static class Passenger {
        static int seq = 1;
        int id;
        Point from;
        Point to;

        public Passenger(Point from, Point to) {
            this.id = seq++;
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "P" + id + ":" + from + "->" + to;
        }
    }

    static class Point implements Comparable<Point> {
        int r;
        int c;

        public Point(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public boolean inRange(int n, int m) {
            return r > 0 && r <= n && c > 0 && c <= m;
        }

        @Override
        public String toString() {
            return "(" + r + "," + c + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point) {
                Point p = (Point)o;
                return this.r == p.r && this.c == p.c;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.r, this.c);
        }

        @Override
        public int compareTo(Point p) {
            if (this.r == p.r) {
                return this.c - p.c;
            } else {
                return this.r - p.r;
            }
        }
    }
}