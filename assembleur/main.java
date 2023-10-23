import java.io.*;
import java.util.*;

public class main {

    //convert negatif number in binary with a 11  bit in java in complement 2


    public static String binaryToHex(String binary) {
        String res = "";
        int digitNumber = 1;
        int sum = 0;
        for (int i = 0; i < binary.length(); i++) {
            if (digitNumber == 1)
                sum += Integer.parseInt(binary.charAt(i) + "") * 8;
            else if (digitNumber == 2)
                sum += Integer.parseInt(binary.charAt(i) + "") * 4;
            else if (digitNumber == 3)
                sum += Integer.parseInt(binary.charAt(i) + "") * 2;
            else if (digitNumber == 4 || i < binary.length() + 1) {
                sum += Integer.parseInt(binary.charAt(i) + "") * 1;
                digitNumber = 0;
                if (sum < 10)
                    res += sum;
                else if (sum == 10)
                    res += "a";
                else if (sum == 11)
                    res += "b";
                else if (sum == 12)
                    res += "c";
                else if (sum == 13)
                    res += "d";
                else if (sum == 14)
                    res += "e";
                else if (sum == 15)
                    res += "f";
                sum = 0;
            }
            digitNumber++;
        }
        return res;
    }

    public static String convertBit(int n, int numOfBits){
            String binary = "";
            for(int i = 0; i < numOfBits; ++i, n/=2) {
                switch (n % 2) {
                    case 0:
                        binary = "0" + binary;
                        break;
                    case 1:
                        binary = "1" + binary;
                        break;
                }
            }
            return binary;
    }

    public static ArrayList<String> readFile(){
        ArrayList<String> res=new ArrayList<>();
        try {
            /*System.out.println("Ecrire le nom du fichier");
            Scanner scan = new Scanner(System.in);

            String filename=scan.nextLine();*/
            String filename= "test.s";

            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                res.add(myReader.nextLine());

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return res;
    }

    public static void createFile(String bin) throws IOException {
        FileOutputStream fos = new FileOutputStream("example.bin");
        fos.write(bin.getBytes());
        fos.flush();
        fos.close();
    }

    public static String fct(String tmp, String[] val){
        tmp += stringToHexa(val, 2, 1, "#",5);
        tmp += stringToHexa(val, 1, 1, "r", 3);
        tmp += stringToHexa(val, 0, 1, "r", 3);
        return tmp;
    }

    public static String fct1(String tmp, String[] val){
        tmp += stringToHexa(val, 2, 1, "#",3);
        tmp += stringToHexa(val, 1, 1, "r", 3);
        tmp += stringToHexa(val, 0, 1, "r", 3);
        return tmp;
    }

    public static String fct2(String tmp, String[] val){
        tmp += stringToHexa(val, 0, 1, "r", 3);
        tmp += stringToHexa(val, 1, 1, "#",8);
        return tmp;
    }

    public static String fct6(String tmp, String val,String val1){
        tmp+=convertBit(Integer.parseInt(val.split("r")[1]),3);
        tmp += convertBit(Integer.parseInt(val1.split("#")[1])/4,8);
        return tmp;
    }
    public static String fct3(String tmp, String[] val){
        tmp += stringToHexa(val, 2, 1, "r",3);
        tmp += stringToHexa(val, 1, 1, "r", 3);
        tmp += stringToHexa(val, 0, 1, "r", 3);
        return tmp;
    }

    public static String fct4(String tmp,String[]val){
        tmp += stringToHexa(val, 1, 1, "r",3);
        tmp += stringToHexa(val, 0, 1, "r", 3);
        return tmp;
    }

    public static String fct5(String tmp,String val){
        tmp += convertBit(Integer.parseInt(val.split("#")[1])/4,7);
        return tmp;
    }
    public static String fct7(String tmp,String[] val){
        tmp+=  stringToHexa(val, 0, 1, "r",3);
        tmp += convertBit(Integer.parseInt(val[1].split("#")[1])/4,8);
        return tmp;
    }

    public static String fct7n(String tmp,String[] val){
        tmp+=  stringToHexa(val, 0, 1, "r",3);
        tmp += convertBit(Integer.parseInt(val[1].split("#")[1]),8);
        return tmp;
    }
    public static String stringToHexa(String[] val, int i, int i2, String regex,int nb){
            return convertBit(Integer.parseInt(val[i].split(regex)[i2]),nb);
    }

    public static String b(int nb){
        String tmp="11100";
        String a =Integer.toBinaryString((nb));
        if(nb>0){
            a=convertBit(nb,11);
            tmp+=a;
        }else {
        if(nb==0) {
            a = "00000000000";
        }
        if(a.length()>11){
            for(int i=a.length()-11;i<a.length();i++){
                tmp+=a.charAt(i);
            }
        }else{
            tmp+=a;
        }
        }
        return binaryToHex(tmp);
    }

    public static String bcond(String tmp,int nb){
        String a =Integer.toBinaryString((nb));
        if(nb>0){
            a=convertBit(nb,8);
            tmp+=a;
        }else {
            if(nb==0) {
                a = "00000000";
            }
            if(a.length()>8){
                for(int i=a.length()-8;i<a.length();i++){
                    tmp+=a.charAt(i);
                }
            }else{
                tmp+=a;
            }
        }
        return binaryToHex(tmp);
    }


    public static int fctBne(String tmp,int cptString,ArrayList<String> assembleur,String valeur,int lastB){
        int cpt=0;
        if(lastB==0){
            for (int i = cptString; i < assembleur.size(); i++) {
                if (assembleur.get(i).equals(valeur + ":")) {
                    break;
                } else {
                    if (!(assembleur.get(i).contains(":") || assembleur.get(i).contains("@"))) {

                        cpt++;
                    }
                }
            }
        }else {
            if (lastB >= Integer.parseInt(tmp)) {
                for (int i = cptString - 1; i > 0; i--) {
                    if (assembleur.get(i).equals(valeur + ":")) {
                        break;
                    } else {
                        if (!(assembleur.get(i).contains(":") || assembleur.get(i).contains("@"))) {
                            cpt++;
                        }
                    }
                }
                cpt = -cpt;
            } else {
                for (int i = cptString; i < assembleur.size(); i++) {
                    if (assembleur.get(i).equals(valeur + ":")) {
                        break;
                    } else {
                        if (!(assembleur.get(i).contains(":") || assembleur.get(i).contains("@"))) {

                            cpt++;
                        }
                    }
                }
            }
        }
        return cpt;
    }
    public static void main(String... args) throws IOException {
            int cptString=0;
            int lastB=0;
            ArrayList<String> assembleur=readFile();
            String bin="";
            if(assembleur == null){
                System.out.println("Fichier vide ou nom du fichier mal écrit");
                assembleur=readFile();
            }
            int cptB=1;

            for (String s:assembleur) {

                if (!(((s.contains(".") || s.contains(":")) && !s.contains(".LBB")) || s.contains("push") || s.contains("@")|| s.equals(""))) {
                    String[] res = s.split("\t");
                    String cmd = res[0];
                    String valeur;
                    if (res.length == 1 && !cmd.contains("run")) {
                        cmd=cmd.split(":")[0];
                        String test="";
                        int a=cmd.length()-6;
                        for(int i= cmd.length()-a;i<cmd.length();i++) {
                            test+=cmd.charAt(i);
                        }
                        lastB=Integer.parseInt(test);
                    }else {
                        if (cmd.isEmpty()) {
                            cmd = res[1];
                            valeur = res[2];
                        } else {
                            valeur = res[1];
                        }
                        String[] val = valeur.split(",");
                        boolean dont = false;
                        switch (cmd) {

                            case "b":
                                String tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += b(fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "bne":
                                tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += bcond("11010001", fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "blt":
                                tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += bcond("11011011", fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "beq":
                                tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += bcond("11010000", fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "bcs":
                                tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += bcond("11010010", fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "blo":
                            case "bhs":
                            case "bcc" :
                                tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += bcond("11010011", fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "bmi":
                                tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += bcond("11010100", fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "bpl":
                                tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += bcond("11010101", fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "bvs":
                                tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += bcond("11010110", fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "bvc":
                                tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += bcond("11010111", fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "bhi":
                                tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += bcond("11011000", fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "bls":
                                tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += bcond("11011001", fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "bge":
                                tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += bcond("11011010", fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "bgt":
                                tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += bcond("11011100", fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "ble":
                                tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += bcond("11011101", fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "bal":
                                tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += bcond("11011110", fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "bnv":
                                tmp = "";
                                cptB = valeur.length() - 6;
                                for (int i = valeur.length() - cptB; i < valeur.length(); i++) {
                                    tmp += valeur.charAt(i);
                                }
                                bin += bcond("11011111", fctBne(tmp, cptString, assembleur, valeur, lastB) - 3);
                                break;

                            case "lsls":
                                if (val.length == 3) {
                                    bin += binaryToHex(fct("00000", val));
                                } else {
                                    bin += binaryToHex(fct4("0100000010", val));
                                }
                                break;

                            case "lsrs":
                                if (val.length == 3) {
                                    bin += binaryToHex(fct("00001", val));
                                } else {
                                    bin += binaryToHex(fct4("0100000011", val));
                                }
                                break;

                            case "asrs":
                                if (val.length == 3) {
                                    bin += binaryToHex(fct("00010", val));
                                } else {
                                    bin += binaryToHex(fct4("0100000100", val));
                                }
                                break;

                            case "adds":
                                if (val.length == 2) {
                                    bin+=binaryToHex(fct2("00110",val));
                                } else {
                                    if (!val[2].contains("#")) {
                                        bin += binaryToHex(fct3("0001100", val));
                                    } else {
                                        bin += binaryToHex(fct1("0001110", val));
                                    }
                                }
                                break;


                            case "subs":
                                if(val.length==2){
                                    bin += binaryToHex(fct7n("00111", val));
                                }else {
                                    if (!val[2].contains("#")) {
                                        bin += binaryToHex(fct3("0001101", val));
                                    } else {
                                        bin += binaryToHex(fct1("0001111", val));
                                    }
                                }
                                break;

                            case "movs":
                                if(val[1].contains("#")) {
                                    bin += binaryToHex(fct2("00100", val));
                                }else{
                                    String[] tampon=new String[3];
                                    for(int i=0;i<val.length;i++){
                                        tampon[i]=val[i];
                                    }
                                    tampon[2]=" #0";
                                    bin += binaryToHex(fct("00000", tampon));
                                }
                                break;

                            case "ands":
                                bin += binaryToHex(fct4("0100000000", val));
                                break;

                            case "eors":
                                bin += binaryToHex(fct4("0100000001", val));
                                break;

                            case "adcs":
                                bin += binaryToHex(fct4("0100000101", val));
                                break;


                            //sbcs 0100000110 000 RM 101 rdn
                            case "sbcs":
                                bin += binaryToHex(fct4("0100000110", val));
                                break;

                            case "rors":
                                bin += binaryToHex(fct4("0100000111", val));
                                break;

                            case "tst":
                                bin += binaryToHex(fct4("0100001000", val));
                                break;

                            case "rsbs":
                                bin += binaryToHex(fct4("0100001001", val));
                                break;

                            case "cmp":
                                if (val[1].contains("#")) {

                                    bin += binaryToHex(fct7n("00101", val));
                                } else {
                                    bin += binaryToHex(fct4("0100001010", val));
                                }
                                break;
                            case "cmn":
                                bin += binaryToHex(fct4("0100001011", val));
                                break;

                            case "orrs":
                                bin += binaryToHex(fct4("0100001100", val));
                                break;

                            case "muls":
                                bin += binaryToHex(fct4("0100001101", val));
                                break;
                            case "bics":
                                bin += binaryToHex(fct4("0100001110", val));
                                break;
                            case "mvns":
                                bin += binaryToHex(fct4("0100001111", val));
                                break;

                            case "str":
                                String val1 = valeur.split(",")[0];
                                String val2="";
                                if(valeur.split(",").length==2){
                                        bin+="9000";
                                }else {
                                    val2 = valeur.split(",")[2];
                                    bin += binaryToHex(fct6("10010", val1, val2.split("]")[0]));
                                }
                                break;

                            case "ldr":
                                val1 = valeur.split(",")[0];
                                if(valeur.split(",").length==2){
                                    String[] valldr=new String[2];
                                    valldr[0]=val[0];
                                    valldr[1]=" #0";
                                    bin+=binaryToHex(fct2("10011",valldr));
                                }else {
                                    val2 = valeur.split(",")[2];
                                    bin += binaryToHex(fct6("10011", val1, val2.split("]")[0]));
                                }
                                break;

                            case "add":
                                if (!(valeur.split(",").length == 3)) {
                                    val1 = valeur.split(",")[1];
                                    bin += binaryToHex(fct5("101100000", val1));
                                }else{
                                    dont=true;
                                }

                                break;

                            case "sub":
                                val1 = valeur.split(",")[1];
                                bin += binaryToHex(fct5("101100001", val1));
                                break;

                            case default:
                                break;

                        }

                        if(!dont){
                            bin += " ";
                        }
                        dont=false;

                    }
                }
                cptString++;
            }
            createFile(bin);
    }

}


