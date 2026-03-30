package Q6;

import java.util.HashMap;

public class RickAstley {
    public static void main(String[] args) {

        HashMap<String, Integer> text = new HashMap<>();

        String song = " We're no strangers to love " +
                      " You know the rules and so do I " +
                      " A full commitment's what I'm thinking of " +
                      " You wouldn't get this from any other guy " +
                      " I just wanna tell you how I'm feeling " +
                      " Gotta make you understand " +
                      " Never gonna give you up " +
                      " Never gonna let you down " +
                      " Never gonna run around and desert you " +
                      " Never gonna make you cry " +
                      " Never gonna say goodbye " +
                      " Never gonna tell a lie and hurt you " +
                      " We've know each other for so long " +
                      " Your heart's been aching " +
                      " But you're too shy to say it " +
                      " Inside we both know what's been going on " +
                      " We know the game and we're gonna play it " +
                      " And if you ask me how I'm feeling " +
                      " Don't tell me you're too blind to see " +
                      " Never gonna give you up " +
                      " Never gonna let you down " +
                      " Never gonna run around and desert you " +
                      " Never gonna make you cry " +
                      " Never gonna say goodbye " +
                      " Never gonna tell a lie and hurt you ";

        String[] words = song.toLowerCase().split(" ");

        for (String word: words) {
            Integer count = text.get(word);

            if (count == null) {
                text.put(word,1);
            } else {
                text.put(word, count+1);
            }
        }
//
        System.out.println(text);
    }
}