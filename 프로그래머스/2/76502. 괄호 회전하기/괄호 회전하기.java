import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int solution(String s) {
        String extendedS = s + s;
        int answer = 0;
        for (int i = 0; i < s.length(); i++) {
            if (isValid(extendedS.substring(i, s.length() + i))) answer++;
        }
        return answer;

    }

    // 주어진 문자열이 올바른 괄호 문자열인지 확인하는 함수
    private boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char current : s.toCharArray()) {
            if (current == '(' || current == '[' || current == '{') {
                stack.push(current);
            } else {
                if (stack.isEmpty()) return false;

                char target = stack.pop();
                if ((target == '(' && current != ')') ||
                (target == '[' && current != ']') ||
                (target == '{' && current != '}')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}