import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class test_project {

    public static String mathSymbols = "+-*/";
    public static int operand = 0;

    public static void main (String[] args) {
        start ();
    }

    public static void start () {
        operand = 0;
        System.out.println("Введите выражение");
        Scanner keyboardScan = new Scanner (System.in);
        String string = keyboardScan.nextLine();
        char[] chStr = string.toCharArray();
        String firstStrCh = String.valueOf(chStr[0]);
        String lastStrCh = String.valueOf(chStr[chStr.length-1]);

        if (mathSymbols.contains(firstStrCh) || mathSymbols.contains(lastStrCh)) {
            error("Ошибка ввода. неправильная последовательность арифметических знаков");
        }
        for (int i = 0; i<chStr.length; i++)
        {
            if (i>1 && (chStr[i] == chStr[i-1])) {
                if (mathSymbols.contains(String.valueOf(chStr[i]))) {
                    error("Ошибка ввода. неправильная последовательность арифметических знаков");
                }
            } 
        }
        calc (string);
    }

    public static void calc (String arg) {
        int o = 0;
        Boolean lat = false;
        Boolean rom = false;
        Boolean contune = true;
        String romString = "ivxzlcdm";
        String [] calcArray = new String [arg.length()];
        Arrays.fill(calcArray, "");

        for (String s: arg.toLowerCase().split("")) {
            try {
                int i = Integer.parseInt (s);
                lat = true;
                calcArray [o] += s;
            }
            catch (NumberFormatException e) {
                if (romString.contains(s)){
                    calcArray [o] += s;
                    rom = true;     
                    }
                else{
                    o++;
                    calcArray [o] += s;
                    o++;
                    operand++;
                    }
            }
            if (rom == lat) {
                error ("Ошибка ввода. Калькулятор не складывает арабские и римские числа");
            }
        }
        if (contune == true & lat == true) {
            latCalc (calcArray, o, false);
        }
        else if (contune == true & rom == true) {
            romToLat (calcArray, o);
        }
    }

    public static void romToLat (String[] args, int arg) {
        int currentElement = 0;
        int beforeElement = 0;
        int calcElement = 0;
        String result = "";
        String [] calcArray = new String [arg+1];
        HashMap<String, Integer> latRomMap = new HashMap<>();
        latRomMap.put("i", 1);
        latRomMap.put("v", 5);
        latRomMap.put("x", 10);
        latRomMap.put("l", 50);
        latRomMap.put("c", 100);
        latRomMap.put("d", 500);
        latRomMap.put("m", 1000);

        for (int i = 0; i <= arg; i++) {
            for (String element: args[i].split("")){
                if (!mathSymbols.contains(element)) {
                    if (calcElement == 0) {
                        calcElement = currentElement = beforeElement = latRomMap.get(element);
                    }
                    else {
                        currentElement = latRomMap.get(element);
                        if (beforeElement >= currentElement) {
                            calcElement += currentElement;
                            beforeElement = currentElement;
                        }
                        else {
                            calcElement += currentElement - (beforeElement*2);
                            beforeElement = currentElement;
                        }
                    }   
                }
                else {
                    currentElement=calcElement=beforeElement=0;
                    result = element;
                }
            }
            if (calcElement != 0) {result = String.valueOf(calcElement);}
            calcArray[i] = result;
        }
        latCalc (calcArray, arg, true);
    }

    public static void latToRom (int arg) {
        String [] rom = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
        String result = "";
        int [] lat = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
        int i = 0;

        while (i < rom.length) {
            while (arg >= lat[i]) {
                result += rom[i];
                arg -= lat[i];
            }
            i++;
        }
        System.out.println("Результат: "+result+"\n");
        start ();
    }
 
    public static void latCalc (String[] args, int arg, Boolean rom) {
        int result=0;
        int n=0;
        for (int i = 0; i <= arg; i++) {
            try {
                if (i == 0 ) {
                    n = Integer.parseInt (args[i]);
                    result = n;
                }
                else if (i%2 == 0) {
                    n = Integer.parseInt (args[i]);
                }
                else {
                    n = Integer.parseInt (args[i+1]);
                    switch (args[i]) {
                        case "+": result = result + n; break;
                        case "-": result = result - n; break;
                        case "*": result = result * n; break; 
                        case "/": result = result / n; break; 
                        default: error ("Неверный оператор: " + args[i]);
                    }
                }
            }
            catch (NumberFormatException e) {
                error ("Ошибка ввода данных. Число слишком большое для типа Integer");
            }
        }
        if (operand > 1) {
            System.out.print("Количество операндов не соответствует заданию, но результат вы всё равно узнаете.\n");
        }
        else if (operand == 0) {
            error ("Не является арифметическим выражением");
        }
        if (rom & result > 0 && result < 4000) {latToRom(result);}
        else if (rom) {System.out.print("Вне диапазона допустимых римских числе (1—3999). Результат: " + result+"\n"); start ();}
        else {System.out.print("Результат: " + result+"\n"); start ();}
    }
    
    public static void error (String message) {
        System.out.print(message+"\n"); 
        start ();
        return;
    }
}
