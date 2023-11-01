import global.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class StubS extends UnicastRemoteObject implements StubServer {
    private Map<Integer, Integer> candidateVotes = new HashMap<>();
    public ArrayList<Candidate> candidates;
    public HashMap<Integer, String> users;
    private List<VoteInformation> voteInfos;
    private boolean isVotingSessionActive;

    // Constantes pour les messages d'erreur
    private static final String SESSION_NOT_ACTIVE_ERROR = "La session de vote n'est pas active. Vous ne pouvez pas effectuer cette action actuellement.";

    public StubS(ArrayList<Candidate> candidates, HashMap<Integer, String> users) throws RemoteException {
        this.candidates = candidates;
        this.users = users;
        this.voteInfos = new ArrayList<>();
        this.isVotingSessionActive = false;
    }

    @Override
    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }


    @Override
    public Vote getVoteMaterial(int snumber, ICandidat stub_votant) throws Exception {
        if (!isVotingSessionActive) {
            throw new Exception(SESSION_NOT_ACTIVE_ERROR);
        }

        String password = stub_votant.getPassword();
        String passwordReal = users.get(snumber);

        if (passwordReal == null) {
            throw new Exception("Le numéro d'étudiant n'existe pas.");
        }

        if (!passwordReal.equals(password)) {
            throw new Exception("Mauvais mot de passe.");
        }

        int passw = new Random().nextInt(900000) + 100000;

        // Passer l'état de la session de vote lors de la création de l'instance de Vote
        return new Vote(passw, isVotingSessionActive);
    }
    @Override
    public void startVotingSession() throws RemoteException {
        setVoteSessionActive(true);

        // Démarrez un thread pour écouter les commandes de l'utilisateur
        Thread commandThread = new Thread(this::listenForCommands);

        commandThread.start();
    }
    @Override
    public boolean isVotingSessionActive() throws RemoteException {
        return isVotingSessionActive;
    }

    public void listenForCommands()  {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("fin du vote")) {
                endVotingSession();
                System.out.println("Session de vote terminée.");
            }

        }
    }

    public void setVoteSessionActive(boolean isActive) {
        isVotingSessionActive = isActive;
    }

    @Override
    public void endVotingSession() {
        setVoteSessionActive(false);
        System.out.println("Session de vote terminée.");
    }

    @Override
    public synchronized void submitVotes(Map<Integer, Integer> votes, List<VoteInformation> voteInfos) {
        if (!isVotingSessionActive) {
            System.out.println("La session de vote est terminée. Les votes ne sont pas acceptés.");
            return;
        }
        for (Map.Entry<Integer, Integer> entry : votes.entrySet()) {
            int candidateRank = entry.getKey();
            int voteValue = entry.getValue();

            // Update the vote count for the candidate
            int currentVotes = candidateVotes.getOrDefault(candidateRank, 0);
            candidateVotes.put(candidateRank, currentVotes + voteValue);
        }

        // Store the vote information
        this.voteInfos.addAll(voteInfos);
    }

    @Override
    public synchronized Map<Integer, Integer> calculateElectionResults(List<Candidate> candidates) {
        Map<Integer, Integer> results = new HashMap<>();

        // Initialize scores for all candidates to zero
        for (Candidate candidate : candidates) {
            results.put(candidate.getRank(), 0);
        }

        // Update scores based on received votes
        for (Map.Entry<Integer, Integer> entry : candidateVotes.entrySet()) {
            int candidateRank = entry.getKey();
            int voteCount = entry.getValue();

            // Update the candidate's score
            results.put(candidateRank, results.getOrDefault(candidateRank, 0) + voteCount);
        }

        return results;
    }

    @Override
    public List<Candidate> findWinner(Map<Integer, Integer> votes, List<Candidate> candidates) {
        Map<Integer, Integer> scores = calculateElectionResults(candidates);
        List<Candidate> winners = new ArrayList<>();
        int highestScore = Integer.MIN_VALUE;

        // Find the highest score
        for (Integer score : scores.values()) {
            if (score > highestScore) {
                highestScore = score;
            }
        }

        for (Candidate candidate : candidates) {
            int rank = candidate.getRank();
            if (scores.get(rank) == highestScore) {
                winners.add(candidate);
            }
        }

        return winners;
    }
}
