import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardManager {

    public static int getHighScoreForUser(String username,String filename) {
        int highScore = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String user = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    if (user.equals(username) && score > highScore) {
                        highScore = score;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highScore;
    }
    
    public static void updateHighScore(String username, int newHighScore, String filename) {
        List<String> lines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" "); // Assuming file format is username,score
                if (parts.length == 2) {
                    String user = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    if (user.equals(username)) {
                        // Update the high score if the new score is higher
                        if (newHighScore > score) {
                            line = username + " " + newHighScore;
                            updated = true;
                        }
                    }
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!updated) {
            // If the user was not found or no update was necessary, add or update the score
            lines.add(username + " " + newHighScore);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}