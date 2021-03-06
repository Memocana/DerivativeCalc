/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package derivativecalc;

import java.util.Scanner;

/**
 *
 * @author Memo
 */
public class DerivativeCalc {
public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String str=console.nextLine().toLowerCase();
        String spaceless = "";
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) != ' ')
                spaceless += str.charAt(i);
        }
        System.out.println(spaceless);
        String corrections = "";
        for (int i = 0; i < spaceless.length(); i++) {
            if(spaceless.charAt(i) == 'x')
                if (i == 0 || !isNumeric(spaceless.charAt(i-1)+""))
                    corrections += "1";
            corrections += spaceless.charAt(i);
            if(spaceless.charAt(i) == 'x')
                if (i == spaceless.length() - 1 || spaceless.charAt(i+1) != '^')
                    corrections += "^1";
        }
        
        String unFormatted = derive(corrections);
        System.out.println(unFormatted);
        unFormatted = format(unFormatted);
        System.out.println(unFormatted);
        unFormatted = format(unFormatted);
        System.out.println(unFormatted);
    }  
    public static boolean isNumeric(String str)  {  
        try  {  
            double d = Double.parseDouble(str);  
        }  
        catch(NumberFormatException nfe) {  
            return false;  
        }  
        return true;  
    }
    public static String derive(String str){
        System.out.println(str);
        if (str.isEmpty())
            return "";
        if (str.indexOf("x") == -1)
            return "0";
        if (str.indexOf("+") != -1 && (str.indexOf("(")== -1 || (str.indexOf("(") > str.indexOf("+") || findMatch(str,str.indexOf("(")) < str.indexOf("+"))) && (str.indexOf("-") <= 0 || str.indexOf("-") > str.indexOf("+")))
            return derive(str.substring(0,str.indexOf("+")))+" + " +derive(str.substring(str.indexOf("+")+1));
        if (str.substring(str.indexOf("^")+2).indexOf("-") == 0|| str.substring(str.indexOf("^")+2).indexOf("-") > 0 && (isNumeric(str.substring(str.indexOf("^")+2).charAt(str.substring(str.indexOf("^")+2).indexOf("-")-1)+"") || str.substring(str.indexOf("^")+2).charAt(str.substring(str.indexOf("^")+2).indexOf("-")-1) == ')') && (str.substring(str.indexOf("^")+2).indexOf("(")== -1 || (str.substring(str.indexOf("^")+2).indexOf("(") > str.substring(str.indexOf("^")+2).indexOf("-") || findMatch(str,str.substring(str.indexOf("^")+2).indexOf("(")) < str.substring(str.indexOf("^")+2).indexOf("-"))))
            return derive(str.substring(0,str.substring(str.indexOf("^")+2).indexOf("-")+str.substring(0,str.indexOf("^")+2).length()))+" - " +derive(str.substring(str.substring(str.indexOf("^")+2).indexOf("-")+str.substring(0,str.indexOf("^")+2).length()+1) );
        if (str.indexOf("*") != -1 && (str.indexOf("(")== -1 || (str.indexOf("(") > str.indexOf("*") || findMatch(str,str.indexOf("(")) < str.indexOf("*")))) //product
            return derive(str.substring(0,str.indexOf("*")))+"*"+str.substring(str.indexOf("*")+1) +" + " +str.substring(0,str.indexOf("*")) +"*"+derive(str.substring(str.indexOf("*")+1));
        else if (str.indexOf("/") != -1 && (str.indexOf("(")== -1 || (str.indexOf("(") > str.indexOf("/") || findMatch(str,str.indexOf("(")) < str.indexOf("/")))) //quotient
            return "("+derive(str.substring(0,str.indexOf("/")))+"*"+str.substring(str.indexOf("/")+1) +" - " +str.substring(0,str.indexOf("/")) +"*"+derive(str.substring(str.indexOf("/")+1))+") / (" +str.substring(str.indexOf("/")+1)+")^2" ;
        else if (str.indexOf("sin(") != -1 && (str.indexOf("(")== -1 || str.indexOf("(") > str.indexOf("sin("))) //sin
            return "cos("+(str.substring(str.indexOf("(")+1,findMatch(str,str.indexOf("(")))) +") * (" +derive(str.substring(str.indexOf("(")+1,findMatch(str,str.indexOf("("))))+")";
        else if (str.indexOf("cos(") != -1 && (str.indexOf("(")== -1 || str.indexOf("(") > str.indexOf("cos("))) //cos
            return "-sin("+(str.substring(str.indexOf("(")+1,findMatch(str,str.indexOf("(")))) +") * (" +derive(str.substring(str.indexOf("(")+1,findMatch(str,str.indexOf("("))))+")";
        else if (str.indexOf("tan(") != -1 && (str.indexOf("(")== -1 || str.indexOf("(") > str.indexOf("tan("))) //tan
            return "sec^2("+(str.substring(str.indexOf("(")+1,findMatch(str,str.indexOf("(")))) +") * (" +derive(str.substring(str.indexOf("(")+1,findMatch(str,str.indexOf("("))))+")";
        else if (str.indexOf("cot(") != -1 && (str.indexOf("(")== -1 || str.indexOf("(") > str.indexOf("cot("))) //cot
            return "-csc^2("+(str.substring(str.indexOf("(")+1,findMatch(str,str.indexOf("(")))) +") * (" +derive(str.substring(str.indexOf("(")+1,findMatch(str,str.indexOf("("))))+")";
        else if (str.indexOf("e^(") != -1 && (str.indexOf("(")== -1 || str.indexOf("(") > str.indexOf("e")))
            return str.substring(0,findMatch(str,str.indexOf("("))+1)+" * ("+derive(str.substring(str.indexOf("(")+1,findMatch(str, str.indexOf("("))))+")";
        else if (str.indexOf("ln(") != -1 && (str.indexOf("(")== -1 || str.indexOf("(") > str.indexOf("ln("))) /* ln x */
            return "1 / ("+str.substring(str.indexOf("ln(") + 3,findMatch(str,str.indexOf("(")))+") * ("+derive(str.substring(str.indexOf("(")+1,findMatch(str,str.indexOf("("))))+")";
        else if (str.indexOf("log(") != -1 && (str.indexOf("(")== -1 || str.indexOf("(") > str.indexOf("log("))) /* log x */
            return "1 / ("+str.substring(str.indexOf("(") + 1,findMatch(str,str.indexOf("(")))+"*ln(10)) * ("+derive(str.substring(str.indexOf("(")+1,findMatch(str,str.indexOf("("))))+")";
        else if (str.indexOf("sqrt(") != -1 && (str.indexOf("(")== -1 || str.indexOf("(") > str.indexOf("sqrt("))) /* sqrt x */
            return "("+str.substring(str.indexOf("(") + 1,findMatch(str,str.indexOf("(")))+")^(-1/2) * ("+derive(str.substring(str.indexOf("(")+1,findMatch(str,str.indexOf("(")))) +")";
        else if(str.indexOf("^(") != -1 && str.indexOf("(") > str.indexOf("^(") )
            return str.substring(0,findMatch(str,str.indexOf("("))+1)+" * ln("+str.substring(0, str.indexOf("^")) +") * ("+derive(str.substring(str.indexOf("(")+1,findMatch(str, str.indexOf("("))))+")";
        else if(str.indexOf("(") == 0)
            return derive(str.substring(str.indexOf("(")+1,findMatch(str,str.indexOf("("))));
        else if(str.indexOf("^") != -1 && str.indexOf("^") + 2 < str.length() && str.charAt(str.indexOf("^") - 1) == 'x' && str.charAt(str.indexOf("^") + 2) == 'x') //x^x
            return "(ln(x) + 1)x^(x)" +derive(str.substring(str.indexOf("x")+6));
        else if (str.indexOf("^") != -1 && str.indexOf("^") - str.indexOf("x") == 1 && (str.indexOf("(") == -1 || str.indexOf("(") > str.indexOf("^"))) 
            return (Double.parseDouble(str.substring(0,str.indexOf("x")))*Double.parseDouble((str.substring(str.indexOf("^")+1))) + "x^(" + (Double.parseDouble(str.substring(str.indexOf("^")+1))-1))+")";
        else if (str.indexOf("^") != -1)
            return str.substring(str.indexOf("^")+1,str.indexOf("("))+" * "+(str.substring(0,str.indexOf("^")) +str.substring(str.indexOf("("),findMatch(str,str.indexOf("("))+1)) +"^(" +(Double.parseDouble(str.substring(str.indexOf("^")+1,str.indexOf("("))) - 1) +") * ("+derive(str.substring(0,str.indexOf("^")) +str.substring(str.indexOf("("),findMatch(str,str.indexOf("("))+1)) +")";
        return str;
    }
    public static String format(String str) {
        String formatted = "";
        for (int i = 0; i < str.length(); i++) {
            if(i<str.length()-7&&str.substring(i,i+8).equals(" * (1.0)"))
                i+=9;
            if(i<str.length()-5&&str.substring(i,i+6).equals("*(1.0)"))
                i+=7;
            if(i<str.length()-3&&str.substring(i,i+4).equals("^1.0"))
                i+=4;
            if(i<str.length()-3&&str.substring(i,i+4).equals("-1*-"))
                i+=4;
            if(i<str.length()-1&&str.substring(i,i+2).equals("0*")&&!str.substring(i-1,i).equals("."))
                i+=findNextAdd(str.substring(i));
            if(i<str.length()-1&&str.substring(i,i+2).equals("*0")){
                formatted=formatted.substring(0, findPrevAdd(formatted)-1);
                i+=2;
            }
            if(i+1 < str.length() && str.charAt(i) == '1' && (str.charAt(i+1) == 'x' && (i - 1 < 0 || (i - 1 >= 0 && !isNumeric(str.substring(i-1,i))))) ) {
                i+=1;
            }
            if (i+1 < str.length() && str.charAt(i) == '^' && (str.charAt(i+1) == '1' &&(i + 2 >= str.length() || (str.charAt(i+2) != 'x' && !isNumeric(str.substring(i+2,i+3)))))) {
                i+=2;
            }
            if (i+2 < str.length() && str.charAt(i) == '^' && ( str.charAt(i+2) == '1' && str.charAt(i+3) == ')')) {
                i+=4;
            }
            if (i+3 < str.length() && str.charAt(i) == 'x' && (str.charAt(i+1) == '^' && str.charAt(i+3) == '0')) {
                if (!((i - 1 < 0 || (i - 1 >= 0 && isNumeric(str.substring(i-1,i)) && str.charAt(i-1) != '1'))))
                    formatted += "1";
                i+=7;
            }
            if (i<str.length()-2&&str.substring(i,i+2).equals("^(")&& notHasX(str.substring(i+1))) {
                i+=1;
                formatted+="^";
                formatted+=str.substring(i+1,i+findMatch(str.substring(i),0));
                i+=findMatch(str.substring(i),0)+1;
            }
            if(i < str.length()) {
                formatted += str.charAt(i);
            }
        }
        return formatted;
    }
    
    /*public static String repeatFormatter(String str) {
        String formatted = "";
        int lost = 0;
        for (int i = 0; i <= str.length(); i++) { 
            if(i<str.length()-1&&str.substring(i,i+2).equals("*(")){
                if (str.charAt(i-1) == ')') {
                    
                    String repeat = pattern(str, i-1);
                    int times = repeatAmount(str, i-1, repeat);
                    if (times != 1) {
                        System.out.println(formatted);
                        System.out.println(repeat.length());
                        formatted = formatted.substring(0,(i-lost)-repeat.length()-1) + fixRepeat(str, i-1)+")";
                        i+= (times-1)*repeat.length()+3;
                        lost += (times-1)*repeat.length()-1;
                        System.out.println(formatted);
                    }
                }
            }
            if(i < str.length()) {
                formatted += str.charAt(i);
            }
        }
        return formatted;
    }*/
    
    public static boolean notHasX(String str) {
        for (int i = 0; i < findMatch(str,0); i++) {
            if (str.charAt(i)=='x')
                return false;
        }
        return true;
    }
    
    public static int findMatch(String str, int pos) {
        int open = 1;
        for (int i = pos + 1; i < str.length(); i++) {
            if (str.charAt(i) == '(')
                open++;
            if (str.charAt(i) == ')')
                open--;
            if (open == 0)
                return i;
        }
        return pos + 2;
    }
    
    public static int findMatchRev(String str, int pos) {
        int open = 1;
        for (int i = pos-1; i >= 0; i--) {
            if (str.charAt(i) == ')')
                open++;
            if (str.charAt(i) == '(')
                open--;
            if (open == 0)
                return i;
        }
        return pos - 2;
    }
    
    public static int findNextAdd(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(')
                i = findMatch(str,i);
            if (str.charAt(i) == '+' || str.charAt(i) == '-')
                return i+2;
        }
        return str.length();
    }
    
    public static int findPrevAdd(String str) {
        for (int i = str.length()-1; i >= 0; i--) {
            if (str.charAt(i) == ')')
                i = findMatchRev(str,i);
            if (str.charAt(i) == '+' || str.charAt(i) == '-')
                return i-2;
        }
        return str.length();
    }
    /*
    public static String pattern(String str, int pos) {
        int repeatPos = findMatchRev(str,pos);
        return str.substring(repeatPos+1,pos);
    }
    
    public static int repeatAmount(String str, int pos, String repeat) {
        int length = repeat.length()+3;
        int repeatPos = findMatchRev(str,pos);
        int times = 0;
        for (int i = repeatPos; i < str.length()-length; i+=length) {
            System.out.println(str.substring(i+1,i+length-2)+"repeated pattern");
            if (str.substring(i+1,i+length-2).equals(repeat)) {
                times++;
            } else {
                break;
            }
        }
        return times;
    }
    
    public static String fixRepeat(String str, int pos) {
        int repeatPos = findMatchRev(str,pos);
        String repeat = pattern(str, pos);
        int length = repeat.length()+3;        
        int times = repeatAmount(str, pos, repeat);
        if (repeat.indexOf('x') != -1) {
            int xPos = repeat.indexOf('x');
            String katSayi = "0";
            if (xPos != 0) {
                katSayi = repeat.substring(0,xPos);
            }
            String power = "1";
            if (repeat.substring(xPos).indexOf('^') != -1) {
                power = repeat.substring(xPos+2);
            }
            double requiredKatSayi = Math.pow(Double.parseDouble(katSayi), times);
            double requiredPower = Double.parseDouble(power)*times;
            return requiredKatSayi+"x^"+requiredPower;
        } else {
            double requiredValue = Math.pow(Double.parseDouble(repeat), times);
            return requiredValue+"";
        }
        
    }*/
}
//ceeeeeeem
