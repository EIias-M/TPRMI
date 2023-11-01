
package global;

import global.Execption.RankException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

public class Vote implements VotingSystem, Serializable {

    private List<VoteInformation> voteInfos;
    private ArrayList<Integer> hasVoted = new ArrayList<Integer>();
    private Map<Integer, Integer> votes;
    private int realOTP;
    private boolean voteInProgress;
    private int currentVoterID;
    private boolean isVoteSessionActive;

    public Vote() {
        this.votes = new HashMap<>();
        this.voteInfos = new ArrayList<>();
    }

    public void addVoteLog(Date voteDate, int studentId, String voterID) {
        VoteInformation log = new VoteInformation(voteDate, studentId, voterID);
        voteInfos.add(log);
    }

    public Vote(int realOTP, boolean isVotingSessionActive) {
        this.votes = new HashMap<>();
        this.realOTP = realOTP;
        this.voteInProgress = false;
        this.voteInfos = new ArrayList<>();
        this.isVoteSessionActive = isVotingSessionActive;
    }

    @Override
    public List<VoteInformation> getVoteInfos() {
        return voteInfos;
    }

    public boolean addVote(int rank, int voteValue) {
        if (votes.containsKey(rank)) return false; // Voted two times for the same candidate, so error
        else {
            votes.put(rank, voteValue);
            return true;
        }
    }

    public void setVoteSessionActive(boolean isActive) {
        this.isVoteSessionActive = isActive;
    }

    public List<VoteInformation> getVoteLogs() {
        return voteInfos;
    }

    public void manageVote(String OP, List<Candidate> candidates, int studentid, StubServer s) throws RankException, RemoteException {
        int voterID = Integer.parseInt(OP);
        if (!isVoteSessionActive) {
            System.out.println("La session de vote est terminée. Les nouveaux votes ne sont plus acceptés.");
            return;
        } else {
            if (hasVoted.contains(voterID)) {
                System.out.println("Vous avez déjà voté. Vous ne pouvez pas voter plusieurs fois.");
            } else {
                if (!voteInProgress) {
                    startNewVote(voterID, s);
                    voteInProgress = true;
                    vote(OP, candidates, studentid, s);
                } else {
                    if (isVoteSessionActive) {
                        endVoteAndChooseAction(OP, candidates, studentid, s);
                    } else {
                        System.out.println("Session de vote terminée.");
                    }
                }
            }
        }
    }

    @Override
    public void vote(String OP, List<Candidate> candidates, int studentId, StubServer s) throws RankException, RemoteException {
        clearVotes();
        Scanner sc = new Scanner(System.in);
        int voterID = Integer.parseInt(OP);

        if (voteInProgress && currentVoterID == voterID) {
            for (Candidate candidate : candidates) {
                System.out.println("Candidat " + candidate.Rank + ": " + candidate.firstName + " " + candidate.lastName + "\npitch:" + candidate.pitch + "\n");

                boolean isValid = false;
                int valeur = 0;
                while (!isValid) {
                    System.out.println("Entrer votre note de satisfaction pour " + candidate.firstName);
                    valeur = sc.nextInt();

                    try {
                        checkValue(valeur);
                        isValid = true;
                    } catch (RankException e) {
                        System.out.println(e.getMessage());
                    }
                }
                addVote(candidate.Rank, valeur);
            }
            System.out.println("Vote recorded for Candidate " + getVotes());
            hasVoted.add(voterID);
            voteInProgress = false;
            currentVoterID = -1;
            endVoteAndChooseAction(OP, candidates, studentId, s);
        } else {
            System.out.println("Vous ne pouvez pas voter maintenant. Assurez-vous d'avoir une session de vote active.");
        }
    }

    public void printOtp() {
        System.out.println("Votre OTP est " + realOTP + ". N'hésitez pas à le noter");
    }

    @Override
    public boolean checkOTP() throws Exception {
        Scanner sc = new Scanner(System.in);
        int mdp = sc.nextInt();
        if (mdp == realOTP) {
            System.out.println("Authentification réussie");
            return true;
        }
        System.out.println("Mauvais OTP");
        return false;
    }

    @Override
    public String getOTP() {
        return String.valueOf(this.realOTP);
    }

    public void checkValue(int value) throws RankException {
        if (value < 0 || value > 3) {
            throw new RankException("La valeur de satisfaction doit être entre 0 et 3.");
        }
    }

    public Map<Integer, Integer> getVotes() {
        return votes;
    }

    public void clearVotes() {
        this.votes.clear();
    }

    public void startNewVote(int voterID, StubServer s) throws RemoteException {
        if (!s.isVotingSessionActive()) {
            System.out.println("Vous ne pouvez pas voter, la session a expiré.");
        } else {
            if (!voteInProgress) {
                voteInProgress = true;
                currentVoterID = voterID;
            } else {
                System.out.println("Un vote est déjà en cours. Veuillez demander un nouvel OTP.");
                voteInProgress = false;
            }
        }
    }

    private void endVoteAndChooseAction(String voterID, List<Candidate> candidates, int studentId, StubServer s) throws RankException, RemoteException {
        System.out.println("Voulez-vous quitter (tapez 'quitter') ou refaire un autre vote (tapez 'refaire') ?");
        Scanner scanner = new Scanner(System.in);
        String choix = scanner.nextLine();

        if (choix.equalsIgnoreCase("quitter")) {
            addVoteLog(new Date(), studentId, voterID);
            System.out.println("Merci d'avoir voté. Au revoir !");
            System.exit(0);
        } else if (choix.equalsIgnoreCase("refaire")) {
            int newVoterID = new Random().nextInt(900000) + 10000;
            System.out.println("Nouvel identifiant unique pour le nouveau vote : " + newVoterID);
            System.out.println("Veuillez entrer le nouvel OTP pour le nouveau vote : ");
            try {
                if (!checkNewOTP(newVoterID)) {
                    System.out.println("Vous ne pouvez pas voter en raison d'une authentification incorrecte.");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Une erreur s'est produite lors de la vérification de l'OTP.");
                return;
            }
            startNewVote(newVoterID, s);
            vote(String.valueOf(newVoterID), candidates, studentId, s);
        } else {
            System.out.println("Choix invalide. Le vote se termine par défaut.");
        }
    }

    private boolean checkNewOTP(int newVoterID) throws Exception {
        Scanner sc = new Scanner(System.in);
        int enteredOTP = sc.nextInt();
        if (enteredOTP == newVoterID) {
            System.out.println("Authentification réussie avec le nouvel OTP.");
            return true;
        }
        System.out.println("Mauvais OTP pour le nouveau vote.");
        return false;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Integer candidateRank : votes.keySet()) {
            str.append("Rank : ").append(candidateRank).append(" vote given : ").append(votes.get(candidateRank)).append("\n");
        }
        return str.toString();
    }
}
