package byog.Core;

public class GameTest {
    public static void TestPlayWithInputString() {
        Game game = new Game();
        //game.playWithInputString("N12345S");

        game.playWithInputString("N9223372036854775807S");
    }

    public static void main(String[] args) {
        TestPlayWithInputString();
    }
}
