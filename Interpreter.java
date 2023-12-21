import java.util.*;

public class Interpreter {
    
    public static HashMap<String, Integer> m = new HashMap<String, Integer>();
    public static void main(String[] args){
            ArrayList<Deque<String>> clist  = toCharList(args);
            System.out.println(clist);
            evaulateAssignments(clist);
            for (HashMap.Entry<String, Integer> entry : m.entrySet()){
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
        }

    public static ArrayList<Deque<String>> toCharList(String[] s) throws IllegalArgumentException{
        ArrayList<Deque<String>> assignmentList  = new ArrayList<Deque<String>>();
        for (String a : s){
            Deque<String> c = new LinkedList<String>();
            for(int j = 0; j < a.length(); j++){
                if(a.charAt(j) == ' ' || a.charAt(j) == '\n' ){
                    continue;
                }
                else{
                    c.add(a.substring(j, j+1));
                }
            }
            if (!(c.getLast().equals(";"))){
                    throw new IllegalArgumentException("Missing a semicolon"); 
            }
            else{
                assignmentList.add(c);
            }
        }
        
        return assignmentList;
    }

    public static void evaulateAssignments(ArrayList<Deque<String>> c){
        for (int i = 0; i < c.size(); i++){
            HashMap.Entry<String, Integer> entry = parseAssignment(c.get(i));
            m.put(entry.getKey(), entry.getValue() );
        }

    }

    public static Map.Entry<String, Integer> parseAssignment(Deque<String> c) throws IllegalArgumentException  {
        String s = parseId(c);
        if(!(c.remove().equals("="))){
            throw new IllegalArgumentException("Missing equals sign in assignment");
        }
        int value = parseExpression(c);
        
        return new AbstractMap.SimpleEntry<String, Integer>(s, value);
    }
    
    public static String parseId (Deque<String> s) throws IllegalArgumentException {
        String result = "";
        if(!(s.peek().matches("[a-zA-Z_]"))){
            throw new IllegalArgumentException("Inavalid Identifier");
        }
        while((s.peek().matches("(\\w)"))){
            result += s.remove();
        }
        return result;
    }
    /*potential problem: an expression could end up being an identifier, which is supposed
    *to be another previously assigned value - that would return a String, and require evaulation of the 
    *previous elements in the arraylist to be evaluated first - not sure what to do about that
    maybe check through previous map entrys for the key and get its value pair 
    edit: solved using above method
    */
    public static int parseExpression (Deque<String> s){
        int value = parseTerm(s);
        while (s.peek().equals("+") || s.peek().equals("-")){
            String op = s.remove();
            int nextTerm = parseTerm(s);
            if(op.equals("+")){
                value += nextTerm;
            }
            else{
                value -= nextTerm;
            }
        }
        return value;
    }

    public static int parseTerm (Deque<String> s){
        int value = parseFactor(s);
        while (s.peek().equals("*")){
            s.remove();
            int nextTerm = parseFactor(s);
            value *= nextTerm;
        }
        return value;
    }
    //solved unary operator problems - was missing s.remove() for each operator, which was causing an infite recursive loop
    public static int parseFactor(Deque<String> s) throws IllegalArgumentException{
        int value;
        if (s.peek().equals("(")){
            s.remove();
            value = parseExpression(s);
            if(!(s.remove().equals(")"))){
                throw new IllegalArgumentException("Missing parentheses");
            }
        }
        else if (s.peek().equals("-")){
            s.remove();
            value = -parseFactor(s);
        }
        else if (s.peek().equals("+")){            
            s.remove();
            value = +parseFactor(s);
        }
        else if (s.peek().matches("\\d")){
            value = parseLiteral(s);
        }
        else{
            value = (m.get(parseId(s)));
        }
        return value;
    }

    public static int parseLiteral(Deque<String> s) throws IllegalArgumentException{
        String result = "";
        while((s.peek().matches("0|[1-9\\d*]")) && !s.isEmpty()){
            result += s.remove();
            if (result.length() >= 2 && result.substring(0,2).equals("00")){
                throw new IllegalArgumentException("Bad literal - double 0");
            }
        }

        return Integer.parseInt(result);
    }
}
    

