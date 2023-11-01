import global.Candidate;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Admin {
    public static void addCandidates(ObjectOutputStream oos, Candidate c1) throws Exception {
        oos.writeObject(c1);
    }



    public static void main(String[] args) throws Exception {
        FileOutputStream fileStream = new FileOutputStream("C:\\Users\\elias\\OneDrive\\Bureau\\Tp_vote_2\\Tp_vote\\Tp vote\\TPLong (1)\\TPLong 2\\src\\candidat.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fileStream);
        boolean start =true;
        Scanner sc= new Scanner(System.in);
        int i=0;
        while(start){
            System.out.println("Veuillez entrer un nombre, 1 pour ajouter un candidat , 2 pour quitter");
            i=sc.nextInt();
            switch (i){
                case 1:
                    String pitch,nom,prenom,passw;
                    int snumber;
                    System.out.print("Veuillez entrer un pitch :");
                    sc.nextLine();
                    pitch=sc.nextLine();
                    System.out.print("Veuillez entrer le nom du candidat:");
                    nom=sc.nextLine();
                    System.out.print("Veuillez entrer le prenom du candidat:");
                    prenom=sc.nextLine();
                    System.out.print("Veuillez entrer le numero d'étudiant du candidat:");
                    snumber= sc.nextInt();
                    System.out.println(snumber);
                    System.out.print("Veuillez entrer votre mot de passe:");
                    sc.nextLine();
                    passw=sc.nextLine();
                    Candidate c1=new Candidate(pitch,nom,prenom,snumber,passw);
                    addCandidates(oos,c1);
                    break;
                default:
                    start=false;
            }
        }
    }
}
