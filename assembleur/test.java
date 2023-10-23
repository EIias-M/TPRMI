import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

import org.junit.Test;

public class test {
    // add r0,r1,r2 = r0=r1+r2 addition
    // ADD R2, R1, #3 = R2= R1+3
    // sub r0,r1,r2 = r0=r1-r2 soustraction
    // SUB R2, R1, #3 = R2= R1-3
    // and r0,r1 = r0=r1&r0 et
    // eor r0,r1 = r0=r1^r0 ou eclusif
    // LSL vers la gauche rd=rm decalé de imm (Left Shift Logical) deplacement logique
    // LSR vers la droite rd=rm decalé de imm (Logical Shift Right) deplacement logique
    // asrs (Arithmetic Shift Right) rd=rm decalé de imm
    // ASL (Arithmetic Shift Left) rd=rm decalé de imm
    // adc r0,r1,r2 = r0=r1+r2+C addition avec retenue
    // ror decalage circulaire vers la droite
    // tst r0,r1 = cpsr <= r0&r1 tester les bits indiqué par r1 Flag Z CPSR
    // rsb r0,r1,r2 = r0=r2-r1 soustraction inversée
    // cmp r0,r1 = psr <= r0-r1 comparer permet de faire les flags Z,N,C,V
    // cmn r0,r1 = psr <= r0+r1 comparer a l'inverse R0 et R1 seront additionnés (bit à bit) et le résultat sera comparé avec zéro
    // orr r0,r1  = r0=r1|0 ou
    // muls r0,r1 = multiplication
    // bics et inversé
    // mvns r0,r1 = r0 = !r1 NOT
    // str r3, [sp, #768] (Store register) valeur R3 sera stockée dans la mémoire à l'emplacement déterminé par l'addition de l'adresse de départ SP et de l'offset #768
    // ldr (load register) charge la valuer située a l'adreses = sp+768 dans R3
    // ADD SP, #256 signifie Add to Stack Pointer Cette instruction ajoutera la valeur 256 à l'adresse actuelle du pointeur de pile (SP).
    // sub SP, #256 signifie sub to Stack Pointer Cette instruction soustrait la valeur 256 à l'adresse actuelle du pointeur de pile (SP).


    //BEQ signifie Branch if Equal l'instruction effectue un saut vers une nouvelle adresse dans le code si la valeur dans le registre de condition (flag) est égale à zéro.
    //BNE Branch if Not Equal flag not equal
    //bcs Branch if Carry Set si carry est defini fait le saut
    // bcc carry Clear si carry est effacé
    // BMI signifie Branch if Minus l'instruction effectue un saut vers une nouvelle adresse dans le code si le flag de signe est défini.
    // BMI signifie Branch if plus  signe non defini
    // bvs Branch if Overflow Set
    // bvc overflow clear
    // bhi Branch if Higher si le flag de carry est défini et le flag de zéro n'est pas défini.
    // bls Branch if Lower or Same  flag de carry n'est pas défini ou si le flag de zéro est défini.
    // bge  if Greater than or Equal  if the sign and overflow flags are the same.
    // blt Branch if Less Than sign != overflow
    // bgt Branch if greater Than zero not set ET overflow != signe OU overflown not set
    // ble  Branch if Less Than or Equal the zero flag is set or the sign = overflow flags or the overflow flag is set
    // bal Branch And Link and the instruction performs a jump to a new address in code and stores the return address in the link register (LR).
    // bnv
    // b va au label
    public static String e(int nb){
        String tmp="";
        String a =Integer.toBinaryString((nb));
        if(nb>0){
            a= main.convertBit(nb,8);
            tmp+=a;
        }else {
            if(nb==0) {
                a = "00000000000";
            }
            if(a.length()>8){
                for(int i=a.length()-8;i<a.length();i++){
                    tmp+=a.charAt(i);
                }
            }else{
                tmp+=a;
            }
        }
        return tmp;
    }
    @Test
    public void test(){
        System.out.println(e(-67));
        System.out.println(main.binaryToHex("1001100101001100"));

    }
}
