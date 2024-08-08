import java.util.*;

class Solution {
    public int[] solution(String[][] places) {
        int[] answer = new int[5];
        
        for (int i = 0; i < 5; i++) {
            answer[i] = checkPlace(places[i]);
        }
        
        return answer;
    }
    
    private int checkPlace(String[] place) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (place[i].charAt(j) == 'P') {
                    if (!checkDistance(place, i, j)) {
                        return 0;
                    }
                }
            }
        }
        return 1;
    }
    
    private boolean checkDistance(String[] place, int row, int col) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        
        // 상하좌우 한 칸 체크
        for (int i = 0; i < 4; i++) {
            int nx = row + dx[i];
            int ny = col + dy[i];
            
            if (nx >= 0 && nx < 5 && ny >= 0 && ny < 5) {
                if (place[nx].charAt(ny) == 'P') {
                    return false;
                }
            }
        }
        
        // 상하좌우 두 칸 체크
        for (int i = 0; i < 4; i++) {
            int nx = row + dx[i] * 2;
            int ny = col + dy[i] * 2;
            
            if (nx >= 0 && nx < 5 && ny >= 0 && ny < 5) {
                if (place[nx].charAt(ny) == 'P') {
                    if (place[row + dx[i]].charAt(col + dy[i]) != 'X') {
                        return false;
                    }
                }
            }
        }
        
        // 대각선 체크
        int[] dxDiag = {-1, -1, 1, 1};
        int[] dyDiag = {-1, 1, -1, 1};
        
        for (int i = 0; i < 4; i++) {
            int nx = row + dxDiag[i];
            int ny = col + dyDiag[i];
            
            if (nx >= 0 && nx < 5 && ny >= 0 && ny < 5) {
                if (place[nx].charAt(ny) == 'P') {
                    if (place[row].charAt(ny) != 'X' || place[nx].charAt(col) != 'X') {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
}