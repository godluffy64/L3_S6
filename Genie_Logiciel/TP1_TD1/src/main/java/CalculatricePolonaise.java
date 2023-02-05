import java.util.Stack;

public class CalculatricePolonaise {
    public float calcule(String chaine) {
        int result = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < chaine.length(); i++) {
            if (chaine.charAt(i) != '+' && chaine.charAt(i) != '-' && chaine.charAt(i) != '*' && chaine.charAt(i) != '/' && chaine.charAt(i) != ' ') {

                stack.push((int) (chaine.charAt(i)) - '0');
            }
            else {
                int b = stack.pop();
                int a = stack.pop();
                if (chaine.charAt(i) == '+') {
                    stack.push(a + b);

                } else if (chaine.charAt(i) == '-') {
                    stack.push(a - b);
                } else if (chaine.charAt(i) == '/') {
                    stack.push((int) (b / a));

                } else if (chaine.charAt(i) == '*') {
                    stack.push((int) (b * a));
                }
            }
        }


                return stack.get(stack.size() - 1);


            }
        }

