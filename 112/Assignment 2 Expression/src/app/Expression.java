package app;
//This is the answer
import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {
   
    public static String delims = " \t*+-/()[]";
    
    public static void 
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
        //populates the vars and arrays lists with variables and array
        String tok = new String();
        //iterate the loop
        for (int i = 0; i < expr.length(); i++) {
            String temp = String.valueOf(expr.charAt(i));
            if (delims.contains(temp)) {
                if (tok.isEmpty()){
                    continue;
                }
                if (temp.equals("[")) {
                    Array as = new Array(tok);
                    if (!arrays.contains(as)) {
                        arrays.add(as);
                    }
                } else {
                    if (Character.isDigit(tok.charAt(0))) {
                        tok = new String();
                        continue;
                    }
                    Variable vari = new Variable(tok);
                    if (!vars.contains(vari)) {
                        vars.add(vari);
                    }
                }
                tok = new String();
            } else {
                tok += temp;
            }
        }
        if (!tok.isEmpty()) {
            if (!Character.isDigit(tok.charAt(0))) {
                Variable vari = new Variable(tok);
                if (!vars.contains(vari)) {
                    vars.add(vari);
                }
            }
        }
    }

    public static void 
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;              
                }
            }
        }
    }

    public static float 
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
        
        expr = expr.replaceAll("[\\n\\t ]", "");

        ArrayList<String> index_exp = IndexString(expr);

        if (index_exp.size() == 1) {
            //then get the value of 0 index of the expression
            String value = index_exp.get(0);
            //if the character at 0 of the value is #
            if (value.charAt(0) == '#') {
                //then, replace # with "-"
                value = value.replace("#", "-");
                //return the float value of the value.
                return Float.parseFloat(value);
            } else if (Character.isDigit(value.charAt(0))) {//if the character at 0 of the value is digit
                //return the float value of the value.
                return Float.parseFloat(value);
            } else {
                //otherwise, create an object for the Variable
                Variable scal = new Variable(value);
                //get the index value of the object from the vars
                int idx_value = vars.indexOf(scal);
                //if the index value is -1, then return -1.
                if (idx_value == -1) {
                    return 0;
                }
                //otherwise return float value of the value at idx_value of the vars
                else {
                    return (float) vars.get(idx_value).value;
                }
            }
        }
        //create two Stack objects of strings
        Stack<String> stackOperand = new Stack();
        Stack<String> stackOperator = new Stack();
        //iterate the indexed expression
        for (String tok : index_exp) {
            switch (tok) {
                case "/":
                case "*":
                    if (stackOperator.isEmpty()) {
                        stackOperator.push(tok);
                    } else if (stackOperator.peek().equals("*") || stackOperator.peek().equals("/")) {
                        //if the peek value of the stackOperator stack is * or /
                        //pop the operands from the stackOperand stack
                        String op2 = stackOperand.pop();
                        String op1 = stackOperand.pop();
                        //get the sub expression.
                        String subExpr = op1 + stackOperator.pop() + op2;
                        //call the evaluate method recursively to evaluate the
                        //sub expression
                        float value = evaluate(subExpr, vars, arrays);
                        //get the string value of the value
                        String result = String.valueOf(value);
                        //if the value is less than 0
                        if (value < 0) {
                            //replace "-" with "#" in the resulatant string
                            result = result.replace("-", "#");
                        }
                        stackOperand.push(result);
                        stackOperator.push(tok);
                    } else {//ortherwise, push the token into the stackOperator stack.
                        stackOperator.push(tok);
                    }
                    break;
                
                case "+":
                case "-":
                    if (stackOperator.isEmpty()) {
                        stackOperator.push(tok);
                    } else if (!((stackOperator.peek().equals("(")) || (stackOperator.peek().equals("[")))) {
                        //if the peek value of the stackOperator stack is open braces
                        //pop the operands from the stackOperand stack
                        String op2 = stackOperand.pop();
                        String op1 = stackOperand.pop();
                        //get the sub expression.
                        String subExpr = op1 + stackOperator.pop() + op2;
                        //call the evaluate method recursively to evaluate the sub expression
                        float value = evaluate(subExpr, vars, arrays);
                        //get the string value of the value
                        String result = String.valueOf(value);
                        //if the value is less than 0
                        if (value < 0) {
                            //replace "-" with "#" in the resulatant string
                            result = result.replace("-", "#");
                        }
                        //push the result and token into the stacks
                        stackOperand.push(result);
                        stackOperator.push(tok);
                    } else {//ortherwise, push the token into the stackOperator stack.
                        stackOperator.push(tok);
                    }
                    break;
                    //if the token is * or /
                case "(":
                case "[":
                    //if the token is open braces then push into the stackOperator stack.
                    stackOperator.push(tok);
                    break;
                    //if the token is close brace
                case ")":
                    //itrate the loop, until open brace occur
                    while (!stackOperator.peek().equals("(")) {
                        //pop the operands from the stackOperand stack
                        String op2 = stackOperand.pop();
                        String op1 = stackOperand.pop();
                        //get the sub expression.
                        String subExpr = op1 + stackOperator.pop() + op2;
                        //call the evaluate method recursively to evaluate the
                        //sub expression
                        float value = evaluate(subExpr, vars, arrays);
                        //get the string value of the value
                        String result = String.valueOf(value);
                        //if the value is less than 0
                        if (value < 0) {
                            //replace "-" with "#" in the resultant string
                            result = result.replace("-", "#");
                        }
                        //push the result into the stacks
                        stackOperand.push(result);
                    }
                    //pop the operator from the stackOperator stack
                    stackOperator.pop();
                    break;
                case "]":
                    //iterate the loop, until open brace occur
                    while (!stackOperator.peek().equals("[")) {
                        //itrate the loop, until open brace occur
                        String op2 = stackOperand.pop();
                        String op1 = stackOperand.pop();
                        //get the sub expression.
                        String subExpr = op1 + stackOperator.pop() + op2;
                        //call the evaluate method recursively to evaluate the
                        //sub expression
                        float value = evaluate(subExpr, vars, arrays);
                        String result = String.valueOf(value);
                        //if the value is less than 0
                        if (value < 0) {
                            //replace "-" with "#" in the resultant string
                            result = result.replace("-", "#");
                        }
                        //push the result into the stack
                        stackOperand.push(result);
                    }
                    //push the result into the stacks
                    stackOperator.pop();
                    //pop the operands from the stackOperand stack
                    String indexArr = stackOperand.pop();
                    String strArray = stackOperand.pop();
                    //create an object for the Array
                    Array arrSym = new Array(strArray);
                    //get the index value
                    int val = arrays.indexOf(arrSym);
                    int index = (int) Float.parseFloat(indexArr);
                    if (val == -1) {
                        stackOperand.push("0");
                    } else {
                        stackOperand
                        .push(String.valueOf(arrays.get(val).values[index]));
                    }
                    break;
                default:
                    //push number into the stackOperand stack.
                    stackOperand.push(tok);
                    break;
            }
        }
        //iterate the loop until stackOperator is empty
        while (!stackOperator.isEmpty()) {
            float op2 = evaluate(stackOperand.pop(), vars, arrays);
            float op1 = evaluate(stackOperand.pop(), vars, arrays);
            //get the operator
            String operator = stackOperator.pop();
            //call the function CalculateResult()
            float value = CalculateResult(op1, operator, op2);
            //get the string value of the value
            String result = String.valueOf(value);
            //if the value is less than 0
            if (value < 0) {
                //replace "-" with "#" in the resultant string
                result = result.replace("-", "#");
            }
            //push the result into the stacks
            stackOperand.push(result);
        }
        //return the value
        return Float.valueOf(evaluate(stackOperand.pop(), vars, arrays));
    }


    // helper methpds
    private static ArrayList<String> IndexString(String expr) {

        ArrayList<String> expList = new ArrayList<>();
        String tok = new String();
        
        for (int i = 0; i < expr.length(); i++) {
            String ch = String.valueOf(expr.charAt(i));
            if (delims.contains(ch)) {
                if (!tok.isEmpty()) {
                    expList.add(tok);
                    tok = new String();
                }
                expList.add(ch);
            } else {
                tok += ch;
            }
        }
        if (!tok.isEmpty()) {
            expList.add(tok);
        }
        return expList;
    }

    private static float CalculateResult(float operator1, String operator, float operator2) {
        float result;
        switch (operator) {
            case "+":
                result = operator1 + operator2;
                break;
            case "-":
                result = operator1 - operator2;
                break;
            case "*":
                result = operator1 * operator2;
                break;
            case "/":
                result = operator1 / operator2;
                break;
            default:
                result = 0.0f;
                break;
        }
        return result;
    }
}