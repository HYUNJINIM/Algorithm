import java.util.*;

class Solution {
    Set<Integer> candidates = new HashSet<>();
    
    public int solution(String numbers) {
        // 재귀를 이용해 숫자 조합을 생성한다.
        permutation("", numbers);
        
        // 각 숫자 조합을 확인하여 소수의 개수를 센다.
        int answer = 0;
        for (int num : candidates) {
            if (isPrime(num))  answer++;
        }
        
        return answer;
    }
    
    void permutation(String prefix, String str) {
        int n = str.length();
        if (!prefix.isEmpty()) {
            candidates.add(Integer.parseInt(prefix));
        }
        for (int i = 0; i < n; i++) {
            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
        }
    }
        
    boolean isPrime(int n) {
        if (n < 2) return false;
        
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0) return false;
        }
        
        return true;
    }
}