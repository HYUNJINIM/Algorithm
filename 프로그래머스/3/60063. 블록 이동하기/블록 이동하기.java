import java.util.*;

class Solution {
    static int N;
    static int[][] board;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    
    public int solution(int[][] board) {
        this.N = board.length;
        this.board = board;
        
        return bfs();
    }
    
    private int bfs() {
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        State initial = new State(0, 0, 0, 1, 0);
        queue.offer(initial);
        visited.add(initial.toString());
        
        while (!queue.isEmpty()) {
            State current = queue.poll();
            
            if (isGoal(current)) {
                return current.time;
            }
            
            for (State next : getNextStates(current)) {
                if (!visited.contains(next.toString())) {
                    queue.offer(next);
                    visited.add(next.toString());
                }
            }
        }
        
        return -1;  // 도달할 수 없는 경우
    }
    
    private boolean isGoal(State state) {
        return (state.x1 == N-1 && state.y1 == N-1) || (state.x2 == N-1 && state.y2 == N-1);
    }
    
    private List<State> getNextStates(State state) {
        List<State> nextStates = new ArrayList<>();
        
        // 상하좌우 이동
        for (int i = 0; i < 4; i++) {
            int nx1 = state.x1 + dx[i];
            int ny1 = state.y1 + dy[i];
            int nx2 = state.x2 + dx[i];
            int ny2 = state.y2 + dy[i];
            
            if (isValid(nx1, ny1) && isValid(nx2, ny2)) {
                nextStates.add(new State(nx1, ny1, nx2, ny2, state.time + 1));
            }
        }
        
        // 회전
        if (state.x1 == state.x2) {  // 가로 방향
            if (isValid(state.x1-1, state.y1) && isValid(state.x2-1, state.y2)) {
                nextStates.add(new State(state.x1-1, state.y1, state.x1, state.y1, state.time + 1));
                nextStates.add(new State(state.x2-1, state.y2, state.x2, state.y2, state.time + 1));
            }
            if (isValid(state.x1+1, state.y1) && isValid(state.x2+1, state.y2)) {
                nextStates.add(new State(state.x1, state.y1, state.x1+1, state.y1, state.time + 1));
                nextStates.add(new State(state.x2, state.y2, state.x2+1, state.y2, state.time + 1));
            }
        } else {  // 세로 방향
            if (isValid(state.x1, state.y1-1) && isValid(state.x2, state.y2-1)) {
                nextStates.add(new State(state.x1, state.y1-1, state.x1, state.y1, state.time + 1));
                nextStates.add(new State(state.x2, state.y2-1, state.x2, state.y2, state.time + 1));
            }
            if (isValid(state.x1, state.y1+1) && isValid(state.x2, state.y2+1)) {
                nextStates.add(new State(state.x1, state.y1, state.x1, state.y1+1, state.time + 1));
                nextStates.add(new State(state.x2, state.y2, state.x2, state.y2+1, state.time + 1));
            }
        }
        
        return nextStates;
    }
    
    private boolean isValid(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N && board[x][y] == 0;
    }
    
    class State {
        int x1, y1, x2, y2, time;
        
        State(int x1, int y1, int x2, int y2, int time) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.time = time;
        }
        
        @Override
        public String toString() {
            return x1 + "," + y1 + "," + x2 + "," + y2;
        }
    }
}