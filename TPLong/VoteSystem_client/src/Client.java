import global.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Récupération du registre RMI
            Registry reg = LocateRegistry.getRegistry(2002);

            // Recherche du service "Vote" dans le registre
            StubServer s = (StubServer) reg.lookup("Vote");

            // Démarrage de la session de vote côté serveur
            s.startVotingSession();

            // Attendre que la session de vote soit démarrée par le serveur
            while (!s.isVotingSessionActive()) {
                Thread.sleep(1000);

            }

            // Récupération de la liste des candidats
            ArrayList<Candidate> candidates = s.getCandidates();

            // Affichage des informations sur les candidats
            for (Candidate c : candidates) {
                System.out.println("___________________________________________________________________________");
                System.out.println("Candidat : " + c.getRank() + ": " + c.firstName + " " + c.lastName + "\npitch:" + c.getPitch() + "\n");
                System.out.println("___________________________________________________________________________");
            }

            // Création d'un votant factice (Stub_votant)
            ICandidat sv = new Stub_votant();
            int snumbdr = sv.getVoterSNumber();

            // Récupération du matériel de vote
            VotingSystem v = s.getVoteMaterial(snumbdr, sv);

            // Affichage de l'OTP
            v.printOtp();

            boolean start = false;

            System.out.println("___________________________________________________________________________");

            // Boucle pour vérifier l'OTP
            while (!start) {
                System.out.println("Veuillez entrer votre OTP :");
                start = v.checkOTP();
            }

            System.out.println("___________________________________________________________________________");
            System.out.println("Vous pouvez voter:");
            if (s.isVotingSessionActive()) {
                v.manageVote(v.getOTP(), candidates, snumbdr,s);
                Map<Integer, Integer> userVotes = v.getVotes();

                // Soumission des votes au serveur
                s.submitVotes(userVotes, v.getVoteInfos());
            } else {
                System.out.println("La session de vote est terminée. Vous ne pouvez pas voter.");
            }
            // Lancement de la gestion du vote
            v.manageVote(v.getOTP(), candidates,snumbdr,s );
            Map<Integer, Integer> userVotes = v.getVotes();

            // Soumission des votes au serveur
            s.submitVotes(userVotes, v.getVoteInfos());


          // Attendre que la session de vote soit terminée par le serveur
            while (s.isVotingSessionActive()) {
                Thread.sleep(1000);
            }

            // Affichage des résultats
            List<Candidate> winners = s.findWinner(userVotes, candidates);
            System.out.println("Les gagnants sont :");
            for (Candidate winner : winners) {
                System.out.println(winner.firstName + " " + winner.lastName + " (Candidat " + winner.getRank() + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
