import java.util.*;

public class Main {
    static int N, M;  // 보드의 세로, 가로 크기
    static char[][] board;  // 보드 상태를 저장할 2차원 배열
    static int[] dx = {-1, 1, 0, 0};  // 상하좌우 이동을 위한 x축 변화량
    static int[] dy = {0, 0, -1, 1};  // 상하좌우 이동을 위한 y축 변화량

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();  // 세로 크기 입력
        M = sc.nextInt();  // 가로 크기 입력
        board = new char[N][M];  // 보드 배열 초기화
        
        int rx = 0, ry = 0, bx = 0, by = 0;  // 빨간 구슬과 파란 구슬의 초기 위치
        for (int i = 0; i < N; i++) {
            String line = sc.next();  // 한 줄 입력
            for (int j = 0; j < M; j++) {
                board[i][j] = line.charAt(j);  // 보드에 입력 저장
                if (board[i][j] == 'R') {  // 빨간 구슬 위치 저장
                    rx = i; ry = j;
                } else if (board[i][j] == 'B') {  // 파란 구슬 위치 저장
                    bx = i; by = j;
                }
            }
        }
        
        System.out.println(bfs(rx, ry, bx, by));  // BFS 실행 및 결과 출력
    }
    
    static int bfs(int rx, int ry, int bx, int by) {
        Queue<int[]> queue = new LinkedList<>();  // BFS를 위한 큐
        boolean[][][][] visited = new boolean[N][M][N][M];  // 방문 체크 배열
        
        queue.offer(new int[]{rx, ry, bx, by, 0});  // 초기 상태 큐에 추가
        visited[rx][ry][bx][by] = true;  // 초기 상태 방문 체크
        
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();  // 현재 상태
            int curRx = cur[0], curRy = cur[1], curBx = cur[2], curBy = cur[3], count = cur[4];
            
            if (count >= 10) return 0;  // 10번 이상 이동했으면 실패
            
            for (int i = 0; i < 4; i++) {  // 4방향으로 기울이기
                int[] red = move(curRx, curRy, dx[i], dy[i]);  // 빨간 구슬 이동
                int[] blue = move(curBx, curBy, dx[i], dy[i]);  // 파란 구슬 이동
                
                if (board[blue[0]][blue[1]] == 'O') continue;  // 파란 구슬이 구멍에 빠지면 실패
                if (board[red[0]][red[1]] == 'O') return 1;  // 빨간 구슬만 구멍에 빠지면 성공
                
                // 두 구슬이 같은 위치에 있는 경우 처리
                if (red[0] == blue[0] && red[1] == blue[1]) {
                    if (i == 0) { // 위로 기울이기
                        if (curRx > curBx) red[0]++;
                        else blue[0]++;
                    } else if (i == 1) { // 아래로 기울이기
                        if (curRx < curBx) red[0]--;
                        else blue[0]--;
                    } else if (i == 2) { // 왼쪽으로 기울이기
                        if (curRy > curBy) red[1]++;
                        else blue[1]++;
                    } else { // 오른쪽으로 기울이기
                        if (curRy < curBy) red[1]--;
                        else blue[1]--;
                    }
                }
                
                // 새로운 상태이면 큐에 추가
                if (!visited[red[0]][red[1]][blue[0]][blue[1]]) {
                    visited[red[0]][red[1]][blue[0]][blue[1]] = true;
                    queue.offer(new int[]{red[0], red[1], blue[0], blue[1], count + 1});
                }
            }
        }
        
        return 0;  // 모든 경우를 탐색했는데도 성공하지 못하면 실패
    }
    
    static int[] move(int x, int y, int dx, int dy) {
        while (board[x + dx][y + dy] != '#' && board[x][y] != 'O') {  // 벽이나 구멍을 만날 때까지
            x += dx;  // x 좌표 이동
            y += dy;  // y 좌표 이동
        }
        return new int[]{x, y};  // 최종 위치 반환
    }
}